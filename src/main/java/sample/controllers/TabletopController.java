package sample.controllers;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import sample.GameAttributes;
import sample.Main;
import sample.game.Player;

/**
 * Created by ruslan.babich on 08.08.2016.
 */
public class TabletopController {

    private static Scene scene;
    private static Pane rootPane;


    private static void init() {
        rootPane = new Pane();
        scene = new Scene(rootPane, 1200, 800, Color.BLACK);
        Main.alivePlayers = Main.players;


        calculatePlayersPositions();

    }


    public static void calculatePlayersPositions() {
        double rootCenterX = rootPane.getWidth() / 2;
        double rootCenterY = rootPane.getHeight() / 2;

        double angle = 360 / Main.alivePlayers.size();

        for (int i = 0; i < Main.alivePlayers.size(); i++) {
            Line line = new Line(rootCenterX, rootCenterY + 300, rootCenterX, rootCenterY - 300);
            line.setRotate(angle * i);

            Point2D point = line.localToParent(rootCenterX, rootCenterY + 300);
            Main.alivePlayers.get(i).setTabletopPosition(point);

            drawPlayersStuff(Main.alivePlayers.get(i));
        }
    }

    public static void drawPlayersStuff(Player player) {
        Label lblPlayerName = new Label(player.getName());
        lblPlayerName.setLayoutX(player.getTabletopPosition().getX());
        lblPlayerName.setLayoutY(player.getTabletopPosition().getY() - 100);
        //todo: change this to ID from DB later
        if (player.getName() == Main.player.getName())
            lblPlayerName.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        rootPane.getChildren().add(lblPlayerName);
    }

    public static Scene getTableTopScene() {
        init();

        return scene;
    }
}
