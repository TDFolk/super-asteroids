package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.ViewableObject;


/**
 * Created by Trent on 5/16/2016.
 */
public class ExtraPartDAO {
    private SQLiteDatabase db;
    private static ExtraPartDAO instance = null;
    public ExtraPartDAO() {}

    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public ArrayList<ExtraPart> getAll(){
        final String SQLGet = "SELECT * FROM extraParts";
        ArrayList<ExtraPart> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ExtraPart extraPart = new ExtraPart();

                extraPart.setAttachPoint(new Coordinate(cursor.getString(1)));

                String imagePath = cursor.getString(2);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    throw new Exception();
                }

                extraPart.setViewableInfo(
                        new ViewableObject(
                                imagePath,
                                cursor.getInt(3),
                                cursor.getInt(4),
                                imageID
                        ));

                result.add(extraPart);

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }

        return result;
    }

    public void addExtraPart(ExtraPart extraPart){
        ContentValues values = new ContentValues();
        values.put("attachPoint", extraPart.getAttachPoint().toString());
        values.put("image", extraPart.getViewableInfo().getImage());
        values.put("imageWidth", extraPart.getViewableInfo().getImageWidth());
        values.put("imageHeight", extraPart.getViewableInfo().getImageHeight());
        db.insert("extraParts", null, values);
    }

    public static ExtraPartDAO getInstance() {
        if(instance == null) {
            instance = new ExtraPartDAO();
        }
        return instance;
    }
}
