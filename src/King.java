import java.util.ArrayList;

public class King extends PieceMoves implements ChessPiece {
    private String name = "K";
    private String currentPosition;
    private String color;
    private boolean starting = true;

    public King (String currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
    }

    public boolean move(String inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        ArrayList<String> availablePositions = new ArrayList<String>();

        int r = Character.getNumericValue(currentPosition.charAt(0));
        int c = Character.getNumericValue(currentPosition.charAt(1));
        int rInput = Character.getNumericValue(inputPosition.charAt(0));
        int cInput = Character.getNumericValue(inputPosition.charAt(1));

        if (rInput == r && (starting && c + 2 == cInput && boardPositions[r][cInput + 1] instanceof Rook) || (starting && c - 3 == cInput && boardPositions[r][cInput - 1] instanceof Rook)) {
            boolean flag = true;
            int rookColumn;
            if (cInput == c + 2) {
                rookColumn = c + 3;
                for (int i = 1; i < 3; i++) {
                    if (boardPositions[r][c + i] != null) {
                        flag = false;
                        break;
                    }
                }
            } else {
                rookColumn = c - 4;
                for (int i = 1; i < 4; i++) {
                    if (boardPositions[r][c + i] != null) {
                        flag = false;
                        break;
                    }
                }
            }

            if (flag && ((Rook) boardPositions[r][rookColumn]).isStarting()) {
                //castles
                boardPositions[rInput][cInput] = boardPositions[r][c];
                boardPositions[r][c] = null;
                boardPositions[r][cInput == c + 2 ? cInput - 1 : cInput + 1] = boardPositions[r][rookColumn];
                boardPositions[r][rookColumn] = null;
                starting = false;
                return true;
            }
        }

        for (int i = Math.max(r - 1, 0); i <= Math.min(r + 1, 7); i++) {
            for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, 7); j++) {
                if (i != r && j != c) {
                    availablePositions.add(Integer.toString(i) + j);
                }
            }
        }

        boolean moveAvailable = move(currentPosition, inputPosition, r, c, rInput, cInput, availablePositions, boardPositions, captures);
        if (moveAvailable) return true;

        starting = false;
        return false;
    }

    public boolean castle() {
        return starting;
    }

    @Override
    public String getName() {
        return null;
    }

    public String getCurrentPosition() {
        return "";
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setPosition(String position) {

    }

    @Override
    public void setColor(String color) {

    }
}
