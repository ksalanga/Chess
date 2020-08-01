import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridLayout;

public class GUI extends JPanel implements ActionListener {

    private Tile[][] t;

    public GUI() {
        Board board = new Board();

        Tile[][] t = new Tile[8][8];

        JPanel jp = new JPanel();
        jp.setSize(800,800);
        jp.setLayout(new GridLayout(8,8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                t[i][j] = new Tile(Board.getPieces()[i][j]);
                JButton b = new JButton();
                if ((i+j)%2==0) b.setBackground(Color.white);
                else b.setBackground(Color.black);
                jp.add(b);
                b.addActionListener(this);
//                b.setRolloverIcon(); //for hovering over the buttons
//                b.setPressedIcon();
            }
        }


        JFrame f = new JFrame();//creating instance of JFrame
        f.add(jp);//adding button in JFrame

        f.setSize(1920,1080);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked");
    }
}
