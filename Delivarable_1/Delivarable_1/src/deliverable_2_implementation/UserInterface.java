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

public class UserInterface extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton addView;
	JButton removeView;
	JButton load;
	JButton configure;
	JComboBox<String> viewsList;
	JComboBox<String> geoList;
	JComboBox<String> fromListYears;
	JComboBox<String> fromListMonths;
	JComboBox<String> toListYears;
	JComboBox<String> toListMonths;
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
		initMapping(); //init map
		

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
		south.add(configure);

		south.add(methodLabel);
		south.add(methodsList);
		south.add(recalculate);

		JPanel east = new JPanel();

		// Set charts region
		west = new JPanel();
		west.setLayout(new GridLayout(2, 0));

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(west, BorderLayout.WEST);
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
	

	
//	private void createAfterDeselect(JPanel west) {
//		ArrayList<Visualization> vis_list = visualization.getVisualization();
//		if(count <=0) {
//			for(int i = 0; i < vis_list.size(); i++) {
//				vis_list.get(i).createNewChart();
//			}
//		}
//		count++;
//	}
	
	
	private void deselect(JPanel west, String visualization_value, Visualization vis_delete) {
		JPanel remove_panel = mapToPanel.get(visualization_value);
		this.remove(remove_panel);
		visualization.removeVisualization(vis_delete);
		this.revalidate();
		this.repaint();
	}
	
	private void select(JPanel west, String addValue, Visualization addVis, Parameters parameter) {
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
        	select(west, visualization_value,visualization_addition, parameter);
            System.out.println(viewsList.getSelectedItem().toString());
        }
        if(e.getSource() == removeView) {
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_deletion = mapToVisualization.get(visualization_value);
        	deselect(west,visualization_value, visualization_deletion);
        	System.out.println(viewsList.getSelectedItem().toString());
        }
        if(e.getSource() == load) {
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(data);
        	parameter.storeData();
        	parameter.sendToTable();
        }
        if(e.getSource() == addAdditionalParameters) {
        	String additionalParam = viewsList.getSelectedItem().toString();
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(data);
        	parameter.storeData();
        	parameter.sendToVisualization();
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        }
        
        if(e.getSource() == configure) {
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_new = mapToVisualization.get(visualization_value);
        	deselect(west,visualization_value, visualization_new);
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(data);
        	parameter.storeData();
        	parameter.sendToVisualization();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        	select(west, visualization_value,visualization_addition, parameter);
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

