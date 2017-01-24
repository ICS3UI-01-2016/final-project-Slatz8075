
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
    double P1x = WIDTH / 2 + 35;
    double P1y = HEIGHT / 4;
    //previous positions used for colision detection
    double previousP1xPosition = P1x;
    double previousP1yPosition = P1y;
    //directional
    boolean P1moveForward = false;
    boolean P1moveBack = false;
    boolean P1moveRight = false;
    boolean P1moveLeft = false;
    //weapon changer monitor
    boolean P1changeWeapon = false;
    //test to see if their weapon has been fired
    boolean P1shootWeapon = false;
    //the state of their weapon: 0 = blunderbuss, 1 = revolver rifle
    int P1weapon = 0;
    //original angle
    int P1angle = 0;
    //Rotational Commands
    private boolean P1rotateR;
    private boolean P1rotateL;
    //state of player 1
    boolean P1alive = true;
    //timer for the time inbetween the switching of weapons
    int P1changeTimer = 30;
    //variable speed
    double P1speed = 2;
    //Dx and Dy of P1
    double P1dy = P1speed * (Math.cos(Math.toRadians(P1angle)));
    double P1dx = P1speed * (Math.sin(Math.toRadians(P1angle)));
    //speed of bullet
    double P1bulletSpeed = 6.5;
    //array for speed and position of P1's bullets
    //p1bullets[0][0] = the x co-ord of the first bullet
    //p1bullets[0][1] = the y co-ord of the first bullet
    //p1bullets[0][2] = the dx (speed) first bullet
    //p1bullets[0][3] = the dy (speed) of the first bullet
    double P1bullets[][] = new double[4][4];
    //create a hit box for both players during colision detection
    int size = 5;
    //counter for the revolver rifle
    int P1revolverRifleTimer = 0;
    //counter for the blunderbuss
    int P1blunderbussTimer = 0;
    //boolean for the revolver bullet in motion
    boolean P1revolverRifleBulletInMotion = false;
    //boolean for the blunderbuss bullets to be in motion
    boolean P1blunderBussBulletsInMotion = false;
    //player two variables
    double P2x = WIDTH / 2 - 35;
    double P2y = (HEIGHT / 4) * 3;
    //previous positions used for colision detection
    double previousP2xPosition = P2x;
    double previousP2yPosition = P2y;
    //directional
    boolean P2moveForward = false;
    boolean P2moveBack = false;
    boolean P2moveRight = false;
    boolean P2moveLeft = false;
    //weapon changer monitor
    boolean P2changeWeapon = false;
    //test to see if their weapon has been fired
    boolean P2shootWeapon = false;
    //the state of their weapon: 0 = blunderbuss, 1 = revolver rifle
    int P2weapon = 0;
    //original angle
    int P2angle = 0;
    //Rotational Commands
    private boolean P2rotateR;
    private boolean P2rotateL;
    //state of player 2
    boolean P2alive = true;
    //timer for the time inbetween the switching of weapons
    int P2changeTimer = 30;
    //create background color
    Color background = new Color(82, 82, 82);
    // # of seconds until player respawns
    final int DeathTime = 5;
    final long delayStart = DeathTime * desiredFPS;
    long P1gunTimer = delayStart;
    long P2gunTimer = delayStart;

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // always clear the screen first!
        //clear the screen for a new animation
        g.clearRect(0, 0, WIDTH, HEIGHT);
        //set back ground to the stage back ground colour
        g.setColor(background);
        //fill the background with this color
        g.fillRect(-WIDTH, -HEIGHT, WIDTH * 3, HEIGHT * 3);
        // GAME DRAWING GOES HERE

        //Player one
        //set variable to the bullet array  


        //store the position and rotation of the screen
        AffineTransform P1tx = new AffineTransform();
        //rotate the screen based on the angle
        P1tx.rotate(Math.toRadians(P1angle), P1x, P1y);
        // calculate new center of the screen after a rotation has occured
        double newP1Y = ((HEIGHT / 4 - P1y) * Math.cos(Math.toRadians(P1angle)) - (WIDTH / 2 - P1x) * Math.sin(Math.toRadians(P1angle)));
        double newP1X = ((HEIGHT / 4 - P1y) * Math.sin(Math.toRadians(P1angle)) + (WIDTH / 2 - P1x) * Math.cos(Math.toRadians(P1angle)));
        // translate to the new location
        P1tx.translate(newP1X, newP1Y);
        //draw the stage
        g2d.drawImage(stage, P1tx, null);
        //take old tranformation so I may change it back to it later
        AffineTransform old = g2d.getTransform();
        //set the color of the bullet                       http://stackoverflow.com/questions/14124593/how-to-rotate-graphics-in-java
        g.setColor(Color.WHITE);
        //sets the transformation to the same as the stage 
        g2d.setTransform(P1tx);
        //draw the bullet(s) based on which weapon is beiong used

        //test to see if the player is shooting the weapon and is the blunderbuss
        if ((P1shootWeapon) && P1weapon == 0 && P1alive && P1blunderbussTimer == 0) {
            //set the bullets to be in motion
            P1blunderBussBulletsInMotion = true;
            //start the bullet at the position of the player
            P1bullets[0][0] = P1x;
            P1bullets[0][1] = P1y;
            P1bullets[1][0] = P1x;
            P1bullets[1][1] = P1y;
            P1bullets[2][0] = P1x;
            P1bullets[2][1] = P1y;
            P1bullets[3][0] = P1x;
            P1bullets[3][1] = P1y;
            //restart the timer imbetween shots
            P1blunderbussTimer = 120;
            //test to see if the player is shooting the weapon and is the revolver rifle
        } else if ((P1shootWeapon) && P1weapon == 1 && P1alive && P1revolverRifleTimer == 0) {
            //set the bullet to be in motion
            P1revolverRifleBulletInMotion = true;
            //start the bullet at the position of the player
            P1bullets[0][0] = P1x;
            P1bullets[0][1] = P1y;
            //restart the timer imbetween shots
            P1revolverRifleTimer = 30;
            //test to see if the player is shooting the weapon and is the bear bomb         
        }

        //test to see if the timer has hit zero or not
        if (P1blunderbussTimer > 0) {
            //if it hasn't subtract one from the timer
            P1blunderbussTimer--;
        }
        //test to see if the bullet is active
        if (P1blunderBussBulletsInMotion == true) {
            g.fillOval((int) P1bullets[0][0] - 5, (int) P1bullets[0][1] - 5, 10, 10);
            g.fillOval((int) P1bullets[1][0] - 5, (int) P1bullets[1][1] - 5, 10, 10);
            g.fillOval((int) P1bullets[2][0] - 5, (int) P1bullets[2][1] - 5, 10, 10);
            g.fillOval((int) P1bullets[3][0] - 5, (int) P1bullets[3][1] - 5, 10, 10);
            //P1x trajectory for bullet 1 
            P1bullets[0][2] = P1bulletSpeed * Math.cos(Math.toRadians(-P1angle + (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //P1y trajectory for bullet 1 
            P1bullets[0][3] = P1bulletSpeed * Math.sin(Math.toRadians(-P1angle + (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //P1x trajectory for bullet 2 
            P1bullets[1][2] = P1bulletSpeed * Math.cos(Math.toRadians(-P1angle - (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //P1y trajectory for bullet 2 
            P1bullets[1][3] = P1bulletSpeed * Math.sin(Math.toRadians(-P1angle - (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //P1x trajectory for bullet 3 
            P1bullets[2][2] = P1bulletSpeed * Math.cos(Math.toRadians(-P1angle + (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //P1y trajectory for bullet 3 
            P1bullets[2][3] = P1bulletSpeed * Math.sin(Math.toRadians(-P1angle + (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //P1x trajectory for bullet 4 
            P1bullets[3][2] = P1bulletSpeed * Math.cos(Math.toRadians(-P1angle - (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //P1y trajectory for bullet 4 
            P1bullets[3][3] = P1bulletSpeed * Math.sin(Math.toRadians(-P1angle - (int) (Math.random() * (6 - (-6) + 1)) - 90));
            //make the calculated transformations
            //for bullet 1
            P1bullets[0][0] += P1bullets[0][2];
            P1bullets[0][1] += P1bullets[0][3];
            //for bullet 2
            P1bullets[1][0] += P1bullets[1][2];
            P1bullets[1][1] += P1bullets[1][3];
            //for bullet 3
            P1bullets[2][0] += P1bullets[2][2];
            P1bullets[2][1] += P1bullets[2][3];
            //for bullet 4
            P1bullets[3][0] += P1bullets[3][2];
            P1bullets[3][1] += P1bullets[3][3];
            //murder player 2
            P2alive = false;
        }
        //test to see if the timer has hit zero or not
        if (P1revolverRifleTimer > 0) {
            //if it hasn't subtract one from the timer
            P1revolverRifleTimer--;
        }
        //test to see if the bullet is active
        if (P1revolverRifleBulletInMotion == true) {
            g.fillOval((int) P1bullets[0][0] - 5, (int) P1bullets[0][1] - 5, 10, 10);
            //P1x trajectory for bullet 1 
            P1bullets[0][2] = P1bulletSpeed * Math.cos(Math.toRadians(-P1angle - 90));
            //P1y trajectory for bullet 1 
            P1bullets[0][3] = P1bulletSpeed * Math.sin(Math.toRadians(-P1angle - 90));
            //make the calculated transformations
            //for bullet 1
            P1bullets[0][0] += P1bullets[0][2];
            P1bullets[0][1] += P1bullets[0][3];
            //murder player 2
            P2alive = false;
        }


        //setting it back to its original position
        g2d.setTransform(old);
        // undo all transformations
        P1tx.setToIdentity();
        //set the color of the player
        g.setColor(Color.BLACK);
        //fill the player at the position
        g.fillRect(WIDTH / 2 - 5, HEIGHT / 4 - 5, 10, 10);
        //set the color of the back ground
        g.setColor(background);
        //draw rectangle beneath p2 screen
        g.fillRect(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
        g.clipRect(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

        //Player two
        //g.clipRect(0, (HEIGHT/2), WIDTH, HEIGHT / 2);
        AffineTransform P2tx = new AffineTransform();
        P2tx.rotate(Math.toRadians(P2angle), P2x, P2y);
        // calculate new center of the screen after a rotation has occured
        double newP2Y = (((HEIGHT / 4) * 3 - P2y) * Math.cos(Math.toRadians(P2angle)) - (WIDTH / 2 - P2x) * Math.sin(Math.toRadians(P2angle)));
        double newP2X = (((HEIGHT / 4) * 3 - P2y) * Math.sin(Math.toRadians(P2angle)) + (WIDTH / 2 - P2x) * Math.cos(Math.toRadians(P2angle)));
        // translate to the new location
        P2tx.translate(newP2X, newP2Y);
        g2d.drawImage(stage, P2tx, null);
        // undo all transformations
        P2tx.setToIdentity();
        g.setColor(Color.BLACK);
        g.fillRect(WIDTH / 2 - 5, (HEIGHT / 4) * 3 - 5, 10, 10);
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
        //create a counter that counts down from 5 once player 1 dies

        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            //Player one 
            //respawn logic
            //test to see if player one is dead
            if (P1alive == false) {
                //if he is start a 5 sec timer
                P1gunTimer = delayStart;
                //turn his sttus back to alive
                P1alive = true;
            }
            //test to see ift he timer has run out yet
            if (P1gunTimer > 0) {
                //subtract one millisec from the timer
                P1gunTimer--;
                ////test to see if the playter is pressing the change weapon button and the change weapon timer has run out yet
                if ((P1changeWeapon == true) && (P1changeTimer == 0)) {
                    //change the weapon (0 = blunder buss, 1 = revolver rifle)
                    P1weapon++;
                    //reset the timer so this if statement will not trigger for the next 1/2 sec
                    P1changeTimer = 30;
                    //test to see if the weapon if #3
                    if (P1weapon == 2) {
                        //if so cycle it back to the blunderbuss
                        P1weapon = 0;
                    }
                }
                //test to see if the player is allowed to change their weapon yet
                if (P1changeTimer > 0) {
                    // if not subtract one ffrom the timer
                    P1changeTimer--;
                }
            } else if (P1gunTimer == 0) {
                System.out.println("Respawn: P1 is alive");
                P1changeWeapon = false;
            }
            //test to see if the respective button is being pressed
            if (P1rotateR) {
                //adjust the angle of the stage correspondingly
                P1angle = (P1angle - (int) P1speed) % 360;
            }
            //test to see if the respective button is being pressed
            if (P1rotateL) {
                //adjust the angle of the stage correspondingly
                P1angle = (P1angle + (int) P1speed) % 360;
            }
            if (P1angle < 0) {
                P1angle = 360 + P1angle;
            }
            P1dy = P1speed * (Math.cos(Math.toRadians(P1angle)));
            P1dx = P1speed * (Math.sin(Math.toRadians(P1angle)));

            //test to see if the respective button is being pressed
            if (P1moveForward) {
                //adjust the position stage underneath player 1
                P1y = P1y - P1dy;
                P1x = P1x - P1dx;
            }
            //test to see if the respective button is being pressed
            if (P1moveBack) {
                //adjust the position stage underneath player 1
                P1y = P1y + P1dy;
                P1x = P1x + P1dx;
            }
            //test to see if the respective button is being pressed
            if (P1moveRight) {
                //adjust the position stage underneath player 1
                double P1dyr = P1speed * (Math.cos(Math.toRadians(P1angle + 90)));
                double P1dxr = P1speed * (Math.sin(Math.toRadians(P1angle + 90)));
                P1y = P1y + P1dyr;
                P1x = P1x + P1dxr;
            }
            //test to see if the respective button is being pressed
            if (P1moveLeft) {
                //adjust the position stage underneath player 1
                double P1dyl = P1speed * (Math.cos(Math.toRadians(P1angle - 90)));
                double P1dxl = P1speed * (Math.sin(Math.toRadians(P1angle - 90)));
                P1y = P1y + P1dyl;
                P1x = P1x + P1dxl;
            }
            //test to see if the bullet touches the border
            if ((stage.getRGB((int) P1bullets[0][0], (int) P1bullets[0][1]) == background.getRGB()
                    || stage.getRGB((int) P1bullets[1][0], (int) P1bullets[1][1]) == background.getRGB()
                    || stage.getRGB((int) P1bullets[2][0], (int) P1bullets[2][1]) == background.getRGB()
                    || stage.getRGB((int) P1bullets[3][0], (int) P1bullets[3][1]) == background.getRGB())) {
                //if so stop the bullet from moving
                P1revolverRifleBulletInMotion = false;
                P1blunderBussBulletsInMotion = false;
                //reset the variables for the next shot
                P1bullets[0][2] = 0;
                P1bullets[0][3] = 0;
                P1bullets[1][2] = 0;
                P1bullets[1][3] = 0;
                P1bullets[2][2] = 0;
                P1bullets[2][3] = 0;
                P1bullets[3][2] = 0;
                P1bullets[3][3] = 0;
            }



            //test to see after all of these transformations if P1 is standing on grey which  don't want him to be on
            if ((stage.getRGB((int) P1x + size, (int) P1y + 5) == background.getRGB()
                    || stage.getRGB((int) P1x + size, (int) P1y - 5) == background.getRGB()
                    || stage.getRGB((int) P1x - size, (int) P1y + 5) == background.getRGB()
                    || stage.getRGB((int) P1x - size, (int) P1y - 5) == background.getRGB())) {
                //if he is teleport him back to his previous position
                P1x = previousP1xPosition;
                P1y = previousP1yPosition;
            }
            //record the x and y position of P1 so I can teleport him back to this if he moves in the next game frame
            previousP1xPosition = P1x;
            previousP1yPosition = P1y;

            //player 2
            if (P2alive == false) {
                P2gunTimer = delayStart;
                P2alive = true;
            }
            if (P2gunTimer > 0) {
                P2gunTimer--;
                if ((P2changeWeapon == true) && (P2changeTimer == 0)) {
                    P2weapon++;
                    P2changeTimer = 30;
                    if (P2weapon == 3) {
                        P2weapon = 0;
                    }
                }
                if (P2changeTimer > 0) {
                    P2changeTimer--;
                }
            } else if (P2gunTimer == 0) {
                System.out.println("Respawn: " + System.currentTimeMillis());
                P2changeWeapon = false;

            }

            //test to see if the player is shooting the weapon and is the blunderbuss
            if ((P2shootWeapon) && P2weapon == 0 && P2alive) {
                P1alive = false;
                //test to see if the player is shooting the weapon and is the revolver rifle
            } else if ((P2shootWeapon) && P2weapon == 1 && P2alive) {
                P1alive = false;
                //test to see if the player is shooting the weapon and is the bear bomb
            } else if ((P2shootWeapon) && P2weapon == 2 && P2alive) {
                P1alive = false;
            }
            //test to see if the respective button is being pressed
            if (P2rotateR) {
                //adjust the angle of the stage correspondingly
                P2angle = (P2angle - 2) % 360;
            }
            //test to see if the respective button is being pressed
            if (P2rotateL) {
                //adjust the angle of the stage correspondingly
                P2angle = (P2angle + 2) % 360;
            }
            if (P2angle < 0) {
                P2angle = 360 + P2angle;
            }

            double P2dy = (Math.cos(Math.toRadians(P2angle)));
            double P2dx = (Math.sin(Math.toRadians(P2angle)));

            //test to see if the respective button is being pressed
            if (P2moveForward) {
                //adjust the position stage underneath player 2
                P2y = P2y - P2dy;
                P2x = P2x - P2dx;
            }
            //test to see if the respective button is being pressed
            if (P2moveBack) {
                //adjust the position stage underneath player 2

                P2y = P2y + P2dy;
                P2x = P2x + P2dx;
            }
            //test to see if the respective button is being pressed
            if (P2moveRight) {
                //adjust the position stage underneath player 2
                double P2dyr = (Math.cos(Math.toRadians(P2angle + 90)));
                double P2dxr = (Math.sin(Math.toRadians(P2angle + 90)));
                P2y = P2y + P2dyr;
                P2x = P2x + P2dxr;
            }
            //test to see if the respective button is being pressed
            if (P2moveLeft) {
                //adjust the position stage underneath player 2
                double P2dyl = (Math.cos(Math.toRadians(P2angle - 90)));
                double P2dxl = (Math.sin(Math.toRadians(P2angle - 90)));
                P2y = P2y + P2dyl;
                P2x = P2x + P2dxl;
            }
            //test to see after all of these transformations if P2 is standing on grey which  don't want him to be on
            if ((stage.getRGB((int) P2x + size, (int) P2y + 5) == background.getRGB()
                    || stage.getRGB((int) P2x + size, (int) P2y - 5) == background.getRGB()
                    || stage.getRGB((int) P2x - size, (int) P2y + 5) == background.getRGB()
                    || stage.getRGB((int) P2x - size, (int) P2y - 5) == background.getRGB())) {
                //if he is tele port him back to his previous position
                P2x = previousP2xPosition;
                P2y = previousP2yPosition;
            }
            //record the x and y position of P2 so I can teleport him back to this if he moves in the next game frame
            previousP2xPosition = P2x;
            previousP2yPosition = P2y;

            System.out.println("Player one weapon: " + P1weapon + " Player two weapon: " + P2weapon);
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
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_X) {
            //change the state of the boolean from false to true
            P1changeWeapon = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_C) {
            //change the state of the boolean from false to true
            P1shootWeapon = true;

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
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_M) {
            //change the state of the boolean from false to true
            P2changeWeapon = true;
        }
        //test to see if the key has been pressed
        if (key == KeyEvent.VK_N) {
            //change the state of the boolean from false to true
            P2shootWeapon = true;
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
        //test to see if the key has been released
        if (key == KeyEvent.VK_X) {
            //change the state of the boolean from true to false
            P1changeWeapon = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_C) {
            //change the state of the boolean from true to false
            P1shootWeapon = false;
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
        if (key == KeyEvent.VK_M) {
            //change the state of the boolean from true to false
            P2changeWeapon = false;
        }
        //test to see if the key has been released
        if (key == KeyEvent.VK_N) {
            //change the state of the boolean from true to false
            P2shootWeapon = false;
        }
    }
}
