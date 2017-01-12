
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author slatz8075
 */
public class ScreenCheat extends JComponent implements KeyListener {

    // Height and Width of the game
    static final int WIDTH = 768;
    static final int HEIGHT = 768;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    BufferedImage stage = ImageHelper.loadImage("First Stage.png");
    
    //player one variables
    int P1x = WIDTH/2;
    int P1y = HEIGHT/4;
    //directional
    boolean P1moveForward = false;
    boolean P1moveBack = false;
    boolean P1moveRight = false;
    boolean P1moveLeft = false;
    //original angle
    int P1angle = 0;
    //Rotational Commands
    private boolean P1rotateR;
    private boolean P1rotateL;
    
    //player 2 variables
    int P2x = WIDTH/2;
    int P2y = (HEIGHT/4)*3;
    //directional
    boolean P2moveForward = false;
    boolean P2moveBack = false;
    boolean P2moveRight = false;
    boolean P2moveLeft = false;
    //original angle
    int P2angle = 0;
    //Rotational Commands
    private boolean P2rotateR;
    private boolean P2rotateL;
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // always clear the screen first!
        //create background color
        Color background = new Color(82, 82, 82);
        //clear the screen for a new animation
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        //set back ground to the stage back ground colour
        g.setColor(background);
        //
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //Player one
        g.clipRect(0, 0, WIDTH, HEIGHT / 2);
//g2d.translate(x, y);
//g2d.rotate(Math.toRadians(angle));
//g2d.translate(- x, -y);
        AffineTransform P1tx = new AffineTransform();
        P1tx.rotate(Math.toRadians(P1angle), P1x, P1y);
        P1tx.translate(WIDTH / 2 - P1x, HEIGHT/3 - P1y);
        g2d.drawImage(stage, P1tx, null);
        P1tx.setToIdentity();
        g.setColor(Color.red);
        g.fillRect(WIDTH/2 - 5, HEIGHT/3 - 5, 10, 10);
        g.setColor(Color.yellow);
        g.fillRect(P1x - 2, P1y - 2, 4, 4);
        
        //Player two
        g.clipRect(0, 384, WIDTH, 384);
//g2d.translate(x, y);
//g2d.rotate(Math.toRadians(angle));
//g2d.translate(- x, -y);
        AffineTransform P2tx = new AffineTransform();
        P2tx.rotate(Math.toRadians(P2angle), P2x, P2y);
        P2tx.translate(WIDTH / 2 - P2x, (HEIGHT - (HEIGHT/4)) - P2y);
        g2d.drawImage(stage, P2tx, null);
        P2tx.setToIdentity();
        g.setColor(Color.BLUE);
        g.fillRect(WIDTH/2 - 5, (HEIGHT - (HEIGHT/4)) - 5, 10, 10);
        g.setColor(Color.yellow);
        g.fillRect(P2x - 2, P2y - 2, 4, 4);

        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
            //Player one
            //test to see if the respective button is being pressed
            if (P1rotateR) {
                //adjust the angle of the stage correspondingly
                P1angle = (P1angle - 2) % 360;
            }
            //test to see if the respective button is being pressed
            if (P1rotateL) {
                //adjust the angle of the stage correspondingly
                P1angle = (P1angle + 2) % 360;
            }
            //test to see if the respective button is being pressed
            if (P1moveForward) {
                //adjust the position stage underneath player 1
                P1y = P1y - 1;
            }
            //test to see if the respective button is being pressed
            if (P1moveBack) {
                //adjust the position stage underneath player 1
                P1y = P1y + 1;
            }
            //test to see if the respective button is being pressed
            if (P1moveRight) {
                //adjust the position stage underneath player 1
                P1x = P1x + 1;
            }
            //test to see if the respective button is being pressed
            if (P1moveLeft) {
                //adjust the position stage underneath player 1
                P1x = P1x - 1;
            }

            //player 2
            //test to see if the respective button is being pressed
            if (P2rotateR) {
                //adjust the angle of the stage correspondingly
                P2angle = (P1angle - 2) % 360;
            }
            //test to see if the respective button is being pressed
            if (P2rotateL) {
                //adjust the angle of the stage correspondingly
                P2angle = (P1angle + 2) % 360;
            }
            //test to see if the respective button is being pressed
            if (P2moveForward) {
                //adjust the position stage underneath player 2
                P2y = P2y - 1;
            }
            //test to see if the respective button is being pressed
            if (P2moveBack) {
                //adjust the position stage underneath player 2
                P2y = P2y + 1;
            }
            //test to see if the respective button is being pressed
            if (P2moveRight) {
                //adjust the position stage underneath player 2
                P2x = P2x + 1;
            }
            //test to see if the respective button is being pressed
            if (P2moveLeft) {
                //adjust the position stage underneath player 2
                P2x = P2x - 1;
            }
            
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                };
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        ScreenCheat game = new ScreenCheat();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);
        frame.addKeyListener(game);
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //Player one
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_W) {
            //change the state of the boolean from false to true
            P1moveForward = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_A) {
            //change the state of the boolean from false to true
            P1moveLeft = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_D) {
            //change the state of the boolean from false to true
            P1moveRight = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_S) {
            //change the state of the boolean from false to true
            P1moveBack = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_E) {
            //change the state of the boolean from false to true
            P1rotateR = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_Q) {
            //change the state of the boolean from false to true
            P1rotateL = true;
        }

        //Player two
        if (key == KeyEvent.VK_I) {
            //change the state of the boolean from false to true
            P2moveForward = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_J) {
            //change the state of the boolean from false to true
            P2moveLeft = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_L) {
            //change the state of the boolean from false to true
            P2moveRight = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_K) {
            //change the state of the boolean from false to true
            P2moveBack = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_O) {
            //change the state of the boolean from false to true
            P2rotateR = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_U) {
            //change the state of the boolean from false to true
            P2rotateL = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        //player one
        //test to see if the key has been released
        if (key == KeyEvent.VK_W) {
            //change the state of the boolean from true back to false
            P1moveForward = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_A) {
            //change the state of the boolean from true back to false
            P1moveLeft = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_D) {
            //change the state of the boolean from true back to false
            P1moveRight = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_S) {
            //change the state of the boolean from true back to false
            P1moveBack = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_E) {
            //change the state of the boolean from true back to false
            P1rotateR = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_Q) {
            //change the state of the boolean from true back to false
            P1rotateL = false;
        }

        //player two
        if (key == KeyEvent.VK_I) {
            //change the state of the boolean from true back to false
            P2moveForward = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_J) {
            //change the state of the boolean from true back to false
            P2moveLeft = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_L) {
            //change the state of the boolean from true back to false
            P2moveRight = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_K) {
            //change the state of the boolean from true back to false
            P2moveBack = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_O) {
            //change the state of the boolean from true back to false
            P2rotateR = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_U) {
            //change the state of the boolean from true back to false
            P2rotateL = false;
        }
    }
}