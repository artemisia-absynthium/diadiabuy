package model;

import java.util.List;

import persistence.FornitoriDAO;
import persistence.OrdineDAO;
import persistence.PersistenceException;
import persistence.ProdottoDAO;
import persistence.UtenteDAO;
import persistence.postgres.FornitoriDAOpostgres;
import persistence.postgres.OrdineDAOpostgres;
import persistence.postgres.ProdottoDAOpostgres;
import persistence.postgres.UtenteDAOpostgres;

public class DiadiaBuyFacade {
	
	private final ProdottoDAO prodottoDAO;
	
	private final OrdineDAO ordineDAO;
	
	private final UtenteDAO utenteDAO;
	
	private final FornitoriDAO fornitoriDAO;
	
	private DiadiaBuyFacade() {
		this.prodottoDAO = new ProdottoDAOpostgres();
		this.ordineDAO = new OrdineDAOpostgres();
		this.utenteDAO = new UtenteDAOpostgres();
		this.fornitoriDAO = new FornitoriDAOpostgres();
	}
	
	private static class SingletonHolder {
		private static final DiadiaBuyFacade INSTANCE = new DiadiaBuyFacade();
	}

	public static DiadiaBuyFacade getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public boolean newProdotto(Utente admin, Prodotto prodotto) {
		this.onlyAdminCheck(admin);
		try {
			this.prodottoDAO.persist(prodotto);
			return true;
		} catch (PersistenceException e) {
			System.err.println("Creazione del Prodotto " + prodotto + " fallita.");
			e.printStackTrace();
			return false;
		}
	}
	
	public Utente getUtente(String username) {
		try {
			Utente utente = this.utenteDAO.doRetrieveUtenteByUsername(username);
			return utente;
		} catch (PersistenceException e) {
			System.err.println("Recupero dell'Utente fallito.");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean newUtente(Utente utente) {
		try {
			this.utenteDAO.persist(utente);
			return true;
		} catch (PersistenceException e) {
			System.err.println("Creazione dell' Utente" + utente + " fallita.");
			e.printStackTrace();
			return false;
		}
	}
	

	public boolean newFornitore(Utente utente, Fornitore fornitore) {
		this.onlyAdminCheck(utente);
		try {
			this.fornitoriDAO.persist(fornitore);
			return true;
		} catch (PersistenceException e) {
			System.err.println("Creazione del fornitore" + fornitore + " fallita.");
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Prodotto> getCatalogo() {
		try {
			return this.prodottoDAO.doRetrieveAll();
		} catch (PersistenceException e) {
			System.err.println("Impossibile prendere il Catalogo prodotti.");
			e.printStackTrace();
			return null;
		}
	}
	
	public void registraOrdine(Ordine ordine) {
		try {
			this.ordineDAO.persist(ordine);
		} catch (PersistenceException e) {
			System.err.println("Impossibile creare l'ordine.");
			e.printStackTrace();
		}
	}

	public List<Fornitore> getFornitoreByCodiceProdotto(Utente admin, String codiceProdotto) {
		this.onlyAdminCheck(admin);
		try {
			List<Fornitore> fornitori = this.fornitoriDAO.doRetrieveFornitoryByCodiceProdotto(codiceProdotto);
			return fornitori;
		} catch (PersistenceException e) {
			System.err.println("Impossibile creare l'ordine.");
			e.printStackTrace();
			return null;
		}
	}

	private void onlyAdminCheck(Utente admin) {
		if(!admin.isAdmin())
			throw new SecurityException("Operazione aggiungi prodotto consentita solo ad utenti amministratori." +
						" L'utente " + admin + " non è un utente amministratore.");
	}

	public boolean aggiungiFornitore(Utente admin, String codiceProdotto, Fornitore fornitore) {
		this.onlyAdminCheck(admin);
		try {
			Prodotto prodotto = this.prodottoDAO.doRetrieveProdottoByCodice(codiceProdotto);
			prodotto.aggiungiFornitore(fornitore);
			this.fornitoriDAO.persistFornitura(fornitore, prodotto);
			return true;
		} catch (PersistenceException e) {
			System.err.println("Impossibile creare l'ordine.");
			e.printStackTrace();
			return false;
		}
	}

}
