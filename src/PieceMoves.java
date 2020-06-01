import java.util.ArrayList;

public class PieceMoves {
    //can introduce private variables in this class later to shorten paramter length
    protected void move(int r, int c, int x, int y, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions) {
        if (r < 0 || r > 7 || c < 0 || c > 7) return;
        int[] position = new int[] {r,c};
        if (boardPositions[r][c] != null) {
            availablePositions.add(position);
            return; //when we accessed the first position it wasn't null so it just returned
        }
        availablePositions.add(position);
        r += y;
        c += x;
        move(r, c, x, y, availablePositions, boardPositions);
    }

    protected boolean move(int[] currentPosition, int[] inputPosition, int r, int c, int rInput, int cInput, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures, BoardScanner[][] bs) {
        if (has(inputPosition, availablePositions, boardPositions, bs)) {
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

    private boolean has(int[] inputPosition, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, BoardScanner[][] bs) {
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
}
