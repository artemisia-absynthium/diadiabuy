package persistence.postgres;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	Ordine newOrdineFromResultSet(ResultSet result) throws SQLException {
		Ordine ordine = new Ordine();
		ordine.setId(result.getInt("id_ordine"));
		ordine.setCodice(result.getString("codice"));
		Calendar dataOrdine = Calendar.getInstance();
		dataOrdine.setTime(result.getDate("data"));
		ordine.setStato(result.getString("stato"));
		ordine.setData(dataOrdine);
		List<RigaOrdine> righeOrdine = new LinkedList<RigaOrdine>();
		do {
			RigaOrdine rigaOrdine = this.rigaOrdineDAO.newRigaOrdine(ordine, result);
			righeOrdine.add(rigaOrdine);
		} while(result.next());
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
			boolean endOfResult = false;
			List<Ordine> ordini = new LinkedList<Ordine>();
			while(!endOfResult && result.next()) {
				Ordine ordine = this.newOrdineFromResultSet(result);
				ordini.add(ordine);
			}
			return ordini;
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare l'ordine.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
	}

	public void persist(Ordine ordine) throws PersistenceException {
		ordine.setId(this.idBroker.newId(IdBrokerPostgresql.ORDINE_SEQUENCE_ID));
		Connection connection = this.dataSource.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = "INSERT INTO ordini(id_ordine, codice, stato, data, id_utente) VALUES " +
											 "(?,  		  ?,      ?,     ?,    ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, ordine.getId());
			statement.setString(2, ordine.getCodice());
			statement.setString(3, ordine.getStato());
			statement.setDate(4, new Date(ordine.getData().getTimeInMillis()));
			statement.setInt(5, ordine.getCliente().getId());
			statement.execute();
			for (RigaOrdine riga : ordine.getRigheOrdine())
				this.prodottoDAO.updateAvailability(riga.getProdotto());
		} catch (SQLException e) {
			throw new PersistenceException("Impossibile inserire salvare l'ordine.", e);
		} finally {
			DBUtil.silentClose(connection, statement, result);
		}
		for (RigaOrdine rigaOrdine : ordine.getRigheOrdine()) {
			this.rigaOrdineDAO.persist(rigaOrdine);
		}
		System.out.println("Ordine salvato correttamente.");
	}
	
}