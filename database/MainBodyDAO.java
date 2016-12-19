package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Trent on 5/16/2016.
 */
public class MainBodyDAO {
    private SQLiteDatabase db;
    private static MainBodyDAO instance = null;
    public MainBodyDAO() {}

    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public ArrayList<MainBody> getAll(){
        final String SQLGet = "SELECT * FROM mainBodies";

        ArrayList<MainBody> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MainBody mainBody = new MainBody();

                mainBody.setCannonAttach(new Coordinate(cursor.getString(1)));
                mainBody.setEngineAttach(new Coordinate(cursor.getString(2)));
                mainBody.setExtraAttach(new Coordinate(cursor.getString(3)));

                String imagePath = cursor.getString(4);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    throw new Exception();
                }

                mainBody.setViewableInfo(
                        new ViewableObject(
                                imagePath,
                                cursor.getInt(5),
                                cursor.getInt(6),
                                imageID
                        ));

                result.add(mainBody);

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }
        return result;
    }

    public void addMainBody(MainBody mainBody){
        ContentValues values = new ContentValues();
        values.put("cannonAttach", mainBody.getCannonAttach().toString());
        values.put("engineAttach", mainBody.getEngineAttach().toString());
        values.put("extraAttach", mainBody.getExtraAttach().toString());
        values.put("image", mainBody.getViewableInfo().getImage());
        values.put("imageHeight", mainBody.getViewableInfo().getImageHeight());
        values.put("imageWidth", mainBody.getViewableInfo().getImageWidth());
        db.insert("mainBodies", null, values);
    }

    public static MainBodyDAO getInstance(){
        if(instance == null) instance = new MainBodyDAO();
        return instance;
    }
}
