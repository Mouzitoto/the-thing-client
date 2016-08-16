package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        primaryStage.setResizable(false);
        showSceneFromFXML(CONNECT_FXML);

        stage.setOnCloseRequest(event ->
            NetworkClient.sendMessage(NetworkMessage.PLAYER_QUIT)
        );
    }

    public static void showSceneFromFXML(String fxmlName) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getClassLoader().getResource(fxmlName));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void showTableTop() {
        stage.setScene(TabletopController.getTableTopScene());
        stage.show();
    }
}
