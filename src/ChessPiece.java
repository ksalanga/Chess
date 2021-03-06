import java.util.ArrayList;

public interface ChessPiece {

    //int[] inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures, BoardScanner[][] bs
    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures);

    public void findPositions();

    public String getName();

    public ArrayList<int[]> getAvailablePositions();

    public void saveCurrentPosition();

    public void revertToPreviousPosition();

    public int[] getCurrentPosition(); //for optimization purposes, probably should convert String to int array [column, row] that way, storage is not dynamic;

    public String getColor();

    public void setName(String name);

    public void setPosition(int[] position);

    public void setColor(String color);

}
