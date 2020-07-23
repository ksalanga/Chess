import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JPanel implements MouseListener {

    public static void main(String args[]) {

        //one way of doing the GUI is with Applet which makes use of an HTML file,
        //Another way is simply with GUI swing, and then we can just run all of the files in a single main method
        System.out.println("Let's Flippin' Go Karthy!!!");
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
