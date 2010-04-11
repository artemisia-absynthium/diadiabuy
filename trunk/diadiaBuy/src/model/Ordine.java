package model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Ordine {
	private int id;
	private Calendar data;
	private String codice;
	private Stati stato;
	private Cliente cliente;
	private List<RigaOrdine> righeOrdine;

	private enum Stati {
		APERTO, CHIUSO, EVASO
	};

	public Ordine() {
		this.setRigheOrdine(new LinkedList<RigaOrdine>());
		this.stato = Stati.APERTO;
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

	public Stati getStato() {
		return stato;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setRigheOrdine(List<RigaOrdine> righeOrdine) {
		this.righeOrdine = righeOrdine;
	}

	public List<RigaOrdine> getRigheOrdine() {
		return righeOrdine;
	}

	public void chiudi() {
		this.stato = Stati.CHIUSO;
	}

	public void evadi() {
		this.stato = Stati.EVASO;
	}

}