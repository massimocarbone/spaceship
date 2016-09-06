/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshipclasses;

import java.io.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

import java.util.ArrayList;

public class SpaceShipClasses extends JFrame implements Runnable {
    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;

    Image outerSpaceImage;

//variables for rocket.
    Image rocketImage;
    Image rocketImageAnim;
    int rocketXPos;
    int rocketYPos;
    int rocketXSpeed;
    int rocketYSpeed;
    boolean rocketXDirRight;

    int numStars = 4;
    ArrayList<Star> stars = new ArrayList<Star>();
    Image starImage;

    ArrayList<Missile> missiles = new ArrayList<Missile>();
    
    boolean gameOver;
    int highScore = 0;
    int score;
    
    static Sound starwarsSound = null;
    static Sound ouchSound = null;
    
    static SpaceShipClasses frame;
    public static void main(String[] args) {
        frame = new SpaceShipClasses();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public SpaceShipClasses() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button

                    if (gameOver)
                        return;
// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();

                    missiles.add(new Missile(rocketXDirRight,rocketXPos,rocketYPos));
                    
                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                    if (gameOver)
                        return;
                
                if (e.VK_UP == e.getKeyCode()) {
                    rocketYSpeed++;
                } else if (e.VK_DOWN == e.getKeyCode()) {
                    rocketYSpeed--;
                } else if (e.VK_LEFT == e.getKeyCode()) {
                    rocketXSpeed--;
                    if (rocketXSpeed < -10)
                        rocketXSpeed = -10;
                    if (rocketXSpeed < 0)
                        rocketXDirRight = false;
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                    rocketXSpeed++;
                    if (rocketXSpeed > 10)
                        rocketXSpeed = 10;
                    if (rocketXSpeed > 0)
                        rocketXDirRight = true;
                    
                } else if (e.VK_INSERT == e.getKeyCode()) {                 
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }



////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
//fill border
        g.setColor(Color.black);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        g.drawImage(outerSpaceImage,Window.getX(0),Window.getY(0),
                Window.getWidth2(),Window.getHeight2(),this);

        
        if (rocketXDirRight)
        {
            if (rocketXSpeed == 0)
                drawRocket(rocketImage,Window.getX(rocketXPos),Window.getYNormal(rocketYPos),
                0.0,1.0,1.0 );
            else
                drawRocket(rocketImageAnim,Window.getX(rocketXPos),Window.getYNormal(rocketYPos),
                0.0,1.0,1.0 );
        }
        else
        {
            if (rocketXSpeed == 0)
                drawRocket(rocketImage,Window.getX(rocketXPos),Window.getYNormal(rocketYPos),
                0.0,-1.0,1.0 );
            else
                drawRocket(rocketImageAnim,Window.getX(rocketXPos),Window.getYNormal(rocketYPos),
                0.0,-1.0,1.0 );
        }   
        for (int i=0;i<missiles.size();i++)
            missiles.get(i).draw(g);   

        for (int i=0;i<stars.size();i++)
            stars.get(i).draw(this,g,starImage);   

        
            g.setColor(Color.black);
            g.setFont(new Font("Monospaced",Font.BOLD,12) );
            g.drawString("High Score: " + highScore, 300, 40);
        
            g.setColor(Color.black);
            g.setFont(new Font("Monospaced",Font.BOLD,12) );
            g.drawString("Score: " + score, 20, 40);
        
        if (gameOver)
        {
            g.setColor(Color.white);
            g.setFont(new Font("Monospaced",Font.BOLD,40) );
            g.drawString("Game Over", 50, 200);
        }        
        
        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
    public void drawRocket(Image image,int xpos,int ypos,double rot,double xscale,
            double yscale) {
        int width = image.getWidth(this);
        int height = image.getHeight(this);
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.drawImage(image,-width/2,-height/2,
        width,height,this);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.04;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        gameOver = false;
        rocketXSpeed = 0;
        rocketYSpeed = 0;
        rocketXDirRight = true;
//init the location of the rocket to the center.
        rocketXPos = Window.getWidth2()/2;
        rocketYPos = Window.getHeight2()/2;
        missiles.clear();
        stars.clear();
        for (int i=0;i<numStars;i++)
            stars.add(new Star());
        score = 0;
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            InOutData.numStars = numStars;
            InOutData.highScore = highScore;
            InOutData.readCurrentState();
            highScore = InOutData.highScore;
            numStars = InOutData.numStars;
            InOutData.writeCurrentState(); 
            
            
            
            outerSpaceImage = Toolkit.getDefaultToolkit().getImage("./outerSpace.jpg");
            rocketImage = Toolkit.getDefaultToolkit().getImage("./rocket.GIF");
            rocketImageAnim = Toolkit.getDefaultToolkit().getImage("./animRocket.GIF");
            starImage = Toolkit.getDefaultToolkit().getImage("./starAnim.GIF");
            reset();
            starwarsSound = new Sound("starwars.wav");
        }
        if (starwarsSound != null && starwarsSound.donePlaying)
          starwarsSound = new Sound("starwars.wav");
                    if (gameOver)
                        return;
        
        if (rocketYSpeed > 0)
        {
            if (rocketYPos < Window.getHeight2())
                rocketYPos += rocketYSpeed;
            else
            {
                rocketYSpeed = 0;
                rocketYPos = Window.getHeight2();
            }
        }    
        else if (rocketYSpeed < 0)
        {
            if (rocketYPos > 0)
                rocketYPos += rocketYSpeed;
            else
            {
                rocketYSpeed = 0;
                rocketYPos = 0;
            }
        } 

        
        for (int i=0;i<stars.size();i++)
        {
             
          if (stars.get(i).collide(rocketXPos,rocketYPos))
          {              
                gameOver = true; 
          }
         stars.get(i).move(-rocketXSpeed);
          
            
        }
        
        for (int i=0;i<stars.size();i++)
        {
            for (int j=0;j<missiles.size();j++)
            {
                if (stars.get(i).collide(missiles.get(j).getXPos(),
                missiles.get(j).getYPos()))
                {
                    score++;
                    if (score > highScore)
                    {
                        highScore = score;
                        InOutData.numStars = numStars;
                        InOutData.highScore = highScore;
                        InOutData.writeCurrentState();
                        
                        
                    }
                    ouchSound = new Sound("ouch.wav");
                   // stars.remove(i);
                    missiles.remove(j);
                    break;
                }
            }
        } 

        
        for (int i=0;i<missiles.size();i++)
        {
            if (missiles.get(i).move())
                missiles.remove(i);
        }
        
        
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
    

    
        
    
}


