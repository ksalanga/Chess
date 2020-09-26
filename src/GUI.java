import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI extends JPanel implements ActionListener { //a GUI version of Game Class.
    private JButton[][] buttons = new JButton[8][8];
    private ArrayList<int[]> selections;
    private HashMap<JButton, ChessPiece> boardConnector;
    private JFrame f;
    private JPanel GUIboard;
    private JScrollPane gameHistory;
    private boolean whitesTurn;
    private PieceMoves pm;
    private boolean check = false;
    ChessPiece[][] currentBoard;
    private ArrayList<ChessPiece> whiteCaptures = new ArrayList<>();
    private ArrayList<ChessPiece> blackCaptures = new ArrayList<>();
    private JScrollPane wCaptures;
    private JScrollPane bCaptures;
    private int selection = 0;

    public GUI() {
        currentBoard = Board.getPieces();
        boardConnector = new HashMap<>();
        selections = new ArrayList<>();
        Board board = new Board();
        board.setPositions();
        pm = new PieceMoves();
        whitesTurn = true;
        wCaptures = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bCaptures = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

//        ImageIcon knight = new ImageIcon("images/BlackKnight.png");
//        Image img = knight.getImage();
//
//        Image newimg = img.getScaledInstance(70,70,0);
        GUIboard = new JPanel();
        gameHistory = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        GUIboard.setSize(new Dimension(800,800));
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
                if (whitesTurn) Board.scanBlackAttacks();
                else Board.scanWhiteAttacks();
                updateBoard();
            }
        });

        flip.setBounds(300, 800, 200, 110);
        flip.setVisible(true);
        flip.setBackground(Color.black);
        flip.setIcon(new ImageIcon("images/flip.png"));

        f = new JFrame();//creating instance of JFrame
        f.add(GUIboard);//adding button in JFrame
//        f.add(button);
//        button.setBackground(new Color(225, 197, 158));
        f.add(wCaptures);
        f.add(bCaptures);
        f.add(flip);
        f.add(gameHistory);
        f.setSize(1920,1080);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void updateBoard() {
        boolean isCurrentBoard = Board.getGameHistory().size() == selection;

        if (isCurrentBoard) {
            System.out.println("CB");
            currentBoard = Board.getPieces();
            boolean legalMoveAvailable = pm.legalMoveAvailable(whitesTurn);
            if(!legalMoveAvailable && check) System.out.println("checkmate :)");
            if (!legalMoveAvailable && !check) System.out.println("stalemate :)");
        }
        else currentBoard = Board.getGameHistory().get(selection);

        GUIboard.removeAll();
        Board.printBoard(new ArrayList<ChessPiece>(), new ArrayList<ChessPiece>());

        //Board Panel
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton();
                JButton b = buttons[i][j];

                if (isCurrentBoard) {
                    b.setAction(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton button = (JButton) e.getSource();
                            if (selections.size() == 0 && boardConnector.get(button) == null
                                    || (boardConnector.get(button) != null && selections.size() == 0 && boardConnector.get(button).getAvailablePositions().size() == 0)) return;

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
                                ArrayList<int[]> availablePositions = piece.getAvailablePositions();

                                if (availablePositions == null) System.out.println(piece.getName() + "nullers");
                                else {
                                    for (int[] availablePosition : availablePositions) {
                                        int r = availablePosition[0];
                                        int c = availablePosition[1];
                                        buttons[r][c].setBackground(Color.red);
                                    }
                                }
                            }

                            if (selections.size() == 2) {
                                int r = selections.get(0)[0];
                                int c = selections.get(0)[1];
                                int moveR = selections.get(1)[0];
                                int moveC = selections.get(1)[1];

                                if (Board.getPieces()[r][c].move(selections.get(1), whitesTurn ? whiteCaptures : blackCaptures)) {
                                    boardConnector.put(buttons[r][c], Board.getPieces()[r][c]);
                                    boardConnector.put(buttons[moveR][moveC], Board.getPieces()[moveR][moveC]);
                                    //updating hashmap values
                                    whitesTurn = !whitesTurn;

                                    selection++;
                                    Board.addToGameHistory();
                                    check();
                                    f.repaint();
                                }
                                updateBoard();
                                selections.clear();
                            }
                        }
                    });

                    if (Board.getPieces()[i][j] == null) boardConnector.put(b, null);
                    else boardConnector.put(b,Board.getPieces()[i][j]);
                }


                if (!Board.isFlipped()) {
                    if ((i+j)%2==0) b.setBackground(new Color(225, 197, 158));
                    else b.setBackground(new Color(101, 48, 17));
                } else {
                    if ((i+j)%2==0) b.setBackground(new Color(101, 48, 17));
                    else b.setBackground(new Color(225, 197, 158));
                }

                if(!(currentBoard[i][j] == null)) {
                    b.setIcon(getImage(currentBoard[i][j], b));
                }

                GUIboard.add(b);
                b.addActionListener(this);
