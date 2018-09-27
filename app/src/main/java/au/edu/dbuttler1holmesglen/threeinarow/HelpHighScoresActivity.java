package au.edu.dbuttler1holmesglen.threeinarow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * HelpHighScoresActivity.java was created with the intention of providing the user with documentation
 * about how to use and create high scores within the application.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-09-05
 */

public class HelpHighScoresActivity extends AppCompatActivity {

    /**
     * Method called on the creation of the activity, i.e. when the application is started by the user.
     * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_high_scores);
    }
}
