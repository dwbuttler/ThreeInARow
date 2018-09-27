package au.edu.dbuttler1holmesglen.threeinarow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper.java was created with the intention of facilitating the transactions to and from
 * a sqlite database stored locally on the Android device.
 *
 * Created with the aid of the following blog post,
 * @see "http://www.blazin.in/2014/09/basics-of-sqlite-database-with-multiple.html"
 *
 * @author David Buttler
 * @version 1.0
 * @since 24/09/2017
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat constant search tag
    private static final String TAG = "Database Helper";

    // Database version
    private static final int DB_VERSION = 1;

    // Database name
    private static final String DB_NAME = "HighScores";

    // Table names
    private static final String tableHighScore = "HighScore";

    // High Score column names
    private static final String keyScoreID = "ScoreID";
    private static final String keyName = "Name";
    private static final String keyDifficulty = "Difficulty";
    private static final String keyTime = "Time";
    private static final String keyGridSize = "GridSize";

    // CREATE table Strings
    // High Score Table
    private static final String createTableHighScore = "CREATE TABLE " + tableHighScore + "("
            + keyScoreID + " INTEGER PRIMARY KEY AUTOINCREMENT," + keyDifficulty + " TEXT,"
            + keyTime + " INTEGER," + keyGridSize + " INTEGER," + keyName + " TEXT)";

    /**
     * Default constructor for a DatabaseHelper object.
     * @param c     Context of the application
     */
    public DatabaseHelper(Context c){
        super(c, DB_NAME, null, DB_VERSION);
    }

    /**
     * Method that creates tables in the given database with some associated data.
     * @param db    Database object that the tables are going to be created within
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + tableHighScore);
        db.execSQL(createTableHighScore);
    }

    /**
     * onUpgrade() was created with the intention of refreshing the database if an upgrade is required.
     * @param db                Database object that is being upgraded
     * @param oldVersion        Old version of database (e.g. 1.1)
     * @param newVersion        New version of database (e.g. 2)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableHighScore);

        onCreate(db);
    }

    /**
     * Method that adds high score to the database.
     * @param difficulty        Difficulty that the game was set at when the score was made
     * @param time              Remaining timer value
     * @param gridSize          Size of the grid
     * @param name              Name that the user wants associated with their score
     */
    public void addScore(String difficulty, int time, int gridSize, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + tableHighScore + "(Difficulty, Time, GridSize, Name) VALUES('" + difficulty + "', " + time + ", " + gridSize + ", '" + name + "')");
    }

    /**
     * Get all scores from the database and format them into a List object.
     * @param difficulty        Difficulty filter for the records returned
     * @param gridSize          Grid size filter for the records returned
     * @return List object that contains all the records returned for the sql query.
     */
    public List getScores(String difficulty, int gridSize) {
        SQLiteDatabase db = this.getReadableDatabase();

        List scores = new ArrayList();

        // Building SQL String for execution
        String sqlQuery = "SELECT * FROM " + tableHighScore + " WHERE " + keyDifficulty + " = '" + difficulty + "' AND " + keyGridSize + " = " + gridSize;

        // Using a Cursor object to assign returned results into a List object
        Cursor c = db.rawQuery(sqlQuery, null);

        if(c.moveToFirst()) {
            do {
                List row = new ArrayList<>();

                row.add(c.getString(c.getColumnIndex(keyName)));
                row.add(c.getInt(c.getColumnIndex(keyTime)));

                scores.add(row);
            } while(c.moveToNext());
        }

        return scores;
    }
}
