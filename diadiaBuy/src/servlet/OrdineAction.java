package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DiadiaBuyFacade;
import model.Utente;
import persistence.PersistenceException;

public class OrdineAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Utente utente = (Utente) session.getAttribute("utente");
		
		int idProdotto = Integer.parseInt(request.getParameter("product_id"));
		int quantita = Integer.parseInt(request.getParameter("quantita"));
		
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		try {
			facade.aggiungiProdottoAlCarrello(utente, idProdotto, quantita);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "prodottoNonAggiunto";
		}
		
		return "prodottoAggiunto";
	}

	
}
