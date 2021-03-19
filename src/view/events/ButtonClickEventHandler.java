package view.events;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//unused
public abstract class ButtonClickEventHandler implements EventHandler<ButtonClickEvent> {
        // must be implemented from receiver
        public abstract void onClickButton(ImageView image);

        @Override
        public void handle(ButtonClickEvent event) {
            event.invokeHandler(this);
        }
}


