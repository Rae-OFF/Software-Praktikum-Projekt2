package model;

/**
 * Klasse der Steuererhöhungskarten.
 */
public class TaxIncrease implements Card {

	private boolean typeSwords;

	/**
	 * Konstruktor.
	 * @param typeSwords
	 * 		Bekommt die Art der Karte übergeben (minimale Siegpunkte (false) oder maximale Schwerter (true)).
	 */
	public TaxIncrease(boolean typeSwords) {
		this.typeSwords = typeSwords;
	}

	/**
	 *
	 * @return Gibt zurück ob es eine Karte des Typen minimale Siegpunkte (false) oder maximale Schwerter (true) ist.
	 */
	public boolean isTypeSwords() {
		return typeSwords;
	}

	/**
	 * Setzt den Typ.
	 * @param typeSwords
	 * 		Bekommt minimale Siegpunkte (false) oder maximale Schwerter (true) übergeben.
	 */
	public void setTypeSwords(boolean typeSwords) {
		this.typeSwords = typeSwords;
	}


	@Override
	public boolean equals(Card card) {
		if (card instanceof  TaxIncrease){
			return (this.typeSwords == ((TaxIncrease) card).isTypeSwords());
		}
		else{
			return false;
		}
	}

	@Override
	public String toString(){
		return "TaxIncrease: " + "typeSwords: " + typeSwords;

	}
}
