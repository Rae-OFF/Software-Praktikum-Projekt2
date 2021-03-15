package model;

public class Action {

	private ActionType actionType;

	private Card affectedCard;

	private Action action;


	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
}
