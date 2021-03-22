package model;

/**
 * Klasse der Spieler.
 */
public class Player {

	private String name;

	private int score;

	private int wins;

	private PlayerType playerType;

	/**
	 * Konstruktor.
	 * @param name
	 * 		Bekommt einen Namen übergeben.
	 * @param playerType
	 * 		Bekommt einen Spielertyp übergeben.
	 * Erstellt einen neuen Kartenstapel für das Kartenfeld.
	 */
	public Player(String name, PlayerType playerType) {
		this.name = name;
		this.playerType = playerType;
	}

	/**
	 * Implementiert die Vergleichsmethode des Interfaces.
	 * @param other
	 * 		Bekommt einen Spieler übergeben.
	 * @return Gibt (true) zurück wenn der Spieler identisch mit dem aufrufenden ist, ansonsten (false).
	 */
	public boolean equals(Player other){
		return this.name.equals(other.name) && this.playerType.equals(other.playerType);
	}

	/**
	 *
	 * @return Gibt den Namen zurück.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen zurück.
	 * @param name
	 * 		Bekommt den Namen übergeben.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @return Gibt die Punktzahl zurück.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Setzt die Punktzahl.
	 * @param score
	 * 		Bekommt die Punktzahl übergeben.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 *
	 * @return Gibt die Anzahl der gewonnen Spiele zurück.
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * Setzt die Anzahl der gewonnen Spiele.
	 * @param wins
	 * 		Bekommt die Anzahl der gewonnen Spiele übergeben.
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 *
	 * @return Gibt einen Playertypen zurück.
	 */
	public PlayerType getPlayerType() {
		return playerType;
	}

	/**
	 * Setzt einen Playertypen.
	 * @param playerType
	 * 		Bekommt einen Playertypen übergeben (Mensch, einfache/medium/harte KI).
	 */
	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}
}
