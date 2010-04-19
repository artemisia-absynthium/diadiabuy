package persistence.postgres;

import java.util.List;

import model.Fornitore;
import model.Prodotto;
import persistence.PersistenceException;
import persistence.ProxyLoadingException;

public class ProdottoProxy extends Prodotto {
	private boolean descrizioneCaricata;
	private boolean fornitoriCaricati;
	
	private final ProdottoDAOpostgres prodottoDAOpostgres;
	
	private final FornitoriDAOpostgres fornitoreDAO;
	
	public ProdottoProxy() {
		this.fornitoreDAO = new FornitoriDAOpostgres();
		this.prodottoDAOpostgres = new ProdottoDAOpostgres();
	}

	@Override
	public String getDescrizione() {
		if (!this.descrizioneCaricata)
			try {
				String descrizione = this.prodottoDAOpostgres.caricaDescrizione(this);
				this.setDescrizione(descrizione);
				this.descrizioneCaricata = true;
			} catch (PersistenceException e) {
				throw new ProxyLoadingException("Impossibile caricare la descrizione del prodotto.", e);
			}
		return super.getDescrizione();
	}

	@Override
	public List<Fornitore> getFornitori() {
		if (!this.fornitoriCaricati) {
			try {
				List<Fornitore> fornitori = this.fornitoreDAO.doRetrieveFornitoryByCodiceProdotto(this.getCodice());
				this.setFornitori(fornitori);
				this.fornitoriCaricati = true;
			} catch (PersistenceException e) {
				throw new ProxyLoadingException("Impossibile caricare i fornitori per il prodotto.", e);
			}
		}
		return super.getFornitori();
	}

}
