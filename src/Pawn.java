import java.util.ArrayList;

public class Pawn extends PieceMoves implements ChessPiece{
    private String color;
    private String name;
    private boolean enPassant;
    private boolean starting = true;
    private boolean promotion = false;
    private int[] currentPosition;

    public Pawn(int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♙" : "♟";
    }

    public boolean move(int[] inputPosition, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures, BoardScanner[][] bs) {
        ArrayList<int[]> availablePositions = new ArrayList<>();
        enPassant = false;

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

        if (color.toLowerCase().equals("white")) {
            //one space move
            if (r - 1 > 0 && boardPositions[r - 1][c] == null) {
                availablePositions.add(new int[] {r - 1, c});
            }

            //starting two space move
            if (starting && r - 2 > 0 && boardPositions[r - 2][c] == null) {
                availablePositions.add(new int[] {r - 2, c});
                if (rInput == r - 2 && cInput == c) enPassant = true;
            }

            //captures right
            if (r - 1 > 0 && c + 1 < 8) {
                if (boardPositions[r - 1][c + 1] != null) availablePositions.add(new int[] {r - 1, c + 1});
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
                if (boardPositions[r - 1][c - 1] != null) availablePositions.add(new int[] {r - 1, c - 1});
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
                availablePositions.add(new int[] {r + 1, c});
            }

            //starting two space move
            if (starting && r + 2 < 8 && boardPositions[r + 2][c] == null) {
                availablePositions.add(new int[] {r + 2, c});
                if (rInput == r + 2 && cInput == c) enPassant = true;
            }

            //captures right
            if (r + 1 < 8 && c + 1 < 8) {
                if (boardPositions[r + 1][c + 1] != null) availablePositions.add(new int[] {r + 1, c + 1});
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
                if (boardPositions[r + 1][c - 1] != null) availablePositions.add(new int[] {r + 1, c - 1});
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

        setCurrentPosition(currentPosition); setInputPosition(inputPosition); setR(r); setC(c); setAvailablePositions(availablePositions); setBoardPositions(boardPositions); setCaptures(captures);

        boolean moveAvailable = move(rInput, cInput);
        if (moveAvailable) {
            if (color.equals("white") && rInput == 0) {
                promotion = true;
            }
            if (color.equals("black") && rInput == 7) {
                promotion = true;
            }
            return true;
        }

        starting = false;
        return false;
    }

    public boolean isPromoted() { return promotion; }

    public boolean isEnPassant() { return enPassant; }

    @Override
    public String getName() { return name; }

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
        name = this.color.equals("white") ? "P" : "p";
    }
}
