package sample.controllers;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.game.Card;
import sample.game.CardImageView;
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

    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 100;
    private static final int CARD_SEPARATOR = 20;


    private static void init(Rectangle2D screenBounds) {
        rootPane = new Pane();
        scene = new Scene(rootPane, screenBounds.getWidth(), screenBounds.getHeight(), Color.BLACK);

        rootCenterX = rootPane.getWidth() / 2;
        rootCenterY = rootPane.getHeight() / 2;

//        only for debugging, just to know where the center is
//        Line line1 = new Line(rootCenterX - 1, rootCenterY, rootCenterX + 1, rootCenterY);
//        Line line2 = new Line(rootCenterX, rootCenterY - 1, rootCenterX, rootCenterY + 1);
//        rootPane.getChildren().add(line1);
//        rootPane.getChildren().add(line2);

        GameAttributes.setAlivePlayers(GameAttributes.getPlayers());

        calculatePlayersPositions();
        drawDeckAndDroppingDeck();
        drawPlayersStuff();

    }


    public static void calculatePlayersPositions() {
        double angle = 360 / GameAttributes.getAlivePlayers().size();

        for (int i = 0; i < GameAttributes.getAlivePlayers().size(); i++) {
            Line line = new Line(rootCenterX, rootCenterY + 300, rootCenterX, rootCenterY - 300);
            line.setRotate(angle * i);

            Point2D point = line.localToParent(rootCenterX, rootCenterY + 300);
            GameAttributes.getAlivePlayers().get(i).setTabletopPosition(point);

            if (GameAttributes.getAlivePlayers().get(i).getName().equals(GameAttributes.getPlayer().getName()))
                GameAttributes.getPlayer().setTabletopPosition(point);
        }
    }

    public static void drawPlayersStuff() {
        for (Player player : GameAttributes.getAlivePlayers()) {
            //draw player name
            Label lblPlayerName = new Label(player.getName());

            lblPlayerName.setLayoutX(player.getTabletopPosition().getX() - (lblPlayerName.getText().length() * 10) / 2);
            lblPlayerName.setLayoutY(player.getTabletopPosition().getY() - 30);
            //todo: change this to ID from DB later
            if (player.getName() == GameAttributes.getPlayer().getName())
                lblPlayerName.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 16));
            rootPane.getChildren().add(lblPlayerName);

            //draw otherPlayers hand (cover)
            if (!player.getName().equals(GameAttributes.getPlayer().getName())) {
                for (int i = 0; i < 4; i++) {
                    ImageView imageView = new ImageView();
                    imageView.setLayoutX(player.getTabletopPosition().getX() - 190 + i * CARD_WIDTH + i * CARD_SEPARATOR);
                    imageView.setLayoutY(player.getTabletopPosition().getY());
                    imageView.setFitWidth(CARD_WIDTH);
                    imageView.setFitHeight(CARD_HEIGHT);

                    Image img = new Image(Main.class.getClassLoader().getResourceAsStream("cards/event/cover.png"));

                    imageView.setImage(img);

                    rootPane.getChildren().add(imageView);
                }
            }
        }


        //draw player hand
        for (int i = 0; i < GameAttributes.getPlayer().getHandCards().size(); i++) {
            Card card = GameAttributes.getPlayer().getHandCards().get(i);
            CardImageView imageView = new CardImageView();
            int playerHandEdgePoint = (CARD_WIDTH * 4 + CARD_SEPARATOR * 3) / 2 - CARD_WIDTH;
            imageView.setLayoutX(GameAttributes.getPlayer().getTabletopPosition().getX() + playerHandEdgePoint - i * CARD_WIDTH - i * CARD_SEPARATOR);
            imageView.setLayoutY(GameAttributes.getPlayer().getTabletopPosition().getY());
            imageView.setFitWidth(CARD_WIDTH);
            imageView.setFitHeight(CARD_HEIGHT);

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
        ivDeck.setFitWidth(CARD_WIDTH);
        ivDeck.setFitHeight(CARD_HEIGHT);

        Image imgDeck = new Image(Main.class.getClassLoader().getResourceAsStream("cards/panic/cover.png"));
        ivDeck.setImage(imgDeck);


        Label lblDeck = new Label("Колода");
        lblDeck.setLayoutX(40 + CARD_WIDTH/2 - CARD_SEPARATOR);
        lblDeck.setLayoutY(20);

        rootPane.getChildren().add(ivDeck);
        rootPane.getChildren().add(lblDeck);

        //Dropping Deck
        ImageView ivDroppingDeck = new ImageView();
        ivDroppingDeck.setLayoutX(40 + CARD_SEPARATOR + CARD_WIDTH);
        ivDroppingDeck.setLayoutY(40);
        ivDroppingDeck.setFitWidth(CARD_WIDTH);
        ivDroppingDeck.setFitHeight(CARD_HEIGHT);

        Image imgDroppingDeck = new Image(Main.class.getClassLoader().getResourceAsStream("cards/event/cover.png"));
        ivDroppingDeck.setImage(imgDroppingDeck);

        Label lblDroppingDeck = new Label("Сброс");
        lblDroppingDeck.setLayoutX(40 + CARD_WIDTH/2 - CARD_SEPARATOR + CARD_WIDTH + CARD_SEPARATOR);
        lblDroppingDeck.setLayoutY(20);

        rootPane.getChildren().add(ivDroppingDeck);
        rootPane.getChildren().add(lblDroppingDeck);
    }

    public static Scene getTableTopScene(Rectangle2D screenBounds) {
        init(screenBounds);

        return scene;
    }
}
