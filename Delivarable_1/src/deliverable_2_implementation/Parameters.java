package deliverable_2_implementation;

import java.util.HashMap;

public class Parameters {
	DataLoading data;
	DataForRegion dataRegion[];
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
