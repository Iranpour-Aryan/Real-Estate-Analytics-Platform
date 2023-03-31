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
		System.out.println("Region: " + this.region + " From " + this.startMonth + ":" + this.startYear + " To "
				+ this.endMonth + ":" + this.endYear);
		startDate = this.startYear + "-";
		endDate = this.endYear + "-";
		if (Integer.parseInt(startMonth) < 10) {
			startDate += "0";
			endDate += "0";
		}
		startDate += this.startMonth + "-01";
		endDate += this.endMonth + "-01";


	}

	public void storeData() {
		try {
			boolean check = false;
			for (DataForRegion d : data.getData()) {
				if (d.getRegion().equals(region) && d.getDates().get(0).equals(startDate)
						&& d.getDates().get(d.getDates().size() - 1).equals(endDate)) {
					check = true;
					break;
				}
			}
			if (!check) {
				System.out.println(this.region);
				data.getValues(this.region, this.startDate, this.endDate);
				data.putData();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToTable() {
		data.addToTable();
	}

	public void sendToVisualization() {
		data.addToVisualization();
	}

	public void setDataLoading(DataLoading dataLoading) {
		data = dataLoading;
	}

}
