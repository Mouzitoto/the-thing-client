package sample.controllers;

import javafx.beans.binding.Bindings;
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
import sample.game.*;
import sample.Main;
import sample.network.NetworkClient;
import sample.network.NetworkMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan.babich on 08.08.2016.
 */
public class TabletopController {

    private static Scene scene;
    private static Pane rootPane;
    private static double rootCenterX;
    private static double rootCenterY;
    private static List<CardImageView> playerHandCards = new ArrayList<>();
    private static List<ImageView> otherPlayersHandCards = new ArrayList<>();
    private static CardImageView ivNowPlayingCard;

    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 100;
    private static final int CARD_SEPARATOR = 20;


    private static void init(Rectangle2D screenBounds) {
        rootPane = new Pane();
        scene = new Scene(rootPane, screenBounds.getWidth(), screenBounds.getHeight(), Color.BLACK);

        rootCenterX = rootPane.getWidth() / 2;
        rootCenterY = rootPane.getHeight() / 2;

        if (GameAttributes.getPlayers() == null) {
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
        drawPlayerNames();

    }

    public static void drawNowPlayingCard() {
        //delete if exist
        if (ivNowPlayingCard != null)
            rootPane.getChildren().remove(ivNowPlayingCard);

        //draw new
        ivNowPlayingCard = new CardImageView();
        ivNowPlayingCard.setLayoutX(rootCenterX - CARD_WIDTH / 2);
        ivNowPlayingCard.setLayoutY(rootCenterY - CARD_HEIGHT / 2);
        ivNowPlayingCard.setFitWidth(CARD_WIDTH);
        ivNowPlayingCard.setFitHeight(CARD_HEIGHT);

        Card nowPlayingCard = GameAttributes.getNowPlayingCard();
        Image imgNowPlayingCard = new Image(Main.class.getClassLoader().getResourceAsStream("cards/" + nowPlayingCard.getType() + "/" + nowPlayingCard.getAction().name() + ".png"));

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
        double angle = 360 / GameAttributes.getPlayers().size();

        for (int i = 0; i < GameAttributes.getPlayers().size(); i++) {
            Line line = new Line(rootCenterX, rootCenterY + 300, rootCenterX, rootCenterY - 300);
            line.setRotate(angle * i);

            GamePoint2D gamePoint2D = new GamePoint2D(line.localToParent(rootCenterX, rootCenterY + 300));
            GameAttributes.getPlayers().get(i).setTabletopPosition(gamePoint2D);

            if (GameAttributes.getPlayers().get(i).getName().equals(GameAttributes.getPlayer().getName()))
                GameAttributes.getPlayer().setTabletopPosition(gamePoint2D);
        }

        try {
            NetworkMessage message = new NetworkMessage();
            message.setType(NetworkMessage.NEW_PLAYER_POSITIONS);
            message.setPlayers(GameAttributes.getPlayers());
            NetworkClient.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void drawPlayerNames() {
        for (Player player : GameAttributes.getPlayers()) {
            //draw player name
            Label lblPlayerName = new Label(player.getName());
            lblPlayerName.setLayoutX(player.getTabletopPosition().getX() - (lblPlayerName.getText().length() * 10) / 2);
            lblPlayerName.setLayoutY(player.getTabletopPosition().getY() - 30);
            if (player.getName().equals(GameAttributes.getPlayer().getName()))
                lblPlayerName.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 16));
            rootPane.getChildren().add(lblPlayerName);
        }
    }

    public static void drawOtherPlayersHandCards() {
        //remove current cards if they r exist
        for (ImageView iv : otherPlayersHandCards)
            rootPane.getChildren().remove(iv);

        //draw new cards
        for (Player player : GameAttributes.getPlayers()) {
            if (!player.getName().equals(GameAttributes.getPlayer().getName())) {
                for (int i = 0; i < player.getHandCardsCount(); i++) {
                    ImageView imageView = new ImageView();
                    imageView.setLayoutX(player.getTabletopPosition().getX() - 190 + i * CARD_WIDTH + i * CARD_SEPARATOR);
                    imageView.setLayoutY(player.getTabletopPosition().getY());
                    imageView.setFitWidth(CARD_WIDTH);
                    imageView.setFitHeight(CARD_HEIGHT);

                    Image img = new Image(Main.class.getClassLoader().getResourceAsStream("cards/event/cover.png"));

                    imageView.setImage(img);

                    otherPlayersHandCards.add(imageView); //need for future deleting

                    rootPane.getChildren().add(imageView);
                }
            }
        }
    }

    public static void drawPlayerHandCards() {
        //delete current player hand cards if they r exist
        for (CardImageView civ : playerHandCards)
            rootPane.getChildren().remove(civ);

        //draw new player hand cards
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

            playerHandCards.add(imageView); //we need that for future deleting from tabletop

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

        ivDeck.setOnMouseClicked(event -> {
            try {
                NetworkMessage message = new NetworkMessage();
                message.setType(NetworkMessage.GET_CARD_FROM_DECK);
                NetworkClient.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Label lblDeck = new Label("Колода");
        lblDeck.setLayoutX(40 + CARD_WIDTH / 2 - CARD_SEPARATOR);
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
        lblDroppingDeck.setLayoutX(40 + CARD_WIDTH / 2 - CARD_SEPARATOR + CARD_WIDTH + CARD_SEPARATOR);
        lblDroppingDeck.setLayoutY(20);

        rootPane.getChildren().add(ivDroppingDeck);
        rootPane.getChildren().add(lblDroppingDeck);
    }

    public static Scene getTableTopScene(Rectangle2D screenBounds) {
        init(screenBounds);

        return scene;
    }
}
