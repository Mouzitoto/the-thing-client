package sample.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import javafx.geometry.Point2D;
import sample.game.*;

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
        kryo.register(NetworkMessage.class, 111);
        kryo.register(Player.class, 112);
        kryo.register(ArrayList.class, 113);
        kryo.register(Point2D.class, 114);
        kryo.register(Card.class, 115);
//        kryo.register(CardTypes.class, 216);
        kryo.register(CardActions.class, 117);



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
