package au.edu.dbuttler1holmesglen.threeinarow;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author David Buttler
 * @version 1.0
 * @since 27/10/2017
 *
 * GameTimer.java is a class that controls and implements the game timer functionality for the Three
 * In A Row application.
 */

public class GameTimer {
    private static CountDownTimer timer;

    /**
     * Method that creates that starts the timer, has additional callback functions.
     * @param c                 Context for the Toast message to appear within
     * @param textTimer         TextView object that the timer will reside in
     * @param gridView          GridView object from the main game activity
     * @param difficulty        Difficulty that the game was set at by the user
     */
    public static void initializeTimer(Context c, TextView textTimer, GridView gridView, String difficulty) {
        final TextView txtTimer = textTimer;
        final Context context = c;
        final GridView gridview = gridView;

        // If the user restarts the game...
        if(timer != null)
            endTimer();

        // Initialize timer based off difficulty value given through parameter
        timer = new CountDownTimer(Integer.parseInt(difficulty + "000"), 1000) {

            /**
             * CountDownTimer callback method that executes after the timer 'ticks' one second.
             * @param millisUntilFinished       Time remaining in ms
             */
            public void onTick(long millisUntilFinished) {
                txtTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            /**
             * CountDownTimer callback method that executes after the timer reaches '0'.
             */
            public void onFinish() {
                txtTimer.setText("0");
                Toast.makeText(context, "You have lost the game!", Toast.LENGTH_LONG).show();
                gridview.setEnabled(false);
            }
        }.start();
    }

    /**
     * Method that cancels and stops the game timer.
     */
    public static void endTimer() {
        timer.cancel();
    }

    public static CountDownTimer resumeTimer() { CountDownTimer gameTimer = timer.start(); return gameTimer;}
}
