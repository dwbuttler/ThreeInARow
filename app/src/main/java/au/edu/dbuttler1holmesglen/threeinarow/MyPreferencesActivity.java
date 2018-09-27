package au.edu.dbuttler1holmesglen.threeinarow;

/**
 * Class that handles and facilitates SharedPreferences.
 *
 * @author David Buttler
 * @version 1.0
 * @since 2017-09-05
 */

import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class MyPreferencesActivity extends PreferenceActivity {

    /**
     * Method called on the creation of the activity, i.e. when the application is started by the user.
     * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        getListView().setBackgroundColor(Color.WHITE);
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        /**
         * Method called on the creation of the activity, i.e. when the application is started by the user.
         * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
         */
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
