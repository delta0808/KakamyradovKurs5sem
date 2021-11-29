package data_base;

import model.*;

import java.util.ArrayList;

public class QueryerToDB {
    private ConnectorToDB connector =  ConnectorToDB.getInstance();
    public String lastError = "";

    public ArrayList<TransactionModel> getAllTransactions(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<TransactionModel>  transactions = new ArrayList<>();

            models = connector.select(TransactionModel.getViewNameStatic(), where, orderBy, param, isInt);
            for (int i = 0; i < models.size(); i++)
                transactions.add((TransactionModel) models.get(i));
            return transactions;
        } catch (Exception e){
            lastError = "Ошибка обработки операции: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }

    public ArrayList<UserModel> getAllUsers(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<UserModel>  users = new ArrayList<>();
            models = connector.select(UserModel.getViewNameStatic(), where, orderBy, param, isInt);

            for (int i = 0; i < models.size(); i++)
                users.add((UserModel) models.get(i));
            return users;
        } catch (Exception e){
            lastError = "Ошибка обработки операции: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }


    public ArrayList<FamilyAccountModel> getAllFamilyAccounts(String orderBy, String where, String param, String isInt){
        try {
            ArrayList<Model> models;
            ArrayList<FamilyAccountModel> familyAccounts = new ArrayList<>();
            models = connector.select(FamilyAccountModel.getViewNameStatic(), where, orderBy, param, isInt);
            for (int i = 0; i < models.size(); i++)
                familyAccounts.add((FamilyAccountModel) models.get(i));
            return familyAccounts;
        } catch (Exception e){
            lastError = "Ошибка обработки операции: \"" + e.getClass() + "\"" + connector.lastError;
            return null;
        }
    }

    public boolean delete(String table, int id) {
        lastError = "";
        try {
            ConnectorToDB connector =  ConnectorToDB.getInstance();
            if (connector.delete(table,id)) {
                return true;
            } else {
                throw new Exception(connector.lastError);
            }
        } catch (Exception e){
            lastError = "Ошибка обработки операции: \"" + e.getClass() + "\"" + connector.lastError;
            return false;
        }
    }

    public boolean insert(Model model, String table) {
        lastError = "";
        try {
            FieldsTransformation transformator = new FieldsTransformation();
            String values = prepareQueryComponents(model);
            String SIMPLE_FIELD = prepareQuerySIMPLE_FIELD(model);
            String[] valueByQuery = transformator.newData(model);

            if (connector.insert(table, SIMPLE_FIELD, values, valueByQuery, isStrCurrent(model))) {
                return true;
            } else {
                lastError = connector.lastError;
                return false;
            }
        } catch (Exception  e){
            e.printStackTrace();
            if(e.getMessage().length() > 150) lastError = "Ошибка обработки операции: \"" + e.getClass() + "\"";
            else lastError = "Ошибка обработки операции: \"" + e.getMessage() + "\"";
            return false;
        }
    }

    private String prepareQueryComponents(Model model){
        if(model instanceof TransactionModel)   return "?,?,?,?,?";
        if(model instanceof UserModel)      return "?,?,?,?,?,?,?,?";
        if(model instanceof FamilyAccountModel) return "?,?,?,?";
        return null;
    }

    private String prepareQuerySIMPLE_FIELD(Model model){
        if(model instanceof TransactionModel)   return prepareSIMPLE_FIELD(TransactionModel.SIMPLE_FIELD);
        if(model instanceof UserModel)      return prepareSIMPLE_FIELD(UserModel.SIMPLE_FIELD);
        if(model instanceof FamilyAccountModel) return prepareSIMPLE_FIELD(FamilyAccountModel.SIMPLE_FIELD);
        return null;
    }

    private boolean[] isStrCurrent(Model model){
        if(model instanceof TransactionModel)   return TransactionModel.SIMPLE_FIELD_TYPE;
        if(model instanceof UserModel)      return UserModel.SIMPLE_FIELD_TYPE;
        if(model instanceof FamilyAccountModel) return FamilyAccountModel.SIMPLE_FIELD_TYPE;
        return null;
    }

    private String prepareSIMPLE_FIELD(String SIMPLE_FIELD[]){
        String str = "";
        for (int i = 0; i < SIMPLE_FIELD.length; i++) {
            str += SIMPLE_FIELD[i];
            if(i != SIMPLE_FIELD.length - 1) str +=",";
        }
        return str;
    }


    public boolean update(Model model, String table, int id) {
        lastError = "";
        try {
            FieldsTransformation transformator = new FieldsTransformation();
            String[] valueByQuery = transformator.newData(model);

            String values = prepareQueryValue(model);
            if (connector.update(table, id, values, valueByQuery, isStrCurrent(model))) {
                return true;
            } else {
                lastError = connector.lastError;
                return false;
            }
        } catch (Exception e){
            if(e.getMessage().length() > 150) lastError = "Ошибка обработки операции: \"" + e.getClass() + "\"";
            else lastError = "Ошибка обработки операции: \"" + e.getMessage() + "\"";
            return false;
        }
    }

    private String prepareQueryValue(Model model){

        if(model instanceof UserModel){
            return prepareUserValue();
        }
        if(model instanceof FamilyAccountModel){
            return prepareFamilyAccountValue();
        }
        return "";
    }

    private String prepareUserValue(){
        String str;
        str = "firstName = ?, lastName = ?, login = ?, password = ?, phoneNumber = ?, isBlocked = ?, "
            + "idFamilyAccount = ?, idAuthority = ?";
        return str;
    }

    private String prepareFamilyAccountValue(){
        String str;
        str = "sum = ?, familyLogin = ?, familyPassword = ?, idCurrency = ?";
        return str;
    }

}
