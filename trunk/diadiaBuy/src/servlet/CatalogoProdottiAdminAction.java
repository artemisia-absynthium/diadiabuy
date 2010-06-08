package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.DiadiaBuyFacade;
import model.Prodotto;
import persistence.PersistenceException;

public class CatalogoProdottiAdminAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		List<Prodotto> prodotti;
		try {
			prodotti = facade.getCatalogo();
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "listaxAdminNonCreata";
		}
		request.setAttribute("prodotti", prodotti);
		return "listaxAdminCreata";
	}

}
