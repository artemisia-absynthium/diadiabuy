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
		RequestDispatcher rd = application.getRequestDispatcher(response.encodeURL(prossimaPagina));
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
		this.comando2azione.put("consulta_prodotti", "servlet.ConsultaProdottiAction");
		this.comando2azione.put("dettagli", "servlet.DettagliProdottoAction");
		this.comando2azione.put("registrazione", "servlet.RegistrazioneUtenteAction");
		this.comando2azione.put("conferma_registrazione", "servlet.ConfermaRegistrazioneUtenteAction");
		this.comando2azione.put("login", "servlet.LoginAction");
		this.comando2azione.put("aggiungi_al_carrello", "servlet.OrdineAction");
		this.comando2azione.put("carrello", "servlet.CarrelloAction");
		this.comando2azione.put("conferma_acquisto", "servlet.ConfermaAcquistoAction");
		this.comando2azione.put("ordini", "servlet.StoricoOrdiniAction");
		this.comando2azione.put("dettagli_ordine", "servlet.DettagliOrdineAction");
		this.comando2azione.put("gestisci_fornitori", "servlet.CatalogoProdottiAdminAction");
		this.comando2azione.put("inserisci_fornitore", "servlet.InserisciFornitoreAction");
		this.comando2azione.put("elenco_fornitori", "servlet.FornitoriAction");
		this.comando2azione.put("inserisci_prodotto", "servlet.InserisciProdottoAction");
		this.comando2azione.put("logout", "servlet.LogoutAction");
		this.comando2azione.put("ordini_chiusi", "servlet.OrdiniChiusiAction");
		this.comando2azione.put("evadi", "servlet.EvadiOrdineAction");
		this.esito2pagina = new HashMap<String, String>();
		this.esito2pagina.put("listaNonCreata", "/error.jsp");
		this.esito2pagina.put("listaCreata", "/catalogo.jsp");
		this.esito2pagina.put("noDescrizione", "/error.jsp");
		this.esito2pagina.put("descrizioneOK", "/descrizione.jsp");
		this.esito2pagina.put("nonConvalidato", "/registrazione_utente.jsp");
		this.esito2pagina.put("convalidato", "/conferma_registrazione_utente.jsp");
		this.esito2pagina.put("RegistrazioneDaCorreggere", "/registrazione_utente.jsp");
		this.esito2pagina.put("erroreRegistrazione", "/error.jsp");
		this.esito2pagina.put("utenteRegistrato", "/utente_registrato.jsp");
		this.esito2pagina.put("nonLoggato", "/login.jsp");
		this.esito2pagina.put("loggato", "/index.jsp");
		this.esito2pagina.put("prodottoNonAggiunto", "/error.jsp");
		this.esito2pagina.put("prodottoAggiunto", "/consulta_prodotti.do");
		this.esito2pagina.put("carrelloPreso", "/carrello.jsp");
		this.esito2pagina.put("ordineRegistrato", "/ordine_registrato.jsp");
		this.esito2pagina.put("ordiniCaricati", "/storico_ordini.jsp");
		this.esito2pagina.put("ordiniNonCaricati", "/error.jsp");
		this.esito2pagina.put("dettagliOrdineNonCaricati", "/error.jsp");
		this.esito2pagina.put("dettagliOrdineCaricati", "/dettagli_ordine.jsp");
		this.esito2pagina.put("fornitoreNonInserito", "/error.jsp");
		this.esito2pagina.put("fornitoreInserito", "/inserisci_fornitore.jsp");
		this.esito2pagina.put("listaFornitoriNonCaricata", "/error.jsp");
		this.esito2pagina.put("listaFornitoriCaricata", "/elenco_fornitori.jsp");
		this.esito2pagina.put("listaxAdminNonCreata", "/error.jsp");
		this.esito2pagina.put("listaxAdminCreata", "/catalogoxAdmin.jsp");
		this.esito2pagina.put("nonInserito", "/inserimento_prodotto.jsp");
		this.esito2pagina.put("inserito", "/prodotto_inserito.jsp");
		this.esito2pagina.put("logout", "/index.jsp");
		this.esito2pagina.put("ordiniChiusiNonCaricati", "/error.jsp");
		this.esito2pagina.put("ordiniChiusiCaricati", "/ordini_chiusi.jsp");
		this.esito2pagina.put("ordineNonEvaso", "/error.jsp");
		this.esito2pagina.put("ordineEvaso", "/ordini_chiusi.do");
	}

}
