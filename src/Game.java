import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<ChessPiece> whiteCaptures;
    private ArrayList<ChessPiece> blackCaptures;
    Board board;
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
            board.printBoard();
            System.out.println();
            System.out.print("Select a piece: ");
            String selection = s.nextLine();
            int [] selectedTile = board.convertToCoords(selection);
            int r = selectedTile[0];
            int c = selectedTile[1];
            if (selection.length() != 2 || ((r < 0 || r > 7) || (c < 0 || c > 7))) { //checks if out of bounds
                System.out.println("Out of bounds");
                whitesTurn = !whitesTurn;
            } else if (pieces[r][c] == null) {
                System.out.println("You Selected a tile, select a piece");
                whitesTurn = !whitesTurn;
            } else if ((!whitesTurn && pieces[r][c].getColor().equals("white")) || (whitesTurn && pieces[r][c].getColor().equals("black"))) {
                System.out.println("Choose the right color");
                whitesTurn = !whitesTurn;
            } else {
                System.out.print("Move the piece: ");
                selection = s.nextLine();
                selectedTile = board.convertToCoords(selection);
                int rInput = selectedTile[0];
                int cInput = selectedTile[1];
                if (!(selection.length() != 2 || ((rInput < 0 || rInput > 7) || (cInput < 0 || cInput > 7)))) { //inBounds
                    if (pieces[r][c].move(selectedTile, pieces, whitesTurn ? whiteCaptures : blackCaptures)) {
                        pieces[rInput][cInput].setPosition(selectedTile);
                    } else {
                        System.out.println("Invalid Input");
                        whitesTurn = !whitesTurn;
                    }
                } else {
                    System.out.println("Out of bounds");
                    whitesTurn = !whitesTurn;
                }
            }
            System.out.println();
            whitesTurn = !whitesTurn;
        }
    }

    public boolean checkmate() {
        return true;
    }
}
