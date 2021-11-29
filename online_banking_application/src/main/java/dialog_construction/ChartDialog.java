package dialog_construction;

import dialogs.CDialog;
import model.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import validation.Validator;

import java.awt.*;
import java.util.ArrayList;

public class ChartDialog {

    public ChartDialog(){
    }

    public XYDataset createDataset(ArrayList<Model> models, double sum) {
        XYSeries series = new XYSeries("График Бюджета компании!");

        sum = prepareSum(models, sum);
        series.add(0, sum);

        for (int i = 0; i < models.size(); i++) {
            if(((TransactionModel)models.get(i)).getCategory().equals("Доход")){
                sum += ((TransactionModel)models.get(i)).getSum();
            } else {
                sum -= ((TransactionModel)models.get(i)).getSum();
            }
            series.add(i + 1, sum);
        }
        XYDataset dataset = new XYSeriesCollection();
        ((XYSeriesCollection) dataset).addSeries(series);
        ((XYSeriesCollection) dataset).setAutoWidth(false);
        ((XYSeriesCollection) dataset).setIntervalWidth(10);
        return dataset;
    }

    private double prepareSum(ArrayList<Model> models, double sum){
        for (int i = 0; i < models.size(); i++) {
            double iSum = ((TransactionModel)models.get(i)).getSum();
            if(((TransactionModel)models.get(i)).getCategory().equals("Доход")){
                sum -= iSum;
            } else {
                sum += iSum;
            }
        }
        return sum;
    }


    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Статистика бюджета!", "Время", "Бюджет",
                dataset, PlotOrientation.VERTICAL,
                        true, true, true);

        chart.setBackgroundPaint(new GradientPaint(
                new Point(0, 0),
                new Color(200, 200, 200),
                new Point(400, 200),
                Color.WHITE)
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setOutlineVisible(false);

        return chart;
    }

}