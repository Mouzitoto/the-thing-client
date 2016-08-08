package sample;

/**
 * Created by ruslan.babich on 26.07.2016.
 */
public class Player {
    private String name;
    private boolean isGameOwner;

    public Player() {}

    public Player(String name) {
        this.name = name;
    }


    //GETTERS AND SETTERS

    public boolean isGameOwner() {
        return isGameOwner;
    }

    public void setGameOwner(boolean gameOwner) {
        isGameOwner = gameOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
