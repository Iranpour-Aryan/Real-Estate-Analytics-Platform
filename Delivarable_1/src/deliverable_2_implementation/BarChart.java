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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.Year;

public class BarChart extends Visualization{
	CategoryPlot plot;
	JFreeChart chart;
	DefaultCategoryDataset dataset;
	int firstNum = 0;
	int secondNum = 0;
	public JPanel createNewChart(ArrayList<DataForRegion> dataRegionList) {
		dataset = new DefaultCategoryDataset();
		DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
        		dataset.setValue(avg, "Values for " + data.region, data.dates.get(i).substring(0, 4));
        	}
        }
		plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis("Values"));

		plot.mapDatasetToRangeAxis(firstNum, secondNum);// 1st dataset to 1st y-axis
		firstNum++;
		secondNum++;
//		plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		chart = new JFreeChart("Values for region "+ data.region,
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);


		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
       	
	}
	
	public JPanel CreateAddData(ArrayList<DataForRegion> dataRegionList) {
//		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
        		dataset.setValue(avg, "Values for " + data.region, data.dates.get(i).substring(0, 4));
        	}
        }
        
        BarRenderer barrenderer1 = new BarRenderer();
		plot.setDataset(1, dataset);
//		plot.setRenderer(1, barrenderer1);
//		plot.setRangeAxis(new NumberAxis("Values"));
//		plot.mapDatasetToRangeAxis(firstNum, secondNum); // 2nd dataset to 2nd y-axis
//		firstNum++;
//		secondNum++;
		chart = new JFreeChart("Values for region "+ data.region,
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;

	}
	
	public JPanel CreateConfiguredChart(Color color, Shape shape, int width, int length,  ArrayList<DataForRegion> dataRegionList) {
        int year = 0;
        int nowYear = 0;
        for(int a = 0; a < dataRegionList.size(); a++) {
    		DataForRegion data = dataRegionList.get(a);
        	for(int i = 0; i < data.values.size(); i++) {
            	year = Integer.parseInt(data.dates.get(i).substring(0, 4));
            	if(year != nowYear) {
            		nowYear = year;
            		double avg = this.getAverageForYear(data,Integer.parseInt(data.dates.get(i).substring(0, 4)));
            		dataset.setValue(avg, "Values for " + data.region, data.dates.get(i).substring(0, 4));
            	}
            }
        	BarRenderer barrenderer1 = new BarRenderer();
        	barrenderer1.setSeriesPaint(0, color);
//    		plot.setRenderer(1, barrenderer1);
//        	barrenderer1.setSeriesShape(0,  shape);
    		plot.setDataset(1, dataset);
        }
        
		chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
<<<<<<< HEAD
=======

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(width, length));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
	}
>>>>>>> 8682079deb1b16332627aaab18323cc466b15d22

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(width, length));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
	}

}