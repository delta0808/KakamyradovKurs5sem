package listeners;

import dialog_construction.ITableDialog;
import dialogs.CDialog;
import dialog_construction.TransactionTableDialog;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// TODO Паттерн адаптер: Класс Client, запускает работу через адаптер, метод update работает
public class BDelListener extends BAbstractEditListener {
    public BDelListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models, ITableDialog parental){
        super(frameDialog, currentClass, models, parental);
    }

    public void actionPerformed(ActionEvent e) {
        if(!isIdCorrect()) return;
        int id = Integer.valueOf(frameDialog.textFields.get(0).getText());

        if(!"".equals(getQueryDelete(id))) {
            JOptionPane.showMessageDialog(frameDialog, "Ошибка удаления!", "System Error", JOptionPane.PLAIN_MESSAGE);
        }
        parental.update();
    }

}
