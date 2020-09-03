import java.util.ArrayList;

public class Bishop extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;
    private int[] savedPosition;
    private ArrayList<int[]> availablePositions;

    public Bishop(int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♗" : "♝";
    }

    public Bishop(Bishop copy) {
        this.color = copy.color;
        this.name = copy.name;
        this.currentPosition = copy.currentPosition;
        this.availablePositions = copy.availablePositions;
    }

    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures) {
        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

        setCurrentPosition(currentPosition);
        setAvailablePositions(availablePositions);
        setInputPosition(inputPosition); setR(r); setC(c); setCaptures(captures);
        return move(rInput, cInput);
    }

    public void findPositions() {
        availablePositions = new ArrayList<>();

        int r = currentPosition[0];
        int c = currentPosition[1];

        setCurrentPosition(currentPosition);
        setAvailablePositions(availablePositions);

        setR(r + 1); setC(c + 1);
        moveAcross(1, 1);

        setR(r + 1); setC(c - 1);
        moveAcross(-1, 1);

        setR(r - 1); setC(c + 1);
        moveAcross(1, -1);

        setR(r - 1); setC(c - 1);
        moveAcross(-1, -1);
    }

    @Override
    public String getName() { return name; }

    public ArrayList<int[]> getAvailablePositions() {
        return availablePositions;
    }

    public void saveCurrentPosition() {
        savedPosition = new int[]{currentPosition[0], currentPosition[1]};
    }

    public void revertToPreviousPosition() {
        currentPosition[0] = savedPosition[0];
        currentPosition[1] = savedPosition[1];
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
        name = this.color.equals("white") ? "B" : "b";
    }
}
