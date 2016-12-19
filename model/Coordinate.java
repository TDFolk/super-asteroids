package edu.byu.cs.superasteroids.model;

/**
 * Created by Trent on 5/23/2016.
 */
public class Coordinate {
    private int posX;
    private int posY;

    public Coordinate() {
    }

    public Coordinate(int XPos, int YPos) {
        posX = XPos;
        posY = YPos;
    }

    public Coordinate(float Xpos, float Ypos) {
        posX = Math.round(Xpos);
        posY = Math.round(Ypos);
    }

    public Coordinate(String coordinate_string) {
        String[] positions = coordinate_string.split(",");
        if(positions.length != 2) {

        }
        else{
            posX = Integer.parseInt(positions[0]);
            posY = Integer.parseInt(positions[1]);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(posX) + "," + Integer.toString(posY);
    }

    public int getXPos() {
        return posX;
    }

    public void setXPos(int XPos) {
        posX = XPos;
    }

    public int getYPos() {
        return posY;
    }

    public void setYPos(int YPos) {
        posY = YPos;
    }
}
