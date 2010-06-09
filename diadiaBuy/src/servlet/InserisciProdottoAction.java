package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DiadiaBuyFacade;
import model.Prodotto;
import model.Utente;

public class InserisciProdottoAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		InserimentoProdottoHelper helper;
		try {
			helper = new InserimentoProdottoHelper(request);
		} catch (ServletException e) {
			e.printStackTrace();
			return "nonInserito";
		}

		if (!helper.convalida())
			return "nonInserito";
		
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
		String nome = request.getParameter("nome");
		String codice = request.getParameter("codice");
		String descrizione = request.getParameter("descrizione");
		double prezzo = Double.parseDouble(request.getParameter("prezzo"));
		int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));
		Prodotto prodotto = new Prodotto(nome, codice, descrizione, prezzo, disponibilita);
		if (!facade.newProdotto(utente, prodotto))
			return "nonInserito";
		
		return "inserito";
	}

}
