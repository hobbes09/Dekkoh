
package com.dekkoh.custom.handler;

public class FragmentTransitionManager {

    public final static int SWIPE_UP = 1; // To go UP
    public final static int SWIPE_DOWN = 2; // To go DOWN
    public final static int SWIPE_LEFT = 3; // To go LEFT
    public final static int SWIPE_RIGHT = 4; // To go RIGHT
    public final static int SWIPE_NULL = 0;

    public static int detectSwipe(float xDown, float xUp, float yDown, float yUp) {
        int SWIPE_ACTION = SWIPE_NULL;

        if ((yDown > yUp) && (Math.abs(yDown - yUp) > Math.abs(xDown - xUp)))
            SWIPE_ACTION = SWIPE_DOWN;

        if ((yDown < yUp) && (Math.abs(yDown - yUp) > Math.abs(xDown - xUp)))
            SWIPE_ACTION = SWIPE_UP;

        if ((xDown < xUp) && (Math.abs(yDown - yUp) < Math.abs(xDown - xUp)))
            SWIPE_ACTION = SWIPE_LEFT;

        if ((xDown > xUp) && (Math.abs(yDown - yUp) < Math.abs(xDown - xUp)))
            SWIPE_ACTION = SWIPE_RIGHT;

        return SWIPE_ACTION;
    }

}
