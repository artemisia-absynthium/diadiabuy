package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

	private String dbURI = "jdbc:postgresql://localhost:5432/diadiabuy";
	private String userName = "postgres";
	private String password = "postgres";

	public Connection getConnection() throws PersistenceException {
		Connection connection;
		try {
		    Class.forName("org.postgresql.Driver");
		    connection = DriverManager.getConnection(dbURI, userName, password);
		} catch (ClassNotFoundException e) {
			throw new PersistenceException("Impossibile caricare il driver", e);
		} catch(SQLException e) {
			throw new PersistenceException("Impossibile stabilire la connessione con il DB", e);
		}
		return connection;
	}

}
