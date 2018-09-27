package au.edu.dbuttler1holmesglen.threeinarow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Contains navigational elements that enable the user to access the user documentation.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-09-15
 */

public class HelpActivity extends AppCompatActivity {

    /**
     * Method called on the creation of the activity, i.e. when the application is started by the user.
     * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Adding and customizing the header toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.cube);

        // Initializing GUI elements on the game activity
        Button btnSettings = (Button) findViewById(R.id.btnHelpSettings);
        btnSettings.setTextColor(Color.WHITE);
        Button btnHighScores = (Button) findViewById(R.id.btnHelpScores);
        btnHighScores.setTextColor(Color.WHITE);
        Button btnHowToPlay = (Button) findViewById(R.id.btnHelpPlay);
        btnHowToPlay.setTextColor(Color.WHITE);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpActivity.this, HelpSettingsActivity.class);
                startActivity(i);
            }
        });

        btnHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpActivity.this, HelpHighScoresActivity.class);
                startActivity(i);
            }
        });

        btnHowToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpActivity.this, HelpPlayActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_help, menu);
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
            case R.id.helpHighScore:
                i = new Intent(HelpActivity.this, HighScoreActivity.class);
                startActivity(i);
                return true;
            case R.id.helpSettings:
                i = new Intent(HelpActivity.this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }
}
