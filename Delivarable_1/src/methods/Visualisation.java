package methods;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;  
import javax.swing.SwingUtilities;  
import javax.swing.WindowConstants;  
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;  
import org.jfree.data.xy.XYSeries;  
import org.jfree.data.xy.XYSeriesCollection;  
  
public class Visualisation extends JFrame {  
  private static final long serialVersionUID = 6294689542092367723L;  
  
  public Visualisation(String title) {  
	  super(title);  

	  // Create dataset  
	  TimeSeriesCollection dataset1 = new TimeSeriesCollection();
	  TimeSeries series = new TimeSeries("Values for Region ");
		XYPlot plot = new XYPlot();

	  for(int i = 0; i < 9; i++) {
		  series.add(new Month(i+1, 2022), 100);
//		  series.add(new Month(2, 2022), 200);
//		  series.add(new Month(3, 2022), 300);
//		  series.add(new Month(4, 2022), 400);
//		  series.add(new Month(5, 2022), 500);
//		  series.add(new Month(6, 2022), 600);
//		  series.add(new Month(7, 2022), 700);
//		  series.add(new Month(8, 2022), 800);
	  }
	  // Create chart 
	  	dataset1.addSeries(series);
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
//		splinerenderer2.setSeriesPaint(0, color);
//		splinerenderer2.setSeriesShape(0,  shape);

		plot.setDataset(0, dataset1);
		plot.setRenderer(0, splinerenderer2);


	  //Changes background color  
//	  XYPlot plot = (XYPlot)chart.getPlot();  
//	  plot.setBackgroundPaint(new Color(255,228,196));  
		JFreeChart chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
	  // Create Panel  
	  ChartPanel panel = new ChartPanel(chart);  
	  setContentPane(panel);  
  }
    
  
//  private XYDataset createDataset() {  
//    XYSeriesCollection dataset = new XYSeriesCollection();  
//  
//    XYSeries series1 = new XYSeries("Québec, Quebec");  
//    series1.add(181, 30.1);  
//    series1.add(281, 30.6);  
//    series1.add(381, 35.4);  
//    series1.add(481, 20.9);  
//    series1.add(581, 36.1);
//  
//    dataset.addSeries(series1);  
//      
//    XYSeries series2 = new XYSeries("Toronto, Ontario");  
//    series2.add(181, 22.6);  
//    series2.add(281, 23.2);  
//    series2.add(381, 24.8);  
//    series2.add(481, 40);  
//    series2.add(581, 40.1);  
//
//    dataset.addSeries(series2);  
//  
//    return dataset;  
//  }  
  
//  public static void main(String[] args) {  
////	  Visualisation example = new Visualisation("Scatter Chart Visualisation");  
////      example.setSize(800, 400);  
////      example.setLocationRelativeTo(null);  
////      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
////      example.setVisible(true);  
//	  
//  }  
  public static void main(String[] args) {
      TimeSeries series = new TimeSeries("Data");
      series.add(new Month(1, 2022), 100.0);
      series.add(new Month(2, 2022), 120.0);
      series.add(new Month(3, 2022), 130.0);

      TimeSeriesCollection dataset = new TimeSeriesCollection();
      dataset.addSeries(series);

      JFreeChart chart = ChartFactory.createTimeSeriesChart(
         "Chart Title", // chart title
         "X Label", // x-axis label
         "Y Label", // y-axis label
         dataset // data
      );

      ChartFrame frame = new ChartFrame("Chart", chart);
      frame.pack();
      frame.setVisible(true);
   }
} 
