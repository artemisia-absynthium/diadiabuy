package persistence;

import java.util.List;

import model.Fornitore;
import model.Prodotto;

public interface FornitoriDAO {

	public List<Fornitore> doRetrieveAll() throws PersistenceException;
	
	public List<Fornitore> doRetrieveFornitoryByCodiceProdotto(String codiceProdotto) throws PersistenceException;
	
	public void persist(Fornitore fornitore) throws PersistenceException;

	public void persistFornitura(Fornitore fornitore, Prodotto prodotto) throws PersistenceException;

}
