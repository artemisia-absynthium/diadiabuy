package model;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

public class Utente {

	protected int id;
	
	private String username;
	
	private String password;
	
	private String ruolo;
	
	private List<Ordine> ordini;
	
	public interface Ruolo {
		public static final String AMMINISTRATORE = "admin";
		public static final String REGISTRATO = "registrato";
		public static final String GUEST = "guest";
	}

	public Utente() {
		/* bean */
	}
	
	public Utente(String username, String password, String ruolo) {
		this.ordini = new LinkedList<Ordine>();
		this.username = username;
		this.password = password;
		this.ruolo = ruolo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return this.ruolo.equalsIgnoreCase(Ruolo.AMMINISTRATORE);
	}

	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Ordine newOrdine(String codice) {
		Ordine ordine = new Ordine(this, codice);
		this.getOrdini().add(ordine);
		return ordine;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", username=" + username + ", ruolo="
				+ ruolo + ", ordini=" + ordini + "]";
	}

	public boolean isAuthorized(HttpServletRequest request) {
		if (!DigestUtils.md5Hex(request.getParameter("password")).equals(this.password))
			return false;
		return true;
	}


}
