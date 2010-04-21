package model;

import java.util.LinkedList;
import java.util.List;

public class Prodotto {
	protected int id;
	private String nome;
	private String codice;
	private String descrizione;
	private double prezzo;
	protected int disponibilita;
	private List<Fornitore> fornitori;

	public Prodotto() {
		this.fornitori = new LinkedList<Fornitore>();
	}
	
	public Prodotto(String nome, String codice, String descrizione, double prezzo, int disponibilita) {
		this();
		this.nome = nome;
		this.codice = codice;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.disponibilita = disponibilita;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		if (disponibilita < 0)
			throw new IllegalArgumentException("La disponibilita' non puo' essere negativa");
		this.disponibilita = disponibilita;
	}

	public List<Fornitore> getFornitori() {
		return this.fornitori;
	}

	public void setFornitori(List<Fornitore> fornitori) {
		this.fornitori = fornitori;
	}

	public void aggiungiFornitore(Fornitore fornitore) {
		this.fornitori.add(fornitore);
	}

	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", codice=" + codice + ", nome=" + nome
				+ ", prezzo=" + prezzo + ", disponibilita=" + disponibilita
				+ ", descrizione=" + descrizione + ", fornitori=" + fornitori
				+ "]";
	}

}
