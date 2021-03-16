package model;

public class Action {

	private ActionType actionType;

	private Card affectedCard;

	public Action(ActionType actionType, Card affectedCard) {
		this.actionType = actionType;
		this.affectedCard = affectedCard;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
}
