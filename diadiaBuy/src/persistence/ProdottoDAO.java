package persistence;

import java.util.List;

import model.Prodotto;

public interface ProdottoDAO {
	
	public List<Prodotto> doRetrieveAll() throws PersistenceException;
	
	public void persist(Prodotto prodotto) throws PersistenceException;

	public Prodotto doRetrieveProdottoById(int idProdotto) throws PersistenceException;
	
	public Prodotto doRetrieveProdottoByCodice(String codiceProdotto) throws PersistenceException;

}
