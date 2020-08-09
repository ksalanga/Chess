import java.util.ArrayList;

public class Pawn extends PieceMoves implements ChessPiece{
    private String color;
    private String name;
    private boolean enPassant;
    private boolean starting = true;
    private boolean promotion = false;
    private boolean scanning = false;
    private int[] currentPosition;
    private ArrayList<int[]> availablePositions;

    public Pawn(int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♙" : "♟";
    }

    public Pawn(Pawn copy) {
        this.color = copy.color;
        this.name = copy.name;
        this.enPassant = copy.enPassant;
        this.starting = copy.starting;
        this.promotion = copy.promotion;
        this.scanning = copy.scanning;
        this.currentPosition = new int[]{addressChange(copy.currentPosition[0]), addressChange(copy.currentPosition[1])}; //still referring to the same position so when it accesses the white pawn that moved, it refers to the new Board.getPieces() copy, which still has the pawn that remained still
    }

    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures) {
        availablePositions = new ArrayList<>();
        if (!scanning) enPassant = false;

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

        if (color.toLowerCase().equals("white")) {
            //one space move
            if (r - 1 >= 0 && Board.getPieces()[r - 1][c] == null) {
                availablePositions.add(new int[] {r - 1, c});
                //starting two space move
                if (starting && r - 2 > 0 && Board.getPieces()[r - 2][c] == null) {
                    availablePositions.add(new int[] {r - 2, c});
                    if (rInput == r - 2 && cInput == c && !scanning) enPassant = true;
                }
            }

            //captures right
            if (r - 1 >= 0 && c + 1 < 8) {
                if (Board.getPieces()[r - 1][c + 1] != null && Board.getPieces()[r - 1][c + 1].getColor().equals("black")) availablePositions.add(new int[] {r - 1, c + 1});
                if (Board.getPieces()[r - 1][c + 1] != null && Board.getPieces()[r - 1][c + 1].getColor().equals("white")) Board.getBoardScanner()[r - 1][c + 1].isWhiteMove();
                //captures enpassant, right : special case
                if ((Board.getPieces()[r][c + 1] != null) && (Board.getPieces()[r][c + 1] instanceof Pawn) && (Board.getPieces()[r][c + 1].getColor().equals("black"))) {
                    availablePositions.add(new int[]{r - 1, c + 1});

                    Pawn p = (Pawn) Board.getPieces()[r][c + 1];

                    if (p.isEnPassant() && (rInput == r - 1)
                            && (cInput == c + 1)) {
                        Board.getBlackPieces().remove(Board.getPieces()[r][c + 1]);
                        captures.add(Board.getPieces()[r][c + 1]);
                        Board.getPieces()[r][c + 1] = null;
                    }
                }

            }

            //captures left
            if (r - 1 >= 0 && c - 1 >= 0) {
                if (r - 1 > 7 || c - 1 > 7) {
                        if (scanning) System.out.println("SCANNING!!!");
                        System.out.println("r:" + r + "c:" + c);
                        System.out.println("CP:" + currentPosition[0] + ", " + currentPosition[1]);
                        System.out.println("IP:" + inputPosition[0] + ", " + inputPosition[1]);
                }

                if (Board.getPieces()[r - 1][c - 1] != null && Board.getPieces()[r - 1][c - 1].getColor().equals("black")) availablePositions.add(new int[] {r - 1, c - 1});
                if (Board.getPieces()[r - 1][c - 1] != null && Board.getPieces()[r - 1][c - 1].getColor().equals("white")) Board.getBoardScanner()[r - 1][c - 1].isWhiteMove();
                
                //captures enpassant, left : special case
                if ((Board.getPieces()[r][c - 1] != null) && (Board.getPieces()[r][c - 1] instanceof Pawn) && (Board.getPieces()[r][c - 1].getColor().equals("black"))) {
                    availablePositions.add(new int[]{r - 1, c - 1});

                    Pawn p = (Pawn) Board.getPieces()[r][c - 1];
                    if (p.isEnPassant() && rInput == r - 1
                            && cInput == c - 1) {
                        Board.getBlackPieces().remove(Board.getPieces()[r][c+1]);
                        captures.add(p);
                        Board.getPieces()[r][c - 1] = null;
                    }
                }
            }
        } else {
            //one space move
            if (r + 1 < 8 && Board.getPieces()[r + 1][c] == null) {
                availablePositions.add(new int[] {r + 1, c});
                //starting two space move
                if (starting && r + 2 < 8 && Board.getPieces()[r + 2][c] == null) {
                    availablePositions.add(new int[] {r + 2, c});
                    if (rInput == r + 2 && cInput == c && !scanning) enPassant = true;
                }
            }

            //captures right
            if (r + 1 < 8 && c + 1 < 8) {
                if (Board.getPieces()[r + 1][c + 1] != null && Board.getPieces()[r + 1][c + 1].getColor().equals("white")) availablePositions.add(new int[] {r + 1, c + 1});
                if (Board.getPieces()[r + 1][c + 1] != null && Board.getPieces()[r + 1][c + 1].getColor().equals("black")) Board.getBoardScanner()[r + 1][c + 1].isBlackMove();
                if ((Board.getPieces()[r][c + 1] != null) && (Board.getPieces()[r][c + 1] instanceof Pawn) && (Board.getPieces()[r][c + 1].getColor().equals("white"))) {
                    availablePositions.add(new int[]{r + 1, c + 1});
                    Pawn p = (Pawn) Board.getPieces()[r][c + 1];
                    if (p.isEnPassant() && rInput == r + 1
                            && cInput == c + 1) {
                        Board.getWhitePieces().remove(Board.getPieces()[r][c + 1]);
                        captures.add(p);
                        Board.getPieces()[r][c + 1] = null;
                    }
                }
            }

            //captures left
            if (r + 1 < 8 && c - 1 >= 0) {
                if (Board.getPieces()[r + 1][c - 1] != null && Board.getPieces()[r + 1][c - 1].getColor().equals("white")) availablePositions.add(new int[] {r + 1, c - 1});
                if (Board.getPieces()[r + 1][c - 1] != null && Board.getPieces()[r + 1][c - 1].getColor().equals("black")) Board.getBoardScanner()[r + 1][c + 1].isBlackMove();

                if ((Board.getPieces()[r][c - 1] != null) && (Board.getPieces()[r][c - 1] instanceof Pawn) && (Board.getPieces()[r][c - 1].getColor().equals("white"))) {
                    availablePositions.add(new int[]{r + 1, c - 1});
                    Pawn p = (Pawn) Board.getPieces()[r][c - 1];
                    if (p.isEnPassant() && rInput == r + 1
                            && cInput == c - 1) {
                        Board.getWhitePieces().remove(Board.getPieces()[r][c - 1]);
                        captures.add(p);
                        Board.getPieces()[r][c - 1] = null;
                    }
                }
            }
        }

        setCurrentPosition(currentPosition); setInputPosition(inputPosition); setR(r); setC(c); setAvailablePositions(availablePositions); setCaptures(captures);

        boolean moveAvailable = move(rInput, cInput);
        if (moveAvailable && !scanning) {
            if (color.equals("white") && rInput == 0) {
                promotion = true;
            }
            if (color.equals("black") && rInput == 7) {
                promotion = true;
            }
            starting = false;
            return true;
        }

        return false;
    }

    public void scanning() {
        scanning = !scanning;
    }

    public boolean isPromoted() { return promotion; }

    public boolean isEnPassant() { return enPassant; }

    @Override
    public String getName() { return name; }

    public ArrayList<int[]> getAvailablePositions() {
        return availablePositions;
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
        name = this.color.equals("white") ? "P" : "p";
    }
}
