package deliverable_2_implementation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
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

import methods.Ttest;

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
	
	JButton button2;
	private JComboBox<DataForRegion> firstSelection;
	private JComboBox<DataForRegion> secondSelection;
	private JLabel result;
	

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
	
	
	public InternalWindow(DataLoading d) {
		frame = new JFrame();
		frame.setSize(900, 600);
		Vector<DataForRegion> dataList = new Vector<>();
		for(DataForRegion dataReg: d.getData()) {
			dataList.add(dataReg);
		}
		
		JLabel firstData = new JLabel("Select your first data:");
		firstSelection = new JComboBox<>(dataList);
		
		JLabel secondData = new JLabel("Select your second data:");
		secondSelection = new JComboBox<>(dataList);
		
	    button2 = new JButton("Operate tests");
		button2.addActionListener(this);
		
		result = new JLabel();
		result.setLocation(new Point(button2.getX(), button2.getY() + 5));
		
		panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(900, 300));
		panel.add(firstData);
		panel.add(firstSelection);
		panel.add(secondData);
		panel.add(secondSelection);
		panel.add(button2);
		panel.add(result);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
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
		if(e.getSource() == button2) {
			DataForRegion d1 = (DataForRegion) firstSelection.getSelectedItem();
			DataForRegion d2 = (DataForRegion) secondSelection.getSelectedItem();
			result.setText("Result: " + Ttest.calculateTTest(d1, d2));
			result.setHorizontalAlignment(JLabel.CENTER);
			result.setVerticalAlignment(JLabel.CENTER);
			Font font = new Font("Serif", Font.PLAIN, 20);
			result.setFont(font);
			frame.revalidate();
			frame.repaint();
		}
		
	}

}
