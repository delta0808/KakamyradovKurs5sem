package connection;

import java.io.*;
import java.net.Socket;

public class ClientSocketConnector {
    private ObjectOutputStream coos;
    private ObjectInputStream cois;
    private Socket clientSocket;
    private String host;
    private int port;
    public boolean isHaveError;
    public String errorMessage = "";

    public ClientSocketConnector(String host, int port){
        this.host = host;
        this.port = port;
    }


    public void run(){
        try {
            clientSocket = new Socket(host, port);
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            cois = new ObjectInputStream(clientSocket.getInputStream());
            coos.writeObject("NOTBREAK");
            clearLastError();
        } catch(Exception e){
            generateError("Ошибка соединения с сервером", e.getMessage());
        }
    }

    public String closeSocket(String message){
        writeCommand("exit_" + message + "_server", "");
        exit();
        return isHaveError ? errorMessage : "";
    }


    public void exit(){
        try {
            coos.writeObject(null);
            coos.close();
            cois.close();
            clientSocket.close();
            clearLastError();
        } catch (Exception e){
            generateError("Ошибка рассоединения с сервером", e.getMessage());
        }
    }

    public String writeCommand(String command, String data){
        try {
            coos.writeObject(command);
            coos.writeObject(data);
            clearLastError();

            return cois.readObject().toString();
        } catch (Exception e){
            generateError("Ошибка общения с сервером", e.getMessage() );
            return "";
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
