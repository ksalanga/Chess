import java.util.ArrayList;

public interface ChessPiece {

    public boolean move(String inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures);

    public String getName();

    public String getCurrentPosition();

    public String getColor();

    public void setName(String name);

    public void setPosition(String position);

    public void setColor(String color);

}
