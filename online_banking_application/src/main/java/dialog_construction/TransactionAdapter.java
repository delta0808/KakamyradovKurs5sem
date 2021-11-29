package dialog_construction;

import dialogs.CDialog;
import model.Model;
import model.UserModel;

import java.util.ArrayList;

// TODO Паттерн адаптер: Класс Adapter, реализация метода update, который адаптирует updateData метод
public class TransactionAdapter implements ITableDialog {
    private TransactionTableDialog dialog;

    public TransactionAdapter(CDialog frameDialog, String type, UserModel currentUser) {
        dialog = new TransactionTableDialog(frameDialog, type, currentUser);
    }

    public TransactionAdapter(TransactionTableDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public ArrayList<Model> update() {
        return dialog.updateData();
    }
}
