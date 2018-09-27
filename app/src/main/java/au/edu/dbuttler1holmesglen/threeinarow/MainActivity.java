package au.edu.dbuttler1holmesglen.threeinarow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * MainActivity.java is a class that represents the main menu screen that the user is first exposed
 * to when opening the application. It contains UI elements to access all the activities within the
 * game, including the game activity, help documentation, high scores and settings.
 *
 * @author David Buttler
 * @version 1.0
 * @since 2017-09-05
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Method called on the creation of the activity, i.e. when the application is started by the user.
     * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating and customizing the header toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.cube);

        // Registering pressable UI buttons
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnHelp = (Button) findViewById(R.id.btnHelp);

        // Registering OnClick() listeners for both buttons
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Fills the menu element in the game activity with the specified options from the menu_main.xml file.
     * @param menu          The xml file containing the options and behaviour for the menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * On press event method for the options menu within the game activity.
     * @param item          Item id of the current item being selected by the user
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;

        switch (item.getItemId()) {
            case R.id.mainHelp:
                i = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(i);
                return true;
            case R.id.mainHighScore:
                i = new Intent(MainActivity.this, HighScoreActivity.class);
                startActivity(i);
                return true;
            case R.id.mainSettings:
                i = new Intent(MainActivity.this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }
}
