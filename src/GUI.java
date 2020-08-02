import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JPanel implements ActionListener {

    public GUI() {

        Board board = new Board();
        board.setPositions();

//        ImageIcon knight = new ImageIcon("images/BlackKnight.png");
//        Image img = knight.getImage();
//
//        Image newimg = img.getScaledInstance(70,70,0);
        JPanel GUIboard = new JPanel();
        GUIboard.setSize(800,800);
        GUIboard.setLayout(new GridLayout(8,8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton b = new JButton();

                if(!(Board.getPieces()[i][j] == null)) {
                    String s = "";
                    if (Board.getPieces()[i][j].getColor().equals("white")) {
                        switch(Board.getPieces()[i][j].getName()) {
                            case "♔":
                                s = "images/WhiteKing.png";
                                break;
                            case "♕":
                                s = "images/WhiteQueen.png";
                                break;
                            case "♖":
                                s = "images/WhiteRook.png";
                                break;
                            case "♗":
                                s = "images/WhiteBishop.png";
                                break;
                            case "♘":
                                s = "images/WhiteKnight.png";
                                break;
                            case "♙":
                                s = "images/WhitePawn.png";
                                break;
                        }
                    } else {
                        switch (Board.getPieces()[i][j].getName()) {
                            case "♚":
                                s = "images/BlackKing.png";
                                break;
                            case "♛":
                                s = "images/BlackQueen.png";
                                break;
                            case "♜":
                                s = "images/BlackRook.png";
                                break;
                            case "♝":
                                s = "images/BlackBishop.png";
                                break;
                            case "♞":
                                s = "images/BlackKnight.png";
                                break;
                            case "♟":
                                s = "images/BlackPawn.png";
                                break;
                        }
                    }
                    b.setIcon(new ImageIcon(s));
                }
                if ((i+j)%2==0) b.setBackground(new Color(225, 197, 158));
                else b.setBackground(new Color(101, 48, 17));
                GUIboard.add(b);
                b.addActionListener(this);
//                b.setRolloverIcon(); //for hovering over the buttons
//                b.setPressedIcon();
            }
        }

//        JButton button = new JButton();
//        button.setBounds(0,0,70,70);
//        button.setIcon(new ImageIcon(newimg));


        JFrame f = new JFrame();//creating instance of JFrame
        f.add(GUIboard);//adding button in JFrame
//        f.add(button);
//        button.setBackground(new Color(225, 197, 158));

        f.setSize(1920,1080);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked");
    }
}
