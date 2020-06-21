import java.util.ArrayList;
import java.util.HashMap;

public class PieceMoves {
    //can introduce private variables in this class later to shorten parameter length
    protected HashMap<ChessPiece, int[]> piecePositions;
    private int r;
    private int c;
    private ArrayList<int[]> availablePositions;
    private ArrayList<ChessPiece> captures;
    private int[] currentPosition;
    private int[] inputPosition;

    //int r, int c, int x, int y, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions
    protected void moveAcross(int x, int y) {
        ChessPiece[][] board = Board.getPieces();
        if (r < 0 || r > 7 || c < 0 || c > 7) return;
        int[] position = new int[] {r,c};
        if (board[r][c] != null) {
            availablePositions.add(position);
            return; //when we accessed the first position it wasn't null so it just returned
        }
        availablePositions.add(position);
        r += y;
        c += x;
        moveAcross(x, y);
    }

    //int[] currentPosition, int[] inputPosition, int r, int c, int rInput, int cInput, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures, BoardScanner[][] bs
    protected boolean move(int rInput, int cInput) {
        ChessPiece[][] board = Board.getPieces();
        if (scanAvailablePositions()) {
            if (board[rInput][cInput] != null) {
                if (board[rInput][cInput].getColor().equals("white"))  Board.getWhitePieces().remove(board[rInput][cInput]);
                else Board.getBlackPieces().remove(board[rInput][cInput]); //no longer in the board
                captures.add(board[rInput][cInput]);
            }
            board[rInput][cInput] = board[r][c];
            currentPosition[0] = rInput;
            currentPosition[1] = cInput;
            board[r][c] = null;
            return true;
        } else {
            return false;
        }
    }

    //its pointing to the original board and its getting a null error!!!!
    //pointing to the original board which hasn't been updated yet, which has a moved piece,
    //going through the arraylist then, it goes to the copy white pawn, which has a position 6,0 but the board doesn't have that pawn anymore because when we moved that legal move its null
    //thats why its only going through the white pieces
    //maybe try creating an arraylist of copies and returning those so it doesnt interfere with the original pieces.

    //int[] inputPosition, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, BoardScanner[][] bs
    private boolean scanAvailablePositions() {
        ChessPiece[][] board = Board.getPieces();
        BoardScanner[][] bs = Board.getBoardScanner();
        int rInput = inputPosition[0]; //input Position for rook is null
        int cInput = inputPosition[1];
        boolean flag = false;
        if (board[r][c] == null) System.out.println(r + " " + c);
        boolean white = board[r][c].getColor().equals("white");
        for (int[] availablePosition : availablePositions) {
            int row = availablePosition[0];
            int col = availablePosition[1];

            if (white) bs[row][col].whiteMove();
            else bs[row][col].blackMove();
            if (rInput == row && cInput == col) flag = true;
        }
        return flag;
    }

    protected int addressChange(int x) {
        int[] array = new int[]{0,1,2,3,4,5,6,7};

        return array[x];
    }

    public static boolean scanLegalMoves() {
        return true;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getR() {return r;}

    public int getC() {return c;}

    public void setAvailablePositions(ArrayList<int[]> availablePositions) { this.availablePositions = availablePositions; }

    public void setCaptures(ArrayList<ChessPiece> captures) {
        this.captures = captures;
    }

    public void setCurrentPosition(int[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setInputPosition(int[] inputPosition) {
        this.inputPosition = inputPosition;
    }
}
