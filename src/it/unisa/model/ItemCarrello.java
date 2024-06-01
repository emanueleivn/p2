package it.unisa.model;

public class ItemCarrello {

	public ItemCarrello(ProdottoBean prodotto){
		this.prodotto = prodotto;
		quantitàCarrello = 1;
	}
	
	public ProdottoBean getProdotto() {
		return prodotto;
	}
	
	public void setProdotto(ProdottoBean prodotto) {
		this.prodotto = prodotto;
	}
	
	public int getQuantitàCarrello() {
		return quantitàCarrello;
	}
	
	public void setQuantitàCarrello(int quantità) {
		this.quantitàCarrello = quantità;
	}
	
	public int getId() {
		return prodotto.getIdProdotto();
	}
	
	public double getTotalPrice() {
		return quantitàCarrello * prodotto.getPrezzo();
		

	}
	
	public String getDescription() {
		return prodotto.getDescrizione();
	}
	
	public void incrementa() {
		if(quantitàCarrello < prodotto.getQuantità() )
			quantitàCarrello = quantitàCarrello + 1;
	}
	
	public void decrementa() {
		if( quantitàCarrello > 1)
			quantitàCarrello = quantitàCarrello - 1;
	}
	
	private ProdottoBean prodotto;
	private int quantitàCarrello;
}