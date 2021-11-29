package listeners;

import dialog_construction.TransactionTableDialog;
import dialogs.CDialog;
import dialogs.CDialogNotFrame;
import dialog_construction.ChartDialog;
import model.Model;
import model.UserModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BGetInfoListener implements ActionListener {
    private CDialogNotFrame infoDialog;
    private UserModel currentUser;

    public BGetInfoListener(UserModel currentUser){
        this.currentUser = currentUser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        prepareData();
        infoDialog.setVisible(true);
    }

    private void prepareData(){

        infoDialog = new CDialogNotFrame("Статистика транзакций!", 800, 600, true);

        ChartDialog dataset = new ChartDialog();

        JFreeChart chart = dataset.createChart(dataset.createDataset(TransactionTableDialog.getData("", currentUser), currentUser.getSum()));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(100, 100));

        panel.setVisible(true);
        infoDialog.setContentPane(panel);
    }


}
