package sample.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan.babich on 26.07.2016.
 */
public class Player {
    private String name;
    private boolean isGameOwner;
    private Point2D tabletopPosition;
    private List<Card> handCards = new ArrayList<Card>();

    public Player() {}

    public Player(String name) {
        this.name = name;
    }


    //GETTERS AND SETTERS


    public List<Card> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }

    public Point2D getTabletopPosition() {
        return tabletopPosition;
    }

    public void setTabletopPosition(Point2D tabletopPosition) {
        this.tabletopPosition = tabletopPosition;
    }

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
