package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Fornitore;
import model.Prodotto;
import persistence.DataSource;
import persistence.FornitoriDAO;
import persistence.IdBroker;
import persistence.PersistenceException;

public class FornitoriDAOpostgres implements FornitoriDAO {
	
	private final DataSource dataSource;

	private final IdBroker idBroker;
	
	public FornitoriDAOpostgres() {
		this.dataSource = DBUtil.getDataSource();
		this.idBroker = new IdBrokerPostgresql();
	}

	private Fornitore newFornitoreFromResultSet(ResultSet result) throws SQLException {
		Fornitore fornitore = new Fornitore();
		fornitore.setId(result.getInt("id_fornitore"));
		fornitore.setNome(result.getString("nome"));
		fornitore.setIndirizzo(result.getString("indirizzo"));
		fornitore.setTelefono(result.getString("telefono"));
		return fornitore;
	}

	public List<Fornitore> doRetrieveAll() throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		List<Fornitore> fornitori = new LinkedList<Fornitore>();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = 	"SELECT id_fornitore, nome, indirizzo, telefono " + 
							"FROM fornitori";
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			while (result.next()) {
				Fornitore fornitore = this.newFornitoreFromResultSet(result);
				fornitori.add(fornitore);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile caricare i fornitori.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
		return fornitori;
	}

	public List<Fornitore> doRetrieveFornitoryByCodiceProdotto(String codiceProdotto) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		List<Fornitore> fornitori = new LinkedList<Fornitore>();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = 	"SELECT id_fornitore, fornitori.nome, indirizzo, telefono, id_prodotto, codice " + 
							"FROM fornitori JOIN fornitura ON fornitore = id_fornitore " +
											"JOIN prodotti ON prodotto = id_prodotto " +
							"WHERE codice = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, codiceProdotto);
			result = statement.executeQuery();
			while (result.next()) {
				Fornitore fornitore = this.newFornitoreFromResultSet(result);
				fornitori.add(fornitore);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile caricare i fornitori per il prodotto=" + codiceProdotto + ".", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
		return fornitori;
	}
	
	public void persist(Fornitore fornitore) throws PersistenceException {
		fornitore.setId(this.idBroker.newId(IdBrokerPostgresql.FORNITORE_SEQUENCE_ID));
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = "INSERT INTO fornitori(id_fornitore, nome, indirizzo, telefono) VALUES " +
												"(?,            ?,    ?,         ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, fornitore.getId());
			statement.setString(2, fornitore.getNome());
			statement.setString(3, fornitore.getIndirizzo());
			statement.setString(4, fornitore.getTelefono());
			statement.execute();
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare il fornitore.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	public void persistFornitura(Fornitore fornitore, Prodotto prodotto) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = "INSERT INTO fornitura(fornitore, prodotto) VALUES " +
												"(?,         ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, fornitore.getId());
			statement.setInt(2, prodotto.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare la fornitura.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

}
