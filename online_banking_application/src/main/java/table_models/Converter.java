package table_models;

import model.*;

import java.util.ArrayList;

public class Converter {
    public static ArrayList<TransactionModel> modelToTransaction(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<TransactionModel> transactions = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            transactions.add((TransactionModel) models.get(i));
        }

        return transactions;
    }

    public static ArrayList<UserModel> modelToUser(ArrayList<Model> models){
        if(models == null) return null;

        ArrayList<UserModel> users = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            users.add((UserModel) models.get(i));
        }

        return users;
    }
}
