import java.util.ArrayList;

public class Pawn implements ChessPiece{
    private String name = "P";
    private boolean enPassant;
    private boolean starting = true;
    private String color;
    private String currentPosition;

    public Pawn(String currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
    }

    public boolean move(String inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures) {
        ArrayList<String> availablePositions = new ArrayList<String>();
        enPassant = false;

        int r = Character.getNumericValue(currentPosition.charAt(0));
        int c = Character.getNumericValue(currentPosition.charAt(1));
        int rInput = Character.getNumericValue(inputPosition.charAt(0));
        int cInput = Character.getNumericValue(inputPosition.charAt(1));

        if (color.toLowerCase().equals("white")) {
            //one space move
            if (r - 1 > 0 && boardPositions[r - 1][c] == null) {
                availablePositions.add(Integer.toString(r - 1) + c);
            }

            //starting two space move
            if (starting && r - 2 > 0 && boardPositions[r - 2][c] == null) {
                availablePositions.add(Integer.toString(r - 2) + c);
                if (rInput == r - 2 && cInput == c) enPassant = true;
            }

            //captures right
            if (r - 1 > 0 && c + 1 < 8) {
                if (boardPositions[r - 1][c + 1] != null) availablePositions.add(Integer.toString(r - 1) + (c + 1));
                //captures enpassant, right : special case
                if (boardPositions[r][c + 1] instanceof Pawn) {
                    Pawn p = (Pawn) boardPositions[r][c + 1];
                    if (p.isEnPassant() && rInput == r - 1
                            && cInput == c + 1) {
                        captures.add(p);
                        boardPositions[r][c + 1] = null;
                    }
                }

            }

            //captures left
            if (r - 1 > 0 && c - 1 > 0) {
                if (boardPositions[r - 1][c - 1] != null) availablePositions.add(Integer.toString(r - 1) + (c - 1));
                //captures enpassant, left : special case
                if (boardPositions[r][c - 1] instanceof Pawn) {
                    Pawn p = (Pawn) boardPositions[r][c - 1];
                    if (p.isEnPassant() && rInput == r - 1
                            && cInput == c - 1) {
                        captures.add(p);
                        boardPositions[r][c - 1] = null;
                    }
                }
            }
        } else {
            //one space move
            if (r + 1 < 8 && boardPositions[r + 1][c] == null) {
                availablePositions.add(Integer.toString(r + 1) + c);
            }

            //starting two space move
            if (starting && r + 2 < 8 && boardPositions[r + 2][c] == null) {
                availablePositions.add(Integer.toString(r + 2) + c);
                if (rInput == r + 2 && cInput == c) enPassant = true;
            }

            //captures right
            if (r + 1 < 8 && c + 1 < 8) {
                if (boardPositions[r + 1][c + 1] != null) availablePositions.add(Integer.toString(r + 1) + (c + 1));
                if (boardPositions[r][c + 1] instanceof Pawn) {
                    Pawn p = (Pawn) boardPositions[r][c + 1];
                    if (p.isEnPassant() && rInput == r + 1
                            && cInput == c + 1) {
                        captures.add(p);
                        boardPositions[r][c + 1] = null;
                    }
                }
            }

            //captures left
            if (r + 1 < 8 && c - 1 > 0) {
                if (boardPositions[r + 1][c - 1] != null) availablePositions.add(Integer.toString(r + 1) + (c - 1));
                if (boardPositions[r][c - 1] instanceof Pawn) {
                    Pawn p = (Pawn) boardPositions[r][c - 1];
                    if (p.isEnPassant() && rInput == r + 1
                            && cInput == c - 1) {
                        captures.add(p);
                        boardPositions[r][c - 1] = null;
                    }
                }
            }
        }

        starting = false;

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

    public boolean isEnPassant() { return enPassant; }

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
