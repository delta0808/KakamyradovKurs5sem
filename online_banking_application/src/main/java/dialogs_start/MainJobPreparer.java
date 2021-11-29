package dialogs_start;

import connection.DataPreparer;
import dialog_construction.ChildFrameDialogCreator;
import dialog_construction.DirectorByCreate;
import dialog_construction.ParentFrameDialogCreator;
import dialogs.CDialog;
import model.FamilyAccountModel;
import model.UserModel;
import validation.Validator;


import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

// TODO Фасада, предоставляет статический интерфейс для работы с приложением. Пример - метод startCourseWork
public class MainJobPreparer {
    private CDialog authorizationDialog = null;
    private CDialog authorizationFamilyDialog = null;
    private CDialog registryUserDialog = null;
    private CDialog registryFamilyDialog = null;
    private UserModel currentUser = null;
    private FamilyAccountModel currentFamily = null;

    private final static String HOST = "127.0.0.1";
    private final static String PORT = "9999";


    public static void startCourseWork(){
        MainJobPreparer controller = new MainJobPreparer();
        controller.getStarted();
    }

    private void getStarted(){
        String answer = DataPreparer.startConnection(HOST, Integer.valueOf(PORT));
        createAuthorizationDialog();
        if (!answer.equals("")) {
            JOptionPane.showMessageDialog(authorizationDialog, answer, "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
        } 

    }

    private void createAuthorizationDialog(){
        authorizationDialog = new CDialog("Войдите в систему для начала работы", 400, 370);
        prepareAuthorizationDialog();
        setEventsForAuthorizationDialog();
        setEventClose(authorizationDialog);
        authorizationDialog.setVisible(true);
    }

    private void prepareAuthorizationDialog(){
        authorizationDialog.addLabel(300, 30,50, 0, "АВТОРИЗАЦИЯ Пользователя!");
        authorizationDialog.addLabel(300, 30,50, 20, "Логин:");
        authorizationDialog.addTextField(300, 30,50, 50);
        authorizationDialog.addLabel(300, 30,50, 100, "Пароль:");
        authorizationDialog.addPasswordField(300, 30,50, 130);
        authorizationDialog.addButton(300, 30, 50, 180, "Войти");
        authorizationDialog.addLine(300, 30, 50, 250);
        authorizationDialog.addButton(300, 30, 50, 270, "Зарегестрировать аккаунт компании");

    }

    public void setEventsForAuthorizationDialog() {
        setEventAuth();
        setEventRegistry();
    }

    public void setEventAuth(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectLogAndPass(authorizationDialog)) {
                    if((currentUser = getAuthorisationDataFromServer()) != null){
                        if(currentUser.getIsBlocked().equals("Активен")) {
                            createFrameDialog(authorizationDialog);
                            authorizationDialog.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(authorizationDialog, "Акаунт заблокирован", "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(authorizationDialog, "Неверный логин и\\или пароль", "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                clearDialogData();
            }
        };
        authorizationDialog.buttons.get(0).addActionListener(actionListener);
    }

    public void setEventRegistry(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authorizationFamilyDialog = new CDialog("Авторизация в аккаунт компании!", 400, 370);
                authorizationFamilyDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                prepareAuthorizationFamilyDialog();
                setEventsForAuthorizationFamilyDialog();
                setEventCloseClear(authorizationFamilyDialog, authorizationDialog);
                authorizationFamilyDialog.setVisible(true);
                authorizationDialog.setVisible(false);
                clearDialogData();
            }
        };
        authorizationDialog.buttons.get(1).addActionListener(actionListener);
    }

    public void setEventsForAuthorizationFamilyDialog() {
        setEventAuthFamily();
        setEventRegistryFamily();
    }

    public void setEventAuthFamily(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectLogAndPass(authorizationFamilyDialog)) {
                    if((currentFamily = getAuthorisationFamily()) != null){
                        createUserRegistryDialog(authorizationFamilyDialog);
                        clearFamilyDialogData();
                        authorizationFamilyDialog.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(authorizationFamilyDialog, "Неверный логин и\\или пароль", "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                clearFamilyDialogData();
            }
        };
        authorizationFamilyDialog.buttons.get(0).addActionListener(actionListener);
    }

