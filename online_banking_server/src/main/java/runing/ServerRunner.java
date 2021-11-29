package runing;

import connection.ServerSocketConnector;
import dialogs.CDialog;
import logging.LoggerSaver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerRunner extends Thread{

    public ArrayList<ServerSocketConnector> clientSockets = new ArrayList<>();
    private ServerSocket serverSocket = null;
    public int count = 0;
    private int port;
    public String lastError = "";
    public CDialog dialog;

    public ServerRunner(int port, String logFile, CDialog dialog) {
        this.port = port;
        this.dialog = dialog;
        System.setProperty("loging.log", "C:\\log\\" + logFile);
        start();
    }

    public void run(){
        try {
            LoggerSaver.logger.info("Сервер запущен");
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket tempSocket = serverSocket.accept();
                ObjectInputStream nois = new ObjectInputStream(tempSocket.getInputStream());
                ObjectOutputStream noos = new ObjectOutputStream(tempSocket.getOutputStream());

                if(!nois.readObject().toString().equals("BREAK")){
                    clientSockets.add(new ServerSocketConnector(tempSocket, count, this, nois, noos));
                } else {
                    serverSocket.close();
                    break;
                }
                dialog.textFields.get(1).setText(++count + " шт.");
                LoggerSaver.logger.info("Подключился клиент: #" + (count-1) );
            }
        } catch (Exception e) {
            lastError = "Ошибка сокета: \"" + e.getMessage() + "\"";
            LoggerSaver.logger.error("Ошибка сокета: \"" + e.getMessage() + "\"");
        }
    }

    public void exitServer() {
        try {

            for (int i = 0; i < clientSockets.size(); i++) {
                clientSockets.get(i).exit();
            }
            LoggerSaver.logger.info("Сервер завершил работу корректно");
        } catch (Exception e) {
            lastError = "Ошибка сокета: \"" + e.getMessage() + "\"";
            LoggerSaver.logger.error("Сервер завершил с ошибкой сокета: \"" + e.getMessage() + "\"");
        }
        NullSocket nullSocket = new NullSocket();
        interrupt();
    }


}
