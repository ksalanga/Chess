import java.util.ArrayList;

public class Knight extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;
    private ArrayList<int[]> availablePositions;

    public Knight(int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♘" : "♞";
    }

    public Knight(Knight copy) {
        this.color = copy.color;
        this.name = copy.name;
        this.currentPosition = new int[]{addressChange(copy.currentPosition[0]), addressChange(copy.currentPosition[1])};
    }

    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures) {
        availablePositions = new ArrayList<>();

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

        for (int i = Math.max(r - 2, 0); i <= Math.min(r + 2, 7); i++) {
            for (int j = Math.max(c - 2, 0); j <= Math.min(c + 2, 7); j++) {
                if ((i == r - 2 || i == r + 2) && (j == c - 1 || j == c + 1)
                || (i == r - 1 || i == r + 1) && (j == c - 2 || j == c + 2)) {
                    if (color.equals("white")) {
                        if (Board.getPieces()[i][j] == null || Board.getPieces()[i][j].getColor().equals("black")) availablePositions.add(new int[] {i, j});
                    } else {
                        if (Board.getPieces()[i][j] == null || Board.getPieces()[i][j].getColor().equals("white")) availablePositions.add(new int[] {i, j});
                    }
                }
            }
        }

        setCurrentPosition(currentPosition); setInputPosition(inputPosition); setR(r); setC(c); setAvailablePositions(availablePositions); setCaptures(captures);
        return move(rInput, cInput);
    }

    @Override
    public String getName() { return name; }

    public ArrayList<int[]> getAvailablePositions() {
        return availablePositions;
    }

    public int[] getCurrentPosition() { return currentPosition; }

    @Override
    public String getColor() { return color; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setPosition(int[] position) { this.currentPosition = position; }

    @Override
    public void setColor(String color) {
        this.color = color;
        name = this.color.equals("white") ? "N" : "n";
    }
}
