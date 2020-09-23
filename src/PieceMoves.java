import java.util.ArrayList;
import java.util.Iterator;

public class PieceMoves {
    //can introduce private variables in this class later to shorten parameter length
    private int r;
    private int c;
    private ArrayList<int[]> availablePositions;
    private ArrayList<ChessPiece> captures;
    private int[] currentPosition;
    private int[] inputPosition;

    //int r, int c, int x, int y, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions
    protected void moveAcross(int x, int y) {
        if (r < 0 || r > 7 || c < 0 || c > 7) return;
        int[] position = new int[] {r,c};
        if (Board.getPieces()[r][c] != null) {
            int row = currentPosition[0];
            int col = currentPosition[1];
            if (Board.getPieces()[row][col].getColor().equals("white")) {
                if (Board.getPieces()[r][c].getColor().equals("black")) {
                    availablePositions.add(position);
                }
            } else {
                if (Board.getPieces()[r][c].getColor().equals("white")) {
                    availablePositions.add(position);
                }
            }
            return;
            //when we accessed the first position it wasn't null so it just returned
        }

        //this needs to be an enemy piece so that it stops and it still adds to the position, if its not an enemy piece we can't add that to available positions
        availablePositions.add(position);
        r += y;
        c += x;
        moveAcross(x, y);
    }

    //int[] currentPosition, int[] inputPosition, int r, int c, int rInput, int cInput, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, ArrayList<ChessPiece> captures, BoardScanner[][] bs
    protected boolean move(int rInput, int cInput) {
        ChessPiece[][] board = Board.getPieces();
//        for (int i = 0; i < availablePositions.size(); i++) {
//            System.out.printf("(%d,%d)", availablePositions.get(i)[0], availablePositions.get(i)[1]);
//            System.out.println();
//        }

        if (scanAvailablePositions()) {
            if (board[rInput][cInput] != null) { // the problem is here. also we need to check king moves it's not giving the right numbers
                if (board[rInput][cInput].getColor().equals("white"))  Board.getWhitePieces().remove(board[rInput][cInput]);
                else Board.getBlackPieces().remove(board[rInput][cInput]); //no longer in the board
                if (captures != null) captures.add(board[rInput][cInput]);
            }
            board[rInput][cInput] = board[r][c];

            currentPosition[0] = rInput;
            currentPosition[1] = cInput;

            board[r][c] = null;
            return true;
        } else {
            return false;
        }
    }

    //its pointing to the original board and its getting a null error!!!!
    //pointing to the original board which hasn't been updated yet, which has a moved piece,
    //going through the arraylist then, it goes to the copy white pawn, which has a position 6,0 but the board doesn't have that pawn anymore because when we moved that legal move its null
    //thats why its only going through the white pieces
    //maybe try creating an arraylist of copies and returning those so it doesnt interfere with the original pieces.

    //int[] inputPosition, ArrayList<int[]> availablePositions, ChessPiece[][] boardPositions, BoardScanner[][] bs
    private boolean scanAvailablePositions() {

        //should probably do a cleansing here.
        //start scanning pieces and
        ChessPiece[][] board = Board.getPieces();
        BoardScanner[][] bs = Board.getBoardScanner();
        int rInput = inputPosition[0]; //input Position for rook is null
        int cInput = inputPosition[1];

        boolean flag = false;

        boolean white = board[r][c].getColor().equals("white");

        for (int[] availablePosition : availablePositions) {
            int row = availablePosition[0];
            int col = availablePosition[1];

            if (rInput == row && cInput == col) flag = true;

            if (Board.getPieces()[r][c] instanceof King && (col == c + 2 || col == c - 2)) {
                flag = false; //special case for castling
            } else {
                if (white) bs[row][col].whiteMove();
                else bs[row][col].blackMove();
            }
        }
        return flag;
    }

    //1 possibility: Have all of the moves ready with the test moves method, then when the piece actually moves, it can just pick from all of the possible moves fo reach piece

    public boolean legalMoveAvailable(boolean whitesTurn) {

        boolean flag = false;

        if (whitesTurn) {
            for (int i = 0; i < Board.getWhitePieces().size(); i++) {
                ChessPiece piece = Board.getWhitePieces().get(i);
                if (piece instanceof King) Board.scanBlackAttacks();
                piece.findPositions();

                ArrayList<int[]> availablePositions = piece.getAvailablePositions();
                piece.saveCurrentPosition();

                Iterator<int[]> iterator = availablePositions.iterator();

                while (iterator.hasNext()) {
                    int[] position = iterator.next();

                    Board.saveCurrentBoard();
                    Board.scanWhitePiece(piece, position);

                    Board.reInitialize();

                    Board.scanBlackAttacks(); //this method is going to change

                    int kingRow = Board.getWhiteKing()[0];
                    int kingColumn = Board.getWhiteKing()[1];
                    if (Board.getBoardScanner()[kingRow][kingColumn].isBlackMove()) {
                        iterator.remove();
                    }
                    Board.revertToPreviousBoard();
                    piece.revertToPreviousPosition();
                }

                if (availablePositions.size() > 0) flag = true;
            }
        } else {
            for (int i = 0; i < Board.getBlackPieces().size(); i++) {
                ChessPiece piece = Board.getBlackPieces().get(i);
                if (piece instanceof King) Board.scanWhiteAttacks();
                piece.findPositions();

                ArrayList<int[]> availablePositions = piece.getAvailablePositions();

                piece.saveCurrentPosition();

                Iterator<int[]> iterator = availablePositions.iterator();

                while (iterator.hasNext()) {
                    int[] position = iterator.next();

                    Board.saveCurrentBoard();

                    Board.scanBlackPiece(piece, position);

                    Board.reInitialize();

                    Board.scanWhiteAttacks();

                    int kingRow = Board.getBlackKing()[0];
                    int kingColumn = Board.getBlackKing()[1];
                    if (Board.getBoardScanner()[kingRow][kingColumn].isWhiteMove()) {
                        iterator.remove();
                    }
                    Board.revertToPreviousBoard();
                    piece.revertToPreviousPosition();

                }

                if (availablePositions.size() > 0) flag = true;
            }
        }
        return flag;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getR() {return r;}

    public int getC() {return c;}

    public ArrayList<int[]> getAvailablePositions() {
        return availablePositions;
    }

    public void setAvailablePositions(ArrayList<int[]> availablePositions) { this.availablePositions = availablePositions; }

    public void setCaptures(ArrayList<ChessPiece> captures) {
        this.captures = captures;
    }

    public void setCurrentPosition(int[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setInputPosition(int[] inputPosition) {
        this.inputPosition = inputPosition;
    }
}