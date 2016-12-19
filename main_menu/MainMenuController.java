package edu.byu.cs.superasteroids.main_menu;

import android.util.Log;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.model.AsteroidsGameModel;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.SpaceShip;

/**
 * Created by Trent on 5/25/2016.
 */
public class MainMenuController implements  IMainMenuController {
    private MainActivity mMainMenuActivity;

    public MainMenuController(MainActivity mainActivity){
        mMainMenuActivity = mainActivity;
    }

    @Override
    public void onQuickPlayPressed() {
        try {
            if (DbOpenHelper.getInstance(null).dbIsEmpty()) {
                throw new Exception();
            }

            AsteroidsGameModel.resetGame();

            AsteroidsGameModel.getInstance().populate();

            AsteroidsGameModel.getInstance().assemblePresetShip();

            mMainMenuActivity.startGame();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(IView view) {

    }
}
