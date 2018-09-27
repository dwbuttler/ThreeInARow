package au.edu.dbuttler1holmesglen.threeinarow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * HighScoreActivity.java contains the necessary elements to implement a high score functionality to
 * the "Three In A Row" application.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-09-05
 */

public class HighScoreActivity extends AppCompatActivity {
    TableLayout highScores;
    TextView noScores;
    Spinner spinDifficulty;
    String difficulty;
    RadioGroup radGroup;
    List scores;
    DatabaseHelper dh;
    int gridSize;

    /**
     * Method called on the creation of the activity, i.e. when the application is started by the user.
     * @param savedInstanceState    A Bundle object containing the activity's last 'saved' state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.cube);

        highScores = (TableLayout) findViewById(R.id.tableLayout);
        noScores = (TextView) findViewById(R.id.lblNoScores);
        noScores.setVisibility(View.INVISIBLE);
        spinDifficulty = (Spinner) findViewById(R.id.spinDifficulty);
        radGroup = (RadioGroup) findViewById(R.id.radGroup);
        difficulty = "Easy";
        gridSize = 4;

        dh = new DatabaseHelper(HighScoreActivity.this);

        scores = dh.getScores(difficulty, gridSize);

        if(scores.size() == 0)
            noScores.setVisibility(View.VISIBLE);
        else
            formatTable(highScores, scores);

        // Refresh table with new filter on change of radio group value
        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId){
                //highScores.removeViews(1, highScores.getChildCount());

                Log.d("TAG", "Radio Button checked id: " + checkedId);

                if (checkedId == R.id.rad4x4) {
                    gridSize = 4;
                    scores = dh.getScores(difficulty, gridSize);

                    if(scores.size() == 0)
                        noScores.setVisibility(View.VISIBLE);
                    else
                        noScores.setVisibility(View.INVISIBLE);
                        formatTable(highScores, scores);
                } else if (checkedId == R.id.rad5x5) {
                    gridSize = 5;
                    scores = dh.getScores(difficulty, gridSize);

                    if(scores.size() == 0)
                        noScores.setVisibility(View.VISIBLE);
                    else
                        noScores.setVisibility(View.INVISIBLE);
                        formatTable(highScores, scores);
                } else {
                    gridSize = 6;
                    scores = dh.getScores(difficulty, gridSize);

                    if(scores.size() == 0)
                        noScores.setVisibility(View.VISIBLE);
                    else
                        noScores.setVisibility(View.INVISIBLE);
                        formatTable(highScores, scores);
                }
            }
        });

        // Refresh table with new filter on change of spinner value
        spinDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    difficulty = "Easy";
                    scores = dh.getScores(difficulty, gridSize);

                    if(scores.size() == 0)
                        noScores.setVisibility(View.VISIBLE);
                    else
                        noScores.setVisibility(View.INVISIBLE);
                    formatTable(highScores, scores);
                } else if (position == 1) {
                    difficulty = "Medium";
                    scores = dh.getScores(difficulty, gridSize);

                    if(scores.size() == 0)
                        noScores.setVisibility(View.VISIBLE);
                    else
                        noScores.setVisibility(View.INVISIBLE);
                    formatTable(highScores, scores);
                } else {
                    difficulty = "Hard";
                    scores = dh.getScores(difficulty, gridSize);

                    if(scores.size() == 0)
                        noScores.setVisibility(View.VISIBLE);
                    else
                        noScores.setVisibility(View.INVISIBLE);
                    formatTable(highScores, scores);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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
        getMenuInflater().inflate(R.menu.menu_high, menu);
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
            case R.id.highHelp:
                i = new Intent(HighScoreActivity.this, HelpActivity.class);
                startActivity(i);
                return true;
            case R.id.highSettings:
                i = new Intent(HighScoreActivity.this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }

    /**
     * Adapter used to convert a List object into a table of values.
     * @param table         TableLayout that the List is to be adapted for
     * @param scores        List object that will be adapted
     */
    public void formatTable(TableLayout table, List scores) {
        // Clear table
        table.removeAllViews();

        TableRow headerRow = new TableRow(this);
        TextView header1 = new TextView(this);
        header1.setText("Name");

        TextView header2 = new TextView(this);
        header2.setText("Time");

        headerRow.addView(header1);
        headerRow.addView(header2);
        table.addView(headerRow);

        // For every score record...
        for(int i = 0; i < scores.size(); i++) {
            TableRow row = new TableRow(this);

            TextView name = new TextView(this);
            TextView time = new TextView(this);

            name.setText(String.valueOf(((ArrayList) scores.get(i)).get(0)));
            time.setText(String.valueOf(((ArrayList) scores.get(i)).get(1)) + "s");
            row.addView(name);
            row.addView(time);

            table.addView(row);
        }
    }
}
