package persistence;

import java.sql.Connection;
import java.util.List;

import model.Ordine;
import model.Utente;

public interface OrdineDAO {

	public void persist(Ordine ordine) throws PersistenceException;
	
	public void persist(Connection connection, Ordine ordine) throws PersistenceException;

	public List<Ordine> doRetrieveByCliente(Utente utente) throws PersistenceException;
	
	public Ordine doRetrieveById(int id) throws PersistenceException;
	
	public void update(Ordine ordine) throws PersistenceException;

	public List<Ordine> doRetrieveByStato(String chiuso) throws PersistenceException;
	
	public void updateState(Ordine ordine) throws PersistenceException;

}
