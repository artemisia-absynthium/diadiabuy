package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DiadiaBuyFacade;
import model.Fornitore;
import model.Utente;

public class InserisciFornitoreAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Utente utente = (Utente) session.getAttribute("utente");
		String codice = request.getParameter("product_code");
		String nome = request.getParameter("nome_fornitore");
		String indirizzo = request.getParameter("indirizzo_fornitore");
		String telefono = request.getParameter("telefono_fornitore");
		Fornitore fornitore = new Fornitore(nome, indirizzo, telefono);
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		if (!facade.aggiungiFornitore(utente, codice, fornitore))
			return "fornitoreNonInserito";
		request.setAttribute("messaggio", "Fornitore inserito correttamente.");
		return "fornitoreInserito";
	}

}
