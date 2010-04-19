package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Utente;
import persistence.DataSource;
import persistence.IdBroker;
import persistence.PersistenceException;
import persistence.UtenteDAO;

public class UtenteDAOpostgres implements UtenteDAO {
	
	private final DataSource dataSource;

	private final IdBroker idBroker;
	
	public UtenteDAOpostgres() {
		this.dataSource = DBUtil.getDataSource();
		this.idBroker = new IdBrokerPostgresql();
	}

	public void persist(Utente utente) throws PersistenceException {
		utente.setId(this.idBroker.newId(IdBrokerPostgresql.UTENTE_SEQUENCE_ID));
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = "INSERT INTO utenti " +
								"(id_utente, username, ruolo) VALUES " +
								"(?,  		 ?,        ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, utente.getId());
			statement.setString(2, utente.getUsername());
			statement.setString(3, utente.getRuolo());
			statement.execute();
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire/salvare l'utente.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	public Utente doRetrieveUtenteByUsername(String username) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = 	"SELECT id_utente, username, ruolo " +
							"FROM utenti " +
							"WHERE username = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			result = statement.executeQuery();
			if(!result.next())
				throw new PersistenceException("Impossibile trovare l'utente con username = " + username);
			return newUtenteFromResultSet(result);
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare l'utente.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	private Utente newUtenteFromResultSet(ResultSet result) throws SQLException {
		Utente utente = new UtenteProxy();
		utente.setId(result.getInt("id_utente"));
		utente.setUsername(result.getString("username"));
		utente.setRuolo(result.getString("ruolo"));
		return utente;
	}

}
