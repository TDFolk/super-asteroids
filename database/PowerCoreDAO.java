package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.PowerCore;


/**
 * Created by Trent on 5/16/2016.
 */
public class PowerCoreDAO {
    private SQLiteDatabase db;
    private static PowerCoreDAO instance = null;
    public PowerCoreDAO() {}

    public void setDatabase(SQLiteDatabase database){
        db = database;
    }

    public ArrayList<PowerCore> getAll(){
        final String SQLGet = "SELECT * FROM powerCores";
        ArrayList<PowerCore> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQLGet, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PowerCore powerCore = new PowerCore();

                powerCore.setCannonBoost(cursor.getInt(1));
                powerCore.setEngineBoost(cursor.getInt(2));
                String imagePath = cursor.getString(3);
                int imageID = ContentManager.getInstance().loadImage(imagePath);
                if(imageID == -1) {
                    throw new Exception();
                }

                powerCore.setImage(imagePath);
                powerCore.setImageID(imageID);

                result.add(powerCore);

                cursor.moveToNext();
            }
        } catch(Exception e) {
        } finally {
            cursor.close();
        }
        return result;
    }

    public void addPowerCore (PowerCore powerCore){
        ContentValues values = new ContentValues();
        values.put("image", powerCore.getImage());
        values.put("cannonBoost", powerCore.getCannonBoost());
        values.put("engineBoost", powerCore.getEngineBoost());
        db.insert("powerCores", null, values);
    }

    public static PowerCoreDAO getInstance() {
        if (instance == null) {
            instance = new PowerCoreDAO();
        }
        return instance;
    }
}
