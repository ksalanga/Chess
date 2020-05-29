import java.util.ArrayList;

public class Queen extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;

    public Queen (int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "Q" : "q";
    }

    public boolean move(int[] inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        ArrayList<int[]> availablePositions = new ArrayList<>();

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

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

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setPosition(int[] position) { this.currentPosition = position; }

    @Override
    public void setColor(String color) {
        this.color = color;
        name = this.color.equals("white") ? "Q" : "q";
    }
}
