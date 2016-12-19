package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

/**
 * Created by Trent on 5/16/2016.
 */
public class Cannon {
    private ViewableObject mainViewableInfo;
    private Laser laserShot;
    private PointF vPEmitPoint;
    private Coordinate attachPoint;
    private Coordinate emitPoint;

    public Cannon() {}

    public Cannon(Coordinate attachPoint, Coordinate emitPoint,
                  ViewableObject mainViewableInfo,
                  String attackSound, int damage, Laser laserShot) {
        this.attachPoint = attachPoint;
        this.emitPoint = emitPoint;
        this.mainViewableInfo = mainViewableInfo;
        this.laserShot = laserShot;
    }

    public void draw(float bodyXAttach, float bodyYAttach,
                     int shipDirection, float scale){
        float newBodyAttachX = (float)((attachPoint.getXPos()) *
                Math.cos((Math.PI/180) * shipDirection));
        float newBodyAttachY = (float)((attachPoint.getYPos()) *
                Math.sin((Math.PI/180) * shipDirection));

        AsteroidsGameModel.getInstance().drawShipPart(mainViewableInfo.getImageID(),
                bodyXAttach, bodyYAttach, mainViewableInfo.getImageWidth(),
                mainViewableInfo.getImageHeight(),
                new Coordinate(newBodyAttachX, newBodyAttachY),
                .15f, shipDirection);
    }

    public void update(){
    }

    public void setVPEmitPoint(float emitPointX, float emitPointY) {
        vPEmitPoint = new PointF(emitPointX, emitPointY);
    }

    public ViewableObject getMainViewableInfo() {
        return mainViewableInfo;
    }

    public void setMainViewableInfo(ViewableObject mainViewableInfo) {
        this.mainViewableInfo = mainViewableInfo;
    }

    public Laser getLaserShot() {
        return laserShot;
    }

    public void setLaserShot(Laser laserShot) {
        this.laserShot = laserShot;
    }

    public PointF getvPEmitPoint() {
        return vPEmitPoint;
    }

    public void setvPEmitPoint(PointF vPEmitPoint) {
        this.vPEmitPoint = vPEmitPoint;
    }

    public Coordinate getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(Coordinate attachPoint) {
        this.attachPoint = attachPoint;
    }

    public Coordinate getEmitPoint() {
        return emitPoint;
    }

    public void setEmitPoint(Coordinate emitPoint) {
        this.emitPoint = emitPoint;
    }
}
