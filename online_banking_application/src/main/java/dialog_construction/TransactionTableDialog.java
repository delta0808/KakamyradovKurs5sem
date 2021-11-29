package dialog_construction;

import connection.DataPreparer;
import dialogs.CDialog;
import listeners.*;
import model.*;
import table_models.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.ArrayList;

// TODO Паттерн адаптер: Класс Adaptee, которые адаптируется
// TODO Паттерн строитель: Класс Продукта, который строится
public class TransactionTableDialog {
    private CDialog frameDialog;
    private CDialog currentDialog;
    private UserModel currentUser;
    private String type;
    private ArrayList<Model> models = new ArrayList<>();

    public TransactionTableDialog(CDialog frameDialog, String type, UserModel currentUser){
        this.frameDialog = frameDialog;
        this.type = type;
        this.currentUser = currentUser;
    }



    public void create(){
        currentDialog = new CDialog("Транзакции типа \"" + (type.equals("") ? "Компании" : type) + "\"", 800, 560);
        addWindowListener();
        prepareDialog();
        setEventsForButtons();
        addMouseListener();
        currentDialog.setVisible(true);
    }

    private void addMouseListener(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int rowId = currentDialog.tables.get(0).getSelectedRow();
                int id = (int)(currentDialog.tables.get(0).getModel()).getValueAt(rowId, 0);
                currentDialog.textFields.get(0).setText(id + "");
            }
        };
        currentDialog.tables.get(0).addMouseListener(mouseAdapter);
    }

    private void addWindowListener(){
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {
                frameDialog.setVisible(false);
            }
            public void windowClosing(WindowEvent event) {
                currentUser = DataPreparer.checkAuthorization(currentUser.getLogin(), currentUser.getPassword());
                frameDialog.labels.get(0).setText(AbstractFrameDialogCreator.updateStatus(currentUser));
                frameDialog.setVisible(true);
            }
        };

        currentDialog.addWindowListener(windowListener);
        currentDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void prepareDialog(){
        if((models = TransactionTableDialog.getData(type, currentUser)) == null) {
            JOptionPane.showMessageDialog(currentDialog, "Ошибка получения данных!", "System Error",JOptionPane.PLAIN_MESSAGE);
            currentDialog.dispose();
            return;
        }
        currentDialog.addTable(750, 200, 25, 20, TransactionTableDialog.getModel(models));

        currentDialog.addButton(300, 30,250, 240, "Удалить");
        currentDialog.addLabel(300, 30,250, 290, "Номер для удаления:");
        currentDialog.addTextField(300, 30,250, 320);
        currentDialog.addButton(300, 30,250, 370, "Добавить транзакцию рахода");
        currentDialog.addButton(300, 30,250, 420, "Добавить транзакцию дохода");
        currentDialog.addButton(300, 30,250, 470, "Статистика");
    }

    public static ArrayList<Model> getData(String type, UserModel user){
        String whereString = " `idFamilyAccount` =  ? @@@" + user.getIdFamilyAccount() + "@@@false";

        return filtrateDataByAuthority(DataPreparer.getTransactions("", whereString), type);
    }

    private static ArrayList<Model> filtrateDataByAuthority(ArrayList<Model> models, String type){
        for (int i = 0; i < models.size(); i++) {
            if(type.equals("")) break;
            if (!((TransactionModel)models.get(i)).getAuthority().equals(type)){
                models.remove(i);
                i--;
            }
        }
        return models;
    }

    private static AbstractTableModel getModel(ArrayList<Model> model){
         return new TransactionTableModel(model, true);
    }

    protected void setEventsForButtons(){
        setEventDelete();
        setEventAppendIncome();
        setEventAppendOutgo();
        setEventStat();
    }

    private void setEventDelete(){
        ActionListener actionListener = new BDelListener(currentDialog, TransactionModel.class, models, new TransactionAdapter(this));
        currentDialog.buttons.get(0).addActionListener(actionListener);
    }

    private void setEventAppendIncome(){
        ActionListener actionListener = new BAddTransactionListener(currentDialog, TransactionModel.class, models,
                this, "Транзакция расхода", currentUser);
        currentDialog.buttons.get(1).addActionListener(actionListener);
    }

    private void setEventAppendOutgo(){
        ActionListener actionListener = new BAddTransactionListener(currentDialog, TransactionModel.class, models,
                this, "Транзакция дохода", currentUser);
        currentDialog.buttons.get(2).addActionListener(actionListener);
    }

    private void setEventStat(){
        BShowInfoListener actionListener = new BShowInfoListener(currentUser);
        currentDialog.buttons.get(3).addActionListener(actionListener);
    }

    public ArrayList<Model> updateData(){
        if((models = TransactionTableDialog.getData(type, currentUser)) == null) {
            JOptionPane.showMessageDialog(currentDialog, "Ошибка получения данных!", "System Error",JOptionPane.PLAIN_MESSAGE);
            currentDialog.dispose();
        }
        currentDialog.tables.get(0).setModel(getModel(models));
        return models;
    }

}
