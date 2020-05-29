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
    }

    public void start() {
        Scanner s = new Scanner(System.in);

        //put captures ArrayList when a piece moves.
        while (!end) {

        }
    }

    public boolean checkmate() {
        return true;
    }
}
