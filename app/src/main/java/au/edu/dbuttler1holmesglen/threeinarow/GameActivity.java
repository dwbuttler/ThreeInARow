package au.edu.dbuttler1holmesglen.threeinarow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static au.edu.dbuttler1holmesglen.threeinarow.GameLogic.startGame;

/**
 * GameActivity.java was created with the intention of being the facilitator for the Grid Game. The
 * purpose of this file is to lay the foundation for the operation of the game and to call functions
 * that handle the game logic and additional functionality such as saving high scores etc.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-08-15
 */

public class GameActivity extends AppCompatActivity {

    // Declaring and initializing necessary variables
    SharedPreferences sp;
    GridView gridview;
    Item[] gridArray;
    ImageAdapter iAdapter;
    int win = 0;
    int gridSize;
    boolean turnFlag = true;
    int colour1, colour2;
    String difficulty, difficultyString;
    TextView txtTimer, colourLabel;
    EditText txtNameHighScore;
    Button btnSubmitScore, newgame;
    DatabaseHelper dh;
    ImageView colourCurrent;

    private static final String TAG = "GameActivity";
    // Upon loading of the app...

    /**
     * Method called on the creation of the activity, i.e. when the application is started by the user.
     * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Adding and customizing the header toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.cube);

        // Initializing and changing the settings of GUI elements on the game activity
        gridview = (GridView) findViewById(R.id.game_grid);
        newgame = (Button) findViewById(R.id.new_game);
        colourCurrent = (ImageView) findViewById(R.id.colour);
        colourLabel = (TextView) findViewById(R.id.current_colour);
        btnSubmitScore = (Button) findViewById(R.id.btnSubmitScore);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtNameHighScore = (EditText) findViewById(R.id.txtNameHighScore);

        dh = new DatabaseHelper(getApplicationContext());

        // Create and populate the game grid with the default grey tiles
        setUpGrid(gridview);
        Log.i(TAG, "Game grid has been set up");

        // Setting the 'Next Colour' label and image to invisible until the user starts the game
        colourCurrent.setVisibility(View.INVISIBLE);
        colourLabel.setVisibility(View.INVISIBLE);
        btnSubmitScore.setVisibility(View.INVISIBLE);
        txtNameHighScore.setVisibility(View.INVISIBLE);

        // Registering a press listener to the "New Game" button
        newgame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "'New Game' button has been clicked");
                // If the user has finished the previous game and wants to reset the grid, this statement
                // handles that scenario
                setUpGrid(gridview);
                Log.i(TAG, "Game grid has been set up");

                // Enable the user to start playing the game
                gridview.setEnabled(true);

                // Load the two colours that the user has specified into two 'colour' variables
                setColours();

                // Establish a connection to the device's SharedPreferences so that data can be extracted
                sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                gridSize = 4;
                difficulty = "45";

                // If this isn't the first time using the app, then get user preferences...
                if(!sp.getString("gridSize", "").equals(""))
                    gridSize = Integer.parseInt(sp.getString("gridSize", ""));
                else
                    sp.getString("gridSize", "4 x 4");

                Log.d(TAG, "Grid Size is: " + gridSize);

                if(!sp.getString("difficulty", "").equals(""))
                    difficulty = sp.getString("difficulty", "");
                else
                    sp.getString("difficulty", "Easy");

                switch(difficulty) {
                    case "30":
                        difficultyString = "Hard";
                        break;
                    case "45":
                        difficultyString = "Medium";
                        break;
                    default:
                        difficultyString = "Easy";
                        break;
                }

                Log.d(TAG, "Difficulty selected is: " + difficultyString);

                // Randomly creating starting conditions based off some variables from SharedPreferences
                gridArray = startGame(gridArray, colour1, colour2, gridSize);

                iAdapter = new ImageAdapter(GameActivity.super.getApplicationContext(), gridArray);
                gridview.setAdapter(iAdapter);
                Log.i(TAG, "Creating starting conditions for game");

                // Make the 'next colour' elements appear
                colourCurrent.setVisibility(View.VISIBLE);
                colourLabel.setVisibility(View.VISIBLE);

                // Starting colour
                colourCurrent.setImageResource(colour1);

                turnFlag = true;

                // Initializing game timer
                Log.i(TAG, "Initializing game timer");
                GameTimer.initializeTimer(GameActivity.this, txtTimer, gridview, difficulty);
            }
        });

        // Registering press events for each item within gridview
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                btnSubmitScore.setVisibility(View.INVISIBLE);
                txtNameHighScore.setVisibility(View.INVISIBLE);
                Log.i(TAG, "Position " + position + " has been pressed");

                // Alternate between two colours
                if(turnFlag) {
                    gridArray[position].setColor(colour1);
                    turnFlag = false;
                    colourCurrent.setImageResource(colour2);
                } else {
                    gridArray[position].setColor(colour2);
                    turnFlag = true;
                    colourCurrent.setImageResource(colour1);
                }

                // Apply current colour to gridview
                ((ImageView) v).setImageResource(gridArray[position].getColor());

                sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                // Call to GameLogic.java that checks for 3-in-a-row
                win = GameLogic.winnerCheck(gridArray, colour1, colour2, gridSize);

                // If GameLogic.java assigns the "win" variable another integer, do something...
                switch(win) {
                    // User wins and a toast is displayed before the grid is disabled
                    case 1:
                        Toast.makeText(GameActivity.this, "You have won the game!", Toast.LENGTH_LONG).show();
                        gridview.setEnabled(false);
                        GameTimer.endTimer();

                        // Make submitting high score possible for the user
                        txtNameHighScore.setVisibility(View.VISIBLE);
                        btnSubmitScore.setVisibility(View.VISIBLE);
                        Toast.makeText(GameActivity.this, "You have achieved a high score!", Toast.LENGTH_SHORT).show();
                        break;
                    // User loses and a toast is displayed before the grid is disabled
                    case 2:
                        Toast.makeText(GameActivity.this, "You have lost the game!", Toast.LENGTH_LONG).show();
                        gridview.setEnabled(false);
                        GameTimer.endTimer();
                        break;
                }
            }
        });

        // Registering press event for the submit score button
        btnSubmitScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtNameHighScore.getText().toString();

                // Name field validation
                if(name.equals(""))
                    Toast.makeText(GameActivity.this, "You must enter a name to submit a high score", Toast.LENGTH_SHORT).show();
                else {
                    // Make UI elements invisible so that user can't submit score more than once
                    txtNameHighScore.setVisibility(View.INVISIBLE);
                    btnSubmitScore.setVisibility(View.INVISIBLE);

                    Log.i(TAG, "Adding score to the database");
                    dh.addScore(difficultyString, Integer.parseInt(txtTimer.getText().toString()), gridSize, name);
                }
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
                i = new Intent(GameActivity.this, HelpActivity.class);
                startActivity(i);
                return true;
            case R.id.mainHighScore:
                i = new Intent(GameActivity.this, HighScoreActivity.class);
                startActivity(i);
                return true;
            case R.id.mainSettings:
                i = new Intent(GameActivity.this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }

    /**
     * Configuration of the game grid whenever the user presses on the button "New Game".
     * @param gridview      GridView element that will facilitate the main layout for the game activity
     */
    public void setUpGrid(GridView gridview) {
        // Initializing a variable to the hold information extracted from SharedPreferences
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        gridSize = 4;

        // If this isn't the first time using the app, then get user preferences...
        if(!sp.getString("gridSize", "").equals(""))
            gridSize = Integer.parseInt(sp.getString("gridSize", ""));

        // Assigning a number of anonymous Item objects to an array to generate grid
        gridArray = new Item[gridSize * gridSize];

        // Constructing gridArray and anonymously assigning grey images to it
        for(int i = 0; i < gridSize * gridSize; i++) {
            gridArray[i] = new Item(R.drawable.grey, "grey");
        }

        // Generating the UI for the Grid Game and disabling it until "New Game" is pressed
        iAdapter = new ImageAdapter(this, gridArray);
        gridview.setAdapter(iAdapter);
        gridview.setNumColumns(gridSize);
        gridview.setEnabled(false);
    }

