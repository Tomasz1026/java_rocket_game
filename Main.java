import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main{
    static JButton b = new JButton("test");
    public static void main(String[] args) throws InterruptedException {
        CardLayout cl = new CardLayout();
        JFrame window = new JFrame();
        JPanel panelCont = new JPanel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Rocket builder");
        
        panelCont.setLayout(cl);

        PlaygroundView pv = new PlaygroundView();
        ConstructorView cv = new ConstructorView();
        
        cv.pv = pv;
        pv.cv = cv;

        panelCont.add(cv, "1");
        panelCont.add(pv, "2");
        
        cl.show(panelCont, "1");

        window.add(panelCont);  

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //gamePanel.setVisible(true);
        //cv.setVisible(false);
        //cv.cl = cl;
        //cv.panelCont = panelCont;
        cv.startGameThread();
        //pv.cl = cl;
        //pv.panelCont = panelCont;
        pv.startGameThread();
        
        
        //cv.add(b);
    }
}