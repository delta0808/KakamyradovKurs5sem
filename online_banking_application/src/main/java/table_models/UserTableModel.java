package table_models;

import model.Model;
import model.UserModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UserTableModel extends AbstractTableModel {
    private ArrayList<UserModel> models;

    public ArrayList<UserModel> getUsers() {
        return models;
    }

    public UserTableModel(ArrayList<UserModel> users) {
        super();
        this.models = users;
    }

    public UserTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.models = Converter.modelToUser(models);
    }

    @Override
    public int getRowCount() {
        return models.size();
    }

    public UserModel getRow(int r) {
        return models.get(r);
    }

    @Override
    public int getColumnCount() {
        return UserModel.SIMPLE_FIELD_TRANSLATION_SELECT.length + 1;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return models.get(r).getId();
            case 1: return models.get(r).getFirstName();
            case 2: return models.get(r).getLastName();
            case 3: return models.get(r).getLogin();
            case 4: return models.get(r).getPassword();
            case 5: return models.get(r).getPhoneNumber();
            case 6: return models.get(r).getIsBlocked();
            case 7: return models.get(r).getIdFamilyAccount();
            case 8: return models.get(r).getIdAuthority();
            case 9: return models.get(r).getSum();
            case 10: return models.get(r).getFamilyLogin();
            case 11: return models.get(r).getFamilyPassword();
            case 12: return models.get(r).getIdCurrency();
            case 13: return models.get(r).getCurrencyCode();
            case 14: return models.get(r).getCurrencyName();
            case 15: return models.get(r).getAuthority();
        }
    }

    @Override
    public String getColumnName(int c) {
        return c == 0 ? "ИД" : UserModel.SIMPLE_FIELD_TRANSLATION_SELECT[c-1];
    }

}
