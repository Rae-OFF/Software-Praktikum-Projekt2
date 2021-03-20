package model;

import java.util.List;

/**
 * Klasse für Aktionen
 */
public class Action {

	private ActionType actionType;

	private Card affectedCard;

	private List<Card> materials;

	/**
	 *
	 * @return Gibt die Karte zurück die von der Aktion betroffen ist.
	 */
	public Card getAffectedCard() {
		return affectedCard;
	}

	/**
	 * Setzt die Karte die von der Aktion betroffen ist.
	 * @param affectedCard
	 * 		Bekommt eine Karte übergeben.
	 */
	public void setAffectedCard(Card affectedCard) {
		this.affectedCard = affectedCard;
	}

	/**
	 * Konstruktor für Action.
	 * @param actionType
	 * 		Gibt den entsprechenden ActionType der Aktion an.
	 * @param affectedCard
	 * 		Gibt die von der Aktion betroffene Karte an.
	 */
	public Action(ActionType actionType, Card affectedCard) {
		this.actionType = actionType;
		this.affectedCard = affectedCard;
	}

	/**
	 *
	 * @return Gibt den ActionType der Aktion zurück.
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Setzt den ActionType der Aktion.
	 * @param actionType
	 * 		Bekommt den entsprechenden Aktionstyp als Parameter übergeben.
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	/**
	 *
	 * @return Gibt eine Liste von Karten der Materialien zurück (für Expedition).
	 */
	public List<Card> getMaterials() {
		return materials;
	}

	/**
	 * Setzt die Materialien.
	 * @param materials
	 * 		Bekommt eine Liste von Karten übergeben.
	 */
	public void setMaterials(List<Card> materials) {
		this.materials = materials;
	}

}
