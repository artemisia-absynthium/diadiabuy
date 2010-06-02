package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5538458263576826384L;

	private Map<String, String> comando2azione;
	private Map<String, String> esito2pagina;

	/**
	 * 
	 */
	public Controller() {
		this.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String prossimaPagina = null;
		String comando = this.leggiComando(request.getServletPath());
		String nomeAzione = this.comando2azione.get(comando);
		if (nomeAzione == null) {
			prossimaPagina = "/error.jsp";
		} else {
			Azione azione = null;
			try {
				azione = (Azione) Class.forName(nomeAzione).newInstance();
				String esitoAzione = azione.esegui(request);
				prossimaPagina = this.esito2pagina.get(esitoAzione);
			} catch (InstantiationException e) {
				prossimaPagina = "/error.jsp";
			} catch (IllegalAccessException e) {
				prossimaPagina = "/error.jsp";
			} catch (ClassNotFoundException e) {
				prossimaPagina = "/error.jsp";
			}
		}
		ServletContext application = getServletContext();
		RequestDispatcher rd = application.getRequestDispatcher(prossimaPagina);
		rd.forward(request, response);
	}
	
	/**
	 * Restituisce il comando estratto dall'indirizzo della servlet a partire dall'ultima occorrenza
	 * di "/" fino al ".do".
	 * 
	 * @param String servletPath
	 * @return String
	 */
	private String leggiComando(String servletPath) {
		int start = servletPath.lastIndexOf("/");
		int stop = servletPath.lastIndexOf(".do");
		return servletPath.substring(start + 1, stop);
	}
	
	@Override
	public void init() {
		this.comando2azione = new HashMap<String, String>();
		this.comando2azione.put("consulta_prodotti", "servlet.ConsultaProdotti");
		this.esito2pagina = new HashMap<String, String>();
		this.esito2pagina.put("listaNonCreata", "/error.jsp");
		this.esito2pagina.put("listaCreata", "/catalogo.jsp");
	}

}