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
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserInterface extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton addView;
	JButton removeView;
	JButton load;
	JButton configure;
	JButton compare;
	JComboBox<String> viewsList;
	JComboBox<String> geoList;
	JComboBox<String> fromListYears;
	JComboBox<String> fromListMonths;
	JComboBox<String> toListYears;
	JComboBox<String> toListMonths;
	JComboBox<String> forecastingMethods;
	JButton selectMethod;
	JButton addAdditionalParameters;
	DataLoading data;
	int count = 0;

	JPanel west;
	Visualization piechart;
	Visualization visualization;
	HashMap<String, Visualization> mapToVisualization;
	HashMap<String, JPanel> mapToPanel;
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
		data = new DataLoading();

		GridLayout gridLayout = new GridLayout(2, 2);
		setLayout(gridLayout);
		mapToVisualization = new HashMap<String, Visualization>();
		mapToPanel = new HashMap<String, JPanel>();
		// Set top bar
		visualization = new Visualization();
		JLabel chooseGeoParameter = new JLabel("Choose a geographical parameter: ");
		DataLoading d = new DataLoading();
		Vector<String> geoNames = d.getCountries();

		geoList = new JComboBox<String>(geoNames);

		Vector<String> forecastingList = new Vector<String>();
		forecastingList.add("Prediction");
		forecastingList.add("Forecasting");

		forecastingMethods = new JComboBox<String>(forecastingList);

		selectMethod = new JButton("Select Method");
		selectMethod.addActionListener(this);

		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");
		Vector<String> years = new Vector<String>();
		Vector<String> months = new Vector<String>();
		for (int i = 2022; i >= 1981; i--) {
			years.add("" + i);
		}
		for (int i = 1; i < 13; i++) {
			months.add("" + i);
		}
		load = new JButton("Load Data");
		load.addActionListener(this);
		fromListYears = new JComboBox<String>(years);
		fromListMonths = new JComboBox<String>(months);
		toListYears = new JComboBox<String>(years);
		toListMonths = new JComboBox<String>(months);

		addAdditionalParameters = new JButton("Add additional parameters");
		addAdditionalParameters.addActionListener(this);

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
		north.add(addAdditionalParameters);

		compare = new JButton("Compare");
		compare.addActionListener(this);
		updateButtonState();
		north.add(compare);
		// Set bottom bar
		JButton recalculate = new JButton("Recalculate");

		JLabel viewsLabel = new JLabel("Available Views: ");

		Vector<String> viewsNames = new Vector<String>();
		viewsNames.add("Line Chart");
		viewsNames.add("Bar Chart");
		viewsNames.add("Scatter Chart");
		viewsNames.add("Time Series");
		viewsList = new JComboBox<String>(viewsNames);
		addView = new JButton("+");
		removeView = new JButton("-");
		addView.addActionListener(this);
		removeView.addActionListener(this);
		configure = new JButton("Configure Visualization");
		configure.addActionListener(this);
		initMapping(); // init map

		JPanel south = new JPanel();
		south.add(viewsLabel);
		south.add(viewsList);
		south.add(addView);
		south.add(removeView);
