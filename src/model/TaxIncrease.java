package model;

public class TaxIncrease implements Card {

	private boolean typeSwords;

	public boolean isTypeSwords() {
		return typeSwords;
	}

	public void setTypeSwords(boolean typeSwords) {
		this.typeSwords = typeSwords;
	}

	public TaxIncrease(boolean typeSwords) {
		this.typeSwords = typeSwords;
	}
}
