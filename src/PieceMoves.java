import java.util.ArrayList;
import java.util.HashMap;

public class PieceMoves {
    //can introduce private variables in this class later to shorten parameter length
    protected HashMap<ChessPiece, int[]> piecePositions;
    private int r;
    private int c;
    private ArrayList<int[]> availablePositions;
    private ChessPiece[][] boardPositions;
    private ArrayList<ChessPiece> captures;
    BoardScanner[][] bs;
    private int[] currentPosition;
    private int[] inputPosition;

    //int r, int c, int x, int y, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions
    protected void moveAcross(int x, int y) {
        if (r < 0 || r > 7 || c < 0 || c > 7) return;
        int[] position = new int[] {r,c};
        if (boardPositions[r][c] != null) {
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
        if (scanAvailablePositions()) {
            if (boardPositions[rInput][cInput] != null) {
                captures.add(boardPositions[rInput][cInput]);
            }
            boardPositions[rInput][cInput] = boardPositions[r][c];
            currentPosition[0] = rInput;
            currentPosition[1] = cInput;
            boardPositions[r][c] = null;
            return true;
        } else {
            return false;
        }
    }

    //int[] inputPosition, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, BoardScanner[][] bs
    private boolean scanAvailablePositions() {
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];
        boolean flag = false;
        boolean white = boardPositions[rInput][cInput].equals("white") ? true : false;
        for (int i = 0; i < availablePositions.size(); i++) {
            int r = availablePositions.get(i)[0];
            int c = availablePositions.get(i)[1];

            if (white) bs[r][c].whiteMove();
            else bs[r][c].blackMove();
            if (rInput == r && cInput == c) flag = true;
        }
        return flag;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setAvailablePositions(ArrayList<int[]> availablePositions) {
        this.availablePositions = availablePositions;
    }

    public void setBoardPositions(ChessPiece[][] boardPositions) {
        this.boardPositions = boardPositions;
    }

    public void setCaptures(ArrayList<ChessPiece> captures) {
        this.captures = captures;
    }

    public void setBoardScanner(BoardScanner[][] bs) {
        this.bs = bs;
    }

    public void setCurrentPosition(int[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setInputPosition(int[] inputPosition) {
        this.inputPosition = inputPosition;
    }
}
