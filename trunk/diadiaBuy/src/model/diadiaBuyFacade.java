package model;

import java.util.List;

public class diadiaBuyFacade {
	// TODO: Facade

	private static class SingletonHolder {
		private static final DiadiaBuyFacade INSTANCE = new DiadiaBuyFacade();
	}

	public static DiadiaBuyFacade getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public List<Prodotto> getCatalogo() {
		//TODO: getCatalogo
		return null;
	}

}
