package dialog_construction;

import connection.DataPreparer;
import dialogs.CDialog;
import model.UserModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// TODO Паттерн строитель. Класс Строитель, является абстрактным, и через ссылку которого создаются объекты конкретных
abstract class AbstractFrameDialogCreator {
    protected CDialog frameDialog;
    private CDialog dialog;
    protected int height;

    protected UserModel currentUser;

    public AbstractFrameDialogCreator(UserModel currentUser) {
        this.currentUser = currentUser;
    }

    public CDialog getFrameDialog() {
        frameDialog.setVisible(true);
        return frameDialog;
    }

    public static String updateStatus(UserModel currentUser){
        return "Акаункт \"" + currentUser.getAuthority() + "\" (" + currentUser.getFirstName() + " " +
                currentUser.getLastName() + ") Бюджет: " + currentUser.getSum() + " " + currentUser.getCurrencyCode() + ".";
    }

    public void createDialog(CDialog dialog){
        frameDialog = new CDialog("Система интернет-банкинга!", 800, 250);
        setEventsForCloseDialog();
        prepareFrameDialog();
        setEventsForButtons();
        this.dialog = dialog;
        frameDialog.setSize(800, height);
        frameDialog.setLocationByPlatform(true);
    }

    public void setEventsForCloseDialog() {
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {}

            public void windowClosing(WindowEvent event) {
                String answer = DataPreparer.exit("witchout");
                if(!answer.equals("")) {
                    JOptionPane.showMessageDialog(frameDialog, answer, "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                }
            }
        };
        frameDialog.addWindowListener(windowListener);
    }

    abstract void prepareFrameDialog();
    abstract void setEventsForButtons();

    protected ActionListener setEventExit(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameDialog.dispose();
                dialog.setVisible(true);
            }
        };
        return actionListener;
    }

}
