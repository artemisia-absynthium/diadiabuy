package model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Ordine {
	private int id;
	private Calendar data;
	private String codice;
	private String stato;
	private Utente cliente;
	private List<RigaOrdine> righeOrdine;

	public interface Stati {
		public static final String APERTO = "aperto";
		public static final String CHIUSO = "chiuso";
		public static final String EVASO = "evaso";
	}

	public Ordine() {
		/* bean */
	}
	
	Ordine(Utente cliente, String codice) {
		this.righeOrdine = new LinkedList<RigaOrdine>();
		this.codice = codice;
		this.cliente = cliente;
		this.stato = Stati.APERTO;
		this.data = Calendar.getInstance();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setCliente(Utente cliente) {
		this.cliente = cliente;
	}

	public Utente getCliente() {
		return cliente;
	}

	public void setRigheOrdine(List<RigaOrdine> righeOrdine) {
		this.righeOrdine = righeOrdine;
	}

	public List<RigaOrdine> getRigheOrdine() {
		return righeOrdine;
	}

	public void chiudi() {
		if (!canUpdateAllRigheOrdine())
			throw new IllegalStateException("Impossibile chiudere l'ordine. " +
					"Non tutti i prodotti sono disponibili in magazzino");
		updateAllRigheOrdine();
		this.stato = Stati.CHIUSO;
	}

	private void updateAllRigheOrdine() {
		for (RigaOrdine riga : this.getRigheOrdine())
			riga.updateAvailability();
	}

	private boolean canUpdateAllRigheOrdine() {
		for (RigaOrdine riga : this.getRigheOrdine())
			if (!riga.canUpdateAvailability())
				return false;
		return true;
	}

	public void evadi() {
		this.stato = Stati.EVASO;
	}

	public RigaOrdine aggiungiProdotto(Prodotto prodotto, int quantita) {
		RigaOrdine rigaOrdine = cercaRigaOrdine(prodotto);
		if (rigaOrdine == null) {
			rigaOrdine = new RigaOrdine(this, prodotto, quantita, this.righeOrdine.size() + 1, prodotto.getNome());//sarà il prossimo
			this.righeOrdine.add(rigaOrdine);
		} else {
			rigaOrdine.setQuantita(rigaOrdine.getQuantita() + quantita);
		}
		return rigaOrdine;
	}

	private RigaOrdine cercaRigaOrdine(Prodotto prodotto) {
		for (RigaOrdine riga : this.righeOrdine)
			if (riga.getProdotto().equals(prodotto))
				return riga;
		return null;
	}
	
	public double getTotale() {
		double totale = 0;
		for (RigaOrdine riga : this.getRigheOrdine())
			totale += riga.getTotale();
		return totale;
			
	}

	@Override
	public String toString() {
		return "Ordine [cliente=" + cliente + ", id=" + id + ", codice="
				+ codice + ", data=" + data.getTime() + ", stato=" + stato
				+ ", righeOrdine=" + righeOrdine + "]";
	}

}
