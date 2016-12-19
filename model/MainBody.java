package edu.byu.cs.superasteroids.model;



/**
 * Created by Trent on 5/16/2016.
 */
public class MainBody {
    private ViewableObject viewableInfo;
    private Coordinate cannonAttach;
    private Coordinate engineAttach;
    private Coordinate extraAttach;

    public MainBody() {}

    public MainBody(Coordinate cannonAttach, Coordinate engineAttach, Coordinate extraAttach, ViewableObject viewableInfo) {
        this.cannonAttach = cannonAttach;
        this.engineAttach = engineAttach;
        this.extraAttach = extraAttach;
        this.viewableInfo = viewableInfo;
    }

    public ViewableObject getViewableInfo() {
        return viewableInfo;
    }

    public void setViewableInfo(ViewableObject viewableInfo) {
        this.viewableInfo = viewableInfo;
    }

    public Coordinate getCannonAttach() {
        return cannonAttach;
    }

    public void setCannonAttach(Coordinate cannonAttach) {
        this.cannonAttach = cannonAttach;
    }

    public Coordinate getEngineAttach() {
        return engineAttach;
    }

    public void setEngineAttach(Coordinate engineAttach) {
        this.engineAttach = engineAttach;
    }

    public Coordinate getExtraAttach() {
        return extraAttach;
    }

    public void setExtraAttach(Coordinate extraAttach) {
        this.extraAttach = extraAttach;
    }

    public void draw(){}

    public void update(){}
}
