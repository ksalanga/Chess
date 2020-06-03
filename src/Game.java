import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private ArrayList<ChessPiece> whiteCaptures;
    private ArrayList<ChessPiece> blackCaptures;
    private HashMap<ChessPiece, int[]> piecePositions;
    private Board board;
    private boolean end;

    public Game() {
        //start of the game
        board = new Board();
        board.setPositions();
        whiteCaptures = new ArrayList<>();
        blackCaptures = new ArrayList<>();
    }

    public void start() {
        Scanner s = new Scanner(System.in);
        boolean whitesTurn = true;
        ChessPiece[][] pieces = board.getPieces();
        //put captures ArrayList when a piece moves.
        while (!end) {
            board.printBoard(blackCaptures, whiteCaptures);
            System.out.println();
            System.out.print(whitesTurn ? "(White ♙) Select a piece: " : "(Black ♟) " + "Select a piece: ");
            String selection = s.nextLine();
            int [] selectedTile = board.convertToCoords(selection);
            int r = selectedTile[0];
            int c = selectedTile[1];

            if (!outOfBounds(r, c, selectedTile, whitesTurn)) { //checks if out of bounds
                System.out.print("Move the piece: ");
                selection = s.nextLine();
                selectedTile = board.convertToCoords(selection);
                int rInput = selectedTile[0];
                int cInput = selectedTile[1];
                if (!(rInput < 0 || cInput < 0)) { //inBounds
                    if (!pieces[r][c].move(selectedTile, whitesTurn ? whiteCaptures : blackCaptures)) {
                        System.out.println("Invalid Input");
                        whitesTurn = !whitesTurn;
                    }
                } else {
                    System.out.println("Out of bounds");
                    whitesTurn = !whitesTurn;
                }
            } else {
                whitesTurn = !whitesTurn; //gives the position back to the person who didn't type the write input
            }

            check(whitesTurn ? "black" : "white"); //checks if the opposite king is in check

            whitesTurn = !whitesTurn;
            System.out.println();
        }
    }

    private boolean outOfBounds(int r, int c, int[] selectedTiles, boolean whitesTurn) {
        boolean flag = false;
        if (r < 0 || c < 0) { //checks if out of bounds
            System.out.println("Out of bounds");
            flag = true;
        } else if (Board.getPieces()[r][c] == null) {
            System.out.println("You Selected a tile, select a piece");
            flag = true;
        } else if ((!whitesTurn && Board.getPieces()[r][c].getColor().equals("white")) || (whitesTurn && Board.getPieces()[r][c].getColor().equals("black"))) {
            System.out.println("Choose the right color");
            flag = true;
        }
        return flag;
    }

    private void check(String color) {
        //initialize boardScanner

        Board.reInitialize();

        if (color.equals("white"))  { //if the white king is in check
            Board.scanPositions("black"); //checks possible positions for black to attack the king
            int r = Board.getWhiteKing()[0];
            int c = Board.getWhiteKing()[1];

            if (Board.getBoardScanner()[r][c].isBlackMove()) {
                System.out.println("check!!!!!");
            }
        } else {
            Board.scanPositions("white");

            int r = Board.getBlackKing()[0];
            int c = Board.getBlackKing()[1];

            if (Board.getBoardScanner()[r][c].isWhiteMove()) { //black king under white attack
                System.out.println("check!!!!!");
            }
        }


    }
}
