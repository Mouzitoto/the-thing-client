package sample.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import javafx.application.Platform;
import sample.game.Card;
import sample.game.GameAttributes;
import sample.Main;
import sample.game.Player;

import java.io.IOException;

/**
 * Created by ruslan.babich on 26.07.2016.
 */
public class ClientListener extends Listener {
    public void received(Connection connection, Object object) {
        if (object instanceof NetworkMessage) {
            final NetworkMessage message = (NetworkMessage) object;

            //NEW PLAYER
            if (message.getType().equals(NetworkMessage.NEW_PLAYER)) {
                System.out.println(NetworkMessage.NEW_PLAYER + " received from server");
                //if we need to modify smth from not-this-application-thread (for example another client) - we need to use runLater()
                Platform.runLater(() -> {
                    GameAttributes.setPlayers(message.getPlayers());
                    GameAttributes.setPlayersNames();
                    for (Player player : message.getPlayers()) {
                        //TODO: change this to ID from database in future
                        if (player.getName().equals(GameAttributes.getPlayer().getName()))
                            GameAttributes.setPlayer(player);
                    }
                    try {
                        Main.showSceneFromFXML(Main.LOBBY_FXML);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            //SEND CHAT MESSAGE
            if (message.getType().equals(NetworkMessage.SEND_CHAT_MESSAGE)) {
                System.out.println(NetworkMessage.SEND_CHAT_MESSAGE + " received from server");
                //if we need to modify smth from not-this-application-thread (for example another client) - we need to use runLater()
                Platform.runLater(() -> GameAttributes.setLobbyChat(message.getMessage()));
            }

            //START GAME
            if (message.getType().equals(NetworkMessage.START_GAME)) {
                System.out.println(NetworkMessage.START_GAME + " received from server");
                Platform.runLater(() -> {
                    Main.showTableTop();
                });
            }

            //PLAYER QUIT
            if (message.getType().equals(NetworkMessage.PLAYER_QUIT)) {
                System.out.println(NetworkMessage.PLAYER_QUIT + " received from server");
                GameAttributes.setPlayers(message.getPlayers());
                GameAttributes.setPlayersNames();
                for (Player player : message.getPlayers()) {
                    //TODO: change this to ID from database in future
                    if (player.getName().equals(GameAttributes.getPlayer().getName()))
                        GameAttributes.setPlayer(player);
                }
            }

            //GET CARD FROM DECK
            if (message.getType().equals(NetworkMessage.GET_CARD_FROM_DECK)) {
                System.out.println(NetworkMessage.GET_CARD_FROM_DECK + " received from server");

                GameAttributes.getPlayer().getHandCards().add(message.getCard());

                Card card = GameAttributes.getPlayer().getHandCards().get(GameAttributes.getPlayer().getHandCards().size() - 1);
                System.out.println("My cards: " + card.getType() + " " + card.getAction());
            }
        }
    }
}
