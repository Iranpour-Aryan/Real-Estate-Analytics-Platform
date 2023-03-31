package methods;
import org.apache.commons.math3.stat.inference.TTest;

import deliverable_2_implementation.DataForRegion;

public class Ttest {
	
	
	public static String calculateTTest(DataForRegion data1, DataForRegion data2) {
		double[] test1 = new double[data1.values.size()];
		double[] test2 = new double[data2.values.size()];
		add(test1, data1);
		add(test2, data2);
		TTest tt = new TTest();
		String res = "p-value is " + String.format("%.3f", tt.tTest(test1, test2)) + ".";
		res += (tt.tTest(test1, test2, 0.5)) ? " We can reject the null hypothesis" : " We cannot reject the null hypothesis";
		return res;
	}
	
	private static void add(double[] data, DataForRegion d) {
		for(int i = 0; i < d.values.size(); i++) {
			data[i] = Double.parseDouble(d.values.get(i));
		}
	}

	public static void main(String[] args) {
		TTest tt = new TTest();
		double[] sample1 = {30.1, 30.6, 35.4, 20.9, 36.1};
		double[] sample2 = {22.6, 23.2, 24.8, 40};
//		tt.pairedT(sample1, sample2);
		System.out.println("p equals " + tt.tTest(sample1, sample2));
		if(tt.tTest(sample1, sample2, 0.5) == false) {
			System.out.println("We cannot reject the null hypothesis");
		}
		else {
			System.out.println("We can reject the null hypothesis");
		}
	}

}
