package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Ordine;
import model.Utente;

public class CarrelloAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Utente utente = (Utente) session.getAttribute("utente");
		Ordine carrello = utente.getCarrello();
		request.setAttribute("carrello", carrello);
		return "carrelloPreso";
	}

}
