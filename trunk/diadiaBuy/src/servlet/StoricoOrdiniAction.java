package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import persistence.PersistenceException;

import model.DiadiaBuyFacade;
import model.Ordine;
import model.Utente;

public class StoricoOrdiniAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		try {
			List<Ordine> ordini = facade.getStorico(utente);
			request.setAttribute("ordini", ordini);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "ordiniNonCaricati";
		}
		return "ordiniCaricati";
	}

}
