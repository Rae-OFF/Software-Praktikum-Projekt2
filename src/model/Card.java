package model;

/**
 * Interface für alle Karten.
 */
public interface Card {

    /**
     * Vergleichsmethode.
     * @param card
     *      Bekommt eine Karte übergeben.
     * @return Gibt zurück ob die übergebene Karte die gleiche ist.
     */
    boolean equals(Card card);

    /**
     *
     * @return Gibt einen String zurück.
     */
    String toString();
}
