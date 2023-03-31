package deliverable_2_implementation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.*;
import weka.classifiers.functions.LinearRegression;
import weka.*;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class WekaTimeSeriesPrediction {
	public WekaTimeSeriesPrediction(Vector<String> values, Vector<String> dates, int months) throws Exception {
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute("Timestamp"));
		attributes.add(new Attribute("Value"));

		// Create the dataset
		Instances data = new Instances("time_series", attributes, values.size());
		data.setClassIndex(data.numAttributes() - 1); // Set the class attribute index

		// Convert the date strings to timestamps
		List<Long> timestamps = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < dates.size(); i++) {
		    String dateString = dates.get(i);
		    Date date = dateFormat.parse(dateString);
		    long timestamp = date.getTime();
		    timestamps.add(timestamp);

		    // Add the instance to the dataset
		    Instance instance = new DenseInstance(attributes.size());
		    instance.setValue(attributes.get(0), timestamp);
		    instance.setValue(attributes.get(1), Double.parseDouble(values.get(i)));
		    data.add(instance);
		}

	    // Convert the time series data to the required format
	    Filter filter = new weka.filters.unsupervised.attribute.TimeSeriesDelta();
	    filter.setInputFormat(data);
	    Instances transformedData = Filter.useFilter(data, filter);

	    // Split the data into training and testing sets
	    int trainSize = (int) Math.round(transformedData.numInstances() * 0.8);
	    int testSize = months; // predict the next n months
	    Instances trainData = new Instances(transformedData, 0, trainSize);
	    Instances testData = new Instances(transformedData, trainSize, testSize);

	    // Set the target attribute
	    trainData.setClassIndex(trainData.numAttributes() - 1);
	    testData.setClassIndex(testData.numAttributes() - 1);

	    // Remove unwanted attributes
	    Remove remove = new Remove();
	    remove.setAttributeIndices("1"); // Remove the first attribute (timestamp)
	    remove.setInputFormat(trainData);
	    Instances filteredTrainData = Filter.useFilter(trainData, remove);
	    Instances filteredTestData = Filter.useFilter(testData, remove);

	    // Train the linear regression model
	    LinearRegression lin = new LinearRegression();
	    lin.buildClassifier(filteredTrainData);

	    // Evaluate the linear regression model
	    Evaluation eval = new Evaluation(filteredTrainData);
	    eval.evaluateModel(lin, filteredTestData);

	    // Make predictions on new data
	    Instance newInstance = filteredTestData.lastInstance();
	    double prediction = lin.classifyInstance(newInstance);
	    System.out.println("Predicted value: " + prediction);
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> 8682079deb1b16332627aaab18323cc466b15d22
