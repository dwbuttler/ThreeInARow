package au.edu.dbuttler1holmesglen.threeinarow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Item.java was made to enable images to be used on a GridView layout in an android device. There are
 * crucial methods within this file that allow the Grid Game to function correctly, including changing
 * the colour of tiles that are pressed.
 *
 * @author     David Buttler
 * @version    1.0
 * @since      2017-08-15
 */

public class Item implements Parcelable {
    // Declaring variables
    private int mData;
    private int colorImg;
    int click = 0;

    /**
     * Returns an Item object with a supplied colour.
     * @param colImg        Colour of the Item Object you want to create
     * @param title         *Redundant - Not used*
     */
    public Item(int colImg, String title) {
        this.setColor(colImg);
    }

    public int describeContents() {
        return 0;
    }

    /** save object in parcel */
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /** recreate object from parcel */
    private Item(Parcel in) {
        mData = in.readInt();
    }

    /**
     * Gets the Integer value from an Item Object.
     * @return Integer      Value for the colour of the Item
     */
    public int getColor() {
        return colorImg;
    }

    /**
     * Sets a specified colour for an Item Object.
     * @param color         Integer value for the colour of the Item Object
     */
    public void setColor(int color) {
        colorImg = color;
    }

    /**
     * Method that alternates the current colour of an Item.
     * @return Integer      Memory address reference for a colour image
     *                      (method not used in Three In A Row)
     */
    public int nextColor() {
        colorImg = R.drawable.grey;

        // If click value has reached 4 reset it back to 1 (green)
        if(++click > 3)
            click = 1;

        switch(click) {
            case 1:
                colorImg = R.drawable.green;
                break;
            case 2:
                colorImg = R.drawable.blue;
                break;
            case 3:
                colorImg = R.drawable.grey;
                break;
        }
        return colorImg;
    }

    /**
     * Sets the starting colour for an Item.
     * @param color         Integer value for the starting colour
     * @return Integer      Integer value for the starting colour
     *                      (method not used in Three In A Row)
     */
    public int setStartingColor(int color) {
        colorImg = color;

        return colorImg;
    }
}