//		south.add(configure);
//		

		JPanel east = new JPanel();
		east.add(forecastingMethods);
		east.add(selectMethod);

		// Set charts region
		west = new JPanel();
		west.setLayout(new GridLayout(2, 0));

		west.add(configure);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(west, BorderLayout.WEST);
	}
	private void updateButtonState() {
		compare.setEnabled(data.getData().size() > 1);
	}

	private void initMapping() {
		Visualization barchart = new BarChart();
		mapToVisualization.put("Bar Chart", barchart);
		Visualization scatterchart = new ScatterChart();
		mapToVisualization.put("Scatter Chart", scatterchart);
		Visualization linechart = new LineChart();
		mapToVisualization.put("Line Chart", linechart);
		Visualization timeseries = new TimeSerie();
		mapToVisualization.put("Time Series", timeseries);
	}


	public void addToVisualization(Visualization visualization_addition) {
		if (visualization_addition == null) {

		}
		if (visualization.getVisualization().size() == 0) {

		} else {
			visualization_addition.CreateAddData(visualization.getDataRegion());
			this.validate();
		}
	}
	public void deselect(String visualization_value, Visualization vis_delete) {
		JPanel remove_panel = mapToPanel.get(visualization_value);
		System.out.println(remove_panel);
		this.remove(remove_panel);
		visualization.removeVisualization(vis_delete);
		data.getData().removeAll(data.getData());
		visualization.getDataRegion().removeAll(visualization.getDataRegion());
		this.revalidate();
		this.repaint();
	}
	public void removeConfiguredVisualization(Visualization visualization_type, String visualization_value) {
		JPanel remove_panel = mapToPanel.get(visualization_value);
		this.remove(remove_panel);
		visualization.removeVisualization(visualization_type);
		this.revalidate();
		this.repaint();
	}
	
	public void select(String addValue, Visualization addVis) {
		if(visualization.getVisualization().size() < 2) {
			visualization.addVisualization(addVis);
			JPanel holder = addVis.createNewChart(visualization.getDataRegion());
			this.add(holder, BorderLayout.WEST);
			mapToPanel.put(addValue, holder);
			this.validate();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addView){
			data.setVisualization(visualization);
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(data);
        	parameter.storeData();
        	parameter.sendToVisualization();
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        	select(visualization_value,visualization_addition);
            System.out.println(viewsList.getSelectedItem().toString());
        }
        if(e.getSource() == removeView) {
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_deletion = mapToVisualization.get(visualization_value);
        	deselect(visualization_value, visualization_deletion);
        	System.out.println(viewsList.getSelectedItem().toString());
        }
		if (e.getSource() == load) {
			Parameters parameter = new Parameters(geoList.getSelectedItem().toString(),
					fromListMonths.getSelectedItem().toString(), fromListYears.getSelectedItem().toString(),
					toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
			parameter.setDataLoading(data);
			parameter.storeData();
			parameter.sendToTable();
			updateButtonState();
		}
		if(e.getSource() == addAdditionalParameters) {
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(data);
        	parameter.storeData();
        	parameter.sendToTable();
        }
        if(e.getSource() == addAdditionalParameters) {
			data.setVisualization(visualization);
        	String additionalParam = viewsList.getSelectedItem().toString();
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(data);
        	parameter.storeData();
        	parameter.sendToVisualization();
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        	addToVisualization(visualization_addition);
        }
        
        if(e.getSource() == configure) {
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        	new ConfigureWindow(this.visualization,visualization_addition,visualization_value, this);
        }

		if (e.getSource() == selectMethod) {
			String method = forecastingMethods.getSelectedItem().toString();
        	new InternalWindow(method, geoList, fromListYears, fromListMonths, toListYears,toListMonths, visualization, this);
		}

		if (e.getSource() == compare) {
			new InternalWindow(data);
		}
        
        if(e.getSource() == selectMethod) {
        	String method = forecastingMethods.getSelectedItem().toString();
        	InternalWindow window = new InternalWindow(method, geoList, fromListYears, fromListMonths, toListYears,toListMonths, visualization, this);
        }
    }

	public static void main(String[] args) throws Exception {
		frame = UserInterface.getInstance();
		frame.setSize(900, 600);
		frame.pack();
		frame.setVisible(true);
	}
	// TODO Auto-generated method stub

	public void createConfiguredChart(Color color, Shape shape, int width, int length, Visualization visualization_type, String viz_value) {
		this.visualization.addVisualization(visualization_type);
		mapToVisualization.put(viz_value, visualization_type);
		System.out.println(visualization_type);
		JPanel holder = visualization_type.CreateConfiguredChart(color, shape, width, length, this.visualization.dataRegionList);
		this.add(holder, BorderLayout.WEST);
		mapToPanel.put(viz_value, holder);
		this.validate();
	}

}
