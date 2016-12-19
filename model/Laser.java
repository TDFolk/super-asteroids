package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Trent on 5/23/2016.
 */
public class Laser {
    private ViewableObject attackViewableInfo;
    private int speed = 48;
    private float direction = -1;
    private String attackSound;
    private int damage = 3;
    private int attackSoundID;
    private Coordinate position;

    public Laser() {}

    public Laser(ViewableObject attackViewableInfo, String attackSound, int attackSoundID, int damage) {
        this.attackViewableInfo = new ViewableObject(attackViewableInfo);
        this.attackSound = attackSound;
        this.attackSoundID = attackSoundID;
        this.damage = damage;
    }

    public Laser(Laser laserIn){
        attackViewableInfo = laserIn.getAttackViewableInfo();
        attackSound = laserIn.getAttackSound();
        attackSoundID = laserIn.getAttackSoundID();
        position = new Coordinate(0, 0);
    }

    public Laser(ViewableObject attackViewableInfo, String attackSound, int damage) {
        this.attackViewableInfo = attackViewableInfo;
        this.attackSound = attackSound;
        this.damage = damage;
    }

    public void draw(float scale){
        ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
        Coordinate viewPos = vp.toViewCoordinates(position);
        DrawingHelper.drawImage(attackViewableInfo.getImageID(),
                viewPos.getXPos(),
                viewPos.getYPos(),
                direction, scale/3, scale/3, 255);
    }

    public void update(){
        int topLeftX = position.getXPos() - attackViewableInfo.getImageWidth()/2;
        int topLeftY = position.getYPos() - attackViewableInfo.getImageHeight()/2;
        int bottomRightX = position.getXPos() + attackViewableInfo.getImageWidth()/2;
        int bottomRightY = position.getYPos() + attackViewableInfo.getImageHeight()/2;

        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(
                new PointF(position.getXPos(), position.getYPos()),
                new RectF(topLeftX, topLeftY, bottomRightX, bottomRightY),
                speed, Math.toRadians(direction - 90), 1);
        position.setXPos(Math.round(result.getNewObjPosition().x));
        position.setYPos(Math.round(result.getNewObjPosition().y));
    }

    public boolean touch(Object o) {
        if(o.getClass() == AsteroidType.class){
            return true;
        }
        else if(o.getClass() == Laser.class){
        }
        return false;
    }

    public ViewableObject getAttackViewableInfo() {
        return attackViewableInfo;
    }

    public void setAttackViewableInfo(ViewableObject attackViewableInfo) {
        this.attackViewableInfo = attackViewableInfo;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public String getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAttackSoundID() {
        return attackSoundID;
    }

    public void setAttackSoundID(int attackSoundID) {
        this.attackSoundID = attackSoundID;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
