package persistence;

import java.util.List;

import model.Prodotto;

public interface ProdottoDAO {
	
	public List<Prodotto> doRetrieveAll() throws PersistenceException;
	
	public void persist(Prodotto prodotto) throws PersistenceException;

}
