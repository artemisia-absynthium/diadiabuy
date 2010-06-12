package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import persistence.PersistenceException;

import model.DiadiaBuyFacade;
import model.Ordine;

public class OrdiniChiusiAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		List<Ordine> ordiniChiusi;
		try {
			ordiniChiusi = facade.getOrdiniChiusi();
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "ordiniChiusiNonCaricati";
		}
		request.setAttribute("ordini", ordiniChiusi);
		return "ordiniChiusiCaricati";
	}

}
