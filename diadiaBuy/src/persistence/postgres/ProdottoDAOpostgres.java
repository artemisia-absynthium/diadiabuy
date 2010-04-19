package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Prodotto;
import persistence.DataSource;
import persistence.IdBroker;
import persistence.PersistenceException;
import persistence.ProdottoDAO;

public class ProdottoDAOpostgres implements ProdottoDAO {
	
	private final DataSource dataSource;

	private final IdBroker idBroker;
	
	public ProdottoDAOpostgres() {
		this.dataSource = DBUtil.getDataSource();
		this.idBroker = new IdBrokerPostgresql();
	}

	private Prodotto newProdottoFromResultSet(ResultSet result)
			throws SQLException {
		Prodotto prodotto = new ProdottoProxy();
		prodotto.setId(result.getInt("id_prodotto"));
		prodotto.setCodice(result.getString("codice"));
		prodotto.setNome(result.getString("nome"));
		prodotto.setDisponibilita(result.getInt("disponibilita"));
		prodotto.setPrezzo(result.getDouble("prezzo"));
		return prodotto;
	}

	public List<Prodotto> doRetrieveAll() throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		List<Prodotto> prodotti = new LinkedList<Prodotto>();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = 	"SELECT id_prodotto, codice, nome, disponibilita, prezzo " + 
							"FROM prodotti";
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			while (result.next()) {
				Prodotto prodotto = this.newProdottoFromResultSet(result);
				prodotti.add(prodotto);
			}
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile visualizzare il catalogo.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
		return prodotti;
	}

	public void persist(Prodotto prodotto) throws PersistenceException {
		prodotto.setId(this.idBroker.newId(IdBrokerPostgresql.PRODOTTO_SEQUENCE_ID));
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = "INSERT INTO prodotti " +
								"(id_prodotto, codice, nome, disponibilita, prezzo, descrizione) VALUES " +
								"(?,  ?,      ?,    ?,             ?,      ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, prodotto.getId());
			statement.setString(2, prodotto.getCodice());
			statement.setString(3, prodotto.getNome());
			statement.setInt(4, prodotto.getDisponibilita());
			statement.setDouble(5, prodotto.getPrezzo());
			statement.setString(6, prodotto.getDescrizione());
			statement.execute();
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare il prodotto.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	public Prodotto doRetrieveProdottoById(int idProdotto) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = 	"SELECT id_prodotto, codice, nome, disponibilita, prezzo " + 
							"FROM prodotti " +
							"WHERE id_prodotto = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, idProdotto);
			result = statement.executeQuery();
			if(!result.next())
				throw new PersistenceException("Nessun prodotto trovato con id="+ idProdotto + ".");
			Prodotto prodotto = this.newProdottoFromResultSet(result);
			return prodotto;
		} catch (SQLException e) { 
			throw new PersistenceException("Impossibile caricare il prodotto con id=" + idProdotto + ".", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	public Prodotto doRetrieveProdottoByCodice(String codiceProdotto) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = 	"SELECT id_prodotto, codice, nome, disponibilita, prezzo " + 
							"FROM prodotti " +
							"WHERE codice = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, codiceProdotto);
			result = statement.executeQuery();
			if(!result.next())
				throw new PersistenceException("Nessun prodotto trovato con codice="+ codiceProdotto + ".");
			Prodotto prodotto = this.newProdottoFromResultSet(result);
			return prodotto;
		} catch (SQLException e) { 
			throw new PersistenceException("Impossibile caricare il prodotto con id=" + codiceProdotto + ".", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}
	
	String caricaDescrizione(ProdottoProxy prodotto) throws PersistenceException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = "SELECT descrizione " +
						"FROM prodotti " +
						"WHERE id_prodotto = ?";
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, prodotto.getId());
			result = statement.executeQuery();
			if (!result.next()) {
				throw new PersistenceException("Impossibile caricare la descrizione per il prodotto " + prodotto + ".");
			}
			return result.getString("descrizione");
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile visualizzare la descrizione", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}
	
}
