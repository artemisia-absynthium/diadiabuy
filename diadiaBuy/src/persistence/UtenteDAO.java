package persistence;

import model.Utente;

public interface UtenteDAO {

	public void persist(Utente utente) throws PersistenceException;

	public Utente doRetrieveUtenteByUsername(String username) throws PersistenceException;

}
