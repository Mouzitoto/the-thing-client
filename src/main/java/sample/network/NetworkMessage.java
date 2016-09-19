package sample.network;

import sample.game.Card;
import sample.game.Player;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ruslan.babich on 21.07.2016.
 */
public class NetworkMessage implements Serializable{

    public static final String HANDSHAKE = "handshake";
    public static final String SEND_CHAT_MESSAGE = "sendChatMessage";
    public static final String NEW_PLAYER = "newPlayer";
    public static final String START_GAME = "startGame";
    public static final String PLAYER_QUIT = "playerQuit";
    public static final String GET_CARD_FROM_DECK = "getCardFromDeck";


    private String type;
    private String message;
    private Player player;
    private String nowMovingPlayerName;
    private List<Player> players;
    private List<Player> alivePlayers;
    private Card card;


    //GETTERS AND SETTERS


    public String getNowMovingPlayerName() {
        return nowMovingPlayerName;
    }

    public void setNowMovingPlayerName(String nowMovingPlayerName) {
        this.nowMovingPlayerName = nowMovingPlayerName;
    }

    public List<Player> getAlivePlayers() {
        return alivePlayers;
    }

    public void setAlivePlayers(List<Player> alivePlayers) {
        this.alivePlayers = alivePlayers;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
