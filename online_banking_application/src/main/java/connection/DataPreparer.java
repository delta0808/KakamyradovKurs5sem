package connection;


import com.google.gson.reflect.TypeToken;
import model.*;
import serialization.Serrializator;

import java.util.ArrayList;


public class DataPreparer {
    private static ClientSocketConnector connector;

    public static String startConnection(String host, int port){
        connector = new ClientSocketConnector(host, port);
        connector.run();
        return connector.isHaveError ? connector.errorMessage : "";
    }

    public static UserModel checkAuthorization(String login, String password) {
        String userJSON = login + "___" + password, command = "authorization", answer;
        answer = connector.writeCommand(command, userJSON);
        return  !("".equals(answer)) ? (UserModel)Serrializator.JSONToObject(answer, UserModel.class) : null;
    }

    public static FamilyAccountModel checkFamilyAuthorization(String login, String password) {
        String userJSON = login + "___" + password, command = "authorization_family", answer;
        answer = connector.writeCommand(command, userJSON);
        return  !("".equals(answer)) ? (FamilyAccountModel)Serrializator.JSONToObject(answer, FamilyAccountModel.class) : null;
    }

    public static UserModel checkRegistry(UserModel userModel) {
        insert(userModel, "users");
        try {
            return (UserModel) getUsers("idUser desc", "").get(0);
        } catch (Exception ex){
            return new UserModel();
        }
    }

    public static FamilyAccountModel checkRegistryFamily(FamilyAccountModel userModel) {
        insert(userModel, "familyAccounts");
        try {
            return (FamilyAccountModel) getFamilyAccounts("idFamilyAccount desc", "").get(0);
        } catch (Exception ex){
            return new FamilyAccountModel();
        }
    }

    public static ArrayList<Model> getTransactions(String orderBy, String where){
        String answer = getModelsFromServer("getTransactions", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<TransactionModel>>(){}.getType()) : null;
    }


    public static ArrayList<Model> getUsers(String orderBy, String where){
        String answer = getModelsFromServer("getUsers", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<UserModel>>(){}.getType()) : null;
    }

    public static ArrayList<Model> getFamilyAccounts(String orderBy, String where){
        String answer = getModelsFromServer("getFamilyAccounts", orderBy, where);
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<FamilyAccountModel>>(){}.getType()) : null;
    }

    public static ArrayList<String> getAuthority(){
        String answer = getModelsFromServer("getAuthority", "", "");
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<String>>(){}.getType(), true) : null;
    }

    public static ArrayList<String> getCurrency(){
        String answer = getModelsFromServer("getCurrency", "", "");
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<String>>(){}.getType(), true) : null;
    }

    public static ArrayList<String> getTypes(){
        String answer = getModelsFromServer("getType", "", "");
        return !("".equals(answer)) ? Serrializator.JSONToObjects(answer, new TypeToken<ArrayList<String>>(){}.getType(), true) : null;
    }

    private static String getModelsFromServer(String command, String orderBy, String where) {
        String data = "";
        if(!orderBy.trim().equals("") || !where.trim().equals("")) {
            data = orderBy + " @@@ " + where;
        }
        return connector.writeCommand(command, data);
    }


    public static String insert(Model model, String table){
        String command = "insert_", data;
        data = Serrializator.ObjectToJSON(model);
        return connector.writeCommand(command + table, data);
    }

    public static String update(Model model, String table, int id){
        String command = "update_", data;
        data = Serrializator.ObjectToJSON(model) + "@@@" + id;
        return connector.writeCommand(command + table, data);
    }

    public static String delete(int id, String table){
        String command = "delete", data;
        data = table + "@@@" + id;
        return connector.writeCommand(command, data);
    }

    public static String exit(String message){
        return connector.closeSocket(message);
    }

}
