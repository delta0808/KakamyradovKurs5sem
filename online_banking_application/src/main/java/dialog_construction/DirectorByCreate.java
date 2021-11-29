package dialog_construction;

import dialogs.CDialog;

// TODO Паттерн строитель. Класс Директор, который управляет созданием объектов
public class DirectorByCreate {
    private CDialog dialog;
    private AbstractFrameDialogCreator dialogCreator;

    public DirectorByCreate(CDialog authorizationjDialog){
        this.dialog = authorizationjDialog;
    }

    public void setDialogCreator(AbstractFrameDialogCreator dialogCreator) {
        this.dialogCreator = dialogCreator;
    }

    public CDialog getDialog() {
        return dialogCreator.getFrameDialog();
    }

    public void createDialog() {
        dialogCreator.createDialog(dialog);
    }
}
