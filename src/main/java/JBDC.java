import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

public class JBDC {

	public static void main(String[] args) {
		BDD bdd = new BDD();
		try {
			bdd.connect();
			bdd.read();
			bdd.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
