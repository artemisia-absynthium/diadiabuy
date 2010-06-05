package servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class RegistrazioneUtenteHelper {
	
	private String username;
	private String password;
	private HttpServletRequest req;
	
	/**
	 * @throws ServletException  
	 */
	public RegistrazioneUtenteHelper(HttpServletRequest req) throws ServletException {
		this.req = req;
		this.username = req.getParameter("username");
		this.password = req.getParameter("password");
	}
	
	public boolean convalida() {
		boolean tuttoOk = true;
		Map<String,String> errori = new HashMap<String, String>();

		if (username == null || username.equals("")) {
			tuttoOk = false;
			errori.put("username","campo obbligatorio");
		}
		if (password == null || password.equals("")) {
			tuttoOk = false;
			errori.put("password","campo obbligatorio");
		}
		
		if (!tuttoOk)
			req.setAttribute("errori", errori);
		
		return tuttoOk;
	}

}
