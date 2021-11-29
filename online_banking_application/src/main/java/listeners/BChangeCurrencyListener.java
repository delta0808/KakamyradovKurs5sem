package listeners;

import connection.DataPreparer;
import dialog_construction.ITableDialog;
import dialog_construction.TransactionTableDialog;
import dialogs.CDialog;
import dialogs.CDialogNotFrame;
import model.FamilyAccountModel;
import model.Model;
import model.TransactionModel;
import model.UserModel;
import validation.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BChangeCurrencyListener extends BAbstractEditListener {
    private CDialogNotFrame dialogAdd;
    private UserModel currentUser;

    public BChangeCurrencyListener(CDialog frameDialog, Class<? extends Model> currentClass, ArrayList<Model> models,
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
        dialogAdd = new CDialogNotFrame("Смена валюты!", 300, 180, true);

        dialogAdd.addLabel(200, 30, 50, 10, "Валюта:");
        dialogAdd.addComboBox(false, 200, 30, 50, 40);
        addComboBoxItems(dialogAdd.comboBoxes, DataPreparer.getCurrency());
        dialogAdd.addButton(200, 30,50, 90, "Выбрать");
    }

    private void addComboBoxItems(ArrayList<JComboBox> boxes, ArrayList<String> values){
        for (int j = 0; j < values.size(); j++) {
            boxes.get(boxes.size() - 1).addItem(values.get(j));
        }
    }

    private void addEventSave(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addData(generateNewModel());
                parental.update();
                dialogAdd.dispose();
            }
        };
        dialogAdd.buttons.get(0).addActionListener(actionListener);
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
                currentUser.getFamilyPassword(),
                Integer.valueOf(((String)dialogAdd.comboBoxes.get(0).getSelectedItem()).split("\\) ")[0])
        );
        return model;
    }
}
