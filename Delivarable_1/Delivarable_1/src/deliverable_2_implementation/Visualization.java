package deliverable_2_implementation;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
/*import org.jfree.chart.util.TableOrder;
*/import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.TableOrder;
public class Visualization extends JPanel{
	private ArrayList<String> visualizationOptions;
	protected ArrayList<Visualization> visualization;
	protected int index;
	protected ArrayList<DataForRegion> dataRegionList;	
	
	public Visualization() {
		this.visualization = new ArrayList<Visualization>();
		this.dataRegionList = new ArrayList<DataForRegion>();
//		this.visualizationOptions = new ArrayList<>
//		visualizationOptions.add("Barchart");
//		visualizationOptions.add("LineChart");
//		visualizationOptions.add("Piechart");
//		visualizationOptions.add("TimeSerie");
//		visualizationOptions.add("ScatterChart");
//		visualizationOptions.add("");
	}
	
	public void createChart(JPanel west) {
		
	}
	
	public void addVisualization(Visualization v) {

		visualization.add(v);
	}
	
	public ArrayList<Visualization> getVisualization(){
		return this.visualization;
	}
	
	public void removeVisualization(Visualization v) {
		this.visualization.remove(v);
	}

	public void addDataForRegion(DataForRegion data) {
		this.dataRegionList.add(data);
	}

	public ArrayList<DataForRegion> getDataRegion() {
		return dataRegionList;
	}

	public void setDataRegion(ArrayList<DataForRegion> dataRegion) {
		this.dataRegionList = dataRegion;
	}
	
	public JPanel createNewChart(ArrayList<DataForRegion> arrayList) {
		JPanel panel = new JPanel();
		return panel;
	}
	
	public void closeFrame() {
		
	}
	
	public JPanel getPanel() {
		return new JPanel();
	}
	
	public double getAverageForYear(DataForRegion data, int year) {
		double sum = 0;
		int count = 0;
		for(int i = 0; i < data.values.size(); i++) {
			if(Integer.parseInt(data.dates.get(i).substring(0, 4)) == year) {
				sum += Double.parseDouble(data.values.get(i));
				count++;
			}
		}
		double avg = sum / count;
		return avg;
		
	}
	
	
	
}
