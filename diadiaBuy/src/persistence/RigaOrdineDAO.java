package persistence;

import model.RigaOrdine;

public interface RigaOrdineDAO {

	public void persist(RigaOrdine rigaOrdine) throws PersistenceException;
	
}
