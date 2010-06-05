package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Ordine;
import model.Prodotto;
import model.Utente;
import persistence.OrdineDAO;
import persistence.PersistenceException;
import persistence.ProdottoDAO;
import persistence.postgres.OrdineDAOpostgres;
import persistence.postgres.ProdottoDAOpostgres;

public class OrdineAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Utente utente = (Utente) session.getAttribute("utente");
		List<Ordine> ordini = utente.getOrdini();
		Ordine ordineAttuale = null;
		for (Ordine o : ordini) {
			if (o.getStato().equals(Ordine.Stati.APERTO))
				ordineAttuale = o;
		}
		if (ordineAttuale == null)
			ordineAttuale = new Ordine();
		
		int id = Integer.parseInt(request.getParameter("product_id"));
		ProdottoDAO prodottoDAO = new ProdottoDAOpostgres();
		Prodotto prodotto;
		try {
			prodotto = prodottoDAO.doRetrieveProdottoById(id);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "prodottoNonAggiunto";
		}
		
		ordineAttuale.aggiungiProdotto(prodotto, Integer.parseInt(request.getParameter("quantita")));
		OrdineDAO ordineDAO = new OrdineDAOpostgres();
		try {
			ordineDAO.persist(ordineAttuale);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "prodottoNonAggiunto";
		}
		return "prodottoAggiunto";
	}

}
