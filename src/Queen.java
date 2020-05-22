import java.util.ArrayList;

public class Queen extends PieceMoves implements ChessPiece {
    private String name = "Q";
    private String currentPosition;
    private String color;

    public Queen (String currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
    }

    public boolean move(String inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        ArrayList<String> availablePositions = new ArrayList<>();

        int r = Character.getNumericValue(currentPosition.charAt(0));
        int c = Character.getNumericValue(currentPosition.charAt(1));
        int rInput = Character.getNumericValue(inputPosition.charAt(0));
        int cInput = Character.getNumericValue(inputPosition.charAt(1));

        move(r + 1, c + 1, 1, 1, availablePositions, boardPositions);
        move(r + 1, c - 1, -1, 1, availablePositions, boardPositions);
        move(r - 1, c + 1, 1, -1, availablePositions, boardPositions);
        move(r - 1, c - 1, -1, -1, availablePositions, boardPositions);
        move(r + 1, c, 0, 1, availablePositions, boardPositions);
        move(r - 1, c, 0, -1, availablePositions, boardPositions);
        move(r, c + 1, 1, 0, availablePositions, boardPositions);
        move(r, c - 1, -1, 0, availablePositions, boardPositions);

        return move(currentPosition, inputPosition, r, c, rInput, cInput, availablePositions, boardPositions, captures);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setPosition(String position) { this.currentPosition = position; }

    @Override
    public void setColor(String color) { this.color = color; }
}
