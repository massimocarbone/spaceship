
package spaceshipclasses;

import java.awt.Color;
import java.awt.Graphics2D;


public class Missile {
    private int xPos;
    private int yPos;
    private boolean moveRight;
    Missile(boolean _moveRight,int startXPos,int startYPos)
    {
        xPos = startXPos;
        yPos = startYPos;
        moveRight = _moveRight;
    }
    public int getXPos()
    {
        return(xPos);
    }
    public int getYPos()
    {
        return(yPos);
    }    
    public boolean move()
    {
        if (moveRight)
            xPos+=5;
        else
            xPos-=5;
        
        if (xPos > Window.getWidth2())
            return (true);
        else if (xPos < 0)
            return (true);
        
        return (false);
    }    
    public void draw(Graphics2D g)
    {
        g.setColor(Color.white);
        drawCircle(g,Window.getX(xPos),Window.getYNormal(yPos),0,.2,.2); 
    }   
////////////////////////////////////////////////////////////////////////////
    public void drawCircle(Graphics2D g,int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.fillOval(-10,-10,20,20);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }       
}
