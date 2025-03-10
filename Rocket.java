import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Rocket {
    String name;
    RocketElement elements[] = new RocketElement[15];
    int elementsID = 0;
    int x = 0;
    int y = 0;
    double xAcceleration = 0;
    double yAcceleration = 0;
    int height;
    int width;
    
    public void AddElement(RocketElement re) {
        this.elements[elementsID] = re;
        this.elementsID++;
        /* 
        System.out.println("Elements in rocket: ");
        for(int i=0; i<=elementsID-1; i++) {
            System.out.printf("%d: ", i);
            System.out.println(elements[i]);
        }
        */
    }

    public RocketElement GetElement(RocketElement re) {
        return re;
    }

    public void RemoveElement(RocketElement re) {
        for(int i = 0; i<elementsID; i++) {
            if(this.elements[i] == re) {
                this.elements[i] = null;
                this.elementsID--;
                this.elements[i] = this.elements[elementsID];
                this.elements[elementsID] = null;
                break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        
        for(int i=0; i<elementsID; i++) {
            /*int width = this.elements[i].image.getWidth();
            int height = this.elements[i].image.getHeight();
    
            // Creating a new buffered image
            BufferedImage newImage = new BufferedImage(
                this.elements[i].image.getWidth(), this.elements[i].image.getHeight(), this.elements[i].image.getType());
    
            // creating Graphics in buffered image
            //Graphics2D g3 = newImage.createGraphics();
    
            // Rotating image by degrees using toradians()
            // method
            // and setting new dimension t it
            g2.rotate(Math.toRadians(20), width / 2,
                    height / 2);
            g2.drawImage(this.elements[i].image, null, 0, 0);
    
            // Return rotated buffer image
            this.elements[i].image = newImage;*/
            this.elements[i].draw(g2);
        }

    }

}
