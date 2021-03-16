package model;

/**
 * Klasse für Aktionen
 */
public class Action {

	private ActionType actionType;

	private Card affectedCard;

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
}
