package listener;


import dialogs.CDialog;
import validation.Validator;
import view.MainDialogCreator;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class ButtonOkListener implements ActionListener {
    private CDialog dialog;
    private CDialog mainDialog;
    private int port;
    private String logFileName;
    private String configFileName;
    private String errors = "";

    public ButtonOkListener(CDialog dialog){
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        errors = "";
        if(isGetDataFromFile()) {
            MainDialogCreator creator = new MainDialogCreator(port, logFileName, configFileName, dialog);
            mainDialog  = creator.getMainDialog();
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(dialog, "Ошибки:\r\n" + errors, "Что-то пошло не так....",JOptionPane.PLAIN_MESSAGE);
        }
    }

    private boolean isGetDataFromFile(){
        if (dialog.radioBoxGroups.get(0)[1].isSelected()){
            if(isOkUserData(dialog.textFields.get(0).getText(), dialog.textFields.get(1).getText(), dialog.textFields.get(2).getText())) {
                return writeDataInFile();
            } else return false;
        } else {
            return isOkFile() ? writeDataInFile() : false;
        }
    }


    private boolean isOkUserData(String tempPort, String tempLogFile, String tempConfigFile){
        if(!Validator.isPort(tempPort)){
            errors += "   Порт вне реального диапазона;\r\n";
        } else{
            port = Integer.valueOf(tempPort);
        }
        if (!Validator.isLOG(logFileName = tempLogFile)){
            errors += "   Не log-файл для логирования сервера;\r\n" + logFileName;
        }
        if (!Validator.isTXT(configFileName = tempConfigFile)){
            errors += "   Не txt-файл опций сервера;\r\n";
        }
        return errors.equals("") ? true : false;

    }

    private boolean writeDataInFile(){
        try (
                FileWriter fout = new FileWriter(configFileName, false);
        ){
            fout.write(port + "@@@" + logFileName + "@@@" + configFileName);
            return true;
        } catch (Exception e){

            errors += "   Ошибка чтения файла!";
            return false;
        }
    }


    private boolean isOkFile(){
        if(Validator.isTXT(configFileName = dialog.textFields.get(2).getText())){
            return read();
        } else {
            errors += "   Не txt-файл опций сервера;\r\n";
            return false;
        }
    }

    private boolean read(){
        try(
                FileReader fin  = new FileReader(configFileName);
        ){
            Scanner scan = new Scanner(fin);
            String myAnswer = scan.next(), params[];

            params = myAnswer.split("@@@");

            return isOkUserData(params[0], params[1], params[2]) ? true : false;
        } catch (Exception e){

            errors += "   Ошибка чтения файла!";
            return false;
        }
    }
}
