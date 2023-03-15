package deliverable_2_implementation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;


public class Table {
	public ArrayList<DataForRegion> data;
	
	public Table() {
		data = new ArrayList<DataForRegion>();
	}

    public void CreateTable(DataForRegion dataRegion) {
        String[] colName = new String[] {"Dates" ,"Values" };
        Object[][] products = new Object[dataRegion.values.size()][2];
        for(int i = 0; i < dataRegion.values.size(); i++) {
        	for(int j = 0; j < 2; j++) {
        		if(j == 0) {
        			products[i][j] = dataRegion.dates.get(i);
        		}
        		else {
        			products[i][j] = dataRegion.values.get(i);
        		}
        	}
        }
            

        JFrame frame = new JFrame( "Simple Table Example" );
        JTable table = new JTable( products, colName );
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
        	      BorderFactory.createEtchedBorder(), dataRegion.region, TitledBorder.LEFT,
        	      TitledBorder.TOP));

        // create scroll pane for wrapping the table and add
        // it to the frame
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible( true ); 
    }
	
	public void addData(DataForRegion d) {
		this.data.add(d);
		this.CreateTable(d);
	}

}
