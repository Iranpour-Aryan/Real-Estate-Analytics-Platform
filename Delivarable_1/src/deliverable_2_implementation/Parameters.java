package deliverable_2_implementation;

import java.util.HashMap;

public class Parameters {
	DataLoading data;
//	HashMap<String, String> months;
	String region;
	String startMonth;
	String startYear;
	String endMonth;
	String endYear;
	String startDate;
	String endDate;
	
	public Parameters(String region, String startMonth, String startYear, String endMonth, String endYear) {
		this.region = region;
		this.startMonth = startMonth;
		this.startYear = startYear;
		this.endMonth = endMonth;
		this.endYear = endYear;
//		months = new HashMap<String, String>();
//		months.put("1", "Jan");
//		months.put("2", "Feb");
//		months.put("3", "Mar");
//		months.put("4", "Apr");
//		months.put("5", "May");
//		months.put("6", "Jun");
//		months.put("7", "Jul");
//		months.put("8", "Aug");
//		months.put("9", "Sep");
//		months.put("10", "Oct");
//		months.put("11", "Nov");
//		months.put("12", "Dec");
		System.out.println("Region: "+ this.region + " From " + 
							this.startMonth + ":" + this.startYear + " To " 
        					+ this.endMonth +":" + this.endYear);
		startDate = this.startYear + "-" + this.startMonth + "-1";
		endDate = this.endYear + "-" + this.endMonth + "-1";
		try {
			
			data = new DataLoading();
			System.out.println(this.region);
			data.getValues(this.region, this.startDate, this.endDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
