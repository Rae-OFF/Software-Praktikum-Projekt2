package model;

import java.util.Map;

/**
 * Klasse für die Expeditionskarten.
 */
public class Expedition implements Card {

    private Map<PersonType, Integer> requirements;

    private int coins;

    private int victoryPoints;

    /**
     * Konstruktor.
     *
     * @param requirements  Bekommt die Bedingung übergeben, welche man zum ausrufen erfüllen muss
     *                      (Kombination der Karten Captain, Settler, Priest und Jack of all Trades)
     * @param coins         Bekommt den Wert der Karte in Goldmünzen übergeben.
     * @param victoryPoints Bekommt den Wert der Karte in Siegpunkten übergeben.
     */
    public Expedition(Map<PersonType, Integer> requirements, int coins, int victoryPoints) {
        this.requirements = requirements;
        this.coins = coins;
        this.victoryPoints = victoryPoints;
    }

    /**
     * @return Gibt die Bedingung zurück (Kombination der Karten Captain, Settler, Priest und Jack of all Trades)
     */
    public Map<PersonType, Integer> getRequirements() {
        return requirements;
    }

    /**
     * Setzt die Bedidnung für die Expedition.
     *
     * @param requirements (Kombination der Karten Captain, Settler, Priest und Jack of all Trades)
     */
    public void setRequirements(Map<PersonType, Integer> requirements) {
        this.requirements = requirements;
    }

    /**
     * @return Gibt wieder wie viele Münzen die Karte wert ist.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Setzt wie viele Münzen die Karte wert ist.
     *
     * @param coins Bekommt die Anzahl der Münzen übergeben.
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * @return Gibt die Anzahl der Siegpunkte wieder.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Setzt die Anzahl der Siegpunkte.
     *
     * @param victoryPoints Bekommt die Anzahl der Siegpunkte als Parameter übergeben.
     */
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    @Override
    public boolean equals(Card card) {
        if (card instanceof Expedition) {
            return (this.coins == ((Expedition) card).getCoins()
                    && this.victoryPoints == ((Expedition) card).getVictoryPoints()
                    && this.requirements == ((Expedition) card).requirements);
        } else {
            return false;
        }
    }
}
