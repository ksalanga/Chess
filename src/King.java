import java.util.ArrayList;

public class King extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;
    private int[] savedPosition;
    private boolean starting = true;
    private boolean scanning = false;
    private ArrayList<int[]> availablePositions;

    public King(int[] currentPosition, String color) {
        this.currentPosition = currentPosition;
        this.color = color;
        name = color.equals("white") ? "♔" : "♚";
    }

    public King(King copy) {
        this.color = copy.color;
        this.name = copy.name;
        this.currentPosition = copy.currentPosition;
        this.starting = copy.starting;
        this.scanning = copy.scanning;
        this.availablePositions = copy.availablePositions;
    }

    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures) {

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

        if (scanning) System.out.printf("%s Pre Current Position: (%d, %d)", color, currentPosition[0], currentPosition[1]);
        //we keep this in the move method.
        //rInput == r && (starting && c + 2 == cInput && Board.getPieces()[r][cInput + 1] instanceof Rook) || (starting && c - 2 == cInput && Board.getPieces()[r][cInput - 1] instanceof Rook)
        if (starting) {
            if ((kingSideCastle() && Board.getPieces()[r][c + 3] != null && ((Rook) Board.getPieces()[r][c + 3]).isStarting())
                    || (queenSideCastle() && Board.getPieces()[r][c - 4] != null && ((Rook) Board.getPieces()[r][c - 4]).isStarting())) {

                if (cInput == c + 2 && rInput == r) {
                    Board.getPieces()[rInput][cInput] = Board.getPieces()[r][c];
                    Board.getPieces()[rInput][cInput].setPosition(new int[]{rInput, cInput});
                    Board.getPieces()[r][c] = null;
                    Board.getPieces()[r][cInput - 1] = Board.getPieces()[r][c + 3];
                    Board.getPieces()[r][cInput - 1].setPosition(new int[]{r, cInput - 1});
                    Board.getPieces()[r][c + 3] = null;
                    starting = false;
                    return true;
                } else if (cInput == c - 2 && rInput == r) {
                    Board.getPieces()[rInput][cInput] = Board.getPieces()[r][c];
                    Board.getPieces()[rInput][cInput].setPosition(new int[]{rInput, cInput});
                    Board.getPieces()[r][c] = null;
                    Board.getPieces()[r][cInput + 1] = Board.getPieces()[r][c - 4];
                    Board.getPieces()[r][cInput + 1].setPosition(new int[]{r, cInput + 1});
                    Board.getPieces()[r][c - 4] = null;
                    starting = false;
                    return true;
                }
            }
        }

        setCurrentPosition(currentPosition); setInputPosition(inputPosition); setR(r); setC(c); setAvailablePositions(availablePositions); setCaptures(captures);

        boolean moveAvailable = move(rInput, cInput);

        if (moveAvailable && scanning) {
            System.out.printf("%s Input: (%d, %d)", color, rInput, cInput);
            System.out.printf("%s Post Current Position: (%d, %d)", color, currentPosition[0], currentPosition[1]);
            System.out.printf("White King Position: (%d, %d)", Board.getWhiteKing()[0], Board.getWhiteKing()[1]);
        }
        if (moveAvailable && !scanning) return true;

        if (!scanning) starting = false;
        return false;
    }

    public void findPositions() {
        availablePositions = new ArrayList<int[]>();

        int r = currentPosition[0];
        int c = currentPosition[1];

        for (int i = Math.max(r - 1, 0); i <= Math.min(r + 1, 7); i++) {
            for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, 7); j++) {
                boolean white = color.equals("white");
                ChessPiece piece = Board.getPieces()[i][j];
                if (!(i == r && j == c)
                        && (piece == null)) {
                    availablePositions.add(new int[] {i, j});
                }
                if (!(i == r && j == c)
                        && (piece != null)) {
                    if (white ? piece.getColor().equals("black") : piece.getColor().equals("white")) {
                        availablePositions.add(new int[] {i, j});
                    }
                }
            }
        }

        if (starting) {
            if (kingSideCastle()) {
                availablePositions.add(new int[]{r, c + 2});
            }

            if (queenSideCastle()) {
                availablePositions.add(new int[]{r, c - 2});
            }
        }
    }

    private boolean kingSideCastle() {
        if ((color.equals("white") && Board.getBoardScanner()[Board.getWhiteKing()[0]][Board.getWhiteKing()[1]].isBlackMove())
                || (color.equals("black") && Board.getBoardScanner()[Board.getBlackKing()[0]][Board.getBlackKing()[1]].isWhiteMove())) {
            return false;
        }

        int r = currentPosition[0];
        int c = currentPosition[1];

        for (int i = 1; i < 3; i++) {
            if (Board.getPieces()[r][c + i] != null) {
                return false;
            } else if (color.equals("white") && Board.getBoardScanner()[r][c + i].isBlackMove()) {
                return false;
            } else if (color.equals("black") && Board.getBoardScanner()[r][c + i].isWhiteMove()) {
                return false;
            }
        }
        return true;
    }

    private boolean queenSideCastle() {
        if ((color.equals("white") && Board.getBoardScanner()[Board.getWhiteKing()[0]][Board.getWhiteKing()[1]].isBlackMove())
                || (color.equals("black") && Board.getBoardScanner()[Board.getBlackKing()[0]][Board.getBlackKing()[1]].isWhiteMove())) {
            return false;
        }

        int r = currentPosition[0];
        int c = currentPosition[1];

        for (int i = 1; i < 4; i++) {
            if (Board.getPieces()[r][c - i] != null) {
                return false;
            } else if (color.equals("white") && Board.getBoardScanner()[r][c - i].isBlackMove()) {
                return false;
            } else if (color.equals("black") && Board.getBoardScanner()[r][c - i].isWhiteMove()) {
                return false;
            }
        }
        return true;
    }

    public void scanning() {
        scanning = !scanning;
    }

    public boolean castle() {
        return starting;
    }

    @Override
    public String getName() {
        return name;
    }

    public ArrayList<int[]> getAvailablePositions() {
        return availablePositions;
    }

    public void saveCurrentPosition() {
        savedPosition = new int[]{currentPosition[0], currentPosition[1]};
    }

    public void revertToPreviousPosition() {
        currentPosition[0] = savedPosition[0];
        currentPosition[1] = savedPosition[1];
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPosition(int[] position) {
        currentPosition = position;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
        name = this.color.equals("white") ? "K" : "k";
    }
}
