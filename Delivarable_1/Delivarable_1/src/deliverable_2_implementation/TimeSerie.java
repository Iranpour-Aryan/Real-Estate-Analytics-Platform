package deliverable_2_implementation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

public class TimeSerie extends Visualization{
	TimeSeriesCollection dataset;
	XYPlot plot;
	JFreeChart chart;
	int firstNum = 0;
	int secondNum = 0;
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

		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis("Values"));

		plot.mapDatasetToRangeAxis(firstNum, secondNum);// 1st dataset to 1st y-axis
		firstNum++;
		secondNum++;
		plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

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
        TimeSeries series2 = new TimeSeries("Values for Region" + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
        		series2.add(new Year(Integer.parseInt(data.dates.get(i).substring(0, 4))), avg);
        	}
        }
		dataset.addSeries(series2);
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
		plot.setDataset(1, dataset);
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
	
}
