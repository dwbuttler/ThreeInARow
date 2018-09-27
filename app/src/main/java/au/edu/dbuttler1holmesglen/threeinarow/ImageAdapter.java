package au.edu.dbuttler1holmesglen.threeinarow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * This file was made with the purpose of creating the UI interface for the Grid Game, specifically
 * the grid layout.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-08-15
 */

public class ImageAdapter extends BaseAdapter{
    // Declaring variables
    Item[] gridArray;
    private Context mContext;

    /**
     * Constructor
     * @param c             Context to create the ImageAdapter within
     * @param gridArray     The array to populate the GridView with
     */
    public ImageAdapter(Context c, Item[] gridArray) {
        mContext = c;
        this.gridArray = gridArray;
    }

    /**
     * Method that determines the state of each individual element in the game grid.
     * @param position      The position in the game grid
     * @return boolean      Result of the execution of the method; true if enabled, false if disabled
     */
    @Override
    public boolean isEnabled(int position) {
        if(gridArray[position].getColor() == R.drawable.grey)
            return true;
        else
            return false;
    }

    /**
     * Returns the number of individual elements in the game grid.
     * @return int          Number of elements
     */
    public int getCount() {
        return gridArray.length;
    }

    /**
     * Get the Object of a specified index within the grid.
     * @param position      Position in the array for the element in the grid
     * @return Object       Object that is contained within the position parameter provided
     */
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the memory address of the image located within an element in the game grid.
     * @param position      Position in the array where the image reference is held within
     * @return long         Memory address of the specified image
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Create a new ImageView for each item referenced by the Adapter.
     * @param position      Get current position within the game grid
     * @param convertView   The view that you want the element in the specified position to assume
     * @param parent        ViewGroup of the parent element
     * @return View         The View that the position is going to contain
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // If it's not recycled, initialize some attributes
        if (convertView == null) {
            imageView = new ImageView(mContext);
            int width = ((GridView) parent).getColumnWidth();
            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        // Apply image resource to gridview
        imageView.setImageResource(gridArray[position].getColor());
        return imageView;
    }
}

