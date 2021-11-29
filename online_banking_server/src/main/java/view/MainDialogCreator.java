package view;

import dialogs.CDialog;
import runing.ServerRunner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainDialogCreator {
    private CDialog frameDialog;
    private CDialog mainDialog;
    private int port;
    private String logFile;
    private ServerRunner runner;


    public MainDialogCreator (int port, String logFile, String configFile, CDialog frameDialog){
        this.frameDialog = frameDialog;
        this.port = port;
        this.logFile = logFile;
        mainDialog = new CDialog("Сервер-обработчик интернет-банкинга!", 500, 270);
        prepareMainDialog();
        prepareItems(port, logFile, configFile);
        setEventForButtons();
        setEventForWindow();
        mainDialog.setVisible(true);
    }

    private void prepareMainDialog(){
        mainDialog.addLabel(400, 30,50, 20, "Опции работы сервера:");
        mainDialog.addTextField(400, 30,50, 50);
        mainDialog.addLabel(400, 30,50, 100, "Счётчик подключений:");
        mainDialog.addTextField(400, 30,50, 130);
        mainDialog.addButton(400, 30, 50, 180, "Остановить");
    }

    private void prepareItems(int port, String logFile, String configFile){
        mainDialog.textFields.get(0).setEnabled(false);
        mainDialog.textFields.get(0).setText("Порт: " + port + " Лог: \"" + logFile + "\" Конфиг: \"" + configFile +  "\"");
        mainDialog.textFields.get(1).setEnabled(false);
        mainDialog.textFields.get(1).setText("0 шт.");
    }

    private void setEventForButtons(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runner.exitServer();
                if(!runner.lastError.equals("")){
                    JOptionPane.showMessageDialog(mainDialog, runner.lastError, "Что-то пошло не так....",JOptionPane.PLAIN_MESSAGE);
                }
                frameDialog.setVisible(true);
                mainDialog.dispose();
            }
        };
        mainDialog.buttons.get(0).addActionListener(actionListener);
    }

    private void setEventForWindow(){
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {
                runner = new ServerRunner(port, logFile, mainDialog);
                if(!runner.lastError.equals("")){
                    JOptionPane.showMessageDialog(mainDialog, runner.lastError, "Что-то пошло не так....",JOptionPane.PLAIN_MESSAGE);
                    mainDialog.dispose();
                }
            }
            public void windowClosing(WindowEvent event) {
                runner.exitServer();
                if(!runner.lastError.equals("")){
                    JOptionPane.showMessageDialog(mainDialog, runner.lastError, "Что-то пошло не так....",JOptionPane.PLAIN_MESSAGE);
                }
            }
        };
        mainDialog.addWindowListener(windowListener);
    }

    public CDialog getMainDialog() {
        return mainDialog;
    }

    public static void setCountForDialog(CDialog dialog, int count){
        dialog.textFields.get(2).setText(count + " шт.");

    }

    public static void showErrorMessage(CDialog dialog, String message){
        JOptionPane.showMessageDialog(dialog, message, "Что-то пошло не так....",JOptionPane.PLAIN_MESSAGE);
    }

}
