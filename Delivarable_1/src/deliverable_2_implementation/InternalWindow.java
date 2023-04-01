package deliverable_2_implementation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
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
	DataForRegion dataForRegion;
	DataLoading data;
	WekaMethods wekaMethods;
	private JComboBox<String> geoList;
	private JComboBox<String> fromListMonths;
	private JComboBox<String> fromListYears;
	private JComboBox<String> toListMonths;
	private JComboBox<String> toListYears;
	String vis_value;
	
	JButton button2;
	private JComboBox<DataForRegion> firstSelection;
	private JComboBox<DataForRegion> secondSelection;
	private JLabel result;
	
	public InternalWindow(WekaMethods wekaMethod, Visualization visualization, UserInterface userInterface, DataLoading data, String visualization_value) {
		this.vis_value = visualization_value;
		this.userInterface = userInterface;
		this.wekaMethods = wekaMethod;
		Vector<DataForRegion> dataList = new Vector<>();
		for(DataForRegion dataReg: data.getData()) {
			dataList.add(dataReg);
		}
		JLabel firstData = new JLabel("Select your first data:");
		firstSelection = new JComboBox<>(dataList);
		this.data = data;
		frame = new JFrame();
    	frame.setSize(900, 600);
    	label = new JLabel("Enter number of months:");
        inputBox = new JTextField(20); // create a text field with 20 columns
        button = new JButton("Done");
        button.addActionListener(this);
        panel = new JPanel();
        panel.add(label);
        panel.add(inputBox);
        panel.add(button);
//		JLabel chooseGeoParameter = new JLabel("Choose a geographical parameter: ");
//		panel.add(chooseGeoParameter);
//        panel.add(geoList);
//		JLabel from = new JLabel("From: ");
//        panel.add(from);
//        panel.add(fromListMonths);
//        panel.add(fromListYears);
//		JLabel to = new JLabel("To: ");
//		panel.add(to);
//		panel.add(toListMonths);
//        panel.add(toListYears);
        panel.add(firstData);
        panel.add(firstSelection);
        frame.add(panel);
    	frame.pack();
    	frame.setVisible(true);
    	dataForRegion = new DataForRegion();
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
	
//	public void getTimeSeriesValues(Parameters parameter) {
//		 ArrayList<DataForRegion> dataRegionList = this.data.getData();
//		 for(int i = 0; i<dataRegionList.size(); i++) {
//			 DataForRegion data_item = dataRegionList.get(i);
//			 if(parameter.region.equals(data_item.region) && parameter.startDate.equals(data_item.dates.get(0)) && 
//					 parameter.endDate.equals(data_item.dates.get(data_item.dates.size() - 1))) {
//				 dataForRegion = data_item;
//			 }
//		 }
//		 
//	}
	
//	public Vector<String> getDates(){
//		for(int i = 0; i < dataForRegion.dates.size(); i++) {
//			System.out.println(dataForRegion.dates.get(i));
//		}
//		return dataForRegion.dates;
//	}
	
//	public Vector<String> getValues(){
//		for(int i = 0; i < dataForRegion.dates.size(); i++) {
//			System.out.println(dataForRegion.values.get(i));
//		}
//		return dataForRegion.values;
//	}
	
	public int getNumMonths(String months) {
		System.out.println(months);
		return Integer.parseInt(months);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			DataForRegion dataRegion = (DataForRegion) firstSelection.getSelectedItem();
			try {
//				this.userInterface.createMonthlyData(dataRegion,this.vis_value);
				wekaMethods.buildMethod(dataRegion, getNumMonths(inputBox.getText()));
				this.userInterface.createMonthlyData(dataRegion, this.vis_value);
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
