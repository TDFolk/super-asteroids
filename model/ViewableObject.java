package edu.byu.cs.superasteroids.model;

/**
 * Created by Trent on 5/23/2016.
 */
public class ViewableObject {
    private String image;
    private int imageWidth;
    private int imageHeight;
    private int imageID;

    public ViewableObject() {}

    public ViewableObject(String image, int imageWidth, int imageHeight, int imageID) {
        this.image = image;
        this.imageWidth = imageWidth;
        this. imageHeight = imageHeight;
        this.imageID = imageID;
    }

    public ViewableObject(ViewableObject viewableInfo) {
        imageID = viewableInfo.getImageID();
        image = viewableInfo.getImage();
        imageHeight = viewableInfo.getImageHeight();
        imageWidth = viewableInfo.getImageWidth();
    }

    public ViewableObject(String image, int imageWidth, int imageHeight) {
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
