import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI extends JPanel implements ActionListener { //a GUI version of Game Class.
    private JButton[][] buttons = new JButton[8][8];
    private Board board;
    private ArrayList<int[]> selections;
    private HashMap<JButton, ChessPiece> boardConnector;
    private JFrame f;
    private JPanel GUIboard;
    private boolean whitesTurn;
    private PieceMoves pm;
    private boolean check = false;
    private ArrayList<ChessPiece> whiteCaptures;
    private ArrayList<ChessPiece> blackCaptures;

    public GUI() {
        boardConnector = new HashMap<>();
        selections = new ArrayList<>();
        board = new Board();
        board.setPositions();
        pm = new PieceMoves();
        whitesTurn = true;

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

        JButton flip = new JButton(new AbstractAction("flip") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board.flipBoard();
                Board.reInitialize();
                Board.scanPositions();
                updateBoard();
            }
        });

        flip.setBounds(900, 900, 20, 20);
        flip.setVisible(true);

        f = new JFrame();//creating instance of JFrame
        f.add(GUIboard);//adding button in JFrame
//        f.add(button);
//        button.setBackground(new Color(225, 197, 158));

        f.add(flip);

        f.setSize(1920,1080);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void updateBoard() {
        GUIboard.removeAll();
        Board.printBoard(new ArrayList<ChessPiece>(), new ArrayList<ChessPiece>());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                buttons[i][j] = new JButton(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if (selections.size() == 0 && boardConnector.get(button) == null) return;

                        for (int i = 0; i < buttons.length; i++) {
                            for (int j = 0; j < buttons[i].length; j++) {
                                if (buttons[i][j].equals(button)) {
                                    if (selections.size() == 0 && Board.getPieces()[i][j] != null) {
                                        String color = Board.getPieces()[i][j].getColor();

                                        if ((color.equals("black") && whitesTurn)
                                                || (color.equals("white") && !whitesTurn)) return;
                                    }

                                    selections.add(new int[]{i, j});
                                    if (selections.size() == 1) System.out.println("Select: (" + selections.get(0)[0] + "," + selections.get(0)[1] + ")");
                                    if (selections.size() == 2) System.out.println("Move: (" + selections.get(1)[0] + "," + selections.get(1)[1] + ")");
                                }
                            }
                        }

                        if (selections.size() == 1) {
                            ChessPiece piece = Board.getPieces()[selections.get(0)[0]][selections.get(0)[1]];
                            int[] currentPosition = piece.getCurrentPosition();
                            switch (piece.getName()) {
                                case "♔":
                                case "♚":
                                    ((King) piece).scanning(); //start scanning
                                    piece.move(currentPosition, null);
                                    ((King) piece).scanning(); //stop scanning
                                    break;
                                case "♖":
                                case "♜":
                                    ((Rook) piece).scanning(); //start scanning
                                    piece.move(currentPosition, null);
                                    ((Rook) piece).scanning(); //stop scanning
                                    break;
                                case "♙":
                                case "♟":
                                    ((Pawn) piece).scanning(); //start scanning
                                    piece.move(currentPosition, null);
                                    ((Pawn) piece).scanning(); //stop scanning
                                    break;
                                default:
                                    piece.move(currentPosition, null);
                                    break;
                            }
                            ArrayList<int[]> availablePositions = piece.getAvailablePositions();

                            for (int[] availablePosition : availablePositions) {
                                int r = availablePosition[0];
                                int c = availablePosition[1];
                                buttons[r][c].setBackground(Color.red);
                            }
                        }

                        if (selections.size() == 2) {
                            int r = selections.get(0)[0];
                            int c = selections.get(0)[1];
                            int moveR = selections.get(1)[0];
                            int moveC = selections.get(1)[1];

                            if (check) Board.saveCurrentBoard();

                            if (Board.getPieces()[r][c].move(selections.get(1), new ArrayList<ChessPiece>())) {
                                Board.reInitialize();
                                Board.scanPositions();

                                if (check) {
                                    if (whitesTurn) {
                                        int kingRow = Board.getWhiteKing()[0];
                                        int kingColumn = Board.getWhiteKing()[1];

                                        if (Board.getBoardScanner()[kingRow][kingColumn].isBlackMove()) {
                                            Board.revertToPreviousBoard();
                                            Board.printBoard(new ArrayList<ChessPiece>(), new ArrayList<ChessPiece>());
                                            whitesTurn = !whitesTurn;
                                        }
                                    } else {
                                        int kingRow = Board.getBlackKing()[0];
                                        int kingColumn = Board.getBlackKing()[1];

                                        if (Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove()) {
                                            Board.revertToPreviousBoard();
                                            whitesTurn = !whitesTurn;
                                        }
                                    }
                                }

                                boardConnector.put(buttons[r][c], Board.getPieces()[r][c]);
                                boardConnector.put(buttons[moveR][moveC], Board.getPieces()[moveR][moveC]);
                                //updating hashmap values
                                whitesTurn = !whitesTurn;

                                check();
                                f.repaint();
                            }

                            updateBoard();
                            selections.clear();
                        }
                    }
                });
                JButton b = buttons[i][j];

                if (Board.getPieces()[i][j] == null) boardConnector.put(b, null);
                else boardConnector.put(b,Board.getPieces()[i][j]);

                if (!Board.isFlipped()) {
                    if ((i+j)%2==0) b.setBackground(new Color(225, 197, 158));
                    else b.setBackground(new Color(101, 48, 17));
                } else {
                    if ((i+j)%2==0) b.setBackground(new Color(101, 48, 17));
                    else b.setBackground(new Color(225, 197, 158));
                }

                if(!(Board.getPieces()[i][j] == null)) {
                    String s = "";
                    if (Board.getPieces()[i][j].getColor().equals("white")) {
                        switch(Board.getPieces()[i][j].getName()) {
                            case "♔":
                                if (Board.getBoardScanner()[Board.getWhiteKing()[0]][Board.getWhiteKing()[1]].isBlackMove()) b.setBackground(new Color(148,0,211));
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
                                if (Board.getBoardScanner()[Board.getBlackKing()[0]][Board.getBlackKing()[1]].isWhiteMove()) b.setBackground(new Color(148,0,211));
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

                GUIboard.add(b);
                b.addActionListener(this);
//                b.setRolloverIcon(); //for hovering over the buttons
//                b.setPressedIcon();
            }
        }
        GUIboard.revalidate();
        GUIboard.repaint();
    }

    //checks for check, checkmate, and stalemate!
    private void check() {

        //must check stalemate, if the king is not in check and there are no more available moves, then it is stalemate!
        if (whitesTurn) {
            int kingRow = Board.getWhiteKing()[0];
            int kingColumn = Board.getWhiteKing()[1];

            if (Board.getBoardScanner()[kingRow][kingColumn].isBlackMove()) {

                if (!pm.legalMoveAvailable(whitesTurn)) {
                    System.out.println("Checkmate");
                    return;
                }
                check = true;
            } else {
                check = false;
            }
        } else {
            int kingRow = Board.getBlackKing()[0];
            int kingColumn = Board.getBlackKing()[1];


            if (Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove()) {

                if (!pm.legalMoveAvailable(whitesTurn)) {
                    System.out.println("Checkmate");
                    return;
                }
                check = true;
            } else {
                check = false;
            }
        }

    }
}
