package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Prodotto;
import persistence.PersistenceException;
import persistence.ProdottoDAO;
import persistence.postgres.ProdottoDAOpostgres;

public class ConsultaProdotti extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		ProdottoDAO prodottoDAO = new ProdottoDAOpostgres();
		try {
			List<Prodotto> prodotti = prodottoDAO.doRetrieveAll();
			request.setAttribute("prodotti", prodotti);
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "listaNonCreata";
		}
		return "listaCreata";
	}

}
