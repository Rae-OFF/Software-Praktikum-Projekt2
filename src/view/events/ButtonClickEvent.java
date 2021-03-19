package view.events;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//unused
public class ButtonClickEvent extends Event {

    private final ImageView image;

    public static final EventType<ButtonClickEvent> CLICK_BUTTON_EVENT  = new EventType<>(ANY, "CLICK_BUTTON_EVENT");

    public ButtonClickEvent(ImageView image) {
        super(CLICK_BUTTON_EVENT);
        this.image = image;
    }

    public void invokeHandler(ButtonClickEventHandler handler) {
       handler.onClickButton(image);
    }

}
