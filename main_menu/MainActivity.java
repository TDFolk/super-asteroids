package edu.byu.cs.superasteroids.main_menu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import edu.byu.cs.superasteroids.R;
import edu.byu.cs.superasteroids.base.ActionBarActivityView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.game.GameActivity;
import edu.byu.cs.superasteroids.importer.ImportActivity;
import edu.byu.cs.superasteroids.ship_builder.ShipBuildingActivity;

import edu.byu.cs.superasteroids.R;
import edu.byu.cs.superasteroids.base.ActionBarActivityView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.AsteroidTypeDAO;
import edu.byu.cs.superasteroids.database.BackgroundImageDAO;
import edu.byu.cs.superasteroids.database.CannonDAO;
import edu.byu.cs.superasteroids.database.DbOpenHelper;
import edu.byu.cs.superasteroids.database.EngineDAO;
import edu.byu.cs.superasteroids.database.ExtraPartDAO;
import edu.byu.cs.superasteroids.database.LevelDAO;
import edu.byu.cs.superasteroids.database.MainBodyDAO;
import edu.byu.cs.superasteroids.database.PowerCoreDAO;
import edu.byu.cs.superasteroids.game.GameActivity;
import edu.byu.cs.superasteroids.importer.ImportActivity;
import edu.byu.cs.superasteroids.ship_builder.ShipBuildingActivity;

public class MainActivity extends ActionBarActivityView implements IMainMenuView {
    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //TODO: Set this activity's controller to an instance of your MainMenuController
        //TODO: Pass the MainMenuController's constructor a reference to its IMainMenuView (this)
        IMainMenuController controller = new MainMenuController(this);
        setController(controller);

        database = DbOpenHelper.getInstance(this).getWritableDatabase();
        AsteroidTypeDAO.getInstance().setDatabase(database);
        BackgroundImageDAO.getInstance().setDatabase(database);
        CannonDAO.getInstance().setDatabase(database);
        LevelDAO.getInstance().setDatabase(database);
        MainBodyDAO.getInstance().setDatabase(database);
        ExtraPartDAO.getInstance().setDatabase(database);
        EngineDAO.getInstance().setDatabase(database);
        PowerCoreDAO.getInstance().setDatabase(database);
 

        ContentManager.getInstance().setResources(getResources());

        ContentManager.getInstance().setAssets(getAssets());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void startGame(View v) {
        Intent intent = new Intent(this, ShipBuildingActivity.class);
        startActivity(intent);
    }

    public void quickPlay(View v) {
        if (getController() != null) {
            ((IMainMenuController) getController()).onQuickPlayPressed();
        }
    }

    public void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
    }

    public void importData(View v) {
        Intent intent = new Intent(this, ImportActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
