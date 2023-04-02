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

public class InternalWindow extends JFrame implements ActionListener, WindowStrategy{
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
	DataLoadingAdapter data;
	String wekaMethods;
	String vis_value;
	
	JButton button2;
	private JComboBox<DataForRegion> firstSelection;
	private JComboBox<DataForRegion> secondSelection;
	private JLabel result;
	
	public InternalWindow(String method, Visualization visualization, UserInterface userInterface, DataLoadingAdapter dataLoadingAdapter, String visualization_value) {
		this.vis_value = visualization_value;
		this.userInterface = userInterface;
		this.wekaMethods = method;
		dataForRegion = new DataForRegion();
		this.data = dataLoadingAdapter;
		frame = new JFrame();
    	frame.setSize(900, 600);
	}
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
				WekaFactory wekaFactory = new WekaFactory();
				wekaFactory.buildMethod(dataRegion, getNumMonths(inputBox.getText()), this.method);
//				wekaMethods.buildMethod(dataRegion, getNumMonths(inputBox.getText()));
				this.userInterface.createMonthlyData(dataRegion, this.vis_value);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	@Override
	public void createWindow() {
		Vector<DataForRegion> dataList = new Vector<>();
		for(DataForRegion dataReg: data.getData()) {
			dataList.add(dataReg);
		}
		JLabel firstData = new JLabel("Select your first data:");
		firstSelection = new JComboBox<>(dataList);
    	label = new JLabel("Enter number of months:");
        inputBox = new JTextField(20); // create a text field with 20 columns
        button = new JButton("Done");
        button.addActionListener(this);
        panel = new JPanel();
        panel.add(label);
        panel.add(inputBox);
        panel.add(button);

        panel.add(firstData);
        panel.add(firstSelection);
        frame.add(panel);
    	frame.pack();
    	frame.setVisible(true);
		
	}

}
