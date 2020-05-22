import java.util.ArrayList;

public class PieceMoves {
    protected void move(int r, int c, int x, int y, ArrayList<String> availablePositions, ChessPiece[][] boardPositions) {
        if (r < 0 || r > 7 || c < 0 || c > 7) return;
        String position = Integer.toString(r) + c;
        if (boardPositions[r][c] != null) {
            availablePositions.add(position);
            return; //when we accessed the first position it wasn't null so it just returned
        }
        availablePositions.add(position);
        r += y;
        c += x;
        move(r, c, x, y, availablePositions, boardPositions);
    }

    protected boolean move(String currentPosition, String inputPosition, int r, int c, int rInput, int cInput, ArrayList<String> availablePositions, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        if (availablePositions.contains(inputPosition)) {
            if (boardPositions[rInput][cInput] != null) {
                ChessPiece p = boardPositions[rInput][cInput];
                captures.add(p);
            }
            boardPositions[rInput][cInput] = boardPositions[r][c];
            currentPosition = Integer.toString(rInput) + cInput;
            boardPositions[r][c] = null;
            return true;
        } else {
            return false;
        }
    }
}
