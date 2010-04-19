package persistence;

public interface IdBroker {

	public int newId(String sequence) throws PersistenceException;
	
}
