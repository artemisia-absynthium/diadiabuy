package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import persistence.PersistenceException;

import model.DiadiaBuyFacade;
import model.Prodotto;

public class ConsultaProdottiAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		List<Prodotto> prodotti;
		try {
			prodotti = facade.getCatalogo();
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "listaNonCreata";
		}
		request.setAttribute("prodotti", prodotti);
		return "listaCreata";
	}

}
