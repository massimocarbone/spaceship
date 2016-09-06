
package spaceshipclasses;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

class Star {
    private int xPos;
    private int yPos;
    
    Star()
    {
        xPos = (int)(Math.random() * Window.getWidth2());
        yPos = (int)(Math.random() * Window.getHeight2());                
    }

    public void move(int moveVal)
    {
        xPos += moveVal;
        
        if (xPos < -30)
        {
            xPos = Window.getWidth2()+30;
            yPos = (int)(Math.random() * Window.getHeight2());        
        }
        else if (xPos > Window.getWidth2() + 30)
        {
            xPos = -30;
            yPos = (int)(Math.random() * Window.getHeight2());        
        }        
    }
    public void draw(SpaceShipClasses obj,Graphics2D g,Image image)
    {
        drawStar(obj, g, image,Window.getX(xPos),Window.getYNormal(yPos),0,1,1); 
    }    
////////////////////////////////////////////////////////////////////////////
    public void drawStar(SpaceShipClasses obj,Graphics2D g,Image image,int xpos,int ypos,double rot,double xscale,
            double yscale) {
        int width = image.getWidth(obj);
        int height = image.getHeight(obj);
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.drawImage(image,-width/2,-height/2,
        width,height,obj);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }    
    public boolean collide(int _xPos,int _yPos)
    {
        if (_xPos+20 > xPos-10 && _xPos-20 < xPos+10 && _yPos+10 > yPos-10 && _yPos-10 < yPos+10)
            return true;
        return false;
    }
}
