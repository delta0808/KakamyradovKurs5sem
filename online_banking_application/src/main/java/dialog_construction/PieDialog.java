package dialog_construction;

import connection.DataPreparer;
import model.Model;
import model.TransactionModel;
import model.UserModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import table_models.Converter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PieDialog {

    public PieDialog(){
    }


    public PieDataset createDataset(UserModel currentUser) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<TransactionModel> models = getDataFromServer(currentUser);
        for (int i = 0; i < models.size(); i++) {
            addOneUser(dataset, models.get(i));
        }
        return dataset;
    }

    private ArrayList<TransactionModel> getDataFromServer(UserModel currentUser) {

        String whereString = " `idFamilyAccount` =  ? @@@" + currentUser.getIdFamilyAccount() + "@@@false";

        return Converter.modelToTransaction(DataPreparer.getTransactions("", whereString));
    }


    private void addOneUser(DefaultPieDataset dataset, Model model){
        TransactionModel transaction = (TransactionModel) model;
        String role = transaction.getAuthority();
        try {
            dataset.getValue(role);
            dataset.setValue(role, dataset.getValue(role).intValue() + 1);
        } catch (Exception e){
            dataset.setValue(role, 1);
        }
    }

    public JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart("Статистика транзакций", dataset,false,true, false);

        chart.setBackgroundPaint(new GradientPaint(
                new Point(0, 0),
                new Color(200, 200, 200),
                new Point(400, 200),
                Color.WHITE)
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.10);
        plot.setOutlineVisible(false);

        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(3.0f));

        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);

        return chart;
    }

}