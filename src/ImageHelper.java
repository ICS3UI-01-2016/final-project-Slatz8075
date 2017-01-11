
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author slatz8075
 */
public class ImageHelper {
    
    public static BufferedImage loadImage(String filename){
        BufferedImage img = null;
        try{
            File file = new File(filename);
            img = ImageIO.read(file);
        }catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }
}
