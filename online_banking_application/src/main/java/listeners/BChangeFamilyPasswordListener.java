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

public class BChangeFamilyPasswordListener extends BAbstractEditListener {
    private CDialogNotFrame dialogAdd;
    private UserModel currentUser;

    public BChangeFamilyPasswordListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models,
                                         ITableDialog parental, UserModel currentUser){
        super(frameDialog, currentClass, models, parental);
        this.currentUser = currentUser;
    }

    public void actionPerformed(ActionEvent e) {
        prepareAddData();
        addEventSave();
        dialogAdd.setVisible(true);
    }

    private void prepareAddData( ){
        dialogAdd = new CDialogNotFrame("Смена пароля компании!", 300, 340, true);

        dialogAdd.addLabel(200, 30, 50, 10, "Логин компании:");
        dialogAdd.addTextField( 200, 30, 50, 40);
        dialogAdd.addLabel(200, 30, 50, 90, "Пароль компании:");
        dialogAdd.addPasswordField( 200, 30, 50, 120);
        dialogAdd.addLabel(200, 30, 50, 170, "Новый Пароль компании:");
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
        if (!dialogAdd.textFields.get(0).getText().equals(currentUser.getFamilyLogin()) ||
                !dialogAdd.passwordFields.get(0).getText().equals(currentUser.getFamilyPassword())) {
            message += " Логин и\\или пароль не верны;\r\n";
        }
        if("".equals(passwordFamily) || passwordFamily.length() < 3){
                message += " Новый пароль пуст либо коротк;\r\n";

        }
        if(!message.equals("")) JOptionPane.showMessageDialog(dialogAdd, "Ошибки:\r\n" + message, "System Error", JOptionPane.PLAIN_MESSAGE);
        return message.equals("") ? true : false;
    }

    private void addData(Model model){
        String answer = DataPreparer.update(model, Validator.getCurrentTable(FamilyAccountModel.class.getName()), currentUser.getIdFamilyAccount());
        if (!answer.equals("@ok@")) {
            JOptionPane.showMessageDialog(dialogAdd, "Ошибка обновления!" + answer, "System Error", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private Model generateNewModel() {
        FamilyAccountModel model = new FamilyAccountModel(
                0,
                currentUser.getSum(),
                currentUser.getFamilyLogin(),
                dialogAdd.passwordFields.get(1).getText(),
                currentUser.getIdCurrency()
        );
        return model;
    }
}
