package sample.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private static SimpleBooleanProperty isGameOwner = new SimpleBooleanProperty(Player.class, "isGameOwner");
    private static Player player = new Player();
    private static List<Player> players = new ArrayList<>();
    private static int moveDirection = 1;
    private static SimpleStringProperty nowMovingPlayerName = new SimpleStringProperty(GameAttributes.class, "nowMovingPlayerName");
    private static Card nowPlayingCard;

    //GETTERS AND SETTERS


    public static Card getNowPlayingCard() {
        return nowPlayingCard;
    }

    public static void setNowPlayingCard(Card nowPlayingCard) {
        GameAttributes.nowPlayingCard = nowPlayingCard;
    }

    public static String getNowMovingPlayerName() {
        return nowMovingPlayerName.get();
    }

    public static StringProperty nowMovingPlayerNameProperty() {
        return nowMovingPlayerName;
    }

    public static void setNowMovingPlayerName(String nowMovingPlayerName) {
        GameAttributes.nowMovingPlayerName.set(nowMovingPlayerName);
    }

    public static int getMoveDirection() {
        return moveDirection;
    }

    public static void setMoveDirection(int moveDirection) {
        GameAttributes.moveDirection = moveDirection;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        GameAttributes.player = player;
        if (player.isGameOwner())
            setGameOwner(true);
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(List<Player> players) {
        GameAttributes.players = players;
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

    public static boolean isGameOwner() {
        return isGameOwner.get();
    }

    public static void setGameOwner(boolean gameOwner) {
        isGameOwner.set(gameOwner);
    }

    public static BooleanProperty isGameOwnerProperty() {
        return isGameOwner;
    }
}

