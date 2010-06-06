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
		Connection connection = this.dataSource.getConnection();
		try {
			this.persistOrUpdate(connection, rigaOrdine);
		} finally {
			DBUtil.silentClose(connection);
		}
	}

	public void persistOrUpdate(Connection connection, RigaOrdine rigaOrdine) throws PersistenceException {
		if(rigaOrdine.getId() == 0)
			persist(connection, rigaOrdine);
		else
			update(connection, rigaOrdine);
	}

	private void update(Connection connection, RigaOrdine rigaOrdine) throws PersistenceException {
		PreparedStatement statement = null;
		try {
			String query = "UPDATE righe_ordine " +
							"SET " +
								"numero_di_riga = ?, " +
								"quantita = ?, " +
								"nome_prodotto = ? " +
							"WHERE id_riga_ordine = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, rigaOrdine.getNumeroDiRiga());
			statement.setInt(2, rigaOrdine.getQuantita());
			statement.setString(3, rigaOrdine.getProdotto().getNome());
			statement.setInt(4, rigaOrdine.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare il prodotto.", e);
		} finally {
			DBUtil.silentClose(null, statement);
		}
	}

	public void persist(Connection connection, RigaOrdine rigaOrdine)
			throws PersistenceException {
		rigaOrdine.setId(this.idBroker.newId(IdBrokerPostgresql.RIGA_ORDINE_SEQUENCE_ID));
		PreparedStatement statement = null;
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
			DBUtil.silentClose(null, statement);
		}
	}
	
}
