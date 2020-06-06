import java.util.ArrayList;

public class King extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;
    private boolean starting = true;

    public King (int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♔" : "♚";
    }

    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures) {
        ArrayList<int[]> availablePositions = new ArrayList<int[]>();
        ChessPiece[][] board = Board.getPieces();

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

        if (rInput == r && (starting && c + 2 == cInput && board[r][cInput + 1] instanceof Rook) || (starting && c - 3 == cInput && board[r][cInput - 1] instanceof Rook)) {
            boolean flag = true;
            int rookColumn;
            if (cInput == c + 2) {
                rookColumn = c + 3;
                for (int i = 1; i < 3; i++) {
                    if (board[r][c + i] != null) {
                        flag = false;
                        break;
                    }
                }
            } else {
                rookColumn = c - 4;
                for (int i = 1; i < 4; i++) {
                    if (board[r][c + i] != null) {
                        flag = false;
                        break;
                    }
                }
            }

            if (flag && ((Rook) board[r][rookColumn]).isStarting()) {
                //castles
                //Need to add a new condition, if the king or any of the squares between the rook is under attack it cant castle.
                board[rInput][cInput] = board[r][c];
                board[r][c] = null;
                board[r][cInput == c + 2 ? cInput - 1 : cInput + 1] = board[r][rookColumn];
                board[r][rookColumn] = null;
                starting = false;
                return true;
            }
        }

        for (int i = Math.max(r - 1, 0); i <= Math.min(r + 1, 7); i++) {
            for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, 7); j++) {
                if (i != r && j != c) {
                    availablePositions.add(new int[] {i, j});
                }
            }
        }

        setCurrentPosition(currentPosition); setInputPosition(inputPosition); setR(r); setC(c); setAvailablePositions(availablePositions); setCaptures(captures);

        boolean moveAvailable = move(rInput, cInput);
        if (moveAvailable) return true;

        starting = false;
        return false;
    }

    public boolean castle() {
        return starting;
    }

    @Override
    public String getName() {
        return name;
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPosition(int[] position) {
        currentPosition = position;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
        name = this.color.equals("white") ? "K" : "k";
    }
}
