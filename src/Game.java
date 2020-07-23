import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private ArrayList<ChessPiece> whiteCaptures;
    private ArrayList<ChessPiece> blackCaptures;
    private Board board;
    private boolean end;
    private boolean whitesTurn = true;
    private PieceMoves pm;

    public Game() {
        //start of the game
        board = new Board();
        board.setPositions();
        whiteCaptures = new ArrayList<>();
        blackCaptures = new ArrayList<>();
        pm = new PieceMoves();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        while (!end) {
            if (!pm.legalMoveAvailable(whitesTurn)) {
                System.out.println("Stalemate");
                end = true;
            } else {
                Board.printBoard(blackCaptures, whiteCaptures);
                System.out.println();
                System.out.print(whitesTurn ? "(White ♙) Select a piece: " : "(Black ♟) " + "Select a piece: ");
                String selection = sc.nextLine();
                int [] selectedTile = board.convertToCoords(selection);
                int r = selectedTile[0];
                int c = selectedTile[1];

                if (!outOfBounds(r, c, whitesTurn)) { //checks if out of bounds
                    Board.saveCurrentBoard();
                    System.out.print("Move the " + Board.getPieces()[r][c].getName() + ": ");
                    selection = sc.nextLine();
                    selectedTile = board.convertToCoords(selection);
                    int rInput = selectedTile[0];
                    int cInput = selectedTile[1];
                    if (!(rInput < 0 || cInput < 0)) { //inBounds
                        if (!Board.getPieces()[r][c].move(selectedTile, whitesTurn ? whiteCaptures : blackCaptures)) {
                            System.out.println("Invalid Input");
                            whitesTurn = !whitesTurn;
                        } else {
                            Board.scanPositions();
                            boolean white = Board.getPieces()[rInput][cInput].getColor().equals("white");
                            if (white) {
                                int kingRow = Board.getWhiteKing()[0];
                                int kingCol = Board.getWhiteKing()[1];
                                if (Board.getBoardScanner()[kingRow][kingCol].isBlackMove()) {
                                    System.out.println("Illegal Move");
                                    Board.revertToPreviousBoard();
                                    whitesTurn = !whitesTurn;
                                }
                            } else {
                                int kingRow = Board.getBlackKing()[0];
                                int kingCol = Board.getBlackKing()[1];
                                if (Board.getBoardScanner()[kingRow][kingCol].isWhiteMove()) {
                                    System.out.println("Illegal Move");
                                    Board.revertToPreviousBoard();
                                    whitesTurn = !whitesTurn;
                                }
                            }
                        }
                    } else {
                        System.out.println("Out of bounds");
                        whitesTurn = !whitesTurn;
                    }
                } else {
                    whitesTurn = !whitesTurn; //gives the position back to the person who didn't type the write input
                }

                Board.pawnPromotion(whitesTurn, sc);
                whitesTurn = !whitesTurn;

                check(); //checks if the opposite king is in check
                System.out.println();
            }
        }

        Board.printBoard(blackCaptures, whiteCaptures);
        sc.close();
    }

    public void startWithPresetMoves(String[] moves) {
        for (int i = 0; i < moves.length; i += 2) {
            if (!pm.legalMoveAvailable(whitesTurn)) {
                System.out.println("Stalemate");
                end = true;
            } else {
                Board.printBoard(blackCaptures, whiteCaptures);
                System.out.println();
                int[] piece = board.convertToCoords(moves[i]);
                int pieceRow = piece[0];
                int pieceCol = piece[1];
                int[] move = board.convertToCoords(moves[i + 1]);
                Board.getPieces()[pieceRow][pieceCol].move(move, whitesTurn ? whiteCaptures : blackCaptures);

                whitesTurn = !whitesTurn;

                if (presetCheck(moves, i)) {
                    i += 2; //checks if the opposite king is in check
                    whitesTurn = !whitesTurn;
                }
                System.out.println();
            }
        }
        Board.printBoard(blackCaptures, whiteCaptures);
        start();
    }

    private boolean presetCheck(String[] moves, int i) {
        Board.reInitialize();
        Board.scanPositions();

        if (whitesTurn) {
            int kingRow = Board.getWhiteKing()[0];
            int kingColumn = Board.getWhiteKing()[1];

            if (Board.getBoardScanner()[kingRow][kingColumn].isBlackMove()) {
                if (!pm.legalMoveAvailable(whitesTurn)) {
                    System.out.println("Checkmate");
                    end = true;
                    return true;
                }

                System.out.println("White King in Check");
                int[] piece = board.convertToCoords(moves[i + 2]);
                int pieceRow = piece[0];
                int pieceCol = piece[1];

                int[] move = board.convertToCoords(moves[i + 3]);

                Board.getPieces()[pieceRow][pieceCol].move(move, whitesTurn ? whiteCaptures : blackCaptures);
                Board.printBoard(blackCaptures, whiteCaptures);
                return true;
            }
        } else {
            int kingRow = Board.getBlackKing()[0];
            int kingColumn = Board.getBlackKing()[1];

            if (Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove()) {
                if (!pm.legalMoveAvailable(whitesTurn)) {
                    System.out.println("Checkmate");
                    end = true;
                    return true;
                }
                Board.printBoard(blackCaptures, whiteCaptures);
                System.out.println("Black King in Check");
                int[] piece = board.convertToCoords(moves[i + 2]);
                int pieceRow = piece[0];
                int pieceCol = piece[1];

                int[] move = board.convertToCoords(moves[i + 3]);

                Board.getPieces()[pieceRow][pieceCol].move(move, whitesTurn ? whiteCaptures : blackCaptures);
                Board.printBoard(blackCaptures, whiteCaptures);
                return true;
            }
        }
        return false;
    }

    private boolean outOfBounds(int r, int c, boolean whitesTurn) {
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

    private void check() {
        //initialize boardScanner

        Scanner sc = new Scanner(System.in);
        Board.reInitialize();

        Board.scanPositions();

        if (whitesTurn)  {
            //checks possible positions for black to attack the king
            int kingRow = Board.getWhiteKing()[0];
            int kingColumn = Board.getWhiteKing()[1];

            //need to make an object clone

            //need to make sure to take out white and black captures if the move doesnt go through****!!!!!!
            if (Board.getBoardScanner()[kingRow][kingColumn].isBlackMove()) {
                if (!pm.legalMoveAvailable(whitesTurn)) {
                    System.out.println("Checkmate");
                    end = true;
                    return;
                }

                Board.printBoard(blackCaptures, whiteCaptures);
                System.out.println("White King in Check"); //white king in check

                //copy the board

                //need to add a condition where while the king is under check it cant move anything
                //look for other pieces that can move. if you move and try to block it and then we rescan, again, if the king is under attack, we can't move that piece.
                //the condition for checkmate is if you cant move anywhere else

                //make a copy 2d array of the pieces before you make a move then just revert back to it.
                //keep checking it and making copy arrays. it doesnt matter because we'll only get the real result once we print out the board

                //might have to add an illegalMove parameter.

                while (Board.getBoardScanner()[kingRow][kingColumn].isBlackMove()) {

                    int captureSize =  whiteCaptures.size();
                    int whitePiecesSize = Board.getWhitePieces().size();
                    System.out.print("(White ♙) Select a piece: ");
                    String selection = sc.nextLine();
                    int [] selectedTile = board.convertToCoords(selection);
                    int r = selectedTile[0];
                    int c = selectedTile[1];

                    Board.saveCurrentBoard();

                    if (!outOfBounds(r, c, whitesTurn)) { //checks if out of bounds

                        System.out.print("Move the " + Board.getPieces()[r][c].getName() + ": ");
                        selection = sc.nextLine();
                        selectedTile = board.convertToCoords(selection);
                        int rInput = selectedTile[0];
                        int cInput = selectedTile[1];
                        if (!(rInput < 0 || cInput < 0)) { //inBounds
                            if (!Board.getPieces()[r][c].move(selectedTile, whiteCaptures)) { //change this to blackCaptures for black side
                                System.out.println("Invalid Input");
                            }
                        } else {
                            System.out.println("Out of bounds");
                        }
                    }

                    Board.reInitialize();
                    Board.scanPositions();

                    //could implement a linked list for this case

                    kingRow = Board.getWhiteKing()[0];
                    kingColumn = Board.getWhiteKing()[1];
                    if (Board.getBoardScanner()[kingRow][kingColumn].isBlackMove()) {
                        if (captureSize < whiteCaptures.size()) whiteCaptures.remove(whiteCaptures.size() - 1);
                        Board.revertToPreviousBoard();
                        board.printBoard(blackCaptures, whiteCaptures);
                        System.out.println();
                    } else {
                        whitesTurn = !whitesTurn;
                    }


                    //if its a non null section in the chessboard and its an allied piece, if its under attack

                    //test white rook in top corner, black bishop tries to block other white rook looking up the kings file
                    //check illegal moves around the king method
                    //if all moves make the king under attack, it is an illegal move. if at least one move stops the king from being attack, it is legal.

                    //get the position of the attacker.

                    //calculate the pieces the squares that it is going through and find  way to block it. or capture it.
                    //if the attacking piece is attackable itself or blockable then it isn't in check. if the blocking or attacking piece is an illegal move tho, then that piece cant move.
                    //figure out which one is legal and which one is illegal

                    //checkmate, list of all possible moves, if its in check and there are none, checkmate
                }
            }

        } else {
            int kingRow = Board.getBlackKing()[0];
            int kingColumn = Board.getBlackKing()[1];


            if (Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove()) {

                if (!pm.legalMoveAvailable(whitesTurn)) {
                    System.out.println("Checkmate");
                    end = true;
                    return;
                }

                board.printBoard(blackCaptures, whiteCaptures);
                System.out.println("Black King in Check"); //black king in check

                while (Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove()) {
                    int captureSize =  blackCaptures.size();
                    int blackPiecesSize = Board.getBlackPieces().size();
                    System.out.print("(Black ♙) Select a piece: ");
                    String selection = sc.nextLine();
                    int [] selectedTile = board.convertToCoords(selection);
                    int r = selectedTile[0];
                    int c = selectedTile[1];

                    Board.saveCurrentBoard();

                    if (!outOfBounds(r, c, whitesTurn)) { //checks if out of bounds

                        System.out.print("Move the " + Board.getPieces()[r][c].getName() + ": ");
                        selection = sc.nextLine();
                        selectedTile = board.convertToCoords(selection);
                        int rInput = selectedTile[0];
                        int cInput = selectedTile[1];
                        if (!(rInput < 0 || cInput < 0)) { //inBounds
                            if (!Board.getPieces()[r][c].move(selectedTile, blackCaptures)) {
                                System.out.println("Invalid Input");
                            }
                        } else {
                            System.out.println("Out of bounds");
                        }
                    }

                    Board.reInitialize();
                    Board.scanPositions();

                    //could implement a linked list for this case

                    kingRow = Board.getBlackKing()[0];
                    kingColumn = Board.getBlackKing()[1];
                    if (Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove()) {
                        if (captureSize < blackCaptures.size()) blackCaptures.remove(blackCaptures.size() - 1);
                        Board.revertToPreviousBoard();
                        board.printBoard(blackCaptures, whiteCaptures);
                        System.out.println();
                    } else {
                        whitesTurn = !whitesTurn;
                    }
                }
            }
        }
    }


}
