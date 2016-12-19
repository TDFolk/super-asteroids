package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Trent on 5/16/2016.
 */
public class Level {
    private int number;
    private int width;
    private int height;
    private String title;
    private String hint;
    private String music;
    private List<BackgroundImage> backgroundImages;
    private Map<AsteroidType, Integer> levelAsteroids;

    public Level() {
        backgroundImages = new ArrayList<>();
        levelAsteroids = new HashMap<>();
    }

    public Level(int number, int width, int height,
                 String title, String hint, String music) {
        this.number = number;
        this. width = width;
        this.height = height;
        this. title = title;
        this. hint = hint;
        this.music = music;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public List<BackgroundImage> getBackgroundImages() {
        return backgroundImages;
    }

    public void setBackgroundImages(List<BackgroundImage> backgroundImages) {
        this.backgroundImages = backgroundImages;
    }

    public Map<AsteroidType, Integer> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelAsteroids(Map<AsteroidType, Integer> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }

    public void draw(){}

    public void update(){}

}
