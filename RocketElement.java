import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RocketElement {
    int x;
    int y;
    int width;
    int height;
    public BufferedImage image;
    String imagePath;

    public RocketElement(int width, int height, String imagePath) {
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        this.getImage();
    }
    
    public void draw(Graphics2D g2) {

        
        g2.drawImage(this.image, this.x, this.y, this.width, this.height, null);

    }

    public RocketElement cloneObj() {
        RocketElement reClone = new RocketElement(this.width, this.height, this.imagePath);
        return reClone;
    }

    public void getImage() {
        
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
}
