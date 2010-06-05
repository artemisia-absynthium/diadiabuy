package persistence.postgres;

import java.util.List;

import model.Ordine;
import model.Utente;
import persistence.OrdineDAO;
import persistence.PersistenceException;
import persistence.ProxyLoadingException;

public class UtenteProxy extends Utente {

	private final OrdineDAO ordineDao;
	
	private boolean ordiniCaricati;
	
	public UtenteProxy() {
		this.ordineDao = new OrdineDAOpostgres();
	}

	public UtenteProxy(String username, String password, String ruolo) {
		super(username, password, ruolo);
		this.ordineDao = new OrdineDAOpostgres();
	}

	@Override
	public List<Ordine> getOrdini() {
		if (!this.ordiniCaricati) {
			try {
				this.setOrdini(this.ordineDao.doRetrieveByCliente(this));
				this.ordiniCaricati = true;
			} catch (PersistenceException e) {
				throw new ProxyLoadingException("Errore nel caricamento lazy degli ordini.", e);
			}
		}
		return super.getOrdini();
	}
}