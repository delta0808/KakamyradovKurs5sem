package connection;

import com.google.gson.reflect.TypeToken;
import data_base.ConnectorToDB;
import data_base.QueryerToDB;
import model.*;
import serialization.Serrializator;
import validation.Validator;

import java.util.ArrayList;

public class OrderPerformer {
    private static QueryerToDB queryer = new QueryerToDB();

    public static String doAuthorization(String data){
        String answer;
        String[] searchData = data.split("___");
        ArrayList<UserModel> users = queryer.getAllUsers("", "`login` = ?", searchData[0], "false");
        return (answer = userSeaech(users, searchData)) != "" ? answer : "";

    }

    public static String doAuthorizationFamily(String data){
        String answer;
        String[] searchData = data.split("___");
        ArrayList<FamilyAccountModel> families = queryer.getAllFamilyAccounts("", "`familyLogin` = ?", searchData[0], "false");
        return (answer = familySeaech(families, searchData)) != "" ? answer : "";

    }

    private static String userSeaech(ArrayList<UserModel> users, String[] userData){
        for (int i = 0; i < users.size(); i++) {
            UserModel iUser = users.get(i);
            if(userData[0].equals(iUser.getLogin()) && userData[1].equals(iUser.getPassword())){
                return Serrializator.ObjectToJSON(iUser);
            }
        }
        return "";
    }

    private static String familySeaech(ArrayList<FamilyAccountModel> families, String[] familyData){
        for (int i = 0; i < families.size(); i++) {
            FamilyAccountModel iUser = families.get(i);
            if(familyData[0].equals(iUser.getFamilyLogin()) && familyData[1].equals(iUser.getFamilyPassword())){
                return Serrializator.ObjectToJSON(iUser);
            }
        }
        return "";
    }


    public static String getAll(String orderByAndWhere, Class<? extends Model> currentClass){
        String params[] = new String[]{"", ""};
        if(!(orderByAndWhere.equals("")))
            params = orderByAndWhere.split("@@@");

        if(params.length <= 2)
            params = new String[]{params[0], params[1], "", ""};
        return getAnswerFromBD(params, currentClass);
    }

    public static String getAuthority( ){
        ArrayList<String> values = ConnectorToDB.getInstance().select("authority");
        return Serrializator.ObjectsToJSON(values, true);
    }

    public static String getCurrency( ){
        ArrayList<String> values = ConnectorToDB.getInstance().select("currency");
        return Serrializator.ObjectsToJSON(values, true);
    }

    public static String getType( ){
        ArrayList<String> values = ConnectorToDB.getInstance().select("type");
        return Serrializator.ObjectsToJSON(values, true);
    }

    private static String getAnswerFromBD(String params[], Class<? extends Model> currentClass){
        switch (currentClass.getName()) {
            default:
            case "model.TransactionModel":      return Serrializator.ObjectsToJSON(queryer.getAllTransactions(params[0], params[1], params[2], params[3]));
            case "model.UserModel":         return Serrializator.ObjectsToJSON(queryer.getAllUsers(params[0], params[1], params[2], params[3]));
            case "model.FamilyAccountModel":    return Serrializator.ObjectsToJSON(queryer.getAllFamilyAccounts(params[0], params[1], params[2], params[3]));
        }
    }


    public static String delete(String data){
        String params[] = data.split("@@@");
        if(!Validator.isInt(params[1])) return "";
        boolean isOk = queryer.delete(params[0], Integer.valueOf(params[1]));
        return isOk ? "@ok@" : queryer.lastError;
    }

    public static String insert(String data, String table, Class<? extends Model> modelType){
        boolean isOk = queryer.insert(Serrializator.JSONToObject(data, modelType), table);
        return isOk ? "@ok@" : queryer.lastError;
    }

    public static String update(String data, String table, Class<? extends Model> modelType){
        String params[] = data.split("@@@");
        if(!Validator.isInt(params[1])) return "";
        boolean isOk = queryer.update(Serrializator.JSONToObject(params[0], modelType), table, Integer.valueOf(params[1]));// ? "ok" : "";
        return isOk ? "@ok@" : queryer.lastError;
    }

}
