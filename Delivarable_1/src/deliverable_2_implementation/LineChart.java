package deliverable_2_implementation;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math3.geometry.spherical.twod.Circle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChart extends Visualization{
	XYSeriesCollection dataset;
	XYPlot plot;
	JFreeChart chart;
	int firstNum = 0;
	int secondNum = 0;
	
	public JPanel createNewChart(ArrayList<DataForRegion> dataRegionList) {
		DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
		XYSeries series1 = new XYSeries("Values for Region " + data.region);
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

		dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		chart = ChartFactory.createXYLineChart("Values for region ", "Year", "Values", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		plot = chart.getXYPlot();

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
				new TextTitle("Values for region", new Font("Serif", java.awt.Font.BOLD, 18)));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		return chartPanel;
	}
	
	public JPanel CreateAddData(ArrayList<DataForRegion> dataRegionList) {
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        XYSeries series = new XYSeries("Values for Region " + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
        		series.add(Integer.parseInt(data.dates.get(i).substring(0, 4)), avg);
        	}
        }
		dataset.addSeries(series);
		firstNum++;
		secondNum++;
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
//		renderer.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); 
//		renderer.setSeriesShape(0,  new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
		

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);
//		chart.getXYPlot().getRenderer().setSeriesShape(0, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);
		
		chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;

	}
	public JPanel CreateConfiguredChart(Color color, Shape shape, int width, int length, ArrayList<DataForRegion> dataRegionList) {
		int year = 0;
		int nowYear = 0;
		XYSeriesCollection dataset1 = new XYSeriesCollection();
		for(int a = 0; a < dataRegionList.size(); a++) {
			DataForRegion data = dataRegionList.get(a);
	        XYSeries series = new XYSeries("Values for Region " + data.region);
			for(int i = 0; i < data.values.size(); i++) {
	        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
	        	if(year != nowYear) {
	        		nowYear = year;
	        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
	        		series.add(Integer.parseInt(data.dates.get(i).substring(0, 4)), avg);
	        	}
	        }
			dataset1.addSeries(series);
			XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
			renderer.setSeriesPaint(0, color);
			renderer.setSeriesShape(0,  shape);

			plot.setDataset(0, dataset1);
			plot.setRenderer(0, renderer);
		}
		chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(width, length));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
	}

}
