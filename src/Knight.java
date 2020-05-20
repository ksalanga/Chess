import java.util.ArrayList;

public class Knight implements ChessPiece {
    private String name = "Kn";
    private String currentPosition;
    private String color;

    public Knight (String currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
    }

    public boolean move(String inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        ArrayList<String> availablePositions = new ArrayList<>();
        int r = Character.getNumericValue(currentPosition.charAt(0));
        int c = Character.getNumericValue(currentPosition.charAt(1));
        int rInput = Character.getNumericValue(inputPosition.charAt(0));
        int cInput = Character.getNumericValue(inputPosition.charAt(1));

        for (int i = Math.max(r - 2, 0); i <= Math.min(r + 2, 7); i++) {
            for (int j = Math.max(c - 2, 0); j <= Math.min(c + 2, 7); j++) {
                if ((i == r - 2 || i == r + 2) && (j == c - 1 || j == c + 1)) {
                    availablePositions.add(Integer.toString(i) + j);
                }
                if ((i == r - 1 || i == r + 1) && (j == c - 2 || j == c + 2)) {
                    availablePositions.add(Integer.toString(i) + j);
                }
            }
        }

        System.out.println(availablePositions);

        if (availablePositions.contains(inputPosition)) {
            if (boardPositions[rInput][cInput] != null) {
                ChessPiece p = boardPositions[rInput][cInput];
                captures.add(p);
            }
            boardPositions[rInput][cInput] = boardPositions[r][c];
            currentPosition = Integer.toString(rInput) + cInput;
            boardPositions[r][c] = null;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getName() { return name; }

    public String getCurrentPosition() { return currentPosition; }

    @Override
    public String getColor() { return color; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setPosition(String position) { this.currentPosition = position; }

    @Override
    public void setColor(String color) { this.color = color; }
}
