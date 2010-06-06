package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DiadiaBuyFacade;
import model.Ordine;
import model.Utente;

public class ConfermaAcquistoAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
		Ordine carrello = utente.getCarrello();
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		facade.registraOrdine(carrello);
		return "ordineRegistrato";
	}

}
