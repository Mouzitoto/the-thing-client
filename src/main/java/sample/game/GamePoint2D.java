package sample.game;

import javafx.geometry.Point2D;

import java.io.Serializable;

/**
 * Created by ruslan.babich on 020 20.09.2016.
 */
public class GamePoint2D implements Serializable {

    private double x;
    private double y;

    public GamePoint2D(Point2D point2D) {
        this.x = point2D.getX();
        this.y = point2D.getY();
    }

    public GamePoint2D() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
