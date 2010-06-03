package servlet;

import javax.servlet.http.HttpServletRequest;

import model.Prodotto;
import persistence.PersistenceException;
import persistence.ProdottoDAO;
import persistence.postgres.ProdottoDAOpostgres;

public class DettagliProdotto extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		String pid = request.getParameter("product_id");
		int id = Integer.parseInt(pid);
		ProdottoDAO prodottoDAO = new ProdottoDAOpostgres();
		try {
			Prodotto prodotto = prodottoDAO.doRetrieveProdottoById(id);
			request.setAttribute("descrizione", prodotto.getDescrizione());
			request.setAttribute("prodotto", prodotto);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "noDescrizione";
		}
		return "descrizioneOK";
	}

}
