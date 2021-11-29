package data_base;

import model.*;
import validation.Validator;

import java.sql.*;
import java.util.ArrayList;

// TODO Класс клиентского соединения с БД, паттерн Singleton.
public class ConnectorToDB {
    // Приватное свойство, содержит единственный экземпляр класса
    private static ConnectorToDB ourInstance = new ConnectorToDB();
    private String userName;
    private String password;
    private String connectionURL;
    public String lastError = "";

    // Метод для получения объекта (единственного) данного класса
    public static ConnectorToDB getInstance() {
        return ourInstance;
    }

    // Приватный конструктор, ограничение создания объектов из вне
    private ConnectorToDB() {
        lastError = "";
        try {
            userName = "root";
            password = "root";
            connectionURL = "jdbc:mysql://localhost:3306/online_banking?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            lastError = "Ошибка Работы сервера СУБД MySQL: \"" + e.getClass() + "\"";
        }
    }

    private boolean queryNotReturn(String query, String[] params, boolean[] isString){
        try(
                Connection connection = DriverManager.getConnection(connectionURL, userName, password);
                PreparedStatement statement = connection.prepareStatement(query)
        ){
            String y = "";
            for (int i = 0; i < params.length; i++) {
                y += params[i] + ",-";
            }
            for (int i = 0; i < params.length; i++) {
                if(!isString[i]){
                    setSpecial(statement, i+1, params[i]);
                } else {
                    if(params[i].equals("NULL"))
                        statement.setNull(i+1, java.sql.Types.NULL);
                    else
                        statement.setString(i+1, params[i]);
                }
            }
            statement.executeUpdate();
            statement.close();
            connection.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            lastError = "Ошибка соединения с MYSQL: \"" + e.getClass() + "\"";
            return false;
        }
    }

    private void setSpecial(PreparedStatement statement, int id, String data) throws SQLException {
        if(!Validator.isInt(data)) {
            if (Validator.isDouble(data))
                statement.setDouble(id, Double.valueOf(data));
        } else statement.setInt(id, Integer.valueOf(data));
    }

    public boolean insert (String table, String SIMPLE_FIELD, String values, String[] arrayData, boolean[] isStr){
        lastError = "";
        String query = "insert into `" + table + "`(" + SIMPLE_FIELD + ") values (" + values + ")";
        return queryNotReturn(query, arrayData, isStr);
    }

    public boolean update (String table, int id, String values, String[] arrayData, boolean[] isStr){
        lastError = "";
        String query = "update `" + table + "` set " + values + " where id" + table + " = ?";
        return queryNotReturn(query, getAllStr(arrayData, id), getAllBool(isStr, false));

    }

    private String[] getAllStr(String[] arrayData, int id){
        String[] array = new String[arrayData.length+1];
        for (int i = 0; i < arrayData.length; i++) {
            array[i] = arrayData[i];
        }
        array[array.length-1] = String.valueOf(id);
        return array;
    }

    private boolean[] getAllBool(boolean[] arrayData, boolean id){
        boolean[] array = new boolean[arrayData.length+1];
        for (int i = 0; i < arrayData.length; i++) {
            array[i] = arrayData[i];
        }
        array[array.length-1] = id;
        return array;
    }

    public boolean delete (String table, int id){
        lastError = "";
        String query = "delete from `" + table + "` where id" + table + " = ?"; //+ id;
        return queryNotReturn(query, new String[]{String.valueOf(id)}, new boolean[]{false});
    }

    private void setSelectParam(PreparedStatement statement, String param, String isInt) throws SQLException{
        if(isInt.equals("true")){
            if(!Validator.isInt(param)) statement.setDouble( 1, Double.valueOf(param));
            else statement.setInt( 1, Integer.valueOf(param));
        } else {
            statement.setString(1, param);
        }
    }

