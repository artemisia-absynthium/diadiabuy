package servlet;

import javax.servlet.http.HttpServletRequest;

import model.DiadiaBuyFacade;
import model.Utente;

public class ConfermaRegistrazioneUtenteAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		if (request.getParameter("Correggi") != null)
			return "RegistrazioneDaCorreggere";
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Utente utente = new Utente(username, password, Utente.Ruolo.REGISTRATO);
		DiadiaBuyFacade facade = DiadiaBuyFacade.getInstance();
		
		if (!facade.newUtente(utente))
			return "erroreRegistrazione";
			
		return "utenteRegistrato";
	}

}
