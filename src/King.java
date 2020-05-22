import java.util.ArrayList;

public class King implements ChessPiece {
    private String name = "K";
    private String currentPosition;
    private String color;
    private boolean starting = true;

    public King (String currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
    }

    public boolean move(String inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        starting = false;
        return starting;
    }

    public boolean castle()

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
