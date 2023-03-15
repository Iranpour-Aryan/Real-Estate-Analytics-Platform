package deliverable_2_implementation;

/*************************************************
 * FALL 2022
 * EECS 3311 GUI SAMPLE CODE
 * ONLT AS A REFERENCE TO SEE THE USE OF THE jFree FRAMEWORK
 * THE CODE BELOW DOES NOT DEPICT THE DESIGN TO BE FOLLOWED 
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserInterface extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton addView;
	JButton removeView;
	JButton load;
	JComboBox<String> viewsList;
	JComboBox<String> geoList;
	JComboBox<String> fromListYears;
	JComboBox<String> fromListMonths;
	JComboBox<String> toListYears;
	JComboBox<String> toListMonths;
	
	JPanel west;
	Visualization piechart;
	Visualization visualization;
	static JFrame frame;
	private static UserInterface instance;

	public static UserInterface getInstance() throws Exception {
		if (instance == null)
			instance = new UserInterface();

		return instance;
	}

	private UserInterface() throws Exception {
		// Set window title
		super("Country Statistics");
		// Set top bar
		visualization = new Visualization();
		JLabel chooseGeoParameter = new JLabel("Choose a geographical parameter: ");
		DataLoading d = new DataLoading();
		Vector<String> geoNames = d.getCountries();

		geoList = new JComboBox<String>(geoNames);

		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");
		Vector<String> years = new Vector<String>();
		Vector<String> months = new Vector<String>();
		for (int i = 2022; i >= 1981; i--) {
			years.add("" + i);
		}
		for(int i = 1; i < 13; i++) {
			months.add("" + i);
		}
		load = new JButton("Load Data");
		load.addActionListener(this);
		fromListYears = new JComboBox<String>(years);
		fromListMonths = new JComboBox<String>(months);
		toListYears = new JComboBox<String>(years);
		toListMonths = new JComboBox<String>(months);

		JPanel north = new JPanel();
		north.add(chooseGeoParameter);
		north.add(geoList);
		north.add(from);
		north.add(fromListMonths);
		north.add(fromListYears);
		north.add(to);
		north.add(toListMonths);
		north.add(toListYears);
		north.add(load);

		// Set bottom bar
		JButton recalculate = new JButton("Recalculate");

		JLabel viewsLabel = new JLabel("Available Views: ");

		Vector<String> viewsNames = new Vector<String>();
		viewsNames.add("Pie Chart");
		viewsNames.add("Line Chart");
		viewsNames.add("Bar Chart");
		viewsNames.add("Scatter Chart");
		viewsNames.add("Report");
		viewsList = new JComboBox<String>(viewsNames);
		addView = new JButton("+");
		removeView = new JButton("-");
		addView.addActionListener(this);
		removeView.addActionListener(this);
		

		JLabel methodLabel = new JLabel("        Choose analysis method: ");

		Vector<String> methodsNames = new Vector<String>();
		methodsNames.add("Mortality");
		methodsNames.add("Mortality vs Expenses");
		methodsNames.add("Mortality vs Expenses & Hospital Beds");
		methodsNames.add("Mortality vs GDP");
		methodsNames.add("Unemployment vs GDP");
		methodsNames.add("Unemployment");

		JComboBox<String> methodsList = new JComboBox<String>(methodsNames);

		JPanel south = new JPanel();
		south.add(viewsLabel);
		south.add(viewsList);
		south.add(addView);
		south.add(removeView);

		south.add(methodLabel);
		south.add(methodsList);
		south.add(recalculate);

		JPanel east = new JPanel();

		// Set charts region
		west = new JPanel();
		west.setLayout(new GridLayout(2, 0));
		createCharts(west);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(west, BorderLayout.WEST);
	}

	private void createCharts(JPanel west) {
		piechart = new PieChart();
		piechart.createChart(west);
		Visualization barchart = new BarChart();
		barchart.createChart(west);
		Visualization scatterchart = new ScatterChart();
		scatterchart.createChart(west);
		Visualization reportchart = new ReportChart();
		reportchart.createChart(west);
		Visualization linechart = new LineChart();
		linechart.createChart(west);
		Visualization timeseries = new TimeSerie();
		timeseries.createChart(west);
		visualization.addVisualization(piechart);
		visualization.addVisualization(barchart);
		visualization.addVisualization(scatterchart);
	}
	
	private void createAfterDeselect(JPanel west, Visualization vis_create) {
		frame.getContentPane().removeAll();
		frame.getContentPane().repaint();
		ArrayList<Visualization> vis_list = visualization.getVisualization();
		for(int i = 0; i < vis_list.size(); i++) {
			vis_list.get(i).createChart(west);
		}
	}
	
	private void deselect(JPanel west, Visualization removedVis) {
		if(removedVis instanceof PieChart) {
			visualization.remove(removedVis);
		}
		this.createAfterDeselect(west, visualization);
	}
	
	public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addView){
            System.out.println(viewsList.getSelectedItem().toString());
        }
        if(e.getSource() == removeView) {
        	deselect(west,piechart);
        	System.out.println(viewsList.getSelectedItem().toString());
        }
        if(e.getSource() == load) {
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        }
    }

	public static void main(String[] args) throws Exception {
		frame = UserInterface.getInstance();
		frame.setSize(900, 600);
		frame.pack();
		frame.setVisible(true);
	}
	// TODO Auto-generated method stub

}

