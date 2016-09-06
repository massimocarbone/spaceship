/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipclasses;

/**
 *
 * @author jyee
 */

public class Window {
    static final int WINDOW_WIDTH = 420;
    static final int WINDOW_HEIGHT = 445;
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 25;
    static int xsize = -1;
    static int ysize = -1;    
/////////////////////////////////////////////////////////////////////////
    static public int getX(int x) {
        return (x + XBORDER);
    }

    static public int getY(int y) {
        return (y + YBORDER + YTITLE);
    }

    static public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    
    static public int getWidth2() {
        return (xsize - getX(0) - XBORDER);
    }

    static public int getHeight2() {
        return (ysize - getY(0) - YBORDER);
    }    
}

