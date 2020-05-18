import java.util.ArrayList;

public class Pawn extends ChessPiece{
    private String name = "P";
    private boolean enPassant = false;
    private boolean starting = true;
    private String color;
    private String currentPosition;

    public Pawn(String currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
    }

    //This is going to be White moves, its going to be inversed for black moves
    public String move(String inputPosition, ChessPiece[][] boardPositions) {
        ArrayList<String> availablePositions = new ArrayList<String>();

        int r = Integer.parseInt(currentPosition.valueOf(currentPosition.charAt(0)));
        int c = Integer.parseInt(currentPosition.valueOf(currentPosition.charAt(1)));

        //All Possible Pawn moves
        //going up a column (File) means going down a row in the 2d array
        if (boardPositions[r - 1][c] == null) {
            availablePositions.add(Integer.toString(r - 1) + c);
        }

        if (starting && boardPositions[r - 2][c] == null) {
            availablePositions.add(Integer.toString(r - 2) + c);
        }

        if (boardPositions[r - 1][c + 1] != null ) {
            availablePositions.add(Integer.toString(r - 1) + (c + 1));
        }

        if (boardPositions[r - 1][c - 1] != null) {
            availablePositions.add(Integer.toString(r - 1) + (c - 1));
        }

        System.out.println(availablePositions);

        starting = false;

        if (!availablePositions.contains(inputPosition)) {
            return "";
        } else {
            return inputPosition;
        }
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

}
