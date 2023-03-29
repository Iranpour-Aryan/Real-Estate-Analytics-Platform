package deliverable_2_implementation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class Table {
	public ArrayList<DataForRegion> data;
	String[] colName;
	Object[][] products;
	double avgVal = 0.0;
	double minVal = 0.0, maxVal = 0.0;
	double stdDev = 0.0;
	

	public Table() {
		data = new ArrayList<DataForRegion>();
	}

	public void CreateTable(DataForRegion dataRegion) {
		colName = new String[] { "Dates", "Values" };
		products = new Object[dataRegion.values.size()][2];
		constructData(dataRegion);

		JFrame frame = new JFrame("Simple Table Example");
		frame.setSize(400, 600);
		
		JTable table = new JTable(products, colName);
		JScrollPane scrollTable = new JScrollPane(table);
		
		JPanel summaryTable = new JPanel(new GridLayout(4, 1));
		summaryTable.setPreferredSize(new Dimension(400, 100));
		Font font = new Font("Serif", Font.PLAIN, 20);
	
		JLabel avg = new JLabel("Average value: " + String.format("%.2f", avgVal));
		avg.setHorizontalAlignment(JLabel.CENTER);
	    avg.setVerticalAlignment(JLabel.CENTER);
		avg.setFont(font);
		summaryTable.add(avg);
		
		JLabel std = new JLabel("Standard Deviation value: " + String.format("%.2f", stdDev));
		std.setHorizontalAlignment(JLabel.CENTER);
	    std.setVerticalAlignment(JLabel.CENTER);
		std.setFont(font);
		summaryTable.add(std);
		
		
		JLabel max = new JLabel("Max value: " + String.format("%.2f", maxVal));
		max.setHorizontalAlignment(JLabel.CENTER);
	    max.setVerticalAlignment(JLabel.CENTER);
		max.setFont(font);
		summaryTable.add(max);
		
		
		JLabel min = new JLabel("Min value: " + String.format("%.2f", minVal));
		min.setHorizontalAlignment(JLabel.CENTER);
	    min.setVerticalAlignment(JLabel.CENTER);
		min.setFont(font);
		summaryTable.add(min);
		
		
		JPanel panel = new JPanel();
		JButton button = new JButton("Switch");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(frame.getContentPane().getComponent(1) == summaryTable) {
					frame.remove(summaryTable);
					frame.add(scrollTable, BorderLayout.CENTER);
				} else {
					frame.remove(scrollTable);
					frame.add(summaryTable, BorderLayout.CENTER);
				}
				
				frame.pack();
			}
		});
		panel.add(button);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), dataRegion.region,
				TitledBorder.LEFT, TitledBorder.TOP));
        
		// create scroll pane for wrapping the table and add
		// it to the frame
		
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.NORTH);
		frame.add(scrollTable, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	

	private void constructData(DataForRegion dataRegion) {
		int count = 0;
		double total = 0.0;
		minVal = Float.MAX_VALUE; 
		maxVal = Float.MIN_VALUE;
		for (int i = 0; i < dataRegion.values.size(); i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					products[i][j] = dataRegion.dates.get(i);
				} else {
					products[i][j] = dataRegion.values.get(i);
					total += Double.parseDouble(dataRegion.values.get(i));
					maxVal = Math.max(maxVal, total);
					minVal = Math.min(minVal, total);
				}
			}
			count++;
		}
		avgVal = (count != 0) ? total / count : 0.0;
		minVal = (minVal == Float.MAX_VALUE) ? 0.0 : minVal;
		maxVal = (maxVal == Float.MIN_VALUE) ? 0.0 : maxVal;
		calStdDev(dataRegion);
	}
	
	private void calStdDev(DataForRegion d) {
		for(int i = 0; i < d.values.size(); i++) {
			stdDev += Math.pow(Double.parseDouble(d.values.get(1)) - avgVal, 2);
		}
		stdDev = Math.sqrt(stdDev / d.values.size()); 
	}

	public void addData(DataForRegion d) {
		this.data.add(d);
		this.CreateTable(d);
	}

}