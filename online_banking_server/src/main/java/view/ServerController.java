package view;

import dialogs.CDialog;
import listener.ButtonOkListener;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ServerController {
    private CDialog prepareDialog;
    private static int id = 0;


    public void run(){
        prepareDialog = new CDialog("Сервер-обработчик интернет-банкинга!", 400, 400);
        preparePrepareDialog();
        addDefaultData();
        addItemListener();
        setEventForButton();
        prepareDialog.setVisible(true);
    }

    private void preparePrepareDialog(){
        prepareDialog.addLabel(300, 30,50, 20, "TCP-порт прослушки:");
        prepareDialog.addTextField(300, 30,50, 50);
        prepareDialog.textFields.get(0).setEnabled(false);
        prepareDialog.addLabel(300, 30,50, 100, "Файл логирования сервера:");
        prepareDialog.addTextField(300, 30,50, 130);
        prepareDialog.textFields.get(1).setEnabled(false);
        prepareDialog.addLabel(300, 30,50, 180, "Файл опций сервера:");
        prepareDialog.addTextField(300, 30,50, 210);
        prepareDialog.addRadioButtonGroupe(300, 30,50, 240, new String[]{"Автоматическое получение", "Ручной ввод"});
        prepareDialog.addButton(300, 30, 50, 310, "Запустить");
    }

    private void addItemListener(){
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isEnabled;
                if(id++ % 2 == 0) isEnabled = true;
                else isEnabled = false;
                prepareDialog.textFields.get(0).setEnabled(isEnabled);
                prepareDialog.textFields.get(1).setEnabled(isEnabled);
            }
        };
        prepareDialog.radioBoxGroups.get(0)[0].addItemListener(itemListener);
    }

    private void addDefaultData(){
        prepareDialog.textFields.get(0).setText(String.valueOf(9999));
        prepareDialog.textFields.get(1).setText("logfile.log");
        prepareDialog.textFields.get(2).setText("serverConfig.txt");
    }

    private void setEventForButton(){
        ActionListener actionListener = new ButtonOkListener(prepareDialog);
        prepareDialog.buttons.get(0).addActionListener(actionListener);
    }
}