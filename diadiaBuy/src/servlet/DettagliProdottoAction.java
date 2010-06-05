package servlet;

import javax.servlet.http.HttpServletRequest;

import model.DiadiaBuyFacade;
import model.Prodotto;
import persistence.PersistenceException;

public class DettagliProdottoAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		String pid = request.getParameter("product_id");
		int id = Integer.parseInt(pid);
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		try {
			Prodotto prodotto = facade.getProdottoByID(id);
			String descrizione = prodotto.getDescrizione();
			request.setAttribute("descrizione", descrizione);
			request.setAttribute("prodotto", prodotto);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "noDescrizione";
		}
		return "descrizioneOK";
	}

}
