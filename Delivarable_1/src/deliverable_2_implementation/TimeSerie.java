package deliverable_2_implementation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

public class TimeSerie extends Visualization{
	TimeSeriesCollection dataset;
	XYPlot plot;
	JFreeChart chart;
	int firstNum = 0;
	int secondNum = 0;
	XYSplineRenderer splinerenderer1;
	public JPanel createNewChart(ArrayList<DataForRegion> dataRegionList) {
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        TimeSeries series1 = new TimeSeries("Values for Region" + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
        		series1.add(new Year(Integer.parseInt(data.dates.get(i).substring(0, 4))), avg);
        	}
        }

		dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);

		plot = new XYPlot();
		splinerenderer1 = new XYSplineRenderer();
//		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis("Values"));

		plot.mapDatasetToRangeAxis(firstNum, secondNum);// 1st dataset to 1st y-axis
		firstNum++;
		secondNum++;
//		plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
	}
	
	public JPanel CreateAddData(ArrayList<DataForRegion> dataRegionList) {
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        TimeSeries series = new TimeSeries("Values for Region " + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
        		series.add(new Year(Integer.parseInt(data.dates.get(i).substring(0, 4))), avg);
        	}
        }
		dataset.addSeries(series);
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
		plot.setDataset(1, dataset);
		plot.setRenderer(firstNum, splinerenderer2);
		plot.mapDatasetToRangeAxis(firstNum, secondNum); // 2nd dataset to 2nd y-axis
		firstNum++;
		secondNum++;
		chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;

	}
	public JPanel CreateAddDataMonthly(ArrayList<DataForRegion> dataRegionList) {
		DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        TimeSeries series = new TimeSeries("Values for Region " + data.region);
		int year = 0;
		int nowYear = 0;
		for(int i = 0; i < data.values.size(); i++) {
			year = Integer.parseInt(data.dates.get(i).substring(0, 4));
			if(year != nowYear) {
				nowYear = year;
				double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(5, 7)));
				series.add(new Month(Integer.parseInt(data.dates.get(i).substring(5, 7)),Integer.parseInt(data.dates.get(i).substring(0, 4))), avg);
			}
		}
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		dataset1.addSeries(series);
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
		plot.setDataset(0, dataset1);
		plot.setRenderer(0, splinerenderer2);
		
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
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		for(int a = 0; a < dataRegionList.size(); a++) {
			DataForRegion data = dataRegionList.get(a);
			TimeSeries series = new TimeSeries("Values for Region " + data.region);
			for(int i = 0; i < data.values.size(); i++) {
	        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
	        	if(year != nowYear) {
	        		nowYear = year;
	        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
	        		series.add(new Year(Integer.parseInt(data.dates.get(i).substring(0, 4))), avg);
	        	}
	        }
			dataset1.addSeries(series);
			XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
			splinerenderer2.setSeriesPaint(0, color);
			splinerenderer2.setSeriesShape(0,  shape);

			plot.setDataset(0, dataset1);
			plot.setRenderer(0, splinerenderer2);
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