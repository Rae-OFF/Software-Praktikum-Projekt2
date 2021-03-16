package model;

public class Ship implements Card {

	private Colour colour;

	private int coins;

	private int force;

	public Ship(Colour colour, int coins, int force) {
		this.colour = colour;
		this.coins = coins;
		this.force = force;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}
}
