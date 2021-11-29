package dialog_construction;

import connection.DataPreparer;
import dialogs.CDialog;
import dialogs_start.MainJobPreparer;
import listeners.*;
import model.Model;
import model.TransactionModel;
import model.UserModel;
import table_models.TransactionTableModel;
import table_models.UserTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.ArrayList;

// TODO Паттерн адаптер: Класс Adaptee, которые адаптируется
// TODO Паттерн строитель: Класс Продукта, который строится
public class UserTableDialog implements ITableDialog {
    private CDialog frameDialog;
    private CDialog currentDialog;
    private CDialog registryDialog;
    private UserModel currentUser;
    private ArrayList<Model> models = new ArrayList<>();

    public UserTableDialog(CDialog frameDialog, UserModel currentUser){
        this.frameDialog = frameDialog;
        this.currentUser = currentUser;
    }

    public void create(){
        currentDialog = new CDialog("Пользователи аккаунта компании", 800, 640);
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
                frameDialog.setVisible(true);
            }
        };
        currentDialog.addWindowListener(windowListener);
        currentDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void prepareDialog(){
        if((models = UserTableDialog.getData(currentUser)) == null) {
            JOptionPane.showMessageDialog(currentDialog, "Ошибка получения данных!", "System Error",JOptionPane.PLAIN_MESSAGE);
            currentDialog.dispose();
            return;
        }
        currentDialog.addTable(750, 200, 25, 20, UserTableDialog.getModel(models));

        currentDialog.addLabel(300, 30,250, 220, "Номер для выбора:");
        currentDialog.addTextField(300, 30,250, 250);
        currentDialog.addButton(300, 30,250, 300, "Удалить");
        currentDialog.addButton(300, 30,250, 350, "Забанить\\Разбанить");
        currentDialog.addButton(300, 30,250, 400, "Сменить пользователю пароль");
        currentDialog.addButton(300, 30,250, 450, "Сменить пароль компании");
        currentDialog.addButton(300, 30,250, 500, "Сменить валюту компании");
        currentDialog.addButton(300, 30,250, 550, "Создать пользователя");
    }

    public static ArrayList<Model> getData(UserModel user){
        String whereString = " `idFamilyAccount` =  ? @@@" + user.getIdFamilyAccount() + "@@@false";

        return DataPreparer.getUsers("", whereString);
    }

    private static AbstractTableModel getModel(ArrayList<Model> model){
         return new UserTableModel(model, true);
    }

    protected void setEventsForButtons(){
        setEventDelete();
        setEventBlock();
        changeCurrency();
        setEventPassword();
        setEventFamilyPassword();
        setEventCreate();
    }

    private void setEventDelete(){
        ActionListener actionListener = new BDelListener(currentDialog, UserModel.class, models, new UserAdapter(this));
        currentDialog.buttons.get(0).addActionListener(actionListener);
    }

    private void setEventBlock(){
        ActionListener actionListener = new BBlockListener(currentDialog, UserModel.class, models, new UserAdapter(this));
        currentDialog.buttons.get(1).addActionListener(actionListener);
    }

    private void setEventPassword(){
        ActionListener actionListener = new BChangeFamilyListener(currentDialog, TransactionModel.class, models,
                new UserAdapter(this));
        currentDialog.buttons.get(2).addActionListener(actionListener);
    }

    private void setEventFamilyPassword(){
        ActionListener actionListener = new BChangeFamilyPasswordListener(currentDialog, TransactionModel.class, models,
                new UserAdapter(this), currentUser);
        currentDialog.buttons.get(3).addActionListener(actionListener);
    }

    private void changeCurrency(){
        ActionListener actionListener = new BChangeCurrencyListener(currentDialog, UserModel.class, models,
                (ITableDialog)this, currentUser);
        currentDialog.buttons.get(4).addActionListener(actionListener);
    }

    private void setEventCreate(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registryDialog = new CDialog("Регистрация пользователя!", 400, 620);
                registryDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                prepareRegistrationDialog();
                setEventsForRegistrationDialogAuth();
                MainJobPreparer.setEventCloseClear(registryDialog, currentDialog);
                registryDialog.setVisible(true);
            }
        };
        currentDialog.buttons.get(5).addActionListener(actionListener);
    }

    private void setEventsForRegistrationDialogAuth(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(MainJobPreparer.isCorrectRegistry(registryDialog)) {
                    if((currentUser = MainJobPreparer.getRegistryDataFromServer(registryDialog, currentUser.getIdFamilyAccount())) != null){
                        registryDialog.setVisible(false);
                        updateData();
                    } else {
                        JOptionPane.showMessageDialog(registryDialog, "Неверные данные", "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                MainJobPreparer.clearRegistryDialogData(registryDialog);
            }
        };
        registryDialog.buttons.get(0).addActionListener(actionListener);
    }

    private void prepareRegistrationDialog(){
        registryDialog.addLabel(300, 30,50, 0, "Регистрация пользователя!");
        registryDialog.addLabel(300, 30,50, 420, "Тип:");
        registryDialog.addComboBox(false,300, 30,50, 450);
        addComboBoxItems(registryDialog.comboBoxes, 0, DataPreparer.getAuthority());
        registryDialog.addLabel(300, 30,50, 180, "Логин:");
        registryDialog.addTextField(300, 30,50, 210);
        registryDialog.addLabel(300, 30,50, 260, "Пароль:");
        registryDialog.addTextField(300, 30,50, 290);
        registryDialog.addLabel(300, 30,50, 20, "Имя: ");
        registryDialog.addTextField(300, 30,50, 50);
        registryDialog.addLabel(300, 30,50, 100, "Фамилия:");
        registryDialog.addTextField(300, 30,50, 130);
        registryDialog.addLabel(300, 30,50, 340, "Номер:");
        registryDialog.addTextField(300, 30,50, 370);

        registryDialog.addButton(300, 30, 50, 530, "Зарегистрировать");
    }

    private void addComboBoxItems(ArrayList<JComboBox> boxes, int id, ArrayList<String> values){
        for (int j = 0; j < values.size(); j++) {
            boxes.get(boxes.size()-1).addItem(values.get(j));
        }
    }

    public ArrayList<Model> updateData(){
        if((models = UserTableDialog.getData(currentUser)) == null) {
            JOptionPane.showMessageDialog(currentDialog, "Ошибка получения данных!", "System Error",JOptionPane.PLAIN_MESSAGE);
            currentDialog.dispose();
        }
        currentDialog.tables.get(0).setModel(getModel(models));
        return models;
    }

    @Override
    public ArrayList<Model> update() {
        return this.updateData();
    }
}
