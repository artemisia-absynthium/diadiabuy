package model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
		this.ordineDAO = new OrdineDAOpostgres();
		this.utenteDAO = new UtenteDAOpostgres();
		this.prodottoDAO = new ProdottoDAOpostgres();
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
			System.err.println("Creazione del Prodotto " + prodotto
					+ " fallita.");
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
			System.err.println("Creazione dell'Utente " + utente + " fallita.");
			e.printStackTrace();
			return false;
		}
	}

	public List<Prodotto> getCatalogo() throws PersistenceException {
		return this.prodottoDAO.doRetrieveAll();
	}

	public Prodotto getProdottoByID(int id) throws PersistenceException {
		ProdottoDAO prodottoDAO = new ProdottoDAOpostgres();
		Prodotto prodotto = prodottoDAO.doRetrieveProdottoById(id);
		return prodotto;
	}

	public void registraOrdine(Ordine ordine) {
		try {
			ordine.chiudi();
			this.ordineDAO.update(ordine);
		} catch (PersistenceException e) {
			System.err.println("Impossibile chiudere l'ordine.");
			e.printStackTrace();
		}
	}

	public List<Fornitore> getFornitoreByCodiceProdotto(Utente admin,
			String codiceProdotto) {
		this.onlyAdminCheck(admin);
		try {
			List<Fornitore> fornitori = this.fornitoriDAO
					.doRetrieveFornitoryByCodiceProdotto(codiceProdotto);
			return fornitori;
		} catch (PersistenceException e) {
			System.err.println("Impossibile trovare i fornitori.");
			e.printStackTrace();
			return null;
		}
	}

	private void onlyAdminCheck(Utente admin) {
		if (!admin.isAdmin())
			throw new SecurityException(
					"Operazione aggiungi prodotto consentita solo ad utenti amministratori."
							+ " L'utente " + admin
							+ " non è un utente amministratore.");
	}

	public boolean aggiungiFornitore(Utente admin, String codiceProdotto,
			Fornitore fornitore) {
		this.onlyAdminCheck(admin);
		try {
			Prodotto prodotto = this.prodottoDAO
					.doRetrieveProdottoByCodice(codiceProdotto);
			prodotto.aggiungiFornitore(fornitore);
			this.fornitoriDAO.persist(fornitore);
			this.fornitoriDAO.persistFornitura(fornitore, prodotto);
			return true;
		} catch (PersistenceException e) {
			System.err.println("Impossibile aggiungere il fornitore.");
			e.printStackTrace();
			return false;
		}
	}

	public void aggiungiProdottoAlCarrello(Utente utente, int idProdotto,
			int quantita) throws PersistenceException {
		Ordine carrello = utente.getCarrello();
		Prodotto prodotto = this.prodottoDAO.doRetrieveProdottoById(idProdotto);
		carrello.aggiungiProdotto(prodotto, quantita);
		if (carrello.getId() == 0) {
			/* il carrello non è persistito */
			this.ordineDAO.persist(carrello);
		} else {
			/* il carrello è già persistito */
			this.ordineDAO.update(carrello);
		}

	}

	public List<Ordine> getStorico(Utente utente) throws PersistenceException {
		return this.ordineDAO.doRetrieveByCliente(utente);
	}

	public Ordine getOrdine(int id) throws PersistenceException {
		return this.ordineDAO.doRetrieveById(id);
	}

	public Utente login(HttpServletRequest request) {
		Utente u = this.getUtente(request.getParameter("username"));
		if (u == null || !u.isAuthorized(request))
			return null;
		return u;
	}

	public List<Ordine> getOrdiniChiusi() throws PersistenceException {
		return this.ordineDAO.doRetrieveByStato(Ordine.Stati.CHIUSO);
	}
	
	public void evadiOrdine(int id) throws PersistenceException {
		Ordine ordine = this.ordineDAO.doRetrieveById(id);
		ordine.evadi();
		this.ordineDAO.updateState(ordine);
	}

}
