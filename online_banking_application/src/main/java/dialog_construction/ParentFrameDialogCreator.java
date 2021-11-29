package dialog_construction;

import connection.DataPreparer;
import model.Model;
import model.TransactionModel;
import model.UserModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// TODO Паттерн строитель. Класс Конкретный Строитель 2, который строит конкретный продукт
public class ParentFrameDialogCreator extends ChildFrameDialogCreator {

    public ParentFrameDialogCreator(UserModel currentUser) {
        super(currentUser);
        height = 480;
    }

    @Override
    public void prepareFrameDialog() {
        super.prepareFrameDialog();
        frameDialog.addButton(500, 30,150, 190, "Транзакции сотрудников");
        frameDialog.addButton(500, 30,150, 240, "Транзакции руководителей");
        frameDialog.addButton(500, 30,150, 290, "Очистить историю транзакций сотрудников");
        frameDialog.addButton(500, 30,150, 340, "Очистить историю транзакций руководителей");
        frameDialog.addButton(500, 30,150, 390, "Управление акаунтами");

    }

    @Override
    public void setEventsForButtons() {
        super.setEventsForButtons();
        setEventTransactionChild();
        setEventTransactionParent();
        setEventTransactionClearChild();
        setEventTransactionClearParent();
        setEventAccountManage();
    }

    protected void setEventTransactionChild(){
        setEventTransaction(3,"Сотрудник" );
    }

    private void setEventTransactionParent(){
        setEventTransaction(4,"Директор" );
    }

    private void setEventTransaction(int buttonId, String authority){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransactionTableDialog transactionDialog = new TransactionTableDialog(frameDialog, authority, currentUser);
                transactionDialog.create();
            }
        };
        frameDialog.buttons.get(buttonId).addActionListener(actionListener);
    }

    private void setEventTransactionClearChild(){
        setEventTransactionClear(5,"Сотрудник" );
    }

    private void setEventTransactionClearParent(){
        setEventTransactionClear(6,"Директор" );
    }

    private void setEventTransactionClear(int buttonId, String authority) {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String whereString = " `idFamilyAccount` =  ? @@@" + currentUser.getIdFamilyAccount() + "@@@false";
                ArrayList<Model> models = DataPreparer.getTransactions("", whereString);
                for (int i = 0; i < models.size(); i++) {
                    if (((TransactionModel)models.get(i)).getAuthority().equals(authority)){
                        DataPreparer.delete(models.get(i).getId(), "transaction");
                    }
                }
                JOptionPane.showMessageDialog(frameDialog, "Транзакции типа \"" + authority + "\" очищены",
                        "Готово!", JOptionPane.PLAIN_MESSAGE);
            }
        };
        frameDialog.buttons.get(buttonId).addActionListener(actionListener);
    }

    protected void setEventAccountManage(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserTableDialog categoryDialog = new UserTableDialog(frameDialog, currentUser);
                categoryDialog.create();
            }
        };
        frameDialog.buttons.get(7).addActionListener(actionListener);
    }

}
