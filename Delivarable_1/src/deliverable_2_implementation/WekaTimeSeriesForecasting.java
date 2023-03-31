package deliverable_2_implementation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.timeseries.*;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaTimeSeriesForecasting extends WekaMethods{
	
	public void buildMethod(Vector<String> values, Vector<String> dates, int months) throws Exception {
	    // Create the attribute list
	    ArrayList<Attribute> attributes = new ArrayList<>();
	    attributes.add(new Attribute("Timestamp"));
	    attributes.add(new Attribute("Value"));
	    
	    // Create the dataset
	    Instances data = new Instances("time_series", attributes, values.size());

	    // Convert the date strings to timestamps
	    List<Long> timestamps = new ArrayList<>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    for (int i = 0; i < dates.size(); i++) {
	        String dateString = dates.get(i);
	        Date date = dateFormat.parse(dateString);
	        long timestamp = date.getTime();
	        timestamps.add(timestamp);
//	        System.out.println(timestamp);
	        // Add the instance to the dataset
	        Instance instance = new DenseInstance(attributes.size());
	        instance.setValue(attributes.get(0), timestamp);
	        instance.setValue(attributes.get(1), Double.parseDouble(values.get(i)));
	        data.add(instance);
	        System.out.println(instance);
	    }

	    // Sort the instances by timestamp
	    data.sort(1);

	    // Set the class attribute index
	    data.setClassIndex(data.numAttributes() - 1);

	    // Convert the time series data to the required format
	    WekaForecaster forecaster = new WekaForecaster();
	    forecaster.setFieldsToForecast("Value");
	    forecaster.setBaseForecaster(new SMOreg());
	    forecaster.getTSLagMaker().setTimeStampField("Timestamp");
	    forecaster.getTSLagMaker().setMinLag(1);
	    forecaster.getTSLagMaker().setMaxLag(12);
	    forecaster.getTSLagMaker().setAddMonthOfYear(true);
	    forecaster.getTSLagMaker().setAddQuarterOfYear(false);

	    // Build the forecaster
	    forecaster.buildForecaster(data, System.out);

	    // Set the number of months to forecast
	    int numMonthsToForecast = months;

	    // Generate the forecast
	    forecaster.primeForecaster(data);
	    List<List<NumericPrediction>> forecast = forecaster.forecast(numMonthsToForecast, System.out);

	    // Print the forecast
	    for (int i = 0; i < numMonthsToForecast; i++) {
	        List<NumericPrediction> predsAtStep = forecast.get(i);
	        NumericPrediction predForTarget = predsAtStep.get(0);
	        System.out.println("Month " + (i+1) + " predicted value: " + predForTarget.predicted());
	    }
	}


}
