package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import sample.network.ClientListener;
import sample.network.NetworkClient;
import sample.network.NetworkMessage;

import java.io.IOException;

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

        if (GameAttributes.getAlivePlayers() == null) {
            System.out.println("omg");
        }

//        only for debugging, just to know where the center is
//        Line line1 = new Line(rootCenterX - 1, rootCenterY, rootCenterX + 1, rootCenterY);
//        Line line2 = new Line(rootCenterX, rootCenterY - 1, rootCenterX, rootCenterY + 1);
//        rootPane.getChildren().add(line1);
//        rootPane.getChildren().add(line2);

        calculatePlayersPositions();
        drawDeckAndDroppingDeck();
        drawMoveDirectionArrow(GameAttributes.getMoveDirection());
        drawNowMovingPlayerName();
        drawPlayersStuff();

    }

    public static void drawNowPlayingCard() {
        CardImageView ivNowPlayingCard = new CardImageView();
        ivNowPlayingCard.setLayoutX(rootCenterX - CARD_WIDTH / 2);
        ivNowPlayingCard.setLayoutY(rootCenterY - CARD_HEIGHT / 2);
        ivNowPlayingCard.setFitWidth(CARD_WIDTH);
        ivNowPlayingCard.setFitHeight(CARD_HEIGHT);

        Card nowPlayingCard = GameAttributes.getNowPlayingCard();
        Image imgNowPlayingCard = new Image(Main.class.getClassLoader().getResourceAsStream(nowPlayingCard.getAction().name() + ".png"));

        ivNowPlayingCard.setImage(imgNowPlayingCard);

        rootPane.getChildren().add(ivNowPlayingCard);
    }

    public static void drawNowMovingPlayerName() {
        Label lbl = new Label("Now is moving: ");
        lbl.setLayoutX(40);
        lbl.setLayoutY(120 + CARD_HEIGHT + CARD_WIDTH * 2);

        Label lblNowMovingPlayerName = new Label();
        lblNowMovingPlayerName.setLayoutX(130);
        lblNowMovingPlayerName.setLayoutY(120 + CARD_HEIGHT + CARD_WIDTH * 2);
        lblNowMovingPlayerName.textProperty().bind(Bindings.convert(GameAttributes.nowMovingPlayerNameProperty()));

        rootPane.getChildren().add(lbl);
        rootPane.getChildren().add(lblNowMovingPlayerName);
    }

    public static void drawMoveDirectionArrow(int direction) {
        ImageView imageView = new ImageView();
        imageView.setLayoutX(50);
        imageView.setLayoutY(100 + CARD_HEIGHT);
        imageView.setFitWidth(CARD_WIDTH * 2);
        imageView.setFitHeight(CARD_WIDTH * 2);

        Image imgMoveDirectionArrow;
        if (direction == 1)
            imgMoveDirectionArrow = new Image(Main.class.getClassLoader().getResourceAsStream("clockwise-arrow.png"));
        else
            imgMoveDirectionArrow = new Image(Main.class.getClassLoader().getResourceAsStream("anti-clockwise-arrow.png"));

        imageView.setImage(imgMoveDirectionArrow);

        rootPane.getChildren().add(imageView);
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

        ivDeck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (GameAttributes.getPlayer().getName().equals(GameAttributes.getNowMovingPlayerName())) {
                    NetworkClient.sendMessage(NetworkMessage.GET_CARD_FROM_DECK);
                }
            }
        });

        Button btn = new Button();
        btn.setText("omg");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Main.showSceneFromFXML(Main.LOBBY_FXML);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        rootPane.getChildren().add(btn);

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
