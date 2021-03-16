package model;

/**
 * Klasse der Personenkarten.
 */
public class Person implements Card {

	private int price;

	private int victoryPoints;

	private String name;

	private int swords;

	private Colour colour;

	private PersonType personType;

	/**
	 * Konstruktor.
	 */
	public Person() {
	}

	/**
	 * Setzt die Werte der Personenkarte.
	 * @param price
	 * 		Bekommt den Preis übergeben.
	 * @param victoryPoints
	 * 		Bekommt die Siegpunkte übergeben.
	 * @param swords
	 * 		Bekommt die Anzahl der Schwerter übergeben (für den Sailor und den Captain).
	 */
	public void setValues(int price, int victoryPoints, int swords){
		this.price = price;
		this.victoryPoints = victoryPoints;
		this.swords = swords;
	}

	/**
	 * Setzt die Meta Daten der Personenkarte.
	 * @param name
	 * 		Bekommt den Namen übergeben.
	 * @param colour
	 * 		Bekommt die Farbe übergeben (für den Trader).
	 * @param personType
	 * 		Bekommt den Personentyp übergeben.
	 */
	public void setMetaData(String name, Colour colour, PersonType personType){
		this.name= name;
		this.colour = colour;
		this.personType = personType;

	}

	/**
	 *
	 * @return Gibt den Preis der Karte zurück.
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Setzt den Preis zurück.
	 * @param price
	 * 		Bekommt den Preis übergeben.
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 *
	 * @return Gibt die Siegpunkte zurück.
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * Setzt die Siegpunkte.
	 * @param victoryPoints
	 * 		Bekommt die Siegpunkte übergeben.
	 */
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	/**
	 *
	 * @return Gibt den Namen zurück.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen.
	 * @param name
	 * 		Bekommt den Namen zurück.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return Gibt die Anzahl der Schwerter zurück.
	 */
	public int getSwords() {
		return swords;
	}

	/**
	 * Setzt die Anzahl der Schwerter.
	 * @param swords
	 * 		Bekommt die Anzahl der Schwerter übergeben. Bracuht die Karte keine Schwerter wird diese auf 0 gesetzt.
	 */
	public void setSwords(int swords) {
		this.swords = swords;
	}

	/**
	 *
	 * @return Gibt die Farbe der Karte zurück.
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * Setzt die Farbe der Karte.
	 * @param colour
	 * 		Bekommt die Farbe übergeben. Braucht die Karte keine Farbe wird dieser auf null gesetzt.
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}

	/**
	 *
	 * @return Gibt den Personentyp zurück.
	 */
	public PersonType getPersonType() {
		return personType;
	}

	/**
	 * Setzt den Personentyp.
	 * @param personType
	 * 		Bekommt den Personentyp übergeben.
	 */
	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}
}
