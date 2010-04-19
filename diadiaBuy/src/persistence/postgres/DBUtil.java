package persistence.postgres;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import persistence.DataSource;

public class DBUtil {

	public static void silentClose(Connection connection) {
		silentClose(connection, null);
	}
	
	public static void silentClose(Connection connection, Statement statement) {
		silentClose(connection, statement, null);
	}
	
	public static void silentClose(Connection connection,
			Statement statement, ResultSet result) {
		try {
			if (result != null)
				result.close();
		} catch (SQLException e) {
			System.err.println("Impossibile chiudere il result set");
			e.printStackTrace();
		}
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			System.err.println("Impossibile chiudere lo statement");
			e.printStackTrace();
		}
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.err.println("Impossibile chiudere la connessione");
			e.printStackTrace();
		}
	}
	
	public static DataSource getDataSource() {
		return new DataSource();
	}
}