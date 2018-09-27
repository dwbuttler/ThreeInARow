package au.edu.dbuttler1holmesglen.threeinarow;

import android.util.Log;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * GameLogic.java contains the necessary methods that govern the rules of Grid Game. These are primarily
 * called from the GameActivity.java class.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-08-15
 */

public class GameLogic {
    // Initializing the variable that will determine whether the player has won
    private static int turnCount = 0;

    private static final String TAG = "GameLogic";

    /**
     * This method creates the starting conditions for the grid game by randomly selecting 4 tiles
     * to colour based off information received from SharedPreferences.
     * @param gridArray     An array that contains anonymous Item objects
     * @param colour1       R.drawable memory address that contains the 1st colour for the game
     * @param colour2       R.drawable memory address that contains the 2nd colour for the game
     * @param gridSize      The size of the game grid, as specified from SharedPreferences
     * @return Item[]       The new 'gridArray' containing the 4 different coloured tiles to start
     *                      the game
     */
    public static Item[] startGame(Item[] gridArray, int colour1, int colour2, int gridSize) {
        // Generating 4 random numbers to place initial starting coloured blocks
        Random r = new Random();
        int num1, num2, num3, num4;

        // Finding the amount of squares/tiles in grid
        int range = gridSize * gridSize;

        // 1st number
        num1 = r.nextInt(range);

        // 2nd number
        do {
            num2 = r.nextInt(range);
        } while(num2 == num1);

        // 3rd number
        do {
            num3 = r.nextInt(range);
        } while(num3 == num1 || num3 == num2);

        // 4th number
        do {
            num4 = r.nextInt(range);
        } while(num4 == num1 || num4 == num2 || num4 == num3);

        Log.i(TAG, "Four random indexes selected: " + num1 + ", " + num2 + ", " + num3 + ", " + num4);

        // Resetting squares to grey colour
        for(int i = 0; i < range; i++)
            gridArray[i].setColor(R.drawable.grey);

        // Setting colours in game grid
        gridArray[num1].setColor(colour1);
        gridArray[num2].setColor(colour1);
        gridArray[num3].setColor(colour2);
        gridArray[num4].setColor(colour2);

        Log.i(TAG, "1st colour set for indexes: " + num1 + " and " + num2);
        Log.i(TAG, "2nd colour set for indexes: " + num3 + " and " + num4);

        turnCount = 0;

        return gridArray;
    }

    /**
     * Randomize colours in an array and then return them to GameActivity.java.
     * @return int[]        Array containing the colours used for the game grid
     */
    public static int[] randomizeColours() {
        int randColour[] = {R.drawable.red, R.drawable.blue, R.drawable.green, R.drawable.yellow};

        // Shuffling array of colours
        shuffleArray(randColour);

        return randColour;
    }

    /**
     * Taken from answer to Stack Overflow question - Fisher-Yates shuffle.
     * @see "https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array"
     * @param ar            Array that requires shuffling
     */
    public static void shuffleArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    /**
     * At the end of every player turn, this method will check to see if there is three of the same
     * colour in a row.
     * @param gridArray         The array that contains the coloured tiles
     * @param colour1           1st colour used in the game grid
     * @param colour2           2nd colour used in the game grid
     * @param gridSize          The number of rows/columns in the grid
     * @return int              Integer variable that determines the status of the game
     */
    public static int winnerCheck(Item[] gridArray, int colour1, int colour2, int gridSize) {
        // Dynamically checking the game grid depending on values extracted from SharedPreferences
        int totalGrid = gridSize * gridSize;

        // "0" meaning that the game is ongoing, "1" being the game is won and "2" that the game is lost
        int win = 0;

        // Flag for finding when all grey tiles have been successfully filled with colours
        boolean gameFinish = true;

        // Check horizontal
        for(int i = 0; i < gridSize; i++) {
            for(int j = gridSize * i; j < gridSize + (gridSize * i) - 2; j++) {
                if(gridArray[j].getColor() == colour1 && gridArray[j + 1].getColor() == colour1 &&
                        gridArray[j + 2].getColor() == colour1) {
                    win = 2;
                }
            }

            for(int j = gridSize * i; j < gridSize + (gridSize * i) - 2; j++) {
                if(gridArray[j].getColor() == colour2 && gridArray[j + 1].getColor() == colour2 &&
                        gridArray[j + 2].getColor() == colour2) {
                    win = 2;
                }
            }
        }

        Log.i(TAG, "Checked horizontal combinations. Result: " + win);

        // Check vertical
        for(int i = 0; i < gridSize; i++) {
            for(int j = i; j < totalGrid - (2 * gridSize); j += gridSize) {
                if(gridArray[j].getColor() == colour1 && gridArray[j + gridSize].getColor() == colour1 &&
                        gridArray[j + (gridSize * 2)].getColor() == colour1) {
                    win = 2;
                }
            }

            for(int j = i; j < totalGrid - (2 * gridSize); j += gridSize) {
                if(gridArray[j].getColor() == colour2 && gridArray[j + gridSize].getColor() == colour2 &&
                        gridArray[j + (gridSize * 2)].getColor() == colour2) {
                    win = 2;
                }
            }
        }

        Log.i(TAG, "Checked vertical combinations. Result: " + win);

        // Game counter variable
        turnCount++;
        Log.i(TAG, "Turncount: " + turnCount);

        // If game is ongoing (grey tiles remain), continue...
        if(win == 0) {
            // No grey tiles remain, then the player has won the game
            if(turnCount == totalGrid - 4) {
                win = 1;
            }
        }

        switch(win) {
            case 2:
                Log.i(TAG, "User has lost the game");
                break;
            case 1:
                Log.i(TAG, "User has won the game");
                break;
            default:
                Log.i(TAG, "Game continues");
                break;
        }
        
        return win;
    }
}
