package servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.IntegerValidator;

public class InserimentoProdottoHelper {
	
	private String nome;
	private String descrizione;
	private String prezzo;
	private String disponibilita;
	private HttpServletRequest req;
	
	/**
	 * @throws ServletException  
	 */
	public InserimentoProdottoHelper(HttpServletRequest req) throws ServletException {
		this.req = req;
		this.nome = req.getParameter("nome");
		this.descrizione = req.getParameter("descrizione");
		this.prezzo = req.getParameter("prezzo");
		this.disponibilita = req.getParameter("disponibilita");
	}

	public boolean convalida() {
		boolean tuttoOk = true;
		Map<String,String> errori = new HashMap<String, String>();
		DoubleValidator doubleValue = new DoubleValidator();
		IntegerValidator intValue = new IntegerValidator();
		
		if (nome == null || nome.equals("")) {
			tuttoOk = false;
			errori.put("nome","campo obbligatorio");
		}
		
		if (descrizione == null || descrizione.equals("")) {
			tuttoOk = false;
			errori.put("descrizione", "campo obbligatorio");
		}
		
		if (doubleValue.validate(prezzo) == null) {
			tuttoOk = false;
			errori.put("prezzo", "campo obbligatorio");
		}
		
		if (intValue.validate(disponibilita) == null) {
			tuttoOk = false;
			errori.put("disponibilita", "campo obbligatorio");
		}
		
		if (!tuttoOk)
			req.setAttribute("errori", errori);
		
		return tuttoOk;
	}

}
