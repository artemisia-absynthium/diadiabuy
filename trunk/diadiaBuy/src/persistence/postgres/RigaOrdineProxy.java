package persistence.postgres;

import persistence.PersistenceException;
import persistence.ProdottoDAO;
import persistence.ProxyLoadingException;
import model.Prodotto;
import model.RigaOrdine;

public class RigaOrdineProxy extends RigaOrdine {

	private int idProdotto;
	
	private boolean prodottoCaricato;
	
	private final ProdottoDAO prodottoDAO;
	
	public RigaOrdineProxy() {
		this.prodottoDAO = new ProdottoDAOpostgres();
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}
	
	@Override
	public Prodotto getProdotto() {
		if (!this.prodottoCaricato) {
			try {
				this.setProdotto(this.prodottoDAO.doRetrieveProdottoById(this.idProdotto));
				this.prodottoCaricato = true;
			} catch (PersistenceException e) {
				throw new ProxyLoadingException("Errore nel caricamento lazy degli ordini.", e);
			}
		}
		return super.getProdotto();
	}
	
}
