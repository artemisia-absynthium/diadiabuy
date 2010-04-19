package model;

public class RigaOrdine {

	private int id;
	private int quantita;
	private int numeroDiRiga;

	private Prodotto prodotto;
	private Ordine ordine;

	public RigaOrdine() {
		/* bean */
	}
	
	RigaOrdine(Ordine ordine, Prodotto prodotto, int quantita, int numeroDiRiga) {
		this.ordine = ordine;
		this.quantita = quantita;
		this.prodotto = prodotto;
		this.numeroDiRiga = numeroDiRiga;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}
	
	public int getNumeroDiRiga() {
		return numeroDiRiga;
	}

	public void setNumeroDiRiga(int numeroDiRiga) {
		this.numeroDiRiga = numeroDiRiga;
	}
	
	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	@Override
	public String toString() {
		return "RigaOrdine [id=" + id + ", numeroDiRiga=" + numeroDiRiga
				+ ", prodotto=" + prodotto + ", quantita=" + quantita + "]";
	}

}
