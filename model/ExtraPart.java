package edu.byu.cs.superasteroids.model;

/**
 * Created by Trent on 5/16/2016.
 */
public class ExtraPart {
    private ViewableObject viewableInfo;
    private Coordinate attachPoint;

    public ExtraPart() {
    }

    public ExtraPart(Coordinate attachPoint, ViewableObject viewableInfo) {
        this.viewableInfo = viewableInfo;
        this.attachPoint = attachPoint;
    }

    public ViewableObject getViewableInfo() {
        return viewableInfo;
    }

    public void setViewableInfo(ViewableObject viewableInfo) {
        this.viewableInfo = viewableInfo;
    }

    public Coordinate getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(Coordinate attachPoint) {
        this.attachPoint = attachPoint;
    }
}
