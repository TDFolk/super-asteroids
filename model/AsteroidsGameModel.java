package edu.byu.cs.superasteroids.model;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.byu.cs.superasteroids.database.AsteroidTypeDAO;
import edu.byu.cs.superasteroids.database.BackgroundImageDAO;
import edu.byu.cs.superasteroids.database.CannonDAO;
import edu.byu.cs.superasteroids.database.EngineDAO;
import edu.byu.cs.superasteroids.database.ExtraPartDAO;
import edu.byu.cs.superasteroids.database.LevelDAO;
import edu.byu.cs.superasteroids.database.MainBodyDAO;
import edu.byu.cs.superasteroids.database.PowerCoreDAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Trent on 5/25/2016.
 */

public class AsteroidsGameModel {
    ArrayList<AsteroidType> mAsteroidTypes;
    ArrayList<BackgroundImage> mBackgroundImages;
    ArrayList<Cannon> mCannons;
    ArrayList<Engine> mEngines;
    ArrayList<ExtraPart> mExtraParts;
    ArrayList<Level> mLevels;
    ArrayList<MainBody> mMainBodies;
    ArrayList<PowerCore> mPowerCores;

    private static final float ASTEROID_SCALE = 3f;
    private static Random rng = new Random();
    ViewPort mViewPort;
    SpaceShip mSpaceShip;
    Level mCurrentLevel;

    private static AsteroidsGameModel instance = null;

    private AsteroidsGameModel(){
        mCurrentLevel = new Level();
        mViewPort = new ViewPort(DrawingHelper.getGameViewWidth(),
                DrawingHelper.getGameViewHeight(), mCurrentLevel.getBackgroundImages(), this);
        mSpaceShip = new SpaceShip();
        mAsteroidTypes = new ArrayList<>();
    }

