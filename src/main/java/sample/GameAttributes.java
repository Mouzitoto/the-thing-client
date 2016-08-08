package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ruslan.babich on 26.07.2016.
 */
public class GameAttributes {
    private SimpleIntegerProperty playersCount = new SimpleIntegerProperty(this, "playersCount");
    private SimpleStringProperty playersNames = new SimpleStringProperty(this, "playersNames");
    private SimpleStringProperty lobbyChat = new SimpleStringProperty(this, "lobbyChat");

    public GameAttributes() {
        playersCount.set(0);
    }

    public int getPlayersCount() {
        return playersCount.get();
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount.set(playersCount);
    }

    public IntegerProperty playersCountProperty() {
        return playersCount;
    }


    public String getPlayersNames() {
        return playersNames.get();
    }

    public void setPlayersNames() {
        String allNames = "";
        for (Player player : Main.players) {
            allNames += player.getName() + "\n";
        }
        allNames.trim();

        this.playersNames.set(allNames);
    }

    public StringProperty playersNamesProperty() {
        return playersNames;
    }

    public String getLobbyChat() {
        return lobbyChat.get();
    }

    public void setLobbyChat(String chatMessage) {
        if (lobbyChat.get() != null)
            lobbyChat.set(lobbyChat.get() + "\n" + chatMessage);
        else
            lobbyChat.set(chatMessage);
    }

    public StringProperty lobbyChatProperty() {
        return lobbyChat;
    }
}

