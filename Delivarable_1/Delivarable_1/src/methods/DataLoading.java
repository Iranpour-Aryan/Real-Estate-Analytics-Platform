package methods;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DataLoading {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/database_3311", "root", ""); //third column for password

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

}
