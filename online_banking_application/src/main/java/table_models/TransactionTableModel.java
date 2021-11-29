package table_models;

import model.Model;
import model.TransactionModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TransactionTableModel extends AbstractTableModel {
    private ArrayList<TransactionModel> models;

    public ArrayList<TransactionModel> getTransactions(){
        return models;
    }

    public TransactionTableModel(ArrayList<TransactionModel> transactions) {
        super();
        this.models = transactions;
    }

    public TransactionTableModel(ArrayList<Model> models, boolean isAbstract) {
        super();
        this.models = Converter.modelToTransaction(models);
    }

    @Override
    public int getRowCount() {
        return models.size();
    }

    @Override
    public int getColumnCount() {
        return TransactionModel.SIMPLE_FIELD_TRANSLATION_SELECT.length + 1;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return models.get(r).getId();
            case 1: return models.get(r).getComment();
            case 2: return models.get(r).getSum();
            case 3: return models.get(r).getDate();
            case 4: return models.get(r).getIdUser();
            case 5: return models.get(r).getIdType();
            case 6: return models.get(r).getFirstName();
            case 7: return models.get(r).getLastName();
            case 8: return models.get(r).getIdFamilyAccount();
            case 9: return models.get(r).getCategory();
            case 10: return models.get(r).getName();
            case 11: return models.get(r).getAuthority();
        }
    }

    @Override
    public String getColumnName(int c) {
        return c == 0 ? "ИД" : TransactionModel.SIMPLE_FIELD_TRANSLATION_SELECT[c-1];
    }
}