    public ArrayList<Model> select(String table , String where, String orderBy, String param, String isInt) {
        lastError = "";
        if(!("".equals(orderBy.trim()))) orderBy = "order by " + orderBy;
        if(!("".equals(where.trim()))) where = "where " + where;
        String query = "select * from `" + table + "` " + where + " " + orderBy;
        try(
                Connection connection = DriverManager.getConnection(connectionURL, userName, password);
                PreparedStatement statement = connection.prepareStatement(query)
        ){
            isInt = where.indexOf("LIKE") == -1 ? isInt : "false";
            if(!("".equals(where.trim()))) setSelectParam(statement, param, isInt);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Model> categories = new ArrayList<>();
            while (resultSet.next()) categories.add(resToModel(resultSet, table));

            statement.close();
            connection.close();
            return categories;
        } catch (Exception e){
            e.printStackTrace();
            lastError = "Ошибка Работы сервера СУБД MySQL: \"" + e.getClass() + "\"";
            return null;
        }
    }


    public ArrayList<String> select(String table) {
        lastError = "";
        String query = "select * from `" + table + "` ";
        try(
                Connection connection = DriverManager.getConnection(connectionURL, userName, password);
                PreparedStatement statement = connection.prepareStatement(query)
        ){
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> models = new ArrayList<>();
            while (resultSet.next()) models.add(resToModelSimple(resultSet, table));
            statement.close();
            connection.close();
            return models;
        } catch (Exception e){
            lastError = "Ошибка Работы сервера СУБД MySQL: \"" + e.getClass() + "\"";
            return null;
        }
    }

    private Model resToModel(ResultSet resultSet, String table){
        try {
            switch (table) {
                case "transaction_view":           return resToTransactionModel(resultSet);
                case "user_view":               return resToUserModel(resultSet);
                default: case "familyaccount_view":       return resToFamilyAccountModel(resultSet);
            }
        } catch (Exception e){
            lastError = "Ошибка Работы сервера СУБД MySQL: \"" + e.getClass() + "\"";
            return null;
        }
    }

    private String resToModelSimple(ResultSet resultSet, String table){
        try {
            switch (table) {
                case "authority":           return resToAuthorityModel(resultSet);
                case "currency":               return resToCurrencyModel(resultSet);
                default: case "type":       return resToTypeModel(resultSet);
            }
        } catch (Exception e){
            lastError = "Ошибка Работы сервера СУБД MySQL: \"" + e.getClass() + "\"";
            return null;
        }
    }

    private String resToAuthorityModel(ResultSet resultSet) throws Exception  {
        return resultSet.getInt(1) + ") " + resultSet.getString(2);
    }

    private String resToCurrencyModel(ResultSet resultSet) throws Exception  {
        return resultSet.getInt(1) + ") " + resultSet.getString(2)
                + ". " + resultSet.getString(3) ;
    }

    private String resToTypeModel(ResultSet resultSet) throws Exception  {
        return resultSet.getInt(1) + ") " + resultSet.getString(2)
                + ". " + resultSet.getString(3) ;
    }

    private TransactionModel resToTransactionModel(ResultSet resultSet) throws Exception  {
        TransactionModel transaction = new TransactionModel(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getDouble(3),
            resultSet.getString(4),
            resultSet.getInt(5),
            resultSet.getInt(6),
            resultSet.getString(7),
            resultSet.getString(8),
            resultSet.getInt(9),
            resultSet.getString(10),
            resultSet.getString(11),
            resultSet.getString(12)
        );
        return transaction;
    }


    private UserModel resToUserModel(ResultSet resultSet) throws Exception  {
        UserModel user = new UserModel(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getInt(8),
                resultSet.getInt(9),
                resultSet.getDouble(10),
                resultSet.getString(11),
                resultSet.getString(12),
                resultSet.getInt(13),
                resultSet.getString(14),
                resultSet.getString(15),
                resultSet.getString(16)
        );
        return user;
    }

    private FamilyAccountModel resToFamilyAccountModel(ResultSet resultSet) throws Exception  {
        FamilyAccountModel familyAccount = new FamilyAccountModel(
                resultSet.getInt(1),
                resultSet.getDouble(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getInt(5),
                resultSet.getString(6),
                resultSet.getString(7)
        );
        return familyAccount;
    }

}
