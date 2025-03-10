import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

public class PlaygroundView extends JPanel implements Runnable, MouseListener, KeyListener{
    final int screenWidth = 800;
    final int screenHeight = 600;
    int earth = 100;
    double earthAcceleration = 0;
    double heightMarker = screenHeight - 20 - 5;
    boolean engineOn = false;
    int x=0;
    int y=0;
    double direction=0;
    double rads=0;

    ConstructorView cv = null;
    Rocket rocket = null;
    
    AffineTransform at = new AffineTransform();

    //KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public PlaygroundView() {

        //this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "goUp");
        //this.getActionMap().put("goUp", )
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(50, 145, 200));
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.setFocusable(true);

        
        // The angle of the rotation in radians
    }

    public void startGameThread() {

        



        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        while(gameThread != null) {

            //System.out.println("test");
            update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            repaint();
        }
    }

    public void update() {
        if(earth < 100 && engineOn==false) {
            rocket.yAcceleration -= 0.1;
            //earth += rocket.yAcceleration;
            //heightMarker += earthAcceleration*0.1;
        }
        
        if(earth - rocket.yAcceleration > 100) {
            earth = 100;
            earthAcceleration = 0;
            rocket.yAcceleration = 0;
        }
        
        earth -= rocket.yAcceleration;
        heightMarker = screenHeight + earth*0.01 - 25;
        //System.out.println(rocket.yAcceleration);
        
        if(heightMarker < 20) {
            heightMarker = 20;
        }

        rads = Math.toRadians(direction);
        at.rotate(rads, rocket.x+(rocket.width/2), rocket.y+(rocket.height/2));
    }

    public void paintComponent(Graphics g) {
    
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.GREEN);

        if(earth > 0) {
            g2.fillRect(0, screenHeight-earth, screenWidth, 100);
        }
        
        g2.setColor(Color.lightGray);
        g2.fillRect(screenWidth - 40, 20, 5, screenHeight - 40);
        g2.setColor(Color.red);
        g2.fillRect(screenWidth - 45, (int)heightMarker, 15, 5);

        g2.setTransform(at);
        rocket.draw(g2);

        g2.setColor(Color.red);
        g2.fillRect(rocket.x+(rocket.width/2), rocket.y+(rocket.height/2),5, 5);

        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i=0; i<rocket.elementsID; i++) {
            rocket.elements[i].y -= cv.offsetY;
        }
        
        this.setVisible(false);
        cv.setVisible(true);
        cv.requestFocus();
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    public void goUp() {
        //earth -= 10;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.println(code);
        if(code == 38) {
            rocket.yAcceleration += 0.2;
            engineOn = true;
        } else if(code == 37) {
            direction--;
            //x += Math.sin(Math.toRadians(direction));
            //y += Math.cos(Math.toRadians(direction));
        } else if (code  == 39) {
            direction++;
            //x += Math.sin(Math.toRadians(direction));
        //y += Math.cos(Math.toRadians(direction));
        }

        
        
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        //System.out.println(code);
        if(code == 38) {
            //rocket.yAcceleration -= earthAcceleration;
            engineOn = false;
        }
        direction = 0;
    }
}
