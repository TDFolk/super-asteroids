package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.BackgroundImage;

/**
 * Created by Trent on 5/16/2016.
 */
public class BackgroundImageDAO {
    private SQLiteDatabase db;
    private static BackgroundImageDAO instance = null;
    private BackgroundImageDAO() {}

    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public ArrayList<BackgroundImage> getAllImages(){
        final String SQLGet = "SELECT * FROM objects";
        ArrayList<BackgroundImage> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BackgroundImage backgroundImage = new BackgroundImage();

                backgroundImage.setObjectID(cursor.getInt(0));
                String imagePath = cursor.getString(1);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    throw new Exception();
                }

                backgroundImage.setImagePath(imagePath);
                backgroundImage.setImageID(imageID);
                result.add(backgroundImage);

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }

        return result;
    }

    public void addBackgroundImage(String backgroundImagePath){
        ContentValues values = new ContentValues();
        values.put("filePath", backgroundImagePath);
        db.insert("objects", null, values);
    }

    public static BackgroundImageDAO getInstance() {
        if(instance == null) {
            instance = new BackgroundImageDAO();
        }
        return instance;
    }
}
