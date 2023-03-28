package deliverable_2_implementation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChart extends Visualization{
	
	public JPanel createNewChart(ArrayList<DataForRegion> dataRegionList) {
		XYSeries series1 = new XYSeries("Values for Region");
		DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
        		series1.add(Integer.parseInt(data.dates.get(i).substring(0, 4)), avg);
        	}
        }

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		JFreeChart chart = ChartFactory.createXYLineChart("Values for region ", "Year", "Values", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(
				new TextTitle("Values for region "+ data.region, new Font("Serif", java.awt.Font.BOLD, 18)));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		return chartPanel;
	}

}
