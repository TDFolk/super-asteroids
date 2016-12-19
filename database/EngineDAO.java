package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ViewableObject;


/**
 * Created by Trent on 5/16/2016.
 */
public class EngineDAO {
    private SQLiteDatabase db;
    private static EngineDAO instance = null;
    public EngineDAO() {}

    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public ArrayList<Engine> getAll(){
        final String SQLGet = "SELECT * FROM engines";
        ArrayList<Engine> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Engine engine = new Engine();

                engine.setBaseSpeed(cursor.getInt(1));
                engine.setBaseTurnRate(cursor.getInt(2));
                engine.setAttachPoint(new Coordinate(cursor.getString(3)));
                String imagePath = cursor.getString(4);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    throw new Exception();
                }

                engine.setViewableInfo(
                        new ViewableObject(
                                imagePath,
                                cursor.getInt(5),
                                cursor.getInt(6),
                                imageID
                        ));

                result.add(engine);

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }

        return result;
    }

    public void addEngine (Engine engine){
        ContentValues values = new ContentValues();
        values.put("baseSpeed", engine.getBaseSpeed());
        values.put("baseTurnRate", engine.getBaseTurnRate());
        values.put("attachPoint", engine.getAttachPoint().toString());
        values.put("image", engine.getViewableInfo().getImage());
        values.put("imageWidth", engine.getViewableInfo().getImageWidth());
        values.put("imageHeight", engine.getViewableInfo().getImageHeight());
        db.insert("engines", null, values);
    }

    public static EngineDAO getInstance() {
        if(instance == null) {
            instance = new EngineDAO();
        }
        return instance;
    }
}
