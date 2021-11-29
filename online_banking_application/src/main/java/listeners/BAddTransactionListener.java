package listeners;

import connection.DataPreparer;
import dialog_construction.TransactionAdapter;
import dialogs.CDialog;
import dialogs.CDialogNotFrame;
import dialog_construction.TransactionTableDialog;
import model.*;
import validation.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BAddTransactionListener extends BAbstractEditListener {
    private CDialogNotFrame dialogAdd;
    private String category;
    private UserModel currentUser;

    public BAddTransactionListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models,
                                   TransactionTableDialog parental, String category, UserModel currentUser){
        super(frameDialog, currentClass, models, new TransactionAdapter(parental));
        this.category = category;
        this.currentUser = currentUser;
    }

    public void actionPerformed(ActionEvent e) {
        prepareAddData();
        addEventAdd();
        dialogAdd.setVisible(true);
    }

    private void prepareAddData( ){
        dialogAdd = new CDialogNotFrame("Добавление транзакции (" + category + ")", 300, 370, true);

        dialogAdd.addLabel(200, 30, 50, 10, "Операция:");
        dialogAdd.addComboBox(false, 200, 30, 50, 40);
        dialogAdd.addLabel(200, 30, 50, 90, "Сумма:");
        dialogAdd.addTextField( 200, 30, 50, 120);
        dialogAdd.addLabel(200, 30, 50, 170, "Коментарий:");
        dialogAdd.addTextField(200, 30, 50, 200);
        addComboBoxItems(dialogAdd.comboBoxes, 0, DataPreparer.getTypes());

        dialogAdd.addButton(200, 30,50, 280, "Добавить " + category);
    }

    private void addComboBoxItems(ArrayList<JComboBox> boxes, int id, ArrayList<String> values){
        System.out.println(category);
        for (int j = 0; j < values.size(); j++) {
            if(values.get(j).lastIndexOf("Поступление") != -1 && category.equals("Доход")) {
                boxes.get(boxes.size() - 1).addItem(values.get(j));
            }
            if(values.get(j).lastIndexOf("Оплата") != -1 && category.equals("Расход")) {
                boxes.get(boxes.size() - 1).addItem(values.get(j));
            }
        }
    }

    private void addEventAdd(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(changeAndCheckOk()){
                    addData(generateNewTransaction());
                    parental.update();
                    dialogAdd.dispose();
                }
            }
        };
        dialogAdd.buttons.get(0).addActionListener(actionListener);
    }

    private void addData(Model model){
        String error;
        if (!"".equals(error = getQueryInsert(model))) {
            JOptionPane.showMessageDialog(dialogAdd, "Ошибка добавления: " + error, "System Error", JOptionPane.PLAIN_MESSAGE);
        }
    }



    private Model generateNewTransaction() {
        TransactionModel model = new TransactionModel(
                0,
                dialogAdd.textFields.get(1).getText(),
                Double.valueOf(dialogAdd.textFields.get(0).getText()),
                prepareDate(),
                currentUser.getId(),
                Integer.valueOf(((String)dialogAdd.comboBoxes.get(0).getSelectedItem()).split("\\) ")[0])
        );
        return model;
    }

    private String prepareDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    private boolean changeAndCheckOk(){
        return checkAddOk(TransactionModel.SIMPLE_FIELD_TRANSLATION, TransactionModel.SIMPLE_FIELD_TYPE);
    }


    private boolean checkAddOk(String columns[], boolean isStrColumns[]){
        String message = "";
        if ("".equals(dialogAdd.textFields.get(0).getText().trim()) ||
                !Validator.isDouble(dialogAdd.textFields.get(0).getText())) {
            message += " Сумма пуста, либо не число;\r\n";
        }
        if(((String)dialogAdd.comboBoxes.get(0).getSelectedItem()).lastIndexOf("Поступление") != -1
                && Validator.isDouble(dialogAdd.textFields.get(0).getText())){
            if(Double.valueOf(dialogAdd.textFields.get(0).getText()) > currentUser.getSum()) {
                message += " Расход выше бюджета;\r\n";
            }
        }
        if(!message.equals("")) JOptionPane.showMessageDialog(dialogAdd, "Ошибки:\r\n" + message, "System Error", JOptionPane.PLAIN_MESSAGE);
        return message.equals("") ? true : false;
    }
}