    /**
     * Method that extracts and processes information from SharedPreferences about the colours that
     * will be used for the game grid.
     */
    public void setColours() {
        // Allowing the facilitation of data from SharedPreferences
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        switch (sp.getString("colour1", "")) {
            case "1":
                colour1 = R.drawable.maroon;
                break;
            case "2":
                colour1 = R.drawable.yellow;
                break;
            case "3":
                colour1 = R.drawable.red;
                break;
            case "4":
                colour1 = R.drawable.orange;
                break;
            case "5":
                colour1 = R.drawable.pink;
                break;
            case "6":
                colour1 = R.drawable.purple;
                break;
            default:
                colour1 = R.drawable.maroon;
                break;
        }

        switch (sp.getString("colour2", "")) {
            case "1":
                colour2 = R.drawable.blue;
                break;
            case "2":
                colour2 = R.drawable.brown;
                break;
            case "3":
                colour2 = R.drawable.cyan;
                break;
            case "4":
                colour2 = R.drawable.deep_blue;
                break;
            case "5":
                colour2 = R.drawable.green;
                break;
            case "6":
                colour2 = R.drawable.sage;
                break;
            default:
                colour2 = R.drawable.blue;
                break;
        }
    }

    // Code to handle different game orientation, did not have enough time to implement

    /**
     * Method that saves the current state of the game before the onDestroy() method is called by Android
     * @param outState          Bundle object containing the current state of the game
     *//*
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        outState.putString("GameTimer", txtTimer.getText().toString());
        outState.putParcelableArray("GameGrid", gridArray);
        GameTimer.endTimer();

        super.onSaveInstanceState(outState);
    }

    *//**
     * Restore UI state from the savedInstanceState. This bundle has also been passed to onCreate.
     * @param savedInstanceState
     *//*
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        GameTimer.initializeTimer(GameActivity.this, txtTimer, gridview,
                savedInstanceState.get("GameTimer").toString());
        gridArray = (Item[]) savedInstanceState.getParcelableArray("GameGrid");
        iAdapter = new ImageAdapter(getBaseContext(), gridArray);
        gridview.setAdapter(iAdapter);
        gridview.setNumColumns(gridSize);

        super.onRestoreInstanceState(savedInstanceState);
    }*/
}
