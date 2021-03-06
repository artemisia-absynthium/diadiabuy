package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Ordine;
import model.RigaOrdine;
import model.Utente;
import persistence.DataSource;
import persistence.IdBroker;
import persistence.OrdineDAO;
import persistence.PersistenceException;

public class OrdineDAOpostgres implements OrdineDAO {

	private final DataSource dataSource;

	private final IdBroker idBroker;
	
	private final RigaOrdineDAOpostgres rigaOrdineDAO;
	
	private final ProdottoDAOpostgres prodottoDAO;
	
	public OrdineDAOpostgres() {
		this.dataSource = DBUtil.getDataSource();
		this.idBroker = new IdBrokerPostgresql();
		this.rigaOrdineDAO = new RigaOrdineDAOpostgres();
		this.prodottoDAO = new ProdottoDAOpostgres();
	}

	Ordine newOrdineFromResultSet(Utente utente, ResultSet result) throws SQLException {
		Ordine ordine = new Ordine();
		ordine.setId(result.getInt("id_ordine"));
		ordine.setCodice(result.getString("codice"));
		ordine.setCliente(utente);
		Calendar dataOrdine = Calendar.getInstance();
		dataOrdine.setTime(result.getTimestamp("data"));
		ordine.setStato(result.getString("stato"));
		ordine.setData(dataOrdine);
		List<RigaOrdine> righeOrdine = new LinkedList<RigaOrdine>();
		while(result.getInt("id_ordine") == ordine.getId()) {
			RigaOrdine rigaOrdine = this.rigaOrdineDAO.newRigaOrdine(ordine, result);
			righeOrdine.add(rigaOrdine);
			if(!result.next())
				break;
		}
		ordine.setRigheOrdine(righeOrdine);
		return ordine;
	}

	public List<Ordine> doRetrieveByCliente(Utente utente) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = 	"SELECT   ordini.id_ordine, codice, stato, data, id_utente, id_prodotto, " +
									" id_riga_ordine, quantita, numero_di_riga, nome_prodotto " +
							"FROM ordini LEFT JOIN righe_ordine ON ordini.id_ordine = righe_ordine.id_ordine " +
							"WHERE id_utente = ? " +
							"ORDER BY id_ordine, numero_di_riga";
			statement = connection.prepareStatement(query);
			statement.setInt(1, utente.getId());
			result = statement.executeQuery();
			List<Ordine> ordini = new LinkedList<Ordine>();
			if(!result.next())
				return ordini;
			while(!result.isAfterLast()) {
				Ordine ordine = this.newOrdineFromResultSet(utente, result);
				ordini.add(ordine);
			}
			return ordini;
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare l'ordine.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	@Override
	public Ordine doRetrieveById(int id) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = 	"SELECT ordini.id_ordine, codice, stato, data, id_utente, id_prodotto, " +
									" id_riga_ordine, quantita, numero_di_riga, nome_prodotto " +
							"FROM ordini LEFT JOIN righe_ordine ON ordini.id_ordine = righe_ordine.id_ordine " +
							"WHERE ordini.id_ordine = ? " +
							"ORDER BY numero_di_riga";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeQuery();
			if (!result.next())
				return null;
			Ordine ordine = this.newOrdineFromResultSet(null, result);
			return ordine;
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile caricare l'ordine.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}
	
	public void persist(Ordine ordine) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		try {
			this.persist(connection, ordine);
		} finally {
			DBUtil.silentClose(connection);
		}
	}

	public void persist(Connection connection, Ordine ordine) throws PersistenceException {
		ordine.setId(this.idBroker.newId(IdBrokerPostgresql.ORDINE_SEQUENCE_ID));
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "INSERT INTO ordini(id_ordine, codice, stato, data, id_utente) VALUES " +
											  "(?,  	   ?,      ?,     ?,    ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, ordine.getId());
			statement.setString(2, ordine.getCodice());
			statement.setString(3, ordine.getStato());
			statement.setTimestamp(4, new Timestamp(ordine.getData().getTimeInMillis()));
			statement.setInt(5, ordine.getCliente().getId());
			statement.execute();
			
			if(ordine.getStato().equals(Ordine.Stati.CHIUSO))
				for (RigaOrdine riga : ordine.getRigheOrdine())
					this.prodottoDAO.updateAvailability(connection, riga.getProdotto());
			
			for (RigaOrdine rigaOrdine : ordine.getRigheOrdine())
				this.rigaOrdineDAO.persistOrUpdate(connection, rigaOrdine);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new PersistenceException("Impossibile effettuare il rollback.", e1);
			}
			throw new PersistenceException("Impossibile salvare l'ordine.", e);
		} finally {
			DBUtil.silentClose(null, statement, result);
		}
		System.out.println("Ordine salvato correttamente.");
	}
	
	public void update(Ordine ordine) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		try {
			this.update(connection, ordine);
		} finally {
			DBUtil.silentClose(connection);
		}
	}

	public void update(Connection connection, Ordine ordine) throws PersistenceException {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "UPDATE ordini " +
							"SET " +
								"codice = ?, " +
								"stato = ?, " +
								"data = ?, " +
								"id_utente = ? " +
							"WHERE id_ordine = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, ordine.getCodice());
			statement.setString(2, ordine.getStato());
			statement.setTimestamp(3, new Timestamp(ordine.getData().getTimeInMillis()));
			statement.setInt(4, ordine.getCliente().getId());
			statement.setInt(5, ordine.getId());
			statement.execute();
			
			if(ordine.getStato().equals(Ordine.Stati.CHIUSO))
				for (RigaOrdine riga : ordine.getRigheOrdine())
					this.prodottoDAO.updateAvailability(connection, riga.getProdotto());
			
			for (RigaOrdine rigaOrdine : ordine.getRigheOrdine())
				this.rigaOrdineDAO.persistOrUpdate(connection, rigaOrdine);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new PersistenceException("Impossibile effettuare il rollback.", e1);
			}
			throw new PersistenceException("Impossibile salvare l'ordine.", e);
		} finally {
			DBUtil.silentClose(null, statement, result);
		}
		System.out.println("Ordine salvato correttamente.");
	}

	@Override
	public List<Ordine> doRetrieveByStato(String chiuso)
			throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = 	"SELECT   ordini.id_ordine, codice, stato, data, id_utente, id_prodotto, " +
									" id_riga_ordine, quantita, numero_di_riga, nome_prodotto " +
							"FROM ordini LEFT JOIN righe_ordine ON ordini.id_ordine = righe_ordine.id_ordine " +
							"WHERE stato = ? " +
							"ORDER BY id_ordine, numero_di_riga";
			statement = connection.prepareStatement(query);
			statement.setString(1, chiuso);
			result = statement.executeQuery();
			List<Ordine> ordini = new LinkedList<Ordine>();
			if(!result.next())
				return ordini;
			while(!result.isAfterLast()) {
				Ordine ordine = this.newOrdineFromResultSet(null, result);
				ordini.add(ordine);
			}
			return ordini;
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare l'ordine.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	@Override
	public void updateState(Ordine ordine) throws PersistenceException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String query = "UPDATE ordini " +
							"SET " +
								"stato = ? " +
							"WHERE id_ordine = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, ordine.getStato());
			statement.setInt(2, ordine.getId());
			statement.execute();
						
			for (RigaOrdine rigaOrdine : ordine.getRigheOrdine())
				this.rigaOrdineDAO.persistOrUpdate(connection, rigaOrdine);
			
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile evadere l'ordine.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
		System.out.println("Ordine evaso correttamente.");		
	}
	
}
