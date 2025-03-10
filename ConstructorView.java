import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ConstructorView extends JPanel implements Runnable, MouseListener, MouseMotionListener{
    int screenWidth = 800;
    int screenHeight = 600;
    int meshCols = 16;
    int meshRows = 28;
    int meshBlockSize = 20;
    int elementsBarBlockSize = 104;
    boolean cursorOnMesh = false;
    boolean inHandRocketEl = false;
    long start = 0;
    long finish = 0;
    long timeElapsed = 0;

    CardLayout cl;
    JPanel panelCont;
    PlaygroundView pv = null;

    RocketElement[] elementsBar = {
        new RocketElement(40, 80, "/img/fuelTank.png"),
        new RocketElement(40, 80, "/img/fuelTank2.png"),
        new RocketElement(40, 40, "/img/engine.png"),
        new RocketElement(40, 40, "/img/capsule.png")
    };

    RocketElement inHand = null;
    Rocket rocket = new Rocket();

    Thread gameThread;

    public ConstructorView() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(50, 145, 200));
        this.setDoubleBuffered(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setFocusable(true);
        
    }

    public void startGameThread() {
        this.pv.rocket = rocket;
        gameThread = new Thread(this);
        gameThread.start();
        
    }

    public void run() {
        start = System.currentTimeMillis();
        
        while(gameThread != null) {

            
            //if((finish - start) % 41 == 0) {
                update();
            //}

            //if((finish - start) % 16 == 0) {
                repaint();
            //}

             
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            //finish = System.currentTimeMillis();
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
    
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.lightGray);

        g2.fillRect(0, 0, elementsBarBlockSize, screenHeight);

        for(int i=0; i < elementsBar.length; i++) {
            elementsBar[i].x = 2;
            elementsBar[i].y = 2 + elementsBarBlockSize*i;
            elementsBar[i].draw(g2);
        }

        g2.setColor(Color.lightGray);

        for(int y=0; y < meshRows; y++) {
            for(int x=0; x < meshCols; x++) {
                g2.fillRect((screenWidth/2)-(meshCols/2)*meshBlockSize+x*meshBlockSize, (screenHeight/2)-(meshRows/2)*meshBlockSize+y*meshBlockSize, meshBlockSize-2, meshBlockSize-2);
            }
        }

        rocket.draw(g2);

        if(inHand != null) {
            inHand.draw(g2);
        }

        //this.add(b);

        
        g2.setColor(Color.WHITE);

        g2.fillRect(screenWidth-100, screenHeight-50, 100, 50);
        g2.setColor(Color.BLACK);
        g2.drawString("test", screenWidth-50, screenHeight-25);
        
        g2.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        int x = e.getX();
        int y = e.getY();

        if(inHand != null) {

            if(x > (screenWidth/2)-(meshCols/2)*meshBlockSize && x < (screenWidth/2)+(meshCols/2)*meshBlockSize && y > (screenHeight/2)-(meshRows/2)*meshBlockSize && y < (screenHeight/2)+(meshRows/2)*meshBlockSize) {
                inHand.x = (screenWidth/2-meshCols/2*meshBlockSize) + ((x-(screenWidth/2-meshCols/2*meshBlockSize))/meshBlockSize)*meshBlockSize-1;
                inHand.y = (screenHeight/2-meshRows/2*meshBlockSize) + ((y-(screenHeight/2-meshRows/2*meshBlockSize))/meshBlockSize)*meshBlockSize-1;
                cursorOnMesh = true;
            } else {
                inHand.x = x;
                inHand.y = y;
                cursorOnMesh = false;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(inHand == null) {
            int x = e.getX();
            int y = e.getY();
            
            if(x > 0 && x < elementsBarBlockSize) {
                inHand = elementsBar[y/elementsBarBlockSize].cloneObj();
                inHand.x = x;
                inHand.y = y;
            } else if (x > (screenWidth/2)-(meshCols/2)*meshBlockSize && x < (screenWidth/2)+(meshCols/2)*meshBlockSize && y > (screenHeight/2)-(meshRows/2)*meshBlockSize && y < (screenHeight/2)+(meshRows/2)*meshBlockSize) {
                for(int i=0; i<rocket.elementsID; i++) {
                    if (x > rocket.elements[i].x && x < rocket.elements[i].x+rocket.elements[i].width && y > rocket.elements[i].y && y < rocket.elements[i].y + rocket.elements[i].height) {
                        inHand = rocket.GetElement(rocket.elements[i]);
                        inHandRocketEl = true;
                        break;
                    }
                }
            } else if (x > screenWidth - 100 && y > screenHeight - 50) {
                rocket.height = 0;
                rocket.width = 0;
                offsetY = screenHeight;
                offsetX = screenWidth;

                for(int i=0; i<rocket.elementsID; i++) {
                    rocket.height += rocket.elements[i].height;
                    //rocket.width += rocket.elements[i].width;
                    if(rocket.elements[i].y < offsetY) {
                        offsetY = rocket.elements[i].y;
                    }
                    if(rocket.elements[i].x < offsetX) {
                        offsetX = rocket.elements[i].x;
                        rocket.width = rocket.elements[i].width;
                    }
                }

                rocket.y = offsetY;
                rocket.x = offsetX;

                //System.out.printf("%d, %d" , offsetX, offsetY);
                offsetY += rocket.height;
                offsetY = screenHeight - 100 - offsetY;
                rocket.y += offsetY;


                for(int i=0; i<rocket.elementsID; i++) {
                rocket.elements[i].y += offsetY;
                }

                this.setVisible(false);
                pv.setVisible(true);
                pv.requestFocus();
            }
        }
    }

    int rocketHeight;
    int offsetY;
    int offsetX;

    @Override
    public void mouseClicked(MouseEvent e) {
        
        

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (inHand != null) {
            if(cursorOnMesh && !inHandRocketEl) {
                rocket.AddElement(inHand);
                cursorOnMesh = false;
            }else if (!cursorOnMesh && inHandRocketEl) {
                rocket.RemoveElement(inHand);
            }
            inHand = null;
            inHandRocketEl = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
