package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.*;

/**
 * Created by Trent on 5/23/2016.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "asteroids.sqlite";
    private static final int DB_VERSION = 5;
    private static DbOpenHelper instance;

    private DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DbOpenHelper getInstance(Context context){
        if(instance == null){
            instance =  new DbOpenHelper(context);
        }
        return instance;
    }

    public void onCreate(SQLiteDatabase db) {


        final String DROP_OBJECTS =
                "DROP TABLE IF EXISTS" + " objects;";

        final String DROP_ASTEROID_TYPES =
                "DROP TABLE IF EXISTS" + " asteroidTypes;";

        final String DROP_LEVELS =
                "DROP TABLE IF EXISTS" + " levels;";

        final String DROPE_LEVEL_ASTEROIDS =
                "DROP TABLE IF EXISTS" + " levelAsteroids;";

        final String DROP__LEVEL_OBJECTS =
                "DROP TABLE IF EXISTS" + " levelObjects;";

        final String DROP_MAIN_BODIES =
                "DROP TABLE IF EXISTS" + " mainBodies;";

        final String DROP_CANNONS =
                "DROP TABLE IF EXISTS" + " cannons;";

        final String DROP_EXTRA_PARTS =
                "DROP TABLE IF EXISTS" + " extraParts;";

        final String DROP_ENGINES =
                "DROP TABLE IF EXISTS" + " engines;";

        final String DROP_POWER_CORES =
                "DROP TABLE IF EXISTS" + " powerCores;";


        db.execSQL(DROP_OBJECTS);
        db.execSQL(DROP_ASTEROID_TYPES);
        db.execSQL(DROP_LEVELS);
        db.execSQL(DROPE_LEVEL_ASTEROIDS);
        db.execSQL(DROP__LEVEL_OBJECTS);
        db.execSQL(DROP_MAIN_BODIES);
        db.execSQL(DROP_CANNONS);
        db.execSQL(DROP_EXTRA_PARTS);
        db.execSQL(DROP_ENGINES);
        db.execSQL(DROP_POWER_CORES);

        final String CREATE_OBJECTS =
                "CREATE TABLE objects(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "filepath TEXT NOT NULL" +
                        ")";

        final String CREATE_ASTEROID_TYPES =
                "CREATE TABLE asteroidTypes ( " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "image TEXT NOT NULL," +
                        "imageWidth TEXT NOT NULL," +
                        "imageHeight TEXT NOT NULL," +
                        "type TEXT NOT NULL" +
                        ")";

        final String CREATE_LEVELS =
                "CREATE TABLE levels(" +
                        "number INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "title TEXT NOT NULL," +
                        "hint TEXT NOT NULL," +
                        "width INTEGER NOT NULL," +
                        "height INTEGER NOT NULL," +
                        "music TEXT NOT NULL" +
                        ")";

        final String CREATE_LEVEL_ASTEROIDS =
                "CREATE TABLE levelAsteroids(" +
                        "number INTEGER NOT NULL," +
                        "asteroidId INTEGER NOT NULL," +
                        "levelNumber INTEGER NOT NULL" +
                        ")";

        final String CREATE_LEVEL_OBJECTS =
                "CREATE TABLE levelObjects(" +
                        "position TEXT NOT NULL," +
                        "objectId INTEGER NOT NULL," +
                        "scale TEXT NOT NULL," +
                        "levelNumber INTEGER NOT NULL" +
                        ")";

        final String CREATE_MAIN_BODIES =
                "CREATE TABLE mainBodies(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cannonAttach TEXT NOT NULL," +
                        "engineAttach TEXT NOT NULL," +
                        "extraAttach TEXT NOT NULL," +
                        "image TEXT NOT NULL," +
                        "imageWidth INTEGER NOT NULL," +
                        "imageHeight TEXT NOT NULL" +
                        ")";

        final String CREATE_CANNONS =
                "CREATE TABLE cannons(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "attachPoint TEXT NOT NULL," +
                        "emitPoint TEXT NOT NULL," +
                        "attackImage TEXT NOT NULL," +
                        "attackImageHeight INTEGER NOT NULL," +
                        "attackImageWidth INTEGER NOT NULL," +
                        "attackSound TEXT NOT NULL," +
                        "damage INTEGER NOT NULL," +
                        "image TEXT NOT NULL," +
                        "imageWidth INTEGER NOT NULL," +
                        "imageHeight INTEGER NOT NULL" +
                        ")";

        final String CREATE_EXTRA_PARTS =
                "CREATE TABLE extraParts(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "attachPoint TEXT NOT NULL," +
                        "image TEXT NOT NULL," +
                        "imageWidth INTEGER NOT NULL," +
                        "imageHeight INTEGER NOT NULL" +
                        ")";

        final String CREATE_ENGINES =
                "CREATE TABLE engines(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "baseSpeed INTEGER NOT NULL," +
                        "baseTurnRate INTEGER NOT NULL," +
                        "attachPoint TEXT NOT NULL," +
                        "image TEXT NOT NULL," +
                        "imageWidth INTEGER NOT NULL," +
                        "imageHeight INTEGER NOT NULL" +
                        ")";

        final String CREATE_POWER_CORES =
                "CREATE TABLE powerCores(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cannonBoost INTEGER NOT NULL," +
                        "engineBoost INTEGER NOT NULL," +
                        "image TEXT NOT NULL" +
                        ")";

        db.execSQL(CREATE_OBJECTS);
        db.execSQL(CREATE_ASTEROID_TYPES);
        db.execSQL(CREATE_LEVELS);
        db.execSQL(CREATE_LEVEL_ASTEROIDS);
        db.execSQL(CREATE_LEVEL_OBJECTS);
        db.execSQL(CREATE_MAIN_BODIES);
        db.execSQL(CREATE_CANNONS);
        db.execSQL(CREATE_EXTRA_PARTS);
        db.execSQL(CREATE_ENGINES);
        db.execSQL(CREATE_POWER_CORES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    public boolean dbIsEmpty(){
        if(AsteroidTypeDAO.getInstance().getAll().isEmpty() && BackgroundImageDAO.getInstance().getAllImages().isEmpty()){
            return true;
        };
        return false;
    }

}
