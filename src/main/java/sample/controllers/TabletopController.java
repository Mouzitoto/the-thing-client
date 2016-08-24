package sample.controllers;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import sample.game.Card;
import sample.game.GameAttributes;
import sample.Main;
import sample.game.Player;

/**
 * Created by ruslan.babich on 08.08.2016.
 */
public class TabletopController {

    private static Scene scene;
    private static Pane rootPane;
    private static double rootCenterX;
    private static double rootCenterY;


    private static void init(Rectangle2D screenBounds) {
        rootPane = new Pane();
        scene = new Scene(rootPane, screenBounds.getWidth(), screenBounds.getHeight(), Color.BLACK);

        rootCenterX = rootPane.getWidth() / 2;
        rootCenterY = rootPane.getHeight() / 2;

        //only for debugging, just to know where the center is
        Line line1 = new Line(rootCenterX - 1, rootCenterY, rootCenterX + 1, rootCenterY);
        Line line2 = new Line(rootCenterX, rootCenterY - 1, rootCenterX, rootCenterY + 1);
        rootPane.getChildren().add(line1);
        rootPane.getChildren().add(line2);

        GameAttributes.setAlivePlayers(GameAttributes.getPlayers());

        calculatePlayersPositions();
        drawDeckAndDroppingDeck();

    }


    public static void calculatePlayersPositions() {
        double angle = 360 / GameAttributes.getAlivePlayers().size();

        for (int i = 0; i < GameAttributes.getAlivePlayers().size(); i++) {
            Line line = new Line(rootCenterX, rootCenterY + 300, rootCenterX, rootCenterY - 300);
            line.setRotate(angle * i);

            Point2D point = line.localToParent(rootCenterX, rootCenterY + 300);
            GameAttributes.getAlivePlayers().get(i).setTabletopPosition(point);

            drawPlayersStuff(GameAttributes.getAlivePlayers().get(i));
        }
    }

    public static void drawPlayersStuff(Player player) {
        //draw player name
        Label lblPlayerName = new Label(player.getName());

        lblPlayerName.setLayoutX(player.getTabletopPosition().getX() - (lblPlayerName.getText().length() * 10) / 2);
        lblPlayerName.setLayoutY(player.getTabletopPosition().getY() - 100);
        //todo: change this to ID from DB later
        if (player.getName() == GameAttributes.getPlayer().getName())
            lblPlayerName.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 16));
        rootPane.getChildren().add(lblPlayerName);


        //draw player hand
        for (int i = 0; i < player.getHandCards().size(); i++) {
            Card card = player.getHandCards().get(i);
            ImageView imageView = new ImageView();
            imageView.setLayoutX(player.getTabletopPosition().getX() - 190 + i * 80 + i * 20);
            imageView.setLayoutY(player.getTabletopPosition().getY());
            imageView.setFitWidth(80);
            imageView.setFitHeight(100);

            Image img = new Image(Main.class.getClassLoader().getResourceAsStream("cards/" + card.getType() + "/" + card.getAction().name() + ".png"));

            imageView.setImage(img);

            rootPane.getChildren().add(imageView);
        }

    }

    public static void drawDeckAndDroppingDeck() {
        //Deck
        ImageView ivDeck = new ImageView();
        ivDeck.setLayoutX(40);
        ivDeck.setLayoutY(40);
        ivDeck.setFitWidth(80);
        ivDeck.setFitHeight(120);

        Image imgDeck = new Image(Main.class.getClassLoader().getResourceAsStream("cards/panic/cover.png"));
        ivDeck.setImage(imgDeck);

        rootPane.getChildren().add(ivDeck);

        //Dropping Deck
        ImageView ivDroppingDeck = new ImageView();
        ivDroppingDeck.setLayoutX(140);
        ivDroppingDeck.setLayoutY(40);
        ivDroppingDeck.setFitWidth(80);
        ivDroppingDeck.setFitHeight(120);

        Image imgDroppingDeck = new Image(Main.class.getClassLoader().getResourceAsStream("cards/event/axe.png"));
        ivDroppingDeck.setImage(imgDroppingDeck);

        rootPane.getChildren().add(ivDroppingDeck);
    }

    public static Scene getTableTopScene(Rectangle2D screenBounds) {
        init(screenBounds);

        return scene;
    }
}
