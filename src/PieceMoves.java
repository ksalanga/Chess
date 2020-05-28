import java.util.ArrayList;

public class PieceMoves {
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

    protected boolean move(int[] currentPosition, int[] inputPosition, int r, int c, int rInput, int cInput, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        if (has(inputPosition, availablePositions)) {
            if (boardPositions[rInput][cInput] != null) {
                ChessPiece p = boardPositions[rInput][cInput];
                captures.add(p);
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

    private boolean has(int[] inputPosition, ArrayList<int[]> availablePositions) {
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];
        for (int i = 0; i < availablePositions.size(); i++) {
            int r = availablePositions.get(i)[0];
            int c = availablePositions.get(i)[1];

            if (rInput == r && cInput == c) return true;
        }
        return false;
    }
}
