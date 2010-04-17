package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Prodotto;
import persistence.DataSource;
import persistence.PersistenceException;
import persistence.ProdottoDAO;

public class ProdottoDAOpostgres implements ProdottoDAO {
	private DataSource dataSource;

	public ProdottoDAOpostgres() {
		this.dataSource = new DataSource();
	}

	private Prodotto newProdottoFromResultSet(ResultSet result)
			throws SQLException {
		Prodotto prodotto = new ProdottoProxy();
		prodotto.setId(result.getInt("id"));
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
			String query = "SELECT id, codice, nome, disponibilita, prezzo "
					+ "FROM prodotti";
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			while (result.next()) {
				Prodotto prodotto = this.newProdottoFromResultSet(result);
				prodotti.add(prodotto);
			}
		} catch (SQLException e) {
			throw new PersistenceException(
					"Impossibile visualizzare il catalogo", e);
		} finally {
			DBUtil.SilentClose(connection, statement, result);
		}

		return prodotti;
	}

	public void persist(Prodotto prodotto) throws PersistenceException {
		// TODO Persist prodotto

	}

}
