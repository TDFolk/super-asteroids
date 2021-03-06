package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Random;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Trent on 5/16/2016.
 */
public class AsteroidType {
    ViewableObject mViewableInfo;
    int mDirection;
    int mSpeed;
    int mHitPoints;
    int mID;
    String mName;
    String mType;
    int numberOfAsteroids;
    PointF mPosition;

    Random rand = new Random();
    private float mAsteroidSize = .5f;

    public AsteroidType(){
        this.mPosition = new PointF(0,0);
        this.mDirection = rand.nextInt(181);
        this.mSpeed = 5;
        this.mHitPoints = 5;
    }

    public AsteroidType(AsteroidType toCopy){
        this.mID = toCopy.getID();
        this.mViewableInfo = toCopy.getViewableInfo();
        this.mType = toCopy.getType();
        this.mHitPoints = toCopy.getHitPoints();
        this.mSpeed = toCopy.getSpeed();
        this.mName = toCopy.getName();
        this.mDirection = rand.nextInt(181);
        this.mPosition = new PointF(0,0);
    }

    public AsteroidType(String name, String type, ViewableObject viewableInfo, int id) {
        mName = name;
        mType = type;
        mViewableInfo = viewableInfo;
        mID = id;
    }


    public void takeHit(int damage){
        mHitPoints = mHitPoints - damage;
    }

    public void setPosition(Coordinate pos) {
        mPosition.x = pos.getXPos();
        mPosition.y = pos.getYPos();
    }

    public void setPosition(PointF pos) {
        mPosition.x = pos.x;
        mPosition.y = pos.y;
    }

    public void touch(Object o){
        if(o.getClass() == Laser.class){
            Laser l = (Laser) o;
            takeHit(l.getDamage());
        }
        else if(o.getClass() == SpaceShip.class){}
        else if(o.getClass() == AsteroidType.class){
            mDirection = -mDirection;
        }
    }

    public void draw(float scale){
        ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
        Coordinate c = new Coordinate(mPosition.x, mPosition.y);
        Coordinate vc = vp.toViewCoordinates(c);
        DrawingHelper.drawImage(mViewableInfo.getImageID(),
                vc.getXPos(),
                vc.getYPos(),
                mDirection, scale * mAsteroidSize, scale * mAsteroidSize, 255);
    }

    public ArrayList<AsteroidType> split(){
        ArrayList<AsteroidType> result = new ArrayList<>();
        if(mHitPoints <= 0){
            if(mAsteroidSize == .5f){
                AsteroidType add1 = new AsteroidType();
                AsteroidType add2 = new AsteroidType();

                ViewableObject v = new ViewableObject();
                v.setImageID(this.mViewableInfo.getImageID());
                v.setImage(this.mViewableInfo.getImage());
                v.setImageHeight((int)(this.mViewableInfo.getImageHeight() * .5f));
                v.setImageWidth((int)(this.mViewableInfo.getImageWidth() * .5f));

                add1.setViewableInfo(v);
                add2.setViewableInfo(v);

                add1.setType(this.mType);
                add2.setType(this.mType);

                add1.setName(this.mName);
                add2.setName(this.mName);

                add1.setDirection(this.mDirection + 90);
                add2.setDirection(this.mDirection - 90);

                add1.setHitPoints(1);
                add2.setHitPoints(1);

                add1.setID(this.mID);
                add2.setID(this.mID);

                add1.setPosition(this.mPosition);
                add2.setPosition(this.mPosition);

                add1.setSpeed(this.mSpeed);
                add2.setSpeed(this.mSpeed);

                add1.mAsteroidSize = this.mAsteroidSize * .5f;
                add2.mAsteroidSize = this.mAsteroidSize* .5f;

                result.add(add1);
                result.add(add2);
            }
        }
        return result;
    }

    public void update(){
        ViewPort vp = AsteroidsGameModel.getInstance().getViewPort();
        Level level = AsteroidsGameModel.getInstance().getCurrentLevel();
        Coordinate viewCoord = vp.toViewCoordinates(new Coordinate(mPosition.x, mPosition.y));

        int topLeftX = Math.round(mPosition.x - mViewableInfo.getImageWidth()/2);
        int topLeftY = Math.round(mPosition.y - mViewableInfo.getImageHeight()/2);
        int bottomRightX = Math.round(mPosition.x + mViewableInfo.getImageWidth()/2);
        int bottomRightY = Math.round(mPosition.y + mViewableInfo.getImageHeight() / 2);

        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(
                new PointF(mPosition.x, mPosition.y),
                new RectF(topLeftX, topLeftY, bottomRightX, bottomRightY),
                mSpeed, Math.toRadians(mDirection - 90), 1);

        GraphicsUtils.RicochetObjectResult ricResult = GraphicsUtils.ricochetObject(
                result.getNewObjPosition(), result.getNewObjBounds(),
                Math.toRadians(mDirection - 90), level.getWidth(), level.getHeight());

        float newX = ricResult.getNewObjPosition().x;
        float newY = ricResult.getNewObjPosition().y;
        Coordinate newVP = vp.toViewCoordinates(new Coordinate(newX, newY));

        mPosition.x = newX;
        mPosition.y = newY;

        if(newX != result.getNewObjPosition().x || newY != result.getNewObjPosition().y){
            mDirection = (int)Math.toDegrees(ricResult.getNewAngleRadians()) + 90;
        }
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public ViewableObject getViewableInfo() {
        return mViewableInfo;
    }

    public void setViewableInfo(ViewableObject viewableInfo) {
        mViewableInfo = viewableInfo;
    }

    public int getDirection() {
        return mDirection;
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public int getHitPoints() {
        return mHitPoints;
    }

    public void setHitPoints(int hitPoints) {
        mHitPoints = hitPoints;
    }

    public int getNumberOfAsteroids() {
        return numberOfAsteroids;
    }

    public void setNumberOfAsteroids(int numberOfAsteroids) {
        this.numberOfAsteroids = numberOfAsteroids;
    }

    public PointF getPosition() {
        return mPosition;
    }
}
