package servlet;

import javax.servlet.http.HttpServletRequest;

import model.DiadiaBuyFacade;
import persistence.PersistenceException;

public class EvadiOrdineAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		int id = Integer.parseInt(request.getParameter("product_id"));
		try {
			facade.evadiOrdine(id);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "ordineNonEvaso";
		}
		request.setAttribute("messaggio", "Ordine evaso correttamente");
		return "ordineEvaso";
	}

}
