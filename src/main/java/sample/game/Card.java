package sample.game;

import java.io.Serializable;

/**
 * Created by ruslan.babich on 08.08.2016.
 */
public class Card implements Serializable {
    public static final String CARD_TYPE_PANIC = "panic";
    public static final String CARD_TYPE_EVENT = "event";

    private int id;
    private String type;
    private String description;
    private CardActions action;
    private int playersCount;

    public Card() {}

    public Card (int id, String type, String description, CardActions action, int playersCount) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.action = action;
        this.playersCount = playersCount;
    }


    //GETTERS AND SETTERS


    public CardActions getAction() {
        return action;
    }

    public void setAction(CardActions action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }
}
