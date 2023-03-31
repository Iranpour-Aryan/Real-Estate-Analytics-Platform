package deliverable_2_implementation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InternalWindow extends JFrame implements ActionListener{
	UserInterface userInterface;
	JFrame frame;
	String method;
	JButton button;
	JButton monthly;
	JPanel panel;
	JTextField inputBox;
	JLabel label;
	Visualization visualization;
	DataForRegion data;
	WekaTimeSeriesPrediction wekaPrediction;
	private JComboBox<String> geoList;
	private JComboBox<String> fromListMonths;
	private JComboBox<String> fromListYears;
	private JComboBox<String> toListMonths;
	private JComboBox<String> toListYears;
	
	public InternalWindow(String method, JComboBox<String> geoList, JComboBox<String> fromListYears, JComboBox<String> fromListMonths, JComboBox<String> toListYears, 
			JComboBox<String> toListMonths, Visualization visualization, 	UserInterface userInterface) {
		this.method = method;
		this.geoList = geoList;
		this.fromListMonths = fromListMonths;
		this.fromListYears = fromListYears;
		this.toListMonths = toListMonths;
		this.toListYears = toListYears;
		this.visualization = visualization;
		this.userInterface = userInterface;
		frame = new JFrame();
    	frame.setSize(900, 600);
    	label = new JLabel("Enter number of months:");
        inputBox = new JTextField(20); // create a text field with 20 columns
        button = new JButton("Done");
        button.addActionListener(this);
        monthly = new JButton("Monthly");
        monthly.addActionListener(this);
        panel = new JPanel();
        panel.add(label);
        panel.add(inputBox);
        panel.add(button);
		JLabel chooseGeoParameter = new JLabel("Choose a geographical parameter: ");
		panel.add(chooseGeoParameter);
        panel.add(geoList);
		JLabel from = new JLabel("From: ");
        panel.add(from);
        panel.add(fromListMonths);
        panel.add(fromListYears);
		JLabel to = new JLabel("To: ");
		panel.add(to);
		panel.add(toListMonths);
        panel.add(toListYears);  
        frame.add(panel);
    	frame.pack();
    	frame.setVisible(true);
    	data = new DataForRegion();
	}
	
	public void getTimeSeriesValues(Parameters parameter) {
		 ArrayList<DataForRegion> dataRegionList = this.visualization.getDataRegion();
		 for(int i = 0; i<dataRegionList.size(); i++) {
			 DataForRegion data_item = dataRegionList.get(i);
			 if(parameter.region.equals(data_item.region) && parameter.startDate.equals(data_item.dates.get(0)) && 
					 parameter.endDate.equals(data_item.dates.get(data_item.dates.size() - 1))) {
				 data = data_item;
			 }
		 }
		 
	}
	
	public Vector<String> getDates(){
		for(int i = 0; i < data.dates.size(); i++) {
			System.out.println(data.dates.get(i));
		}
		return data.dates;
	}
	
	public Vector<String> getValues(){
		for(int i = 0; i < data.dates.size(); i++) {
			System.out.println(data.values.get(i));
		}
		return data.values;
	}
	
	public int getNumMonths(String months) {
		System.out.println(months);
		return Integer.parseInt(months);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
			getTimeSeriesValues(parameter);
			try {
				wekaPrediction = new WekaTimeSeriesPrediction(getValues(),getDates(), getNumMonths(inputBox.getText()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
