package deliverable_2_implementation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class DataLoading {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    Table table;
    Visualization visualization;
    DataForRegion dataForRegion;
    
    public DataLoading() throws Exception {
    	
    	 Class.forName("com.mysql.cj.jdbc.Driver");
         // Setup the connection with the DB
         connect = DriverManager
                 .getConnection("jdbc:mysql://localhost:3306/database_3311", "root", "Ai1130611!"); //third column for password
//         visualization = new Visualization();

    }

    public void readDataBase() throws Exception {
        try {
//            // This will load the MySQL driver, each DB has its own driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            // Setup the connection with the DB
//            connect = DriverManager
//                    .getConnection("jdbc:mysql://localhost:3306/database_3311", "root", ""); //third column for password

            // Statements allow to issue SQL queries to the database
            Statement stmnt = connect.createStatement();
            stmnt
                    .execute(
                    		" LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/data_values.csv' "
                    		+ " INTO TABLE NHPI "
                    		+ " FIELDS TERMINATED BY ',' "
                    		+ " ENCLOSED BY '\"'"
                    		+ " LINES TERMINATED BY '\r\n'"
                    		+ " IGNORE 1 ROWS;");

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }
    
    public Vector<String> getCountries() throws SQLException{
    	Statement stm = connect.createStatement();
        String sql = "select distinct GEO from nhpi";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> countries = new Vector<String>();
        while (rst.next()) {
            countries.add(rst.getString(1));
        }
        return countries;
    }

	public Vector<String> getValues(String region, String startDate, String endDate) throws SQLException {
		Statement stm = connect.createStatement();
        String sql = "select VALUE_ from nhpi where geo = '" + region + "' AND REF_DATE >= '" + startDate + "' AND REF_DATE <= '" + endDate + "'";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> values = new Vector<String>();
        while (rst.next()) {
        	values.add(rst.getString(1));
        }
        for(int i = 0; i < values.size(); i++) {
        	System.out.println(values.get(i));
        }
        dataForRegion = new DataForRegion();
        dataForRegion.setRegion(region);
        dataForRegion.setValues(values);
        
        table = new Table();
        getDates(region, startDate, endDate);
        return values;
		
	}
	public Vector<String> getDates(String region, String startDate, String endDate) throws SQLException {
		Statement stm = connect.createStatement();
        String sql = "select REF_DATE from nhpi where geo = '" + region + "' AND REF_DATE >= '" + startDate + "' AND REF_DATE <= '" + endDate + "'";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> dates = new Vector<String>();
        while (rst.next()) {
        	dates.add(rst.getString(1));
        }
        for(int i = 0; i < dates.size(); i++) {
        	System.out.println(dates.get(i));
        }
        dataForRegion.setDates(dates);
//        visualization.addDataForRegion(dataForRegion);
        return dates;
	}

	// You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
    
    public void addToTable() {
    	table.addData(dataForRegion);
    }
    
    public void setVisualization(Visualization setVis) {
    	visualization = setVis;
    }
    
    public void addToVisualization() {
    	visualization.addDataForRegion(dataForRegion);
    }
    
    public static void main(String[] args) throws Exception {
		DataLoading d = new DataLoading();
		d.readDataBase();
	}


}
