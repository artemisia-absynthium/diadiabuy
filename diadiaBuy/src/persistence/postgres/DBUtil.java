package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import persistence.DataSource;

public class DBUtil {

	private static final Logger log = Logger.getLogger(DBUtil.class);

	public static void SilentClose(Connection connection,
			PreparedStatement statement, ResultSet result) {
		try {
			if (result != null)
				result.close();
		} catch (SQLException e) {
			log.warn("Impossibile chiudere il result set", e);
		}
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			log.warn("Impossibile chiudere lo statement", e);
		}
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			log.warn("Impossibile chiudere la connessione", e);
		}
	}
	
	public static DataSource getDataSource() {
		return new DataSource(); //TODO: Non mi piace così il data source
	}
}