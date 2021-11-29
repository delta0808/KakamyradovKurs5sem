package dialog_construction;

import dialogs.CDialog;
import model.Model;
import model.UserModel;

import java.util.ArrayList;


// TODO Паттерн адаптер: Класс Adapter, реализация метода update, который адаптирует updateData метод
public class UserAdapter implements ITableDialog {
    private UserTableDialog dialog;

    public UserAdapter(CDialog frameDialog, UserModel currentUser) {
        dialog = new UserTableDialog(frameDialog, currentUser);
    }

    public UserAdapter(UserTableDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public ArrayList<Model> update() {
        return dialog.updateData();
    }
}
