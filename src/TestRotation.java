
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
public class TestRotation extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 768;
    static final int HEIGHT = 768;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    BufferedImage stage = ImageHelper.loadImage("First Stage.png");
    boolean up = false;
    boolean down = false;
    boolean rotateRight = false;
    boolean rotateLeft = false;
    double x = WIDTH / 2;
    double y = HEIGHT / 4;
    int angle = 0;

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 
        g.clipRect(0, 0, WIDTH, HEIGHT / 2);
//        g2d.translate(x, y);
//        g2d.rotate(Math.toRadians(angle));
//        g2d.translate(- x, -y);
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), x, y);
        // calculate new center of the screen after a rotation has occured
        // see http://stackoverflow.com/questions/20104611/find-new-coordinates-of-a-point-after-rotation
        // we will use this coordinate to draw the map at
        double newY =  ((192 - y) * Math.cos(Math.toRadians(angle)) - (WIDTH / 2 - x) * Math.sin(Math.toRadians(angle)));
        double newX =  ((192 - y) * Math.sin(Math.toRadians(angle)) + (WIDTH / 2 - x) * Math.cos(Math.toRadians(angle)));
        // translate to the new location
        tx.translate(newX, newY);
        g2d.drawImage(stage, tx, null);
        // undo all transformations
        tx.setToIdentity();
        g.setColor(Color.red);
        g.fillRect(WIDTH / 2 - 10, 192 - 10, 20, 20);
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

            if (up) {
               double dy = (-2*Math.cos(Math.toRadians(angle)))/2;
               double dx = (2*Math.sin(Math.toRadians(angle)))/2;
               y = y + dy;
               x = x + dx;
            }
            if (down) {
               double dy = (2*Math.cos(Math.toRadians(angle)))/2;
               double dx = (-2*Math.sin(Math.toRadians(angle)))/2;
               y = y - dy;
               x = x - dx;
            }
            if (rotateRight) {
                angle = (angle - 2) % 360;
            }
            if (rotateLeft) {
                angle = (angle + 2) % 360;
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
        TestRotation game = new TestRotation();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_RIGHT:
                rotateRight = true;
                break;
            case KeyEvent.VK_LEFT:
                rotateLeft = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_RIGHT:
                rotateRight = false;
                break;
            case KeyEvent.VK_LEFT:
                rotateLeft = false;
                break;
        }
    }
}