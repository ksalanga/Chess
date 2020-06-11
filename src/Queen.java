import java.util.ArrayList;

public class Queen extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;

    public Queen(int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♕" : "♛";
    }

    public Queen(Queen copy) {
        this.color = copy.color;
        this.name = copy.name;
        this.currentPosition = copy.currentPosition;
    }

    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures) {
        ArrayList<int[]> availablePositions = new ArrayList<>();

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

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

        setR(r + 1); setC(c + 1);
        moveAcross(1, 1);

        setR(r + 1); setC(c - 1);
        moveAcross(-1, 1);

        setR(r - 1); setC(c + 1);
        moveAcross(1, -1);

        setR(r - 1); setC(c - 1);
        moveAcross(-1, -1);

        setInputPosition(inputPosition); setR(r); setC(c); setCaptures(captures);
        return move(rInput, cInput);
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
