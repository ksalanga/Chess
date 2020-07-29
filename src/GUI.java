import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.GridLayout;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class GUI extends JPanel implements MouseListener, ActionListener {

    public GUI() {
        JPanel jp = new JPanel();
        jp.setSize(800,800);
        jp.setLayout(new GridLayout(8,8));

        JFrame f = new JFrame();//creating instance of JFrame
        f.add(jp);//adding button in JFrame

        f.setSize(1920,1080);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton b = new JButton();
                if ((i+j)%2==0) b.setBackground(Color.white);
                else b.setBackground(Color.black);
                jp.add(b);
                b.addActionListener(this);
            }
        }
    }

    private void addMouseListener(GUI gui) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Click");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Click1");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Click2");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Click3");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Click4");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked");
    }
}
