package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class RegistrazioneUtenteAction extends Azione {

	@Override
	public String esegui(HttpServletRequest request) {
		RegistrazioneUtenteHelper helper;
		try {
			helper = new RegistrazioneUtenteHelper(request);
		} catch (ServletException e) {
			e.printStackTrace();
			return "nonConvalidato";
		}
		
		if (!helper.convalida())
			return "nonConvalidato";
		
		return "convalidato";
	}

}
