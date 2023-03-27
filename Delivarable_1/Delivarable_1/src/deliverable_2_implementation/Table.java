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
//		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTable table = new JTable(products, colName);
		JScrollPane scrollTable = new JScrollPane(table);
		
		JPanel summaryTable = new JPanel(new GridLayout(3, 1));
		Font font = new Font("Serif", Font.PLAIN, 20);
	
		JLabel avg = new JLabel("Average value: " + String.format("%.2f", avgVal));
		avg.setHorizontalAlignment(JLabel.CENTER);
	    avg.setVerticalAlignment(JLabel.CENTER);
		avg.setFont(font);
		summaryTable.add(avg);
		
		
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
		
		JPanel cards = new JPanel(new CardLayout());
		cards.add(scrollTable);
		cards.add(summaryTable);
		
		JPanel panel = new JPanel();
		JButton button = new JButton("Switch");
		button.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed (ActionEvent e) {
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.next(cards);
		    }       
		});
		panel.add(button);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), dataRegion.region,
				TitledBorder.LEFT, TitledBorder.TOP));
        
		// create scroll pane for wrapping the table and add
		// it to the frame
		
		frame.setLayout(new BorderLayout());
		frame.add(cards, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
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
		
	}

	public void addData(DataForRegion d) {
		this.data.add(d);
		this.CreateTable(d);
	}

}