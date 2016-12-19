package edu.byu.cs.superasteroids.importer;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.byu.cs.superasteroids.database.AsteroidTypeDAO;
import edu.byu.cs.superasteroids.database.BackgroundImageDAO;
import edu.byu.cs.superasteroids.database.CannonDAO;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.database.EngineDAO;
import edu.byu.cs.superasteroids.database.ExtraPartDAO;
import edu.byu.cs.superasteroids.database.LevelDAO;
import edu.byu.cs.superasteroids.database.MainBodyDAO;
import edu.byu.cs.superasteroids.database.PowerCoreDAO;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Coordinate;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.Laser;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.ViewableObject;

/**
 * Created by Trent on 5/25/2016.
 */
public class DataImporter implements IGameDataImporter {
    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        //Reset the database and drop all tables
        DbOpenHelper.getInstance(null).onCreate(DbOpenHelper.getInstance(null).getWritableDatabase());
        try {
            BufferedReader parse = new BufferedReader(dataInputReader);
            StringBuilder json_string = new StringBuilder();
            String data;

            while ((data = parse.readLine()) != null) {
                json_string.append(data);
            }

            JSONObject file_data = new JSONObject(json_string.toString());

            JSONObject gameObject = file_data.getJSONObject("asteroidsGame");

            insertAsteroidTypes(gameObject);
            insertBackgroundImages(gameObject);
            insertCannons(gameObject);
            insertLevels(gameObject);
            insertMainBodies(gameObject);
            insertExtraParts(gameObject);
            insertEngines(gameObject);
            insertPowerCores(gameObject);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void insertAsteroidTypes(JSONObject gameObject) throws Exception {
        JSONArray asteroidTypeArray = gameObject.getJSONArray("asteroids");
        for(int i = 0; i < asteroidTypeArray.length(); i++){
            JSONObject currentAsteroid = asteroidTypeArray.getJSONObject(i);
            String name = currentAsteroid.getString("name");
            String imagePath = currentAsteroid.getString("image");
            int imageWidth = currentAsteroid.getInt("imageWidth");
            int imageHeight = currentAsteroid.getInt("imageHeight");
            String type = currentAsteroid.getString("type");

            ViewableObject asteroidViewable = new ViewableObject(imagePath, imageWidth,imageHeight);
            AsteroidType newAsteroid = new AsteroidType(name, type, asteroidViewable, i + 1);
            AsteroidTypeDAO.getInstance().addAsteroidType(newAsteroid);
        }
    }

    private void insertBackgroundImages(JSONObject gameObject) throws Exception {
        JSONArray backgroundObjectArray = gameObject.getJSONArray("objects");
        for (int i = 0; i < backgroundObjectArray.length(); i++) {
            String  backgroundImagePath = backgroundObjectArray.getString(i);
            BackgroundImageDAO.getInstance().addBackgroundImage(backgroundImagePath);
        }
    }

    private void insertCannons (JSONObject gameObject) throws Exception {
        JSONArray cannonArray = gameObject.getJSONArray("cannons");
        for(int i = 0; i < cannonArray.length(); i++) {
            JSONObject currentCannon = cannonArray.getJSONObject(i);
            Coordinate attachPoint = new Coordinate(currentCannon.getString("attachPoint"));
            Coordinate emitPoint = new Coordinate(currentCannon.getString("emitPoint"));
            String image = currentCannon.getString("image");
            int imageWidth = currentCannon.getInt("imageWidth");
            int imageHeight = currentCannon.getInt("imageHeight");
            String attackImage = currentCannon.getString("attackImage");
            int attackImageWidth = currentCannon.getInt("attackImageWidth");
            int attackImageHeight = currentCannon.getInt("attackImageHeight");
            String attackSound = currentCannon.getString("attackSound");
            int damage = currentCannon.getInt("damage");

            ViewableObject main = new ViewableObject(image, imageWidth, imageHeight);
            ViewableObject attack = new ViewableObject(attackImage, attackImageWidth, attackImageHeight);
            Laser laser = new Laser(attack, attackSound, damage);
            Cannon newCannon = new Cannon(attachPoint, emitPoint, main, attackSound, damage, laser);
            CannonDAO.getInstance().addCannon(newCannon);
        }
    }

    private void insertLevels (JSONObject gameObject) throws Exception {
        JSONArray levelArray = gameObject.getJSONArray("levels");
        for(int i = 0; i < levelArray.length(); i++){
            JSONObject currentLevel = levelArray.getJSONObject(i);
            int number = currentLevel.getInt("number");
            String title = currentLevel.getString("title");
            String hint = currentLevel.getString("hint");
            int width = currentLevel.getInt("width");
            int height = currentLevel.getInt("width");
            String music = currentLevel.getString("music");

            Level newLevel = new Level(number, width, height, title, hint, music);
            LevelDAO.getInstance().addLevel(newLevel);


            JSONArray levelAsteroidsArray = currentLevel.getJSONArray("levelAsteroids");
            for(int j = 0; j < levelAsteroidsArray.length(); j++){
                JSONObject levelAsteroid = levelAsteroidsArray.getJSONObject(j);
                int numberOfAsteroid = levelAsteroid.getInt("number");
                int asteroidId = levelAsteroid.getInt("asteroidId");
                LevelDAO.getInstance().addLevelAsteroid(numberOfAsteroid, asteroidId, number);
            }
            JSONArray levelObjectsArray = currentLevel.getJSONArray("levelObjects");
            for(int k = 0; k < levelObjectsArray.length(); k++){
                JSONObject levelObject = levelObjectsArray.getJSONObject(k);
                Coordinate position = new Coordinate(levelObject.getString("position"));
                int objectId = levelObject.getInt("objectId");
                float scale = Float.parseFloat(levelObject.getString("scale"));

                LevelDAO.getInstance().addLevelObject(position, objectId, scale, number);
            }
        }
    }

    private void insertMainBodies (JSONObject gameObject) throws Exception {
        JSONArray mainBodiesArray = gameObject.getJSONArray("mainBodies");
        for(int i = 0; i < mainBodiesArray.length(); i++){
            JSONObject currentMainBody = mainBodiesArray.getJSONObject(i);
            Coordinate cannonAttach = new Coordinate(currentMainBody.getString("cannonAttach"));
            Coordinate engineAttach = new Coordinate(currentMainBody.getString("engineAttach"));
            Coordinate extraAttach = new Coordinate(currentMainBody.getString("extraAttach"));
            String mainBodyImage = currentMainBody.getString("image");
            int imageWidth = currentMainBody.getInt("imageWidth");
            int imageHeight = currentMainBody.getInt("imageHeight");

            ViewableObject mainBodyViewableInfo = new ViewableObject(mainBodyImage, imageWidth,
                    imageHeight);
            MainBody newMainBody = new MainBody(cannonAttach, engineAttach, extraAttach,
                    mainBodyViewableInfo);
            MainBodyDAO.getInstance().addMainBody(newMainBody);
        }
    }

    private void insertExtraParts (JSONObject gameObject) throws Exception {
        JSONArray extraPartsArray = gameObject.getJSONArray("extraParts");
        for(int i = 0; i < extraPartsArray.length(); i++){
            JSONObject currentExtraPart = extraPartsArray.getJSONObject(i);
            Coordinate attachPoint = new Coordinate(currentExtraPart.getString("attachPoint"));
            String image = currentExtraPart.getString("image");
            int imageWidth = currentExtraPart.getInt("imageWidth");
            int imageHeight = currentExtraPart.getInt("imageHeight");

            ViewableObject mainBodyViewableInfo = new ViewableObject(image, imageWidth,
                    imageHeight);
            ExtraPart newExtraPart = new ExtraPart(attachPoint, mainBodyViewableInfo);
            ExtraPartDAO.getInstance().addExtraPart(newExtraPart);
        }
    }

    private void insertEngines (JSONObject gameObject) throws Exception {
        JSONArray enginesArray = gameObject.getJSONArray("engines");
        for(int i = 0; i < enginesArray.length(); i++){
            JSONObject currentEngine = enginesArray.getJSONObject(i);
            int baseSpeed = currentEngine.getInt("baseSpeed");
            int baseTurnRate = currentEngine.getInt("baseTurnRate");
            Coordinate attachPoint = new Coordinate(currentEngine.getString("attachPoint"));
            String image = currentEngine.getString("image");
            int imageWidth = currentEngine.getInt("imageWidth");
            int imageHeight = currentEngine.getInt("imageHeight");

            ViewableObject mainBodyViewableInfo = new ViewableObject(image, imageWidth,
                    imageHeight);
            Engine newEngine = new Engine(baseSpeed, baseTurnRate, attachPoint, mainBodyViewableInfo);
            EngineDAO.getInstance().addEngine(newEngine);
        }
    }

    private void insertPowerCores (JSONObject gameObject) throws Exception {
        JSONArray powerCoresArray = gameObject.getJSONArray("powerCores");
        for(int i = 0; i < powerCoresArray.length(); i++){
            JSONObject currentPowerCore = powerCoresArray.getJSONObject(i);
            String image = currentPowerCore.getString("image");
            int cannonBoost = currentPowerCore.getInt("cannonBoost");
            int engineBoost = currentPowerCore.getInt("engineBoost");

            PowerCore newPowerCore = new PowerCore(image, engineBoost, cannonBoost);
            PowerCoreDAO.getInstance().addPowerCore(newPowerCore);
        }
    }
}
