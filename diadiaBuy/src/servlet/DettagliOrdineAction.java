package servlet;

import javax.servlet.http.HttpServletRequest;

import persistence.PersistenceException;

import model.DiadiaBuyFacade;
import model.Ordine;

public class DettagliOrdineAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ordine_id"));
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		try {
			Ordine ordine = facade.getOrdine(id);
			request.setAttribute("ordine", ordine);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "dettagliOrdineNonCaricati";
		}
		return "dettagliOrdineCaricati";
	}

}