    public void setEventRegistryFamily(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRegistryUserFrame();
                authorizationFamilyDialog.setVisible(false);
                clearFamilyDialogData();
            }
        };
        authorizationFamilyDialog.buttons.get(1).addActionListener(actionListener);
    }

    private void createRegistryUserFrame(){
        registryFamilyDialog = new CDialog("Регистрация аккаунта компании!", 400, 460);
        registryFamilyDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        prepareRegistrationFamilyDialog();
        setEventsForRegistrationFamilyDialog();
        setEventCloseClear(registryFamilyDialog, authorizationFamilyDialog);
        registryFamilyDialog.setVisible(true);
    }

    private void prepareRegistrationFamilyDialog(){
        registryFamilyDialog.addLabel(300, 30,50, 0, "Регистрация аккаунта компании!");
        registryFamilyDialog.addLabel(300, 30,50, 20, "Логин: ");
        registryFamilyDialog.addTextField(300, 30,50, 50);
        registryFamilyDialog.addLabel(300, 30,50, 100, "Пароль:");
        registryFamilyDialog.addTextField(300, 30,50, 130);

        registryFamilyDialog.addLabel(300, 30,50, 180, "Бюджет:");
        registryFamilyDialog.addTextField(300, 30,50, 210);
        registryFamilyDialog.addLabel(300, 30,50, 260, "Валюта:");
        registryFamilyDialog.addComboBox(false,300, 30,50, 290);
        addComboBoxItems(registryFamilyDialog.comboBoxes, 0, DataPreparer.getCurrency());

        registryFamilyDialog.addButton(300, 30, 50, 370, "Зарегистрировать аккаунт компании");
    }

    private void setEventsForRegistrationFamilyDialog(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectRegistryFamily()) {
                    if((currentFamily = getRegistryFamilyDataFromServer()) != null){
                        createUserRegistryDialog(registryFamilyDialog);
                        registryFamilyDialog.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(registryFamilyDialog, "Ошибка данных", "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                clearRegistryFamilyDialogData();
            }
        };
        registryFamilyDialog.buttons.get(0).addActionListener(actionListener);
    }

    private FamilyAccountModel getRegistryFamilyDataFromServer(){
        FamilyAccountModel familyAccount = new FamilyAccountModel (
                0,
                Double.valueOf(registryFamilyDialog.textFields.get(2).getText().trim()),
                registryFamilyDialog.textFields.get(0).getText().trim(),
                registryFamilyDialog.textFields.get(1).getText().trim(),
                Integer.valueOf(((String)registryFamilyDialog.comboBoxes.get(0).getSelectedItem()).split("\\) ")[0])
        );
        return DataPreparer.checkRegistryFamily(familyAccount);
    }

    private boolean isCorrectRegistryFamily(){
        boolean isOk = true;
        String loginFamily, passwordFamily, sum, errors = "";
        loginFamily = registryFamilyDialog.textFields.get(0).getText().trim();
        passwordFamily = registryFamilyDialog.textFields.get(1).getText().trim();
        sum = registryFamilyDialog.textFields.get(2).getText().trim();
        if("".equals(loginFamily) || loginFamily.length() < 3){
            errors += "Логин компании пуст, либо слишком мал для регистрации!\n";
            isOk = false;
        }
        if("".equals(passwordFamily) || passwordFamily.length() < 3){
            errors += "Пароль компании пуст, либо слишком мал для регистрации!\n";
            isOk = false;
        }
        if("".equals(sum) || !Validator.isDouble(sum)){
            errors += "Бюджет пуст, либо не число!\n";
            isOk = false;
        }
        if (!isOk) JOptionPane.showMessageDialog(registryFamilyDialog, errors, "Ввведены некорректные данные", JOptionPane.PLAIN_MESSAGE);
        return isOk;
    }

    private FamilyAccountModel getAuthorisationFamily(){
        String userName = authorizationFamilyDialog.textFields.get(0).getText().trim();
        String userPassword = authorizationFamilyDialog.passwordFields.get(0).getText().trim();

        return DataPreparer.checkFamilyAuthorization(userName, userPassword);
    }

    private void prepareAuthorizationFamilyDialog(){
        authorizationFamilyDialog.addLabel(300, 30,50, 0, "Авторизация в аккаунт компании!");
        authorizationFamilyDialog.addLabel(300, 30,50, 20, "Логин компании:");
        authorizationFamilyDialog.addTextField(300, 30,50, 50);
        authorizationFamilyDialog.addLabel(300, 30,50, 100, "Пароль компании:");
        authorizationFamilyDialog.addPasswordField(300, 30,50, 130);
        authorizationFamilyDialog.addButton(300, 30, 50, 180, "Войти");
        authorizationFamilyDialog.addLine(300, 30, 50, 250);
        authorizationFamilyDialog.addButton(300, 30, 50, 270, "Регистрация аккаунта компании");

    }

    private void clearDialogData(){
        authorizationDialog.textFields.get(0).setText("");
        authorizationDialog.passwordFields.get(0).setText("");
    }

    private void clearFamilyDialogData(){
        authorizationFamilyDialog.textFields.get(0).setText("");
        authorizationFamilyDialog.passwordFields.get(0).setText("");
    }

    public static void clearRegistryDialogData(CDialog dialog){
        dialog.textFields.get(0).setText("");
        dialog.textFields.get(1).setText("");
        dialog.textFields.get(2).setText("");
        dialog.textFields.get(3).setText("");
        dialog.textFields.get(4).setText("");
        dialog.comboBoxes.get(0).setSelectedIndex(0);
    }

    private void clearRegistryFamilyDialogData(){
        registryFamilyDialog.textFields.get(0).setText("");
        registryFamilyDialog.textFields.get(1).setText("");
        registryFamilyDialog.textFields.get(2).setText("");
        registryFamilyDialog.comboBoxes.get(0).setSelectedIndex(0);
    }

    private void createFrameDialog(CDialog dialog) {
        DirectorByCreate directorByCreate = new DirectorByCreate(dialog);
        if(currentUser.getAuthority().equals("Директор")) {
            directorByCreate.setDialogCreator(new ParentFrameDialogCreator(currentUser));
        } else {
            directorByCreate.setDialogCreator(new ChildFrameDialogCreator(currentUser));
        }
        directorByCreate.createDialog();
        directorByCreate.getDialog();
    }

    public void setEventClose(CDialog dialog){
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
                    JOptionPane.showMessageDialog(dialog, answer, "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                }
            }
        };
        dialog.addWindowListener(windowListener);
    }

    public static void setEventCloseClear(CDialog dialog, CDialog dialogLast){
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {}

            public void windowClosing(WindowEvent event) {
                dialog.dispose();
                dialogLast.setVisible(true);
            }
        };
        dialog.addWindowListener(windowListener);
    }

    private boolean isCorrectLogAndPass(CDialog dialog){
        boolean isOk = true;
        String login, password, errors = "";
        login = dialog.textFields.get(0).getText().trim();
        password = dialog.passwordFields.get(0).getText().trim();
        if("".equals(login) || login.length() < 3){
            errors += "Логин пуст, либо слишком мал!\n";
            isOk = false;
        }
        if("".equals(login) || password.length() < 3){
            errors += "Пароль пуст, либо слишком мал!\n";
            isOk = false;
        }
        if(!Validator.isHaveOnlyGoodChars(login) ||  !Validator.isHaveOnlyGoodChars(password)){
            errors += "Содержатся запрещённые символы\n";
            isOk = false;
        }
        if (!isOk) JOptionPane.showMessageDialog(dialog, errors, "Ввведены некорректные данные", JOptionPane.PLAIN_MESSAGE);
        return isOk;
    }

    public static boolean isCorrectRegistry(CDialog dialog){
        boolean isOk = true;
        String login, firstPassword, firstName, lastName, phone, type, errors = "";
        login = dialog.textFields.get(0).getText().trim();
        firstPassword = dialog.textFields.get(1).getText().trim();
        firstName = dialog.textFields.get(2).getText().trim();
        lastName = dialog.textFields.get(3).getText().trim();
        phone = dialog.textFields.get(4).getText().trim();
        if("".equals(login) || login.length() < 3){
            errors += "Логин пуст, либо слишком мал для регистрации!\n";
            isOk = false;
        }
        if("".equals(firstPassword) || firstPassword.length() < 3){
            errors += "Пароль пуст, либо слишком мал для регистрации!\n";
            isOk = false;
        }
        if("".equals(firstName) || firstName.length() < 3){
            errors += "Имя пусто, либо слишком мало для регистрации!\n";
            isOk = false;
        }
        if("".equals(lastName) || lastName.length() < 3){
            errors += "Фамилия пуста, либо слишком мала для регистрации!\n";
            isOk = false;
        }
        if("".equals(phone) || phone.length() < 3){
            errors += "Телефон пуст, либо слишком мал для регистрации!\n";
            isOk = false;
        }
        if (!isOk) JOptionPane.showMessageDialog(dialog, errors, "Ввведены некорректные данные", JOptionPane.PLAIN_MESSAGE);
        return isOk;
    }

    private UserModel getAuthorisationDataFromServer(){
        String userName = authorizationDialog.textFields.get(0).getText().trim();
        String userPassowrd = authorizationDialog.passwordFields.get(0).getText().trim();

        return DataPreparer.checkAuthorization(userName, userPassowrd);
    }

    private void createUserRegistryDialog (CDialog dialog){
        registryUserDialog = new CDialog("Регистрация пользователя!", 400, 620);
        registryUserDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        prepareRegistrationDialog();
        setEventsForRegistrationDialogAuth();
        setEventCloseClear(registryUserDialog, dialog);
        registryUserDialog.setVisible(true);
    }

    private void setEventsForRegistrationDialogAuth(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectRegistry(registryUserDialog)) {
                    if((currentUser = getRegistryDataFromServer(registryUserDialog, currentFamily.getId())) != null){
                        createFrameDialog(registryUserDialog);
                        registryUserDialog.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(registryUserDialog, "Неверные данные", "Что-то пошло не так...", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                clearRegistryDialogData(registryUserDialog);
            }
        };
        registryUserDialog.buttons.get(0).addActionListener(actionListener);
    }


    private void prepareRegistrationDialog(){
        registryUserDialog.addLabel(300, 30,50, 0, "Регистрация пользователя!");
        registryUserDialog.addLabel(300, 30,50, 20, "Имя: ");
        registryUserDialog.addTextField(300, 30,50, 50);
        registryUserDialog.addLabel(300, 30,50, 100, "Фамилия:");
        registryUserDialog.addTextField(300, 30,50, 130);

        registryUserDialog.addLabel(300, 30,50, 180, "Логин:");
        registryUserDialog.addTextField(300, 30,50, 210);
        registryUserDialog.addLabel(300, 30,50, 260, "Пароль:");
        registryUserDialog.addTextField(300, 30,50, 290);
        registryUserDialog.addLabel(300, 30,50, 340, "Номер:");
        registryUserDialog.addTextField(300, 30,50, 370);
        registryUserDialog.addLabel(300, 30,50, 420, "Тип:");
        registryUserDialog.addComboBox(false,300, 30,50, 450);
        addComboBoxItems(registryUserDialog.comboBoxes, 0, DataPreparer.getAuthority());


        registryUserDialog.addButton(300, 30, 50, 530, "Зарегистрировать");
    }

    private void addComboBoxItems(ArrayList<JComboBox> boxes, int id, ArrayList<String> values){
        for (int j = 0; j < values.size(); j++) {
            boxes.get(boxes.size()-1).addItem(values.get(j));
        }
    }


    public static UserModel getRegistryDataFromServer(CDialog dialog, int id){
        UserModel user = new UserModel (
            0,
                dialog.textFields.get(0).getText().trim(),
                dialog.textFields.get(1).getText().trim(),
                dialog.textFields.get(2).getText().trim(),
                dialog.textFields.get(3).getText().trim(),
                dialog.textFields.get(4).getText().trim(),
            "Активен",
                id,
            Integer.valueOf(((String)dialog.comboBoxes.get(0).getSelectedItem()).split("\\) ")[0])
        );
        return DataPreparer.checkRegistry(user);
    }

}
