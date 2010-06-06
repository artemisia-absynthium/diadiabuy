package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.DiadiaBuyFacade;
import model.Utente;

public class LoginAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		Utente utente = facade.login(request);
		
		if (utente == null) {
			request.setAttribute("messaggio", "Username o password errati");
			return "nonLoggato";
		}
			
		HttpSession session = request.getSession(false);
		//Restituisce la sessione corrente, se non c'è, con false, non ne crea una nuova.
		
		if (session != null)
			session.invalidate();
		
		session = request.getSession();
		session.setAttribute("utente", utente);
		return "loggato";
	}

}
