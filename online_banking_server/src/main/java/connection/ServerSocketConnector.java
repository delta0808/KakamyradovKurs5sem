package connection;

import logging.LoggerSaver;
import model.*;
import runing.ServerRunner;

import java.io.*;
import java.net.Socket;

public class ServerSocketConnector extends Thread{
    private ServerRunner parental = null;
    private Socket clientAccepted;
    private ObjectInputStream sois = null;
    private ObjectOutputStream soos = null;
    private boolean isExit = false;
    private boolean isExitWitchServer = false;
    private boolean isBadExit = false;
    public boolean isHaveError = false;
    public String errorMessage = "";
    private int number;


    public ServerSocketConnector(Socket clientAccepted, int number, ServerRunner parental, ObjectInputStream sois, ObjectOutputStream soos) {
        this.clientAccepted = clientAccepted;
        this.number = number;
        this.parental = parental;
        this.sois = sois;
        this.soos = soos;
        start();
    }

    public void run(){
        try {
            while (!isExit) {
                jobWithClient();
                clearLastError();
            }
            if(!isBadExit) {
                if (isExitWitchServer) exitWitchServer();
                else exit();
            }
            interrupt();
        } catch (Exception e){
            LoggerSaver.logger.error("Ошибка соединения с клиентом #" + number + ": " +  e.getMessage());
            generateError("Ошибка соединения с клиентом", e.getMessage());
        }
    }


    private void jobWithClient(){
        try {
            String command = sois.readObject().toString();
            String data = sois.readObject().toString();
            String answer = runCommand(command, data);
            LoggerSaver.logger.info("Запрос от клиента #" + number + ": \"" +  command + "\" обработан");
            clearLastError();

            if(!answer.equals("exit"))
                soos.writeObject(answer);
        } catch (IOException | ClassNotFoundException  e){
            LoggerSaver.logger.warn("Ошибка передачи информации серверу от клиента #" + number + ": " +  e.getMessage());
            isExit = true;  isBadExit = true;
            generateError("Ошибка передачи информации серверу", e.getMessage() );
        }
    }

    private String runCommand(String command, String data){
        switch (command){
            case "authorization": return OrderPerformer.doAuthorization(data);
            case "authorization_family": return OrderPerformer.doAuthorizationFamily(data);
            case "getAuthority": return OrderPerformer.getAuthority();
            case "getCurrency": return OrderPerformer.getCurrency();
            case "getType": return OrderPerformer.getType();
            case "getUsers": return OrderPerformer.getAll(data, UserModel.class);
            case "getFamilyAccounts": return OrderPerformer.getAll(data, FamilyAccountModel.class);
            case "getTransactions": return OrderPerformer.getAll(data, TransactionModel.class);
            case "delete": return OrderPerformer.delete(data);
            case "insert_transaction": return OrderPerformer.insert(data, "transaction", TransactionModel.class);
            case "insert_users": return OrderPerformer.insert(data, "user", UserModel.class);
            case "insert_familyAccounts": return OrderPerformer.insert(data, "familyAccount", FamilyAccountModel.class);
            case "update_transactions": return OrderPerformer.update(data, "transaction", TransactionModel.class);
            case "update_user": return OrderPerformer.update(data, "user", UserModel.class);
            case "update_familyAccount": return OrderPerformer.update(data, "familyAccount", FamilyAccountModel.class);
            case "exit_witchout_server":  isExit = true;     return "exit";
            default: return "";
        }
    }


    public void exitWitchServer(){
        exit();
        parental.dialog.dispose();
        parental.exitServer();
    }

    public void exit(){

        parental.dialog.textFields.get(1).setText(--parental.count + " шт.");
        try {
            sois.close();
            soos.close();
            clientAccepted.close();
            parental.clientSockets.remove(this);
            clearLastError();
            LoggerSaver.logger.info("Отключился клиент: #" + number);
        } catch (IOException e) {
            generateError("Ошибка закрытия соедитения с клиентом", e.getMessage());
            LoggerSaver.logger.error("Ошибка закрытия соедитения с клиентом #" + number + ": " +  e.getMessage());
        }
    }


    private void generateError(String message, String javaMessage){
        isHaveError = true;
        errorMessage = message + ". Ошибка: \"" + javaMessage +"\"";
    }

    private void clearLastError(){
        isHaveError = false;
        errorMessage = "";
    }

}
