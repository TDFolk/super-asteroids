package edu.byu.cs.superasteroids.model;


import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Trent on 5/23/2016.
 */
public class ViewPort {
    private AsteroidsGameModel game;
    private List<BackgroundImage> backgroundImages;
    private MiniMap miniMap;
    private int dimensionX;
    private int dimensionY;
    private float positionX = 0;
    private float positionY = 0;

    public ViewPort(int XDimension, int YDimension, List<BackgroundImage> backgroundImages,
                    AsteroidsGameModel game) {
        dimensionX = XDimension;
        dimensionY = YDimension;
        this.backgroundImages = backgroundImages;
        this.game = game;
        miniMap = new MiniMap();
        ContentManager.getInstance().loadImage("images/space.bmp");
    }

    public Coordinate toViewCoordinates(Coordinate coordinate){
        float xVPPos = coordinate.getXPos() - positionX;
        float yVPPos = coordinate.getYPos() - positionY;
        return new Coordinate(Math.round(xVPPos), Math.round(yVPPos));
    }

    public Coordinate toWorldCoordinates(Coordinate coordinate){
        float xWPos = coordinate.getXPos() + positionX;
        float yWPos = coordinate.getYPos() + positionY;
        return new Coordinate(Math.round(xWPos), Math.round(yWPos));
    }

    public void update(){
        float proposedX = game.getSpaceShip().getXPosition() - (dimensionX / 2);
        float proposedY = game.getSpaceShip().getYPosition() - (dimensionY / 2);
        if(proposedX > 0 && proposedX + dimensionX < game.getCurrentLevel().getWidth()){
            positionX = proposedX;
        }
        if(proposedY > 0 && proposedY + dimensionY < game.getCurrentLevel().getHeight()) {
            positionY = proposedY;
        }

        miniMap.update();
    }

    public void draw(){
        int newXPos = Math.round((positionX / game.getCurrentLevel().getWidth()) * 1300);
        int newYPos = Math.round((positionY / game.getCurrentLevel().getHeight()) * 1500);
        int newXDim = Math.round(newXPos + (dimensionX));
        int newYDim = Math.round(newYPos + (dimensionY));

        DrawingHelper.drawImage(ContentManager.getInstance().getImageId("images/space.bmp"),
                new Rect(newXPos, newYPos, newXDim, newYDim), null);

        miniMap.draw();
    }

    public class MiniMap{

        public MiniMap(){

        }

        public void draw(){
            DrawingHelper.drawFilledRectangle(
                    new Rect((int)(DrawingHelper.getGameViewWidth() * .8f) - 6, 0,
                            DrawingHelper.getGameViewWidth(), (int)(DrawingHelper.getGameViewHeight() * .2f) + 6),
                    Color.BLUE,
                    100);

            DrawingHelper.drawFilledRectangle(
                    new Rect((int) (DrawingHelper.getGameViewWidth() * .8f), 3,
                            DrawingHelper.getGameViewWidth() - 3, (int) (DrawingHelper.getGameViewHeight() * .2f)),
                    Color.BLACK,
                    100);

            ArrayList<AsteroidType> gameAsteroids =
                    AsteroidsGameModel.getInstance().getAsteroidTypes();
            SpaceShip ship = AsteroidsGameModel.getInstance().getSpaceShip();


            float xScaledAndShifted = (ship.getXPosition() * .2f) *
                    dimensionX / game.getCurrentLevel().getWidth() +
                    (DrawingHelper.getGameViewWidth() * .8f);
            float yScaledAndShifted = (ship.getYPosition() * .2f) *
                    dimensionY / game.getCurrentLevel().getHeight();
            DrawingHelper.drawPoint(
                    new PointF(xScaledAndShifted, yScaledAndShifted),
                    3, Color.GREEN, 255);



            for(AsteroidType a : gameAsteroids){
                float xScaleShift = (a.getPosition().x * .2f) *
                        dimensionX / game.getCurrentLevel().getWidth() +
                        (DrawingHelper.getGameViewWidth() * .8f);
                float yScaleShift = (a.getPosition().y * .2f) *
                        dimensionY / game.getCurrentLevel().getHeight();
                DrawingHelper.drawPoint(
                        new PointF(xScaleShift, yScaleShift), 4, Color.RED, 255);
            }
        }

        public void update(){
        }
    }

    public AsteroidsGameModel getGame() {
        return game;
    }

    public void setGame(AsteroidsGameModel game) {
        this.game = game;
    }

    public List<BackgroundImage> getBackgroundImages() {
        return backgroundImages;
    }

    public void setBackgroundImages(List<BackgroundImage> backgroundImages) {
        this.backgroundImages = backgroundImages;
    }

    public MiniMap getMiniMap() {
        return miniMap;
    }

    public void setMiniMap(MiniMap miniMap) {
        this.miniMap = miniMap;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public void setDimensionX(int dimensionX) {
        this.dimensionX = dimensionX;
    }

    public int getDimensionY() {
        return dimensionY;
    }

    public void setDimensionY(int dimensionY) {
        this.dimensionY = dimensionY;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}
