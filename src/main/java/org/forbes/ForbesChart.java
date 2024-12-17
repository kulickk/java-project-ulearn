package org.forbes;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Map;

public class ForbesChart {

    public static void generateChart(Map<String, Double> countryNetWorth) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : countryNetWorth.entrySet()) {
            dataset.addValue(entry.getValue(), "Капитал", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Общий капитал участников Forbes по странам",
                "Страна",
                "Общий капитал (в миллиардах)",
                dataset,
                PlotOrientation.HORIZONTAL,
                false, true, false);

        ChartFrame frame = new ChartFrame("Forbes Chart", barChart);
        frame.pack();
        frame.setVisible(true);
    }
}