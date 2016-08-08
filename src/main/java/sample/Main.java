package sample;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static Player player = new Player();
    public static List<Player> players = new ArrayList<>();
    public static GameAttributes gameAttributes = new GameAttributes();
    private static final Client client = new Client();
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int TCP_PORT = 27015;
    private static final int UDP_PORT = 27016;
    public static final String LOBBY_FXML = "lobby.fxml";
    public static final String CONNECT_FXML = "connect.fxml";
    public static final String TABLETOP_FXML = "tabletop.fxml";
    private static Stage stage;

    public static void connectToServer(String hostIP, String playerName) throws IOException {
        client.start();
        client.connect(CONNECTION_TIMEOUT, hostIP, TCP_PORT, UDP_PORT);

        Kryo kryo = client.getKryo();
        kryo.register(NetworkMessage.class);
        kryo.register(Player.class);
        kryo.register(ArrayList.class);

        player.setName(playerName);

        NetworkMessage message = new NetworkMessage();
        message.setType(NetworkMessage.HANDSHAKE);
        message.setPlayer(player);
        client.sendTCP(message);

        client.addListener(new ClientListener());

        //we need to change Scene to lobby after server response
//        showSceneFromFXML(LOBBY_FXML);
    }

    public static void sendChatMessage(String chatMessage) {
        NetworkMessage message = new NetworkMessage();
        message.setType(NetworkMessage.SEND_CHAT_MESSAGE);
        message.setMessage(chatMessage);
        message.setPlayer(player);

        client.sendTCP(message);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Client");
        primaryStage.setResizable(false);
        showSceneFromFXML(CONNECT_FXML);
    }

    public static void showSceneFromFXML(String fxmlName) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getClassLoader().getResource(fxmlName));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void startGame() throws IOException {
        NetworkMessage message = new NetworkMessage();
        message.setType(NetworkMessage.START_GAME);
        message.setPlayer(player);
        client.sendTCP(message);

        showSceneFromFXML(Main.TABLETOP_FXML);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
