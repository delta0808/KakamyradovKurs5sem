package listeners;

import connection.DataPreparer;
import dialog_construction.ITableDialog;
import dialogs.CDialog;
import dialogs.CDialogNotFrame;
import model.FamilyAccountModel;
import model.Model;
import model.UserModel;
import validation.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BChangeFamilyListener extends BAbstractEditListener {
    private CDialogNotFrame dialogAdd;
    private UserModel currentUser;

    public BChangeFamilyListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models,
                                 ITableDialog parental){
        super(frameDialog, currentClass, models, parental);
    }

    public void actionPerformed(ActionEvent e) {
        if(!isIdCorrect()) return;
        int id = Integer.valueOf(frameDialog.textFields.get(0).getText());
        for (int i = 0; i < models.size(); i++) {
            if (id == models.get(i).getId()) {
                currentUser = (UserModel) models.get(i);
            }
        }

        prepareAddData();
        addEventSave();
        dialogAdd.setVisible(true);
    }

    private void prepareAddData( ){
        dialogAdd = new CDialogNotFrame("Смена пароля пользователя \"" + currentUser.getLogin() +  "\"!",
                300, 340, true);

        dialogAdd.addLabel(200, 30, 50, 10, "Логин:");
        dialogAdd.addTextField( 200, 30, 50, 40);
        dialogAdd.addLabel(200, 30, 50, 90, "Пароль:");
        dialogAdd.addPasswordField( 200, 30, 50, 120);
        dialogAdd.addLabel(200, 30, 50, 170, "Новый Пароль:");
        dialogAdd.addPasswordField( 200, 30, 50, 200);
        dialogAdd.addButton(200, 30,50, 250, "Сохранить");
    }


    private void addEventSave(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectData()) {
                    addData(generateNewModel());
                    parental.update();
                    dialogAdd.dispose();
                }

            }
        };
        dialogAdd.buttons.get(0).addActionListener(actionListener);
    }

    private boolean isCorrectData(){
        String message = "";
        String passwordFamily = dialogAdd.passwordFields.get(1).getText();

        if("".equals(passwordFamily) || passwordFamily.length() < 3){
                message += " Новый пароль пуст либо коротк;\r\n";

        }
        if (!dialogAdd.textFields.get(0).getText().equals(currentUser.getLogin()) ||
                !dialogAdd.passwordFields.get(0).getText().equals(currentUser.getPassword())) {
            message += " Логин и\\или пароль не верны;\r\n";
        }
        if(!message.equals("")) JOptionPane.showMessageDialog(dialogAdd, "Ошибки:\r\n" + message, "System Error", JOptionPane.PLAIN_MESSAGE);
        return message.equals("") ? true : false;
    }

    private void addData(Model model){
        String answer = DataPreparer.update(model, Validator.getCurrentTable(UserModel.class.getName()), currentUser.getId());
        if (!answer.equals("@ok@")) {
            JOptionPane.showMessageDialog(dialogAdd, "Ошибка обновления!" + answer, "System Error", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private Model generateNewModel() {
        UserModel model = new UserModel(
                0,
                currentUser.getFirstName(),
                currentUser.getLastName(),
                currentUser.getLogin(),
                dialogAdd.passwordFields.get(1).getText(),
                currentUser.getPhoneNumber(),
                currentUser.getIsBlocked(),
                currentUser.getIdFamilyAccount(),
                currentUser.getIdAuthority()
        );
        return model;
    }
}
