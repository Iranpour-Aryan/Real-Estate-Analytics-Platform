package methods;
import java.awt.Color;  
import javax.swing.JFrame;  
import javax.swing.SwingUtilities;  
import javax.swing.WindowConstants;  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.plot.XYPlot;  
import org.jfree.data.xy.XYDataset;  
import org.jfree.data.xy.XYSeries;  
import org.jfree.data.xy.XYSeriesCollection;  
  
public class Visualisation extends JFrame {  
  private static final long serialVersionUID = 6294689542092367723L;  
  
  public Visualisation(String title) {  
    super(title);  
  
    // Create dataset  
    XYDataset dataset = createDataset();  
  
    // Create chart  
    JFreeChart chart = ChartFactory.createScatterPlot(  
        "Québec, Quebec VS Toronto, Ontario value comparison chart",   
        "Date (MonthYear)", "Value", dataset);  
  
      
    //Changes background color  
    XYPlot plot = (XYPlot)chart.getPlot();  
    plot.setBackgroundPaint(new Color(255,228,196));  
      
     
    // Create Panel  
    ChartPanel panel = new ChartPanel(chart);  
    setContentPane(panel);  
  }  
  
  private XYDataset createDataset() {  
    XYSeriesCollection dataset = new XYSeriesCollection();  
  
    XYSeries series1 = new XYSeries("Québec, Quebec");  
    series1.add(181, 30.1);  
    series1.add(281, 30.6);  
    series1.add(381, 35.4);  
    series1.add(481, 20.9);  
    series1.add(581, 36.1);
  
    dataset.addSeries(series1);  
      
    XYSeries series2 = new XYSeries("Toronto, Ontario");  
    series2.add(181, 22.6);  
    series2.add(281, 23.2);  
    series2.add(381, 24.8);  
    series2.add(481, 40);  
    series2.add(581, 40.1);  

    dataset.addSeries(series2);  
  
    return dataset;  
  }  
  
  public static void main(String[] args) {  
    SwingUtilities.invokeLater(() -> {  
    	Visualisation example = new Visualisation("Scatter Chart Visualisation");  
      example.setSize(800, 400);  
      example.setLocationRelativeTo(null);  
      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
      example.setVisible(true);  
    });  
  }  
} 
