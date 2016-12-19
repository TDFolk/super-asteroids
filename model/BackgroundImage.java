package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Trent on 5/16/2016.
 */
public class BackgroundImage {
    private String imagePath;
    private float scale;
    private int objectID;
    private int imageID;
    private Coordinate position;

    public BackgroundImage() {}

    public void draw(){
        if(imagePath != null){
            int imageID = ContentManager.getInstance().getImageId(imagePath);
            Coordinate positionInView = AsteroidsGameModel.getInstance()
                    .getViewPort().toViewCoordinates(position);
            DrawingHelper.drawImage(imageID, positionInView.getXPos(), positionInView.getYPos(),
                    0, scale, scale, 255);
        }
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
