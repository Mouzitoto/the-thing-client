package sample.game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan.babich on 26.07.2016.
 */
public class GameAttributes {
    private static SimpleStringProperty playersNames = new SimpleStringProperty(GameAttributes.class, "playersNames");
    private static SimpleStringProperty lobbyChat = new SimpleStringProperty(GameAttributes.class, "lobbyChat");
    private static Player player = new Player();
    private static List<Player> players = new ArrayList<>();
    private static List<Player> alivePlayers = new ArrayList<>();


    //GETTERS AND SETTERS


    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        GameAttributes.player = player;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(List<Player> players) {
        GameAttributes.players = players;
    }

    public static List<Player> getAlivePlayers() {
        return alivePlayers;
    }

    public static void setAlivePlayers(List<Player> alivePlayers) {
        GameAttributes.alivePlayers = alivePlayers;
    }

    public static String getPlayersNames() {
        return playersNames.get();
    }

    public static void setPlayersNames() {
        String allNames = "";
        for (Player player : GameAttributes.getPlayers()) {
            allNames += player.getName() + "\n";
        }
        allNames.trim();

        playersNames.set(allNames);
    }

    public static StringProperty playersNamesProperty() {
        return playersNames;
    }

    public static String getLobbyChat() {
        return lobbyChat.get();
    }

    public static void setLobbyChat(String chatMessage) {
        if (lobbyChat.get() != null)
            lobbyChat.set(lobbyChat.get() + "\n" + chatMessage);
        else
            lobbyChat.set(chatMessage);
    }

    public static StringProperty lobbyChatProperty() {
        return lobbyChat;
    }
}

