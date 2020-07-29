import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class BDD {

    private Connection connection;

    void connect() throws SQLException {
    	java.util.Properties prop = new Properties();
    	try {
			prop.loadFromXML(new FileInputStream("src/main/resources/Properties.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        this.connection = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("host") + ":" + prop.getProperty("port") + "/" + prop.getProperty("database")+"?serverTimezone=UTC", prop.getProperty("dbuser"), prop.getProperty("dbpass"));
    }

    void disconnect() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    /* CRUD (Create Read Update Delete) */
    boolean update(int id, String firstName, String lastName, Date date) throws SQLException {
        String query = "UPDATE person SET first_name=?,last_name=?,dob=? WHERE SERIAL=?";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        stmt.setInt(1,id);
        stmt.setString(2,firstName);
        stmt.setString(3,lastName);
        stmt.setDate(4,date);

        return stmt.execute();
    }

    boolean delete(int id) throws SQLException {
        String query = "DELETE FROM person WHERE SERIAL=?";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        stmt.setInt(1,id);

        return stmt.execute();
    }

    boolean create(String firstName, String lastName, Date dob) throws SQLException {
        String query = "INSERT INTO person(first_name,last_name,dob) VALUES (?,?,?)";
        PreparedStatement stmt = this.connection.prepareStatement(query);

        stmt.setString(1,firstName);
        stmt.setString(2,lastName);
        stmt.setDate(3, dob);

        return stmt.execute();
    }

    ArrayList<String> read() throws SQLException {
        String query = "SELECT first_name FROM person";
        Statement stmt = this.connection.createStatement();
        ResultSet set = stmt.executeQuery(query);

        ArrayList<String> list = new ArrayList<String>();
        while(set.next()){
            list.add(set.getString("first_name"));
        }

        return list;
    }

    
}