import java.util.ArrayList;

public class Bishop implements ChessPiece {
    private String name = "B";
    private String currentPosition;
    private String color;

    public Bishop (String currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
    }

    public boolean move(String inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        ArrayList<String> availablePositions = new ArrayList<>();

        int r = Character.getNumericValue(currentPosition.charAt(0));
        int c = Character.getNumericValue(currentPosition.charAt(1));
        int rInput = Character.getNumericValue(inputPosition.charAt(0));
        int cInput = Character.getNumericValue(inputPosition.charAt(1));

        move(r, c, 1, 1, availablePositions, boardPositions);
        move(r, c, -1, 1, availablePositions, boardPositions);
        move(r, c, 1, -1, availablePositions, boardPositions);
        move(r, c, -1, -1, availablePositions, boardPositions);

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

    private void move(int r, int c, int x, int y, ArrayList<String> availablePositions, ChessPiece[][] boardPositions) {
        if (r < 0 || r > 7 || c < 0 || c > 7) return;
        String position = Integer.toString(r) + c;
        if (boardPositions[r][c] != null) {
            availablePositions.add(position);
            return;
        }
        availablePositions.add(position);
        move(r += y, c += x, x, y, availablePositions, boardPositions);
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
