package deliverable_2_implementation;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
public class Visualization{
	protected ArrayList<Visualization> visualization;
	protected int index;
	
	
	public Visualization() {
		this.visualization = new ArrayList<Visualization>();
	}
	
	public void createChart(JPanel west) {
		
	}
	
	public void addVisualization(Visualization v) {

		visualization.add(v);
	}
	
	public ArrayList<Visualization> getVisualization(){
		return this.visualization;
	}
	
	public void remove(Visualization v) {
		this.visualization.remove(v);
	}
	
	
	
}
