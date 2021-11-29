package dialog_construction;


import listeners.BGetInfoListener;
import model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// TODO Паттерн строитель. Класс Конкретный Строитель 1, который строит конкретный продукт
public class ChildFrameDialogCreator extends AbstractFrameDialogCreator {


    public ChildFrameDialogCreator(UserModel currentUser) {
        super(currentUser);
        height = 230;
    }

    @Override
    public void prepareFrameDialog(){
        frameDialog.setSize(800, 480);
        frameDialog.setTitle(frameDialog.getTitle() + " " + currentUser.getAuthority() + "!");
        frameDialog.addLabel(500, 30,150, 10, "");
        frameDialog.addButton(500, 30,150, 40, "Выход");
        frameDialog.addButton(500, 30,150, 90, "Статистика бюджета");
        frameDialog.addButton(500, 30,150, 140, "Транзакции компании");
        frameDialog.labels.get(0).setText(updateStatus(currentUser));
    }


    @Override
    public void setEventsForButtons() {
        setEventExit();
        setEventChart();
        setEventTransaction();
    }


    protected ActionListener setEventExit(){
        ActionListener actionListener = super.setEventExit();
        frameDialog.buttons.get(0).addActionListener(actionListener);
        return actionListener;
    }

    protected void setEventChart(){
        BGetInfoListener actionListener = new BGetInfoListener(currentUser);
        frameDialog.buttons.get(1).addActionListener(actionListener);
    }

    protected void setEventTransaction(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransactionTableDialog transactionDialog = new TransactionTableDialog(frameDialog, "", currentUser);
                transactionDialog.create();
            }
        };
        frameDialog.buttons.get(2).addActionListener(actionListener);
    }

}
