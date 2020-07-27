import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Grid;

public class GUI extends JPanel implements MouseListener {

    public static void main(String args[]) {

        JPanel jp = new JPanel();
        jp.setSize(800,800);
        jp.setLayout(new GridLayout(8,8));

        JFrame f = new JFrame();//creating instance of JFrame
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton b = new JButton();
                if ((i+j)%2==0) b.setBackground(Color.white);
                else b.setBackground(Color.black);
                jp.add(b);

            }
        }
        //DOT setbounds can do this for JPanel.
        

        //if we have box we dont have to do the x y for this.

        f.add(jp);//adding button in JFrame

        f.setSize(1920,1080);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }

    public GUI() {
        addMouseListener(this);

    }

    private void addMouseListener(GUI gui) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
}
