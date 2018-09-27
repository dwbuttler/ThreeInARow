package au.edu.dbuttler1holmesglen.threeinarow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This class was made to display user documentation about the game settings.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-09-05
 */

public class HelpSettingsActivity extends AppCompatActivity {

    /**
     * Method called on the creation of the activity, i.e. when the application is started by the user.
     * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_settings);
    }
}
