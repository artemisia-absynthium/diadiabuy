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
import persistence.PersistenceException;
import persistence.ProxyLoadingException;

public class ProdottoProxy extends Prodotto {
	private boolean descrizioneCaricata;
	private boolean fornitoriCaricati;
	
	private DataSource dataSource;
	
	public ProdottoProxy() {
		this.dataSource = DBUtil.getDataSource();
	}

	@Override
	public String getDescrizione() {
		if (this.descrizioneCaricata)
			return super.getDescrizione();
		
		String descrizione = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = "SELECT descrizione " +
				"FROM prodotti " +
				"WHERE id = ?";
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.id);
			result = statement.executeQuery();
			if (result.next()) {
				descrizione = result.getString("descrizione");
				this.setDescrizione(descrizione);
			}
			return descrizione;
		} catch (SQLException e) {
			throw new ProxyLoadingException("Impossibile visualizzare la descrizione", e);
		} catch (PersistenceException e) {
			throw new ProxyLoadingException("Impossibile connettersi al DB", e);
		} finally {
			DBUtil.SilentClose(connection, statement, result);
		}
	}

	@Override
	public List<Fornitore> getFornitori() {
		if (this.fornitoriCaricati)
			return super.getFornitori();
		
		List<Fornitore> fornitori = new LinkedList<Fornitore>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = "SELECT id, nome, indirizzo, telefono " +
				"FROM fornitori join fornitura on id = fornitore " +
				"WHERE prodotto = ?";
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, this.id);
			result = statement.executeQuery();
			while (result.next()) {
				Fornitore fornitore = this.newFornitoreFromResultSet(result);
				fornitori.add(fornitore);
			}
			return fornitori;
		} catch (SQLException e) {
			throw new ProxyLoadingException("Impossibile visualizzare i fornitori", e);
		} catch (PersistenceException e) {
			throw new ProxyLoadingException("Impossibile connettersi al DB", e);
		} finally {
			DBUtil.SilentClose(connection, statement, result);
		}
	}

	private Fornitore newFornitoreFromResultSet(ResultSet result)
			throws SQLException {
		Fornitore fornitore = new Fornitore();
		fornitore.setId(result.getInt("id"));
		fornitore.setNome(result.getString("nome"));
		fornitore.setIndirizzo(result.getString("indirizzo"));
		fornitore.setTelefono(result.getString("telefono"));
		return fornitore;
	}

}
