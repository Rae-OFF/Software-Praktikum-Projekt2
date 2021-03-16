package model;

public class Person implements Card {

	private int price;

	private int victoryPoints;

	private String name;



	private int swords;

	private Colour colour;

	private PersonType personType;

	public Person() {
	}

	public void setValues(int price, int victoryPoints, int swords){

		this.price = price;
		this.victoryPoints = victoryPoints;
		this.swords = swords;
	}

	public void setMetaData(String name, Colour colour, PersonType personType){

		this.name= name;
		this.colour = colour;
		this.personType = personType;

	}


	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSwords() {
		return swords;
	}

	public void setSwords(int swords) {
		this.swords = swords;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public PersonType getPersonType() {
		return personType;
	}

	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}
}