//                b.setRolloverIcon(); //for hovering over the buttons
//                b.setPressedIcon();
            }
        }

        //GameHistory Panel
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < Board.getGameHistory().size(); i++) {
            JButton b = new JButton(Integer.toString(i));
            b.getAccessibleContext().setAccessibleName(Integer.toString(i));

            b.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonName = button.getAccessibleContext().getAccessibleName();
                    selection = Integer.parseInt(buttonName);
                    updateBoard();
                }
            });
            b.setSize(100, 100);
            b.setVisible(true);
            p.add(b);
        }

        gameHistory.setBounds(1600, 200, 300, 300);
        gameHistory.setViewportView(p);

        //Captures Panel

        if(!Board.isFlipped()) {
            wCaptures.setBounds(800, 0, 137, 400);
            bCaptures.setBounds(800, 400, 137, 400);
        } else {
            bCaptures.setBounds(800, 0, 137, 400);
            wCaptures.setBounds(800, 400, 137, 400);
        }

        JPanel wCapturesPanel = new JPanel();
        JPanel bCapturesPanel = new JPanel();
        wCapturesPanel.setLayout(new BoxLayout(wCapturesPanel, BoxLayout.PAGE_AXIS));
        wCapturesPanel.setSize(100, 400);
        bCapturesPanel.setLayout(new BoxLayout(bCapturesPanel, BoxLayout.PAGE_AXIS));
        bCapturesPanel.setSize(100, 400);

        for (ChessPiece piece : whiteCaptures) {
            JButton caps = new JButton();

            caps.setSize(100, 100);
            caps.setBackground(Color.white);
            caps.setIcon(getImage(piece, caps));
            caps.setVisible(true);
            wCapturesPanel.add(caps);
        }

        for (ChessPiece piece : blackCaptures) {
            JButton caps = new JButton();

            caps.setSize(100, 100);
            caps.setBackground(Color.black);
            caps.setIcon(getImage(piece, caps));
            caps.setVisible(true);
            bCapturesPanel.add(caps);
        }

        wCaptures.setViewportView(wCapturesPanel);
        bCaptures.setViewportView(bCapturesPanel);

        GUIboard.revalidate();
        GUIboard.repaint();
    }

    //checks for check, checkmate, and stalemate!
    private void check() {
        Board.reInitialize();
        //must check stalemate, if the king is not in check and there are no more available moves, then it is stalemate!
        if (whitesTurn) {
            int kingRow = Board.getWhiteKing()[0];
            int kingColumn = Board.getWhiteKing()[1];

            Board.scanBlackAttacks();
            check = Board.getBoardScanner()[kingRow][kingColumn].isBlackMove();
        } else {
            int kingRow = Board.getBlackKing()[0];
            int kingColumn = Board.getBlackKing()[1];

            Board.scanWhiteAttacks();
            check = Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove();
        }
    }

    private ImageIcon getImage(ChessPiece piece, JButton b) {
        String s = "";
        if (piece.getColor().equals("white")) {
            switch(piece.getName()) {
                case "♔":
                    if ((currentBoard == Board.getPieces()) && (Board.getBoardScanner()[Board.getWhiteKing()[0]][Board.getWhiteKing()[1]].isBlackMove())) b.setBackground(new Color(148,0,211));
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
            switch (piece.getName()) {
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

        return new ImageIcon(s);
    }
}