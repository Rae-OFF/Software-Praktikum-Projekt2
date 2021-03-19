package model;

/**
 * Klasse der Schiffkarten.
 */
public class Ship implements Card {

	private Colour colour;

	private int coins;

	private int force;

	/**
	 * Konstruktor.
	 * @param colour
	 * 		Bekommt Farbe übergeben.
	 * @param coins
	 * 		Bekommt den Wert (Münzenanzahl) übergeben.
	 * @param force
	 * 		Bekommt die Stärke (Anzahl der Schwerter) übergeben.
	 */
	public Ship(Colour colour, int coins, int force) {
		this.colour = colour;
		this.coins = coins;
		this.force = force;
	}

	/**
	 *
	 * @return Gibt die Farbe zurück.
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * Setzt die Farbe.
	 * @param colour
	 * 		Bekommt die Farbe übergeben.
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}

	/**
	 *
	 * @return Gibt den Wert zurück (Münzenanzahl).
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * Setzt den Wert.
	 * @param coins
	 * 		Bekommt die Münzenanzahl übergeben.
	 */
	public void setCoins(int coins) {
		this.coins = coins;
	}

	/**
	 *
	 * @return Gibt die Stärke (Anzahl der Schwerter zurück).
	 */
	public int getForce() {
		return force;
	}

	/**
	 * Setzt die Störke.
	 * @param force
	 * 		Bekommt die Anzahl der Schwerter übergeben.
	 */
	public void setForce(int force) {
		this.force = force;
	}

	public Ship(){

	}

	@Override
	public boolean equals(Card card) {
		if(card instanceof Ship){
			return (this.colour == ((Ship) card).getColour()
					&& this.coins == ((Ship) card).getCoins()
					&& this.force == ((Ship) card).getForce());
		}else{
			return false;
		}
	}

	@Override
	public String toString(){
		return "Ship" + " Colour: " + colour + " Coins: " + coins + " Force: " + force;
	}
}
