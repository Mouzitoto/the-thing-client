package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.controllers.TabletopController;
import sample.network.NetworkClient;
import sample.network.NetworkMessage;

import java.io.IOException;

public class Main extends Application {
    public static final String LOBBY_FXML = "lobby.fxml";
    public static final String CONNECT_FXML = "connect.fxml";
    public static final String TABLETOP_FXML = "tabletop.fxml";
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Client");
        primaryStage.setResizable(true);
        showSceneFromFXML(CONNECT_FXML);
    }

    public static void showSceneFromFXML(String fxmlName) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getClassLoader().getResource(fxmlName));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void showTableTop() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        stage.setScene(TabletopController.getTableTopScene(screenBounds));

        //set Stage boundaries to visible bounds of the main screen
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        stage.show();
    }
}
