package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.IdBroker;
import persistence.PersistenceException;

public class IdBrokerPostgresql implements IdBroker {
	
	static final String PRODOTTO_SEQUENCE_ID 	= "prodotto_sequence_id"; 
	static final String ORDINE_SEQUENCE_ID 		= "ordine_sequence_id"; 
	static final String RIGA_ORDINE_SEQUENCE_ID = "riga_ordine_sequence_id"; 
	static final String FORNITORE_SEQUENCE_ID 	= "fornitore_sequence_id"; 
	static final String UTENTE_SEQUENCE_ID 		= "utente_sequence_id";

	public int newId(String sequence) throws PersistenceException {
		String sqlQuery = "SELECT nextval(?) AS newId";
		Connection connection = DBUtil.getDataSource().getConnection();
		ResultSet result = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, sequence);
			result = statement.executeQuery();
			if (!result.next())
				throw new PersistenceException("Impossibile recuperare l'id, la sequenza è vuota oppure finita.");
			return result.getInt("newId"); 
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile recuperare l'id per le");
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}


}
