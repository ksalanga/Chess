import java.util.ArrayList;

public class King extends PieceMoves implements ChessPiece {
    private String color;
    private String name;
    private int[] currentPosition;
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
        this.currentPosition = new int[]{addressChange(copy.currentPosition[0]), addressChange(copy.currentPosition[1])};
        this.starting = copy.starting;
        this.scanning = copy.scanning;
    }

    public boolean move(int[] inputPosition, ArrayList<ChessPiece> captures) {
        availablePositions = new ArrayList<int[]>();

        int r = currentPosition[0];
        int c = currentPosition[1];
        int rInput = inputPosition[0];
        int cInput = inputPosition[1];

        //rInput == r && (starting && c + 2 == cInput && Board.getPieces()[r][cInput + 1] instanceof Rook) || (starting && c - 2 == cInput && Board.getPieces()[r][cInput - 1] instanceof Rook)
        if (starting) {
            boolean kingSideCastle = true;
            boolean queenSideCastle = true;

            if ((color.equals("white") && Board.getBoardScanner()[Board.getWhiteKing()[0]][Board.getWhiteKing()[1]].isBlackMove())
            || (color.equals("black") && Board.getBoardScanner()[Board.getBlackKing()[0]][Board.getBlackKing()[1]].isWhiteMove())) {
                kingSideCastle = false;
                queenSideCastle = false;
            } else {
                for (int i = 1; i < 3; i++) {
                    if (Board.getPieces()[r][c + i] != null) {
                        kingSideCastle = false; //if tiles in between has a piece or is under white/black attack
                        break;
                    } else if (color.equals("white") && Board.getBoardScanner()[r][c + i].isBlackMove()) {
                        kingSideCastle = false;
                        break;
                    } else if (color.equals("black") && Board.getBoardScanner()[r][c + i].isWhiteMove()) {
                        kingSideCastle = false;
                        break;
                    }
                }
                if (kingSideCastle) {
                    availablePositions.add(new int[]{r, c + 2});
                }

                for (int i = 1; i < 4; i++) {
                    if (Board.getPieces()[r][c - i] != null) {
                        queenSideCastle = false;
                        break;
                    } else if (color.equals("white") && Board.getBoardScanner()[r][c - i].isBlackMove()) {
                        queenSideCastle = false;
                        break;
                    } else if (color.equals("black") && Board.getBoardScanner()[r][c - i].isWhiteMove()) {
                        queenSideCastle = false;
                        break;
                    }
                }
                if (queenSideCastle) {
                    availablePositions.add(new int[]{r, c - 2});
                }
            }

            if ((kingSideCastle && Board.getPieces()[r][c + 3] != null && ((Rook) Board.getPieces()[r][c + 3]).isStarting())
                    || (queenSideCastle && Board.getPieces()[r][c - 4] != null && ((Rook) Board.getPieces()[r][c - 4]).isStarting())) {

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

        for (int i = Math.max(r - 1, 0); i <= Math.min(r + 1, 7); i++) {
            for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, 7); j++) {
                boolean white = color.equals("white");
                ChessPiece piece = Board.getPieces()[i][j];
                BoardScanner bScanner = Board.getBoardScanner()[i][j];
                if (!(i == r && j == c)
                        && (piece == null)
                        && (white ? !bScanner.isBlackMove() : !bScanner.isWhiteMove())) {
                    availablePositions.add(new int[] {i, j});
                }
                if (!(i == r && j == c)
                        && (piece != null)
                        && (white ? !bScanner.isBlackMove() : !bScanner.isWhiteMove())) {
                    if (white ? piece.getColor().equals("black") : piece.getColor().equals("white")) {
                        availablePositions.add(new int[] {i, j});
                    }
                }
            }
        }

        setCurrentPosition(currentPosition); setInputPosition(inputPosition); setR(r); setC(c); setAvailablePositions(availablePositions); setCaptures(captures);

        boolean moveAvailable = move(rInput, cInput);
        if (moveAvailable && !scanning) return true;

        if (!scanning) starting = false;
        return false;
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
