package main;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import model.DiadiaBuyFacade;
import model.Fornitore;
import model.Ordine;
import model.Prodotto;
import model.Utente;
import persistence.postgres.DBUtil;

public class CasiDUso {
	
	private static final DiadiaBuyFacade DIADIABUY_FACADE = DiadiaBuyFacade.getInstance();
	
	public static void casoUC1_ConsultaElencoProdotti() {
		/*
		 	1.	Il cliente consulta il catalogo dei prodotti in magazzino
			2.	Il cliente sceglie un prodotto e ne richiede i dettagli
			3.	Il sistema mostra i dettagli del prodotto scelto al cliente
			4.	Il cliente ripete i passi precedenti un numero indefinito di volte
		 */
		System.out.println("UC1: Consulta Elenco Prodotti");
		List<Prodotto> catalogo = DIADIABUY_FACADE.getCatalogo();
		System.out.println(catalogo);
		Prodotto prodotto = catalogo.get(3);
		System.out.println(prodotto.getDescrizione());
	}
	
	public static void casoUC2_EffettuaOrdine() {
		/*
		 	1.	Il cliente crea un ordine
			2.	Il cliente consulta il catalogo dei prodotti
			3.	Il cliente sceglie un prodotto e lo aggiunge, specificandone la quantità, all'ordine
			4.	Il cliente ripete i due passi precedenti finché necessario
			5.	Il cliente chiude l'ordine
			6.	Il sistema registra l'ordine
		 */
		System.out.println("UC2: Effettua Ordine");
		Utente pippo = DIADIABUY_FACADE.getUtente("pippo");
		Ordine ordine = pippo.newOrdine("Ordine_PIPPO_1");
		List<Prodotto> catalogo = DIADIABUY_FACADE.getCatalogo();
		ordine.aggiungiProdotto(catalogo.get(0), 3);
		ordine.aggiungiProdotto(catalogo.get(1), 1);
		ordine.chiudi();
		DIADIABUY_FACADE.registraOrdine(ordine);
		System.out.println(catalogo);
	}
	
	public static void casoUC3_ConsultaIPropriOrdini() {
		/*
			1.	Il cliente consulta l'elenco dei propri ordini
			2.	Il sistema mostra al cliente i propri ordini specificandone data e stato
		 */
		System.out.println("UC3: Consulta I Propri Ordini");
		Utente pippo = DIADIABUY_FACADE.getUtente("pippo");
		for (Ordine ordine : pippo.getOrdini()) {
			System.out.println(ordine);
		}
	}
	
	public static void casoUC4_InserimentoProdottiNelCatalogo() {
		/*
			1.	L'amministratore inserisce un nuovo prodotto nel catalogo specificandone nome, codice e prezzo, disponibilità in magazzino
			2.	Il sistema registra il prodotto
			3.	I punti precedenti vengono ripetuti fino a che necessario
		 */
		System.out.println("UC4: Inserimento Prodotti Nel Catalogo");
		Utente admin = DIADIABUY_FACADE.getUtente("admin");
		Prodotto pda_1 = new Prodotto("Prodotto_da_admin_1", "PDA_1", "Prodotto inserito dall'amministrazione 1.", 3.4, 12);
		Prodotto pda_2 = new Prodotto("Prodotto_da_admin_2", "PDA_2", "Prodotto inserito dall'amministrazione 2.", 0, 0);
		Prodotto pda_3 = new Prodotto("Prodotto_da_admin_3", "PDA_3", "Prodotto inserito dall'amministrazione 3.", 13.2, 0);
		DIADIABUY_FACADE.newProdotto(admin, pda_1);
		DIADIABUY_FACADE.newProdotto(admin, pda_2);
		DIADIABUY_FACADE.newProdotto(admin, pda_3);
		System.out.println(DIADIABUY_FACADE.getCatalogo());
	}
	
	public static void casoUC5_CercaFornitore() {
		/*
			1.	L'amministratore comunica al sistema il codice di un prodotto
			2.	Il sistema mostra all'amministratore l'elenco dei fornitori per quel prodotto
		 */
		System.out.println("UC5: Cerca Fornitore");
		Utente admin = DIADIABUY_FACADE.getUtente("admin");
		Collection<Fornitore> fornitori = DIADIABUY_FACADE.getFornitoreByCodiceProdotto(admin, "PD_1");
		System.out.println(fornitori);
	}
	
	public static void casoUC6_AggiungiFornitore() {
		/*
			1.	L'amministratore comunica al sistema il codice di un prodotto
			2.	L'amministratore comunica al sistema i dati di un fornitore
			3.	Il sistema registra associa il fornitore al prodotto
		 */
		System.out.println("UC6: Aggiungi Fornitore");
		Utente admin = DIADIABUY_FACADE.getUtente("admin");
		Fornitore fa_1 = new Fornitore("Fornitore_UC6", "Via Dei Campolli Ruspanti 6", "0000");
		DIADIABUY_FACADE.newFornitore(admin, fa_1);
		DIADIABUY_FACADE.aggiungiFornitore(admin, "PD_3", fa_1);
	}
	
	public static void main(String[] args) {
		init();
		casoUC1_ConsultaElencoProdotti();
		casoUC2_EffettuaOrdine();
		casoUC3_ConsultaIPropriOrdini();
		casoUC4_InserimentoProdottiNelCatalogo();
		casoUC5_CercaFornitore();
		casoUC6_AggiungiFornitore();
	}

	private static void init() {
		resetTable();
		Utente admin = new Utente("admin", Utente.Ruolo.AMMINISTRATORE);
		Utente pippo = new Utente("pippo", Utente.Ruolo.REGISTRATO);
		DIADIABUY_FACADE.newUtente(pippo);
		DIADIABUY_FACADE.newUtente(admin);
		
		Prodotto pd_1 = new Prodotto("Prodotto1", "PD_1", "Descrizione Prodotto 1", 10.1, 111);
		Prodotto pd_2 = new Prodotto("Prodotto2", "PD_2", "Descrizione Prodotto 2", 20.2, 222);
		Prodotto pd_3 = new Prodotto("Prodotto3", "PD_3", "Descrizione Prodotto 3", 30.3, 333);
		Prodotto pd_4 = new Prodotto("Prodotto4", "PD_4", "Descrizione Prodotto 4", 40.4, 0);
		DIADIABUY_FACADE.newProdotto(admin, pd_1);
		DIADIABUY_FACADE.newProdotto(admin, pd_2);
		DIADIABUY_FACADE.newProdotto(admin, pd_3);
		DIADIABUY_FACADE.newProdotto(admin, pd_4);
		
		Fornitore f_1 = new Fornitore("Fornitore1", "Via Dei Campolli Ruspanti 4", "610");
		DIADIABUY_FACADE.newFornitore(admin, f_1);
		DIADIABUY_FACADE.aggiungiFornitore(admin, "PD_1", f_1);
	}

	private static void resetTable() {
		final String[] tableNames = new String[]{	"fornitura", 	"fornitori", 
													"righe_ordine", "ordini", "prodotti", 
													"utenti"};
		for (String tableName : tableNames) {
			try {
				Connection connection = DBUtil.getDataSource().getConnection();
				connection.createStatement().execute("DELETE FROM " + tableName);
			} catch (Exception e) {
				throw new RuntimeException("Impossibile effettuare la delete delle tabelle");
			}
		}
	}
}