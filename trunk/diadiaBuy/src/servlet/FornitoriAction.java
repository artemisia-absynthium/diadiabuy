package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DiadiaBuyFacade;
import model.Fornitore;
import model.Utente;

public class FornitoriAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
		String code = request.getParameter("product_code");
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		List<Fornitore> fornitori = facade.getFornitoreByCodiceProdotto(utente, code);
		if (fornitori == null)
			return "listaFornitoriNonCaricata";
		request.setAttribute("fornitori", fornitori);
		return "listaFornitoriCaricata";
	}

}
