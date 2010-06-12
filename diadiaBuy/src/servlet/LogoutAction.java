package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		//Restituisce la sessione corrente, se non c'è, con false, non ne crea una nuova.
		
		if (session != null)
			session.invalidate();
		return "logout";
	}

}
