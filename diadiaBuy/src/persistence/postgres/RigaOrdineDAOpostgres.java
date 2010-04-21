package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Ordine;
import model.RigaOrdine;
import persistence.DataSource;
import persistence.IdBroker;
import persistence.PersistenceException;
import persistence.RigaOrdineDAO;

public class RigaOrdineDAOpostgres implements RigaOrdineDAO {

	private final DataSource dataSource;

	private final IdBroker idBroker;
	
	public RigaOrdineDAOpostgres() {
		this.dataSource = DBUtil.getDataSource();
		this.idBroker = new IdBrokerPostgresql();
	}

	RigaOrdine newRigaOrdine(Ordine ordine, ResultSet result) throws SQLException {
		RigaOrdineProxy rigaOrdine = new RigaOrdineProxy();
		rigaOrdine.setId(result.getInt("id_riga_ordine"));
		rigaOrdine.setQuantita(result.getInt("quantita"));
		rigaOrdine.setNumeroDiRiga(result.getInt("numero_di_riga"));
		rigaOrdine.setIdProdotto(result.getInt("id_prodotto"));
		rigaOrdine.setNomeProdotto(result.getString("nome_prodotto"));
		rigaOrdine.setOrdine(ordine);
		return rigaOrdine;
	}

	public void persist(RigaOrdine rigaOrdine) throws PersistenceException {
		rigaOrdine.setId(this.idBroker.newId(IdBrokerPostgresql.RIGA_ORDINE_SEQUENCE_ID));
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = "INSERT INTO righe_ordine (id_riga_ordine, numero_di_riga, quantita, id_prodotto, id_ordine, nome_prodotto) VALUES " +
											 		"(?,               ?,              ?,        ?,          ?,         ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, rigaOrdine.getId());
			statement.setInt(2, rigaOrdine.getNumeroDiRiga());
			statement.setInt(3, rigaOrdine.getQuantita());
			statement.setInt(4, rigaOrdine.getProdotto().getId());
			statement.setInt(5, rigaOrdine.getOrdine().getId());
			statement.setString(6, rigaOrdine.getProdotto().getNome());
			statement.execute();
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare il prodotto.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}
	
}
