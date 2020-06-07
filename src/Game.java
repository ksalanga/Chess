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
        ChessPiece[][] pieces = board.getPieces();
        //put captures ArrayList when a piece moves.
        boolean whitesTurn = true;
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

        board.printBoard(blackCaptures, whiteCaptures);
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

        ChessPiece[][] pieces = Board.getPieces();
        BoardScanner[][] bs = Board.getBoardScanner();

        if (color.equals("white"))  {
            Board.scanPositions("black"); //checks possible positions for black to attack the king
            int r = Board.getWhiteKing()[0];
            int c = Board.getWhiteKing()[1];

            //need to make sure to take out white and black captures if the move doesnt go through****!!!!!!
            if (bs[r][c].isBlackMove()) {
                System.out.println("White King in Check"); //white king in check


                //need to add a condition where while the king is under check it cant move anything
                //look for other pieces that can move. if you move and try to block it and then we rescan, again, if the king is under attack, we can't move that piece.
                //the condition for checkmate is if you cant move anywhere else

                //make a copy 2d array of the pieces before you make a move then just revert back to it.
                //keep checking it and making copy arrays. it doesnt matter because we'll only get the real result once we print out the board

                //might have to add an illegalMove parameter.

                while (bs[r][c].isBlackMove()) {


                    //if its a non null section in the chessboard and its an allied piece, if its under attack

                    //test white rook in top corner, black bishop tries to block other white rook looking up the kings file
                    //check illegal moves around the king method
                    //if all moves make the king under attack, it is an illegal move. if at least one move stops the king from being attack, it is legal.

                    //get the position of the attacker.

                    //calculate the pieces the squares that it is going through and find  way to block it. or capture it.
                    //if the attacking piece is attackable itself or blockable then it isn't in check. if the blocking or attacking piece is an illegal move tho, then that piece cant move.
                    //figure out which one is legal and which one is illegal
                }
            }
        } else {
            Board.scanPositions("white");

            int r = Board.getBlackKing()[0];
            int c = Board.getBlackKing()[1];

            if (Board.getBoardScanner()[r][c].isWhiteMove()) {
                System.out.println("Black King in Check"); //black king in check
            }
        }


    }
}
