package edu.byu.cs.superasteroids.model;

/**
 * Created by Trent on 5/16/2016.
 */
public class PowerCore {
    private String image;
    private int imageID;
    private int cannonBoost;
    private int engineBoost;

    public PowerCore() {}

    public PowerCore(String image, int engineBoost, int cannonBoost) {
        this.image = image;
        this. engineBoost = engineBoost;
        this.cannonBoost = cannonBoost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getCannonBoost() {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }

    public void draw(){}

    public void update(){}
}
