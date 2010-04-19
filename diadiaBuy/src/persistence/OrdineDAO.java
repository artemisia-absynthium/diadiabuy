package persistence;

import java.util.List;

import model.Ordine;
import model.Utente;

public interface OrdineDAO {

	public void persist(Ordine ordine) throws PersistenceException;
	
	public List<Ordine> doRetrieveByCliente(Utente utente) throws PersistenceException;

}
