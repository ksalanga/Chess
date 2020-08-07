import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI extends JPanel implements ActionListener {
    private JButton[][] buttons = new JButton[8][8];
    private Board board;
    private ArrayList<int[]> selections;
    private HashMap<JButton, ChessPiece> boardConnector;
    private JFrame f;
    private JPanel GUIboard;

    public GUI() {

        boardConnector = new HashMap<>();
        selections = new ArrayList<>();
        board = new Board();
        board.setPositions();

//        ImageIcon knight = new ImageIcon("images/BlackKnight.png");
//        Image img = knight.getImage();
//
//        Image newimg = img.getScaledInstance(70,70,0);
        GUIboard = new JPanel();
        GUIboard.setSize(800,800);
        GUIboard.setLayout(new GridLayout(8,8));

        updateBoard();

//        JButton button = new JButton();
//        button.setBounds(0,0,70,70);
//        button.setIcon(new ImageIcon(newimg));


        f = new JFrame();//creating instance of JFrame
        f.add(GUIboard);//adding button in JFrame
//        f.add(button);
//        button.setBackground(new Color(225, 197, 158));

        f.setSize(1920,1080);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (selections.size() == 0 && boardConnector.get(button) == null) return;
        //if the first selection isn't a piece, then we
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].equals(button)) {
                    selections.add(new int[]{i, j});
                    if (selections.size() == 1) System.out.println("(" + selections.get(0)[0] + "," + selections.get(0)[1] + ")");
                    if (selections.size() == 2) System.out.println("(" + selections.get(1)[0] + "," + selections.get(1)[1] + ")");
                }
            }
        }

        if (selections.size() == 2) {
            int r = selections.get(0)[0];
            int c = selections.get(0)[1];
            int moveR = selections.get(1)[0];
            int moveC = selections.get(1)[1];


            if (Board.getPieces()[r][c].move(selections.get(1), new ArrayList<ChessPiece>())) {
                updateBoard();
                boardConnector.put(buttons[r][c], Board.getPieces()[r][c]);
                boardConnector.put(buttons[moveR][moveC], Board.getPieces()[moveR][moveC]);
                //updating hashmap values
                f.repaint();
            }

            selections.clear();
        }

    }

    private void updateBoard() {
        GUIboard.removeAll();
        Board.printBoard(new ArrayList<ChessPiece>(), new ArrayList<ChessPiece>());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                buttons[i][j] = new JButton();
                JButton b = buttons[i][j];

                if (Board.getPieces()[i][j] == null) boardConnector.put(b, null);
                else boardConnector.put(b,Board.getPieces()[i][j]);

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
        GUIboard.revalidate();
        GUIboard.repaint();
    }
}
