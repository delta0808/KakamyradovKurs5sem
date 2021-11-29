package listeners;

import dialog_construction.ChartDialog;
import dialog_construction.PieDialog;
import dialog_construction.TransactionTableDialog;
import dialogs.CDialogNotFrame;
import model.UserModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BShowInfoListener implements ActionListener {
    private CDialogNotFrame infoDialog;
    private UserModel currentUser;

    public BShowInfoListener(UserModel currentUser){
        this.currentUser = currentUser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        prepareData();
        infoDialog.setVisible(true);
    }

    private void prepareData(){

        infoDialog = new CDialogNotFrame("Аналитическая информация", 800, 600, true);

        PieDialog dataset = new PieDialog();

        JFreeChart chart = dataset.createChart(dataset.createDataset(currentUser));
        chart.setPadding(new RectangleInsets(10, 10, 10, 10));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(100, 100));

        panel.setVisible(true);
        infoDialog.setContentPane(panel);
    }
}
