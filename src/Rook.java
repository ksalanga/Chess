import java.util.ArrayList;

public class Rook extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;
    private boolean starting = true;
    private boolean scanning = false;
    private ArrayList<int[]> availablePositions;

    public Rook(int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♖" : "♜";
    }

    public Rook(Rook copy) {
        this.color = copy.color;
        this.name = copy.name;
        this.currentPosition = new int[]{addressChange(copy.currentPosition[0]), addressChange(copy.currentPosition[1])};
        this.starting = copy.starting;
        this.scanning = copy.scanning;
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
        boolean moveAvailable = move(rInput, cInput);
        if (moveAvailable) return true;

        if (!scanning) starting = false;
        return false;
    }

    public void findPositions() {
        availablePositions = new ArrayList<>();

        int r = currentPosition[0];
        int c = currentPosition[1];

        setCurrentPosition(currentPosition);
        setAvailablePositions(availablePositions);

        setR(r + 1); setC(c);
        moveAcross(0, 1);

        setR(r - 1); setC(c);
        moveAcross(0, -1);

        setR(r); setC(c + 1);
        moveAcross(1, 0);

        setR(r); setC(c - 1);
        moveAcross(-1, 0);
    }

    public void scanning() {
        scanning = !scanning;
    }

    public boolean isStarting() {
        return starting;
    }

    @Override
    public String getName() {
        return name;
    }

    public ArrayList<int[]> getAvailablePositions() {
        return availablePositions;
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
        name = this.color.equals("white") ? "R" : "r";
    }
}
