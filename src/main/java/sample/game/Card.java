package sample.game;

/**
 * Created by ruslan.babich on 08.08.2016.
 */
public class Card {
    private int id;
    private CardTypes type;
    private String description;
    private CardActions action;


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

    public CardTypes getType() {
        return type;
    }

    public void setType(CardTypes type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
