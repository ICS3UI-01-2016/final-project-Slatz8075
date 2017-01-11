
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    int P1x = 384;
    int P1y = 576;
    //directional
    boolean P1moveForward = false;
    boolean P1moveBack = false;
    boolean P1moveRight = false;
    boolean P1moveLeft = false;
    //original angle
    int P1angle = 0;
    //
    private boolean P1rotateR;
    private boolean P1rotateL;
    //player 2 variables
    int P2x = 384;
    int P2y = 192;
    //directional
    boolean P2moveForward = false;
    boolean P2moveBack = false;
    boolean P2moveRight = false;
    boolean P2moveLeft = false;
    //original angle
    int P2angle = 0;
    //
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
        //draw the stage image

        //g.drawImage(stage, 0, 0, 768,768, null);
        //set the color of the player

        
        g.drawImage(stage, P1x, P1y, 384, 384, this);
        g.drawImage(stage, P2x, P2y, 384, 384, this);
        g2d.
        g.drawImage(stage, P1x, P1x, P1x, P1x, P1x, P1x, P1x, P1x, background, this);


        //player one
        g.setColor(Color.RED);
        //g2d.translate(P1x + 25, P1y + 25);
        g2d.rotate(Math.toRadians(P1angle));
        g.fillRect(-6, -6, 12, 12);
        //g2d.rotate(-Math.toRadians(P1angle));
        g2d.translate(-P1x - 25, -P1y - 25);

        //player two
        g.setColor(Color.BLUE);
        //g2d.translate(P2x + 25, P2y + 25);
        g2d.rotate(Math.toRadians(P2angle));
        g.fillRect(-6, -6, 12, 12);
        //g2d.rotate(-Math.toRadians(P2angle));
        g2d.translate(-P2x - 25, -P2y - 25);







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
            //test if boolean player 1 rotate right is true
            if (P1rotateR) {
                //adjust his angle
                P1angle = (P1angle + 3) % 360;
            }
            //test if boolean player 1 rotate left is true
            if (P1rotateL) {
                //adjust his angle
                P1angle = (P1angle - 3) % 360;
            }
            //test if boolean player 1 move forward is true
            if (P1moveForward) {
                //
                int P1dy = (int) Math.ceil(-1 * Math.cos(Math.toRadians(P1angle)));
                int P1dx = (int) Math.ceil(Math.sin(Math.toRadians(P1angle)));
                P1y = P1y + P1dy;
                P1x = P1x + P1dx;
            }
            //test if boolean player 1 move backwards is true
            if (P1moveBack) {
                int P1dy = (int) Math.ceil(-1 * Math.cos(Math.toRadians(P1angle)));
                int P1dx = (int) Math.ceil(Math.sin(Math.toRadians(P1angle)));
                P1y = P1y - P1dy;
                P1x = P1x - P1dx;
            }
            if (P1moveRight) {
                int P1dy = (int) Math.ceil(Math.cos(Math.toRadians(P1angle + 90)));
                int P1dx = (int) Math.ceil(-1 * Math.sin(Math.toRadians(P1angle + 90)));
                P1y = P1y - P1dy;
                P1x = P1x - P1dx;
            }
            if (P1moveLeft) {
                int P1dy = (int) Math.ceil(Math.cos(Math.toRadians(P1angle - 90)));
                int P1dx = (int) Math.ceil(-1 * Math.sin(Math.toRadians(P1angle - 90)));
                P1y = P1y - P1dy;
                P1x = P1x - P1dx;
            }






            //player 2
            if (P2rotateR) {
                P2angle = (P2angle + 3) % 360;
            }
            if (P2rotateL) {
                P2angle = (P2angle - 3) % 360;
            }
            if (P2moveForward) {
                int P2dy = (int) Math.ceil(-1 * Math.cos(Math.toRadians(P2angle)));
                int P2dx = (int) Math.ceil(Math.sin(Math.toRadians(P2angle)));
                P2y = P2y + P2dy;
                P2x = P2x + P2dx;
            }
            if (P2moveBack) {
                int P2dy = (int) Math.ceil(-1 * Math.cos(Math.toRadians(P2angle)));
                int P2dx = (int) Math.ceil(Math.sin(Math.toRadians(P2angle)));
                P2y = P2y - P2dy;
                P2x = P2x - P2dx;
            }
            if (P2moveRight) {
                int P2dy = (int) Math.ceil(Math.cos(Math.toRadians(P2angle + 90)));
                int P2dx = (int) Math.ceil(-1 * Math.sin(Math.toRadians(P2angle + 90)));
                P2y = P2y - P2dy;
                P2x = P2x - P2dx;
            }
            if (P2moveLeft) {
                int P2dy = (int) Math.ceil(Math.cos(Math.toRadians(P2angle - 90)));
                int P2dx = (int) Math.ceil(-1 * Math.sin(Math.toRadians(P2angle - 90)));
                P2y = P2y - P2dy;
                P2x = P2x - P2dx;
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
        if (key == KeyEvent.VK_W) {
            P1moveForward = true;
        }
        if (key == KeyEvent.VK_A) {
            P1moveLeft = true;
        }
        if (key == KeyEvent.VK_D) {
            P1moveRight = true;
        }
        if (key == KeyEvent.VK_S) {
            P1moveBack = true;
        }
        if (key == KeyEvent.VK_E) {
            P1rotateR = true;
        }
        if (key == KeyEvent.VK_Q) {
            P1rotateL = true;
        }






        //Player two
        if (key == KeyEvent.VK_I) {
            P2moveForward = true;
        }
        if (key == KeyEvent.VK_J) {
            P2moveLeft = true;
        }
        if (key == KeyEvent.VK_L) {
            P2moveRight = true;
        }
        if (key == KeyEvent.VK_K) {
            P2moveBack = true;
        }
        if (key == KeyEvent.VK_O) {
            P2rotateR = true;
        }
        if (key == KeyEvent.VK_U) {
            P2rotateL = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        //player one
        if (key == KeyEvent.VK_W) {
            P1moveForward = false;
        }
        if (key == KeyEvent.VK_A) {
            P1moveLeft = false;
        }
        if (key == KeyEvent.VK_D) {
            P1moveRight = false;
        }
        if (key == KeyEvent.VK_S) {
            P1moveBack = false;
        }
        if (key == KeyEvent.VK_E) {
            P1rotateR = false;
        }
        if (key == KeyEvent.VK_Q) {
            P1rotateL = false;
        }






        //player two
        if (key == KeyEvent.VK_I) {
            P2moveForward = false;
        }
        if (key == KeyEvent.VK_J) {
            P2moveLeft = false;
        }
        if (key == KeyEvent.VK_L) {
            P2moveRight = false;
        }
        if (key == KeyEvent.VK_K) {
            P2moveBack = false;
        }
        if (key == KeyEvent.VK_O) {
            P2rotateR = false;
        }
        if (key == KeyEvent.VK_U) {
            P2rotateL = false;
        }
    }
}