package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.BackgroundImage;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.Level;


/**
 * Created by Trent on 5/16/2016.
 */
public class LevelDAO {
    private SQLiteDatabase db;
    private static LevelDAO instance;
    public LevelDAO() {}

    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public ArrayList<Level> getAll(ArrayList<AsteroidType> asteroidTypes, ArrayList<BackgroundImage> backgroundImages){
        final String SQLGet = "SELECT * FROM levels";
        ArrayList<Level> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Level level = new Level();
                int levelNumber = cursor.getInt(0);
                level.setNumber(levelNumber);
                level.setTitle(cursor.getString(1));
                level.setHint(cursor.getString(2));
                level.setWidth(cursor.getInt(3));
                level.setHeight(cursor.getInt(4));
                String soundPath = cursor.getString(5);
                ContentManager.getInstance().loadSound(soundPath);
                result.add(level);

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }

        setLevelAsteroids(result, asteroidTypes);
        setLevelObjects(result, backgroundImages);

        return result;
    }

    private void setLevelAsteroids(ArrayList<Level> levels, ArrayList<AsteroidType> asteroidTypes){

        final String SQLGet = "SELECT * FROM levelAsteroids";

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int numberOfAsteroidOnLevel = cursor.getInt(0);
                int asteroidID = cursor.getInt(1);
                int levelNumber = cursor.getInt(2);

                for(Level l : levels) {
                    if(l.getNumber() == levelNumber) {
                        for (AsteroidType current : asteroidTypes) {
                            if (current.getID() == asteroidID) {
                                l.getLevelAsteroids().put(current, numberOfAsteroidOnLevel);
                                break;
                            }
                        }
                        break;
                    }
                }

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }
    }

    private void setLevelObjects(ArrayList<Level> levels, ArrayList<BackgroundImage> backgroundImages){

        final String SQLGet = "SELECT * FROM levelObjects";

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Coordinate position = new Coordinate(cursor.getString(0));
                int objectID = cursor.getInt(1);
                float scale = cursor.getFloat(2);
                int levelNumber = cursor.getInt(3);

                for(Level l : levels) {
                    if(l.getNumber() == levelNumber) {
                        for (BackgroundImage current : backgroundImages) {
                            if (current.getObjectID() == objectID) {
                                current.setPosition(position);
                                current.setScale(scale);
                                l.getBackgroundImages().add(current);
                                break;
                            }
                        }
                        break;
                    }
                }

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }
    }

    public void addLevel(Level level){
        ContentValues values = new ContentValues();
        values.put("number", level.getNumber());
        values.put("title", level.getTitle());
        values.put("hint", level.getHint());
        values.put("width", level.getWidth());
        values.put("height", level.getHeight());
        values.put("music", level.getMusic());
        db.insert("levels", null, values);
    }

    public void addLevelAsteroid(int number, int asteroidId, int levelNumber){
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("asteroidId", asteroidId);
        values.put("levelNumber", levelNumber);
        db.insert("levelAsteroids", null, values);
    }

    public void addLevelObject(Coordinate position, int objectId, float scale, int levelNumber){
        ContentValues values = new ContentValues();
        values.put("position", position.toString());
        values.put("objectId", Integer.toString(objectId));
        values.put("scale", Float.toString(scale));
        values.put("levelNumber", levelNumber);
        db.insert("levelObjects", null, values);
    }

    public static LevelDAO getInstance() {
        if(instance == null) {
            instance = new LevelDAO();
        }
        return instance;
    }
}
