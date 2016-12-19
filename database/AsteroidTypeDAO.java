package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Trent on 5/16/2016.
 */
public class AsteroidTypeDAO {
    private SQLiteDatabase db;
    private static AsteroidTypeDAO instance = null;
    private AsteroidTypeDAO() {}

    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public ArrayList<AsteroidType> getAll(){
        final String SQLGet = "SELECT * FROM asteroidTypes";
        ArrayList<AsteroidType> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AsteroidType asteroidType = new AsteroidType();

                asteroidType.setID(cursor.getInt(0));
                asteroidType.setName(cursor.getString(1));
                String imagePath = cursor.getString(2);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    throw new Exception();
                }

                asteroidType.setViewableInfo(
                        new ViewableObject(
                                imagePath,
                                cursor.getInt(3),
                                cursor.getInt(4),
                                imageID
                        ));

                asteroidType.setType(cursor.getString(5));
                result.add(asteroidType);

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }

        return result;
    }

    public long addAsteroidType(AsteroidType asteroid){
        ContentValues values = new ContentValues();
        values.put("name", asteroid.getName());
        values.put("type", asteroid.getType());
        values.put("image", asteroid.getViewableInfo().getImage());
        values.put("imageHeight", asteroid.getViewableInfo().getImageHeight());
        values.put("imageWidth", asteroid.getViewableInfo().getImageWidth());
        long result = db.insert("asteroidTypes", null, values);
        return result;
    }

    public static AsteroidTypeDAO getInstance() {
        if(instance == null) {
            instance = new AsteroidTypeDAO();
        }
        return instance;
    }
}
