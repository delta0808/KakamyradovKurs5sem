package listeners;

import connection.DataPreparer;
import dialog_construction.ITableDialog;
import dialogs.CDialog;
import model.Model;
import model.UserModel;
import table_models.UserTableModel;
import validation.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// TODO Паттерн адаптер: Класс Client, запускает работу через адаптер, метод update работает
public class BBlockListener extends BAbstractEditListener {
    private UserModel currentUser;
    public BBlockListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models,
                          ITableDialog parental){
        super(frameDialog, currentClass, models, parental);
    }

    public void actionPerformed(ActionEvent e) {
        if(!isIdCorrect()) return;
        int id = Integer.valueOf(frameDialog.textFields.get(0).getText());

        if(!"".equals(getQueryBlock(id))) {
            JOptionPane.showMessageDialog(frameDialog, "Ошибка блокировки!", "System Error", JOptionPane.PLAIN_MESSAGE);
        }
        parental.update();
    }

    protected String getQueryBlock(int id){
        UserModel user = null;
        for (int i = 0; i < models.size(); i++) {
            if (id == models.get(i).getId()) {
                user = (UserModel) models.get(i);
            }
        }
        if(user.getIsBlocked().equals("Активен")) {
            user.setIsBlocked("Заблокирован");
        } else {
            user.setIsBlocked("Активен");
        }
        String answer = DataPreparer.update(user, Validator.getCurrentTable(currentClass.getName()), id);
        return answer.equals("@ok@") ? "" : answer;
    }
}
