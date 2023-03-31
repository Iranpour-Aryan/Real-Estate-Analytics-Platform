package deliverable_2_implementation;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConfigureWindow implements ActionListener {
	UserInterface userInterface;
	JFrame frame;
	JPanel panel;
	private JComboBox<String> configColours;
	Vector<String> coloursList;
	private JComboBox<String> configShapes;
	Vector<String> shapesList;
//	private JComboBox<String> configSize;
	Visualization visualization;
	Visualization visualization_type;
	JLabel width;
	JLabel length;
	JTextField lengthField;
	JTextField widthField;
	JButton button;
	String viz_value;
	Vector<String> timeList;
	private JComboBox<String> configTimeList;
	private JLabel timeLabel;
	private JLabel colourLabel;
	private JLabel shapeLabel;

	
	public ConfigureWindow(Visualization visualization, Visualization visualization_type, String visualization_value, UserInterface userInterface) {
		this.visualization = visualization;
		this.visualization_type = visualization_type;
		this.userInterface = userInterface;
		this.viz_value = visualization_value;
		frame = new JFrame();
    	frame.setSize(900, 600);
		coloursList = new Vector<String>();
		addColours();
		colourLabel = new JLabel("Enter your colour: ");
		configColours = new JComboBox<String>(coloursList);
		shapesList = new Vector<String>();
		addShapes();
		shapeLabel = new JLabel("Enter your shape: ");
		configShapes = new JComboBox<String>(shapesList);
		length = new JLabel("Enter length of your axis: ");
		lengthField = new JTextField(20);
		width = new JLabel("Enter width of your axis: ");
		widthField = new JTextField(20);
		timeList = new Vector<String>();
		addTimeList();
		timeLabel = new JLabel("Enter your time period: ");
		configTimeList = new JComboBox<String>(timeList);
		button = new JButton("Done");
        button.addActionListener(this);
        mapToShapes();
        mapToColours();
        panel = new JPanel();
        panel.add(timeLabel);
        panel.add(configTimeList);
        panel.add(colourLabel);
        panel.add(configColours);
        panel.add(shapeLabel);
        panel.add(configShapes);
        panel.add(length);
        panel.add(lengthField);
        panel.add(width);
        panel.add(widthField);
        panel.add(button);
        frame.add(panel);
        frame.pack();
    	frame.setVisible(true);
	}
	
	private void addTimeList() {
		timeList.add("Yearly");
		timeList.add("Monthly");
	}

	private void addShapes() {
		shapesList.add("Circle");
		shapesList.add("Square");
		shapesList.add("Triangle");
	}

	public void addColours() {
		coloursList.add("BLACK");
		coloursList.add("RED");
		coloursList.add("BLUE");
		coloursList.add("YELLOW");
		coloursList.add("GREEN");
	}
	
	public void mapToShapes() {
		this.visualization.shapes.put("Circle", new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
		this.visualization.shapes.put("Square", new Rectangle2D.Double(6.0f, 6.0f, 6.0f, 6.0));
		Polygon triangle = new Polygon();
		triangle.addPoint(0, 10);
		triangle.addPoint(5, 0);
		triangle.addPoint(10, 10);
		this.visualization.shapes.put("Triangle", triangle);

	}
	
	public void mapToColours() {
		this.visualization.colours.put("BLACK", Color.BLACK);
		this.visualization.colours.put("RED", Color.RED);
		this.visualization.colours.put("BLUE", Color.BLUE);
		this.visualization.colours.put("YELLOW", Color.YELLOW);
		this.visualization.colours.put("GREEN", Color.GREEN);

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			Color color = this.visualization.colours.get(configColours.getSelectedItem());
			Shape shape = this.visualization.shapes.get(configShapes.getSelectedItem());
			int width = Integer.parseInt(widthField.getText()); 
			int length = Integer.parseInt(lengthField.getText());
			String timePeriod = configTimeList.getSelectedItem().toString();
			this.userInterface.removeConfiguredVisualization(visualization_type, viz_value);
			this.userInterface.createConfiguredChart(color, shape, width, length, visualization_type, viz_value, timePeriod);
			System.out.println(viz_value);
			System.out.println(visualization_type);
		}
		
	}

}
