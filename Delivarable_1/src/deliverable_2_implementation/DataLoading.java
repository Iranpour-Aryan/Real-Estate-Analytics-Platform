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
    
    public DataLoading() throws Exception {
    	 Class.forName("com.mysql.cj.jdbc.Driver");
         // Setup the connection with the DB
         connect = DriverManager
                 .getConnection("jdbc:mysql://localhost:3306/database_3311", "root", "Ai1130611!"); //third column for password

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
                    		" LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/18100205.csv' "
                    		+ " INTO TABLE NHPI "
                    		+ " FIELDS TERMINATED BY ',' "
                    		+ " ENCLOSED BY '\"'"
                    		+ " LINES TERMINATED BY '\n'"
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
    
    public void getStartID(String region, String startMonth, String startYear, String endMonth, String endYear)throws SQLException{
    	Statement stm = connect.createStatement();
    	String sql = "select min(countid) from nhpi where geo = '" + region + "' AND REF_DATE = '" + startMonth + "-" + startYear.substring(2, 4) + "'";
    	ResultSet rst;
    	rst = stm.executeQuery(sql);
    	String startID = "";
    	while (rst.next()) {
    		startID = rst.getString(1);
        }
    	System.out.println(startID);
    	getEndID(region, startMonth, startYear, endMonth, endYear, startID);
    }

    private void getEndID(String region, String startMonth, String startYear, String endMonth, String endYear, String startID) throws SQLException {
    	Statement stm = connect.createStatement();
    	String sql = "select max(countid) from nhpi where geo = '" + region + "' AND REF_DATE = '" + endMonth + "-" + endYear.substring(2, 4) + "'";
    	ResultSet rst;
    	rst = stm.executeQuery(sql);
    	String endID = "";
    	while (rst.next()) {
    		endID = rst.getString(1);
        }
    	getValues(region, startID, endID);
		
	}

	private Vector<String> getValues(String region, String startID, String endID) throws SQLException {
		Statement stm = connect.createStatement();
        String sql = "select VALUE_ from nhpi where geo = '" + region + "' AND countid between '" + startID + "' AND '" + endID + "'";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> values = new Vector<String>();
        while (rst.next()) {
        	values.add(rst.getString(1));
        }
        for(int i = 0; i < values.size(); i++) {
        	System.out.println(values.get(i));
        }
        getDates(region, startID, endID);
        return values;
		
	}
	private Vector<String> getDates(String region, String startID, String endID) throws SQLException {
		Statement stm = connect.createStatement();
        String sql = "select REF_DATE from nhpi where geo = '" + region + "' AND countid between '" + startID + "' AND '" + endID +"'";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> dates = new Vector<String>();
        while (rst.next()) {
        	dates.add(rst.getString(1));
        }
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
    
    public static void main(String[] args) throws Exception {
		DataLoading d = new DataLoading();
		d.readDataBase();
	}

	public Vector<String> getValues(String region, String startMonth, String startYear, String endMonth, String endYear) throws SQLException {
		Statement stm = connect.createStatement();
    	String sql = "select Value_ from nhpi where geo = " + region + " AND where REF_DATE between " + startMonth + "-" + startYear.substring(2, 4) + " AND " + endMonth + "-" + endYear.substring(2,4);
    	ResultSet rst;
    	rst = stm.executeQuery(sql);
    	Vector<String> values = new Vector<String>();
    	while (rst.next()) {
            values.add(rst.getString(1));
        }
    	return values;
	}

}