    public List<Integer> getMainBodyImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(MainBody mainBody : mMainBodies){
            result.add(mainBody.getViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getCannonImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(Cannon cannon : mCannons){
            result.add(cannon.getMainViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getEngineImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(Engine engine : mEngines){
            result.add(engine.getViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getExtraPartImageIDs(){
        List<Integer> result = new ArrayList<>();
        for(ExtraPart extraPart : mExtraParts){
            result.add(extraPart.getViewableInfo().getImageID());
        }
        return result;
    }

    public List<Integer>  getPowerCoreImageIDs() {
        List<Integer> result = new ArrayList<>();
        for (PowerCore powerCore : mPowerCores) {
            result.add(powerCore.getImageID());
        }
        return result;
    }

    public static AsteroidsGameModel getInstance() {
        if(instance == null) {
            instance = new AsteroidsGameModel();
        }
        return instance;
    }

    public void populate(){
        ArrayList<AsteroidType> mAsteroidTypePossibles;
        mAsteroidTypePossibles = AsteroidTypeDAO.getInstance().getAll();
        mBackgroundImages = BackgroundImageDAO.getInstance().getAllImages();
        mCannons = CannonDAO.getInstance().getAll();
        mEngines = EngineDAO.getInstance().getAll();
        mExtraParts = ExtraPartDAO.getInstance().getAll();
        mLevels = LevelDAO.getInstance().getAll(mAsteroidTypePossibles, mBackgroundImages);
        mMainBodies = MainBodyDAO.getInstance().getAll();
        mPowerCores = PowerCoreDAO.getInstance().getAll();
    }

    public void setCurrentLevel(Level currentLevel) {
        mCurrentLevel = currentLevel;
        mSpaceShip.setXPosition(mCurrentLevel.getWidth() / 2);
        mSpaceShip.setYPosition(mCurrentLevel.getHeight() / 2);
        Map<AsteroidType, Integer> levelAsteroids = mCurrentLevel.getLevelAsteroids();
        for(AsteroidType asteroid : levelAsteroids.keySet()){
            for(int i = 0; i < levelAsteroids.get(asteroid); i++){
                AsteroidType newA = new AsteroidType(asteroid);
                Coordinate randPos = new Coordinate(
                        rng.nextInt(mCurrentLevel.getWidth()),
                        rng.nextInt(mCurrentLevel.getHeight())
                );
                Coordinate worldPos = mViewPort.toWorldCoordinates(randPos);
                newA.setPosition(worldPos);
                mAsteroidTypes.add(newA);
            }
        }
    }

    public boolean shipIsComplete() {
        return mSpaceShip.shipIsComplete();
    }

    public void assemblePresetShip(){
        mSpaceShip.setPowerCore(AsteroidsGameModel.getInstance().getPowerCores().get(0));
        mSpaceShip.setMainBody(AsteroidsGameModel.getInstance().getMainBodies().get(0));
        mSpaceShip.setCannon(AsteroidsGameModel.getInstance().getCannons().get(0));
        mSpaceShip.setExtraPart(AsteroidsGameModel.getInstance().getExtraParts().get(0));
        mSpaceShip.setEngine(AsteroidsGameModel.getInstance().getEngines().get(0));
    }

    public void drawShipPart(int imageID, float bodyAttachX, float bodyAttachY, int partWidth,
                             int partHeight, Coordinate partAttachPoint,
                             float scale, int direction){

        float partAttachX = partAttachPoint.getXPos() * scale;
        float partAttachY = partAttachPoint.getYPos() * scale;
        float partOriginX = bodyAttachX - partAttachX;
        float partOriginY = bodyAttachY - partAttachY;

        float scaledPartWidth = partWidth * scale;
        float scaledPartHeight = partHeight * scale;
        float partCenterX = partOriginX + scaledPartWidth/2;
        float partCenterY = partOriginY + scaledPartHeight/2;

        DrawingHelper.drawImage(imageID, partCenterX, partCenterY, 0, scale, scale, 255);
    }


    public void update(){
        mViewPort.setDimensionX(DrawingHelper.getGameViewWidth());
        mViewPort.setDimensionY(DrawingHelper.getGameViewHeight());

        ArrayList<AsteroidType> asteroidsToRemove = new ArrayList<>();
        ArrayList<AsteroidType> asteroidsToAdd = new ArrayList<>();

        mSpaceShip.update();

        float shipTop = mSpaceShip.getYPosition() - mSpaceShip.getShipHeight()/2;
        float shipLeft = mSpaceShip.getXPosition() - mSpaceShip.getShipWidth()/2;
        float shipBottom = mSpaceShip.getYPosition() + mSpaceShip.getShipHeight()/2;
        float shipRight = mSpaceShip.getXPosition() + mSpaceShip.getShipWidth()/2;
        RectF shipBounds = new RectF(shipLeft, shipTop, shipRight, shipBottom);

        mViewPort.update();

        mViewPort.getMiniMap().update();

        for(AsteroidType asteroid : mAsteroidTypes){

            asteroid.update();

            float asteroidTop = asteroid.getPosition().y - asteroid.getViewableInfo().getImageHeight()/2;
            float asteroidLeft = asteroid.getPosition().x - asteroid.getViewableInfo().getImageWidth()/2;
            float asteroidBottom = asteroid.getPosition().y +
                    asteroid.getViewableInfo().getImageHeight()/2;
            float asteroidRight = asteroid.getPosition().x +
                    asteroid.getViewableInfo().getImageWidth()/2;

            RectF aBounds = new RectF(asteroidLeft, asteroidTop, asteroidRight, asteroidBottom);

            if(RectF.intersects(aBounds, shipBounds)){
                asteroid.touch(mSpaceShip);
                mSpaceShip.touch(asteroid);
            }

            ArrayList<AsteroidType> newAsteroids = asteroid.split();
            if(!newAsteroids.isEmpty()){
                for(AsteroidType a : newAsteroids){
                    asteroidsToAdd.add(a);
                }
                asteroidsToRemove.add(asteroid);
                continue;
            }

            if(asteroid.getHitPoints() <= 0){
                asteroidsToRemove.add(asteroid);
                continue;
            }

            Iterator<Laser> laserIterator = mSpaceShip.getLasers().iterator();
            while(laserIterator.hasNext()){
                Laser l = laserIterator.next();

                float lTop = l.getPosition().getYPos() -
                        l.getAttackViewableInfo().getImageHeight()/2;
                float lLeft = l.getPosition().getXPos() -
                        l.getAttackViewableInfo().getImageWidth()/2;
                float lBottom = l.getPosition().getYPos() +
                        l.getAttackViewableInfo().getImageHeight()/2;
                float lRight = l.getPosition().getXPos() +
                        l.getAttackViewableInfo().getImageWidth()/2;
                RectF lBounds = new RectF(lLeft, lTop, lRight, lBottom);
                if(RectF.intersects(aBounds, lBounds)){
                    asteroid.touch(l);
                    l.touch(asteroid);
                    laserIterator.remove();
                }
                else if(l.getPosition().getXPos() < 0 || l.getPosition().getXPos() > mCurrentLevel.getWidth() ||
                        l.getPosition().getYPos() < 0 || l.getPosition().getYPos() > mCurrentLevel.getHeight()){
                    laserIterator.remove();
                }
            }
        }

        mAsteroidTypes.removeAll(asteroidsToRemove);
        mAsteroidTypes.addAll(asteroidsToAdd);
    }

    public void draw(){
        mViewPort.draw();

        for(BackgroundImage backgroundImage : mCurrentLevel.getBackgroundImages()){
            backgroundImage.draw();
        }

        for(AsteroidType asteroid : mAsteroidTypes){
            asteroid.draw(ASTEROID_SCALE);
        }
        mSpaceShip.draw();
        mViewPort.getMiniMap().draw();
    }

    public static void resetGame() {
        instance = new AsteroidsGameModel();
    }


    public SpaceShip getSpaceShip() {
        return mSpaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        mSpaceShip = spaceShip;
    }

    public Level getCurrentLevel() {
        return mCurrentLevel;
    }

    public ArrayList<Cannon> getCannons() {
        return mCannons;
    }

    public void setCannons(ArrayList<Cannon> cannons) {
        mCannons = cannons;
    }

    public ArrayList<Engine> getEngines() {
        return mEngines;
    }

    public void setEngines(ArrayList<Engine> engines) {
        mEngines = engines;
    }

    public ArrayList<ExtraPart> getExtraParts() {
        return mExtraParts;
    }

    public void setExtraParts(ArrayList<ExtraPart> extraParts) {
        mExtraParts = extraParts;
    }

    public ArrayList<Level> getLevels() {
        return mLevels;
    }

    public void setLevels(ArrayList<Level> levels) {
        mLevels = levels;
    }

    public ArrayList<MainBody> getMainBodies() {
        return mMainBodies;
    }

    public void setMainBodies(ArrayList<MainBody> mainBodies) {
        mMainBodies = mainBodies;
    }

    public ArrayList<PowerCore> getPowerCores() {
        return mPowerCores;
    }

    public void setPowerCores(ArrayList<PowerCore> powerCores) {
        mPowerCores = powerCores;
    }

    public ViewPort getViewPort() {
        return mViewPort;
    }

    public void setViewPort(ViewPort viewPort) {
        mViewPort = viewPort;
    }

    public ArrayList<AsteroidType> getAsteroidTypes() {
        return mAsteroidTypes;
    }

    public void setAsteroidTypes(ArrayList<AsteroidType> asteroidTypes) {
        mAsteroidTypes = asteroidTypes;
    }

    public ArrayList<BackgroundImage> getBackgroundImages() {
        return mBackgroundImages;
    }

    public void setBackgroundImages(ArrayList<BackgroundImage> backgroundImages) {
        mBackgroundImages = backgroundImages;
    }
}
