package sample.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Client;
import com.sun.javafx.binding.ExpressionHelper;
import com.sun.javafx.binding.ExpressionHelperBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import sample.game.GameAttributes;
import sample.game.Player;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ruslan.babich on 16.08.2016.
 */
public class NetworkClient {
    private static final Client client = new Client();
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int TCP_PORT = 27015;
    private static final int UDP_PORT = 27016;



    public static void connectToServer(String hostIP, String playerName) throws IOException {
        client.start();
        client.connect(CONNECTION_TIMEOUT, hostIP, TCP_PORT, UDP_PORT);

        Kryo kryo = client.getKryo();
        kryo.register(NetworkMessage.class);
        kryo.register(Player.class);
        kryo.register(ArrayList.class);
        kryo.register(Point2D.class);
        kryo.register(Class.class);

        GameAttributes.getPlayer().setName(playerName);
        //todo: add playerID to player

        sendMessage(NetworkMessage.HANDSHAKE);

        client.addListener(new ClientListener());

        //we need to change Scene to lobby after server response
//        showSceneFromFXML(LOBBY_FXML);
    }

    public static void sendChatMessage(String chatMessage) {
        NetworkMessage message = new NetworkMessage();
        message.setType(NetworkMessage.SEND_CHAT_MESSAGE);
        message.setMessage(chatMessage);
        message.setPlayer(GameAttributes.getPlayer());

        client.sendTCP(message);
    }

    public static void sendMessage(String type) {
        NetworkMessage message = new NetworkMessage();
        message.setType(type);
        message.setPlayer(GameAttributes.getPlayer());
        client.sendTCP(message);
    }
}
