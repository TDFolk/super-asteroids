package edu.byu.cs.superasteroids.model;

/**
 * Created by Trent on 5/16/2016.
 */
public class Engine {
    private Coordinate attachPoint;
    private ViewableObject viewableInfo;
    private int baseSpeed;
    private int baseTurnRate;

    public Engine() {}

    public Engine(int baseSpeed, int baseTurnRate, Coordinate attachPoint, ViewableObject viewableInfo) {
        this.attachPoint = attachPoint;
        this.viewableInfo = viewableInfo;
        this. baseSpeed = baseSpeed;
        this. baseTurnRate = baseTurnRate;
    }

    public Coordinate getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(Coordinate attachPoint) {
        this.attachPoint = attachPoint;
    }

    public ViewableObject getViewableInfo() {
        return viewableInfo;
    }

    public void setViewableInfo(ViewableObject viewableInfo) {
        this.viewableInfo = viewableInfo;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }
}
