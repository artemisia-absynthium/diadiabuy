package persistence;

import java.sql.Connection;
import java.util.List;

import model.Prodotto;

public interface ProdottoDAO {
	
	public List<Prodotto> doRetrieveAll() throws PersistenceException;
	
	public void persist(Prodotto prodotto) throws PersistenceException;

	public Prodotto doRetrieveProdottoById(int idProdotto) throws PersistenceException;
	
	public Prodotto doRetrieveProdottoByCodice(String codiceProdotto) throws PersistenceException;

	public void updateAvailability(Prodotto prodotto) throws PersistenceException;
	
	public void updateAvailability(Connection connection, Prodotto prodotto) throws PersistenceException;

}
