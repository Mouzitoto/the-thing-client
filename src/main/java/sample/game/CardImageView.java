package sample.game;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by ruslan.babich on 025 25.08.2016.
 */
public class CardImageView extends ImageView {
    public CardImageView() {
        CardImageView card = this;
        card.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                card.setFitWidth(200);
                card.setFitHeight(250);
            }
        });
        card.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                card.setFitWidth(80);
                card.setFitHeight(100);
            }
        });
    }
}
