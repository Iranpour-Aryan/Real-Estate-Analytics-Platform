package methods;
import org.apache.commons.math3.stat.inference.TTest;

public class Ttest {

	public static void main(String[] args) {
		TTest tt = new TTest();
		double[] sample1 = {30.1, 30.6, 35.4, 20.9, 36.1};
		double[] sample2 = {22.6, 23.2, 24.8, 40, 40.1};
		tt.pairedT(sample1, sample2);
		System.out.println("p equals " + tt.pairedTTest(sample1, sample2));
		if(tt.pairedTTest(sample1, sample2, 0.5) == false) {
			System.out.println("We cannot reject the null hypothesis");
		}
		else {
			System.out.println("We can reject the null hypothesis");
		}
	}

}
