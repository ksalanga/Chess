import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private static ChessPiece[][] Pieces;

    private static BoardScanner[][] boardScanner;

    private static ArrayList<ChessPiece> whitePieces;
    private static ArrayList<ChessPiece> blackPieces;

    private static ChessPiece[][] boardCopy;

    private static BoardScanner[][] boardScannerCopy;

    private static ArrayList<ChessPiece> copyWhitePieces;
    private static ArrayList<ChessPiece> copyBlackPieces;

    private static boolean flipped;

    //Board.saveCurrentBoard();
    //                    Board.scanWhitePiece(piece, position);
    //revertToPreviousBoard

    public Board() {
        Pieces = new ChessPiece[8][8];
        boardScanner = new BoardScanner[8][8];
        reInitialize();
        whitePieces = new ArrayList<ChessPiece>();
        blackPieces = new ArrayList<ChessPiece>();
        boardCopy = new ChessPiece[8][8];
        boardScannerCopy = new BoardScanner[8][8];
        flipped = false;
    }

    public void setPositions() {

        //setting Kings
        Pieces[7][4] = new King(new int[] {7, 4}, "white"); whitePieces.add(Pieces[7][4]);
        Pieces[0][4] = new King(new int[] {0, 4}, "black"); blackPieces.add(Pieces[0][4]);

        //setting black and white pawns
        for (int i = 0; i < 8; i++) {
            Pieces[1][i] = new Pawn(new int[] {1, i}, "black");
            blackPieces.add(Pieces[1][i]);
            Pieces[6][i] = new Pawn(new int[] {6, i}, "white");
            whitePieces.add(Pieces[6][i]);
        }

        //White Minor & Major Pieces
        Pieces[7][0] = new Rook(new int[]{7, 0}, "white"); whitePieces.add(Pieces[7][0]);
        Pieces[7][1] = new Knight(new int[]{7, 1}, "white"); whitePieces.add(Pieces[7][1]);
        Pieces[7][2] = new Bishop(new int[]{7, 2}, "white"); whitePieces.add(Pieces[7][2]);
        Pieces[7][3] = new Queen(new int[] {7, 3}, "white"); whitePieces.add(Pieces[7][3]);
        Pieces[7][5] = new Bishop(new int[]{7, 5}, "white"); whitePieces.add(Pieces[7][5]);
        Pieces[7][6] = new Knight(new int[]{7, 6}, "white"); whitePieces.add(Pieces[7][6]);
        Pieces[7][7] = new Rook(new int[]{7, 7}, "white"); whitePieces.add(Pieces[7][7]);

        //Black Minor & Major Pieces
        Pieces[0][0] = new Rook(new int[]{0, 0}, "black"); blackPieces.add(Pieces[0][0]);
        Pieces[0][1] = new Knight(new int[]{0, 1}, "black"); blackPieces.add(Pieces[0][1]);
        Pieces[0][2] = new Bishop(new int[]{0, 2}, "black"); blackPieces.add(Pieces[0][2]);
        Pieces[0][3] = new Queen(new int[] {0, 3}, "black"); blackPieces.add(Pieces[0][3]);
        Pieces[0][5] = new Bishop(new int[]{0, 5}, "black"); blackPieces.add(Pieces[0][5]);
        Pieces[0][6] = new Knight(new int[]{0, 6}, "black"); blackPieces.add(Pieces[0][6]);
        Pieces[0][7] = new Rook(new int[]{0, 7}, "black"); blackPieces.add(Pieces[0][7]);

    }

    public static void printBoard(ArrayList<ChessPiece> blackCaptures, ArrayList<ChessPiece> whiteCaptures) {
        System.out.println();
        if (!blackCaptures.isEmpty()) {
            System.out.print("\t[ ");
            for (ChessPiece p : blackCaptures) {
                System.out.print(p.getName() + " ");
            }
            System.out.println("]");
        }

        for (int i = 0; i < Pieces.length; i++) {
            System.out.print(8 - i + "\t");
            for (int j = 0; j < Pieces[i].length; j++) {
                if (Pieces[i][j] != null) System.out.format("%s\t", Pieces[i][j].getName());
                else {
                    if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) System.out.format("%s\t", "⬛");
                    else System.out.format("%s\t", "⬜");
                }
            }
            System.out.println();
        }

        System.out.print("\t");
        for (int i = 0; i < 8; i++) {
            System.out.format("%s\t", (char) (65 + i));
        }

        if (!whiteCaptures.isEmpty()) {
            System.out.println();
            System.out.print("\t[ ");
            for (ChessPiece p : whiteCaptures) {
                System.out.print(p.getName() + " ");
            }
            System.out.println("]");
        }
        System.out.println();
    }

    public static void printScanner() {
        for (int i = 0; i < boardScanner.length; i++) {
            System.out.print(8 - i + "\t");
            for (int j = 0; j < boardScanner[i].length; j++) {
                if (boardScanner[i][j].isBlackMove()) {
                    System.out.format("%s\t", "B");
                } else {
                    System.out.format("%s\t", "⬛");
                }
            }
            System.out.println();
        }
        System.out.print("\t");
        for (int i = 0; i < 8; i++) {
            System.out.format("%s\t", (char) (65 + i));
        }
    }

    public static void flipBoard() {
        for (int i = 0; i < Pieces.length/2; i++) {
            for (int j = 0; j < Pieces[i].length; j++) {
                if (Pieces[i][j] != null && Pieces[7 - i][j] != null) {
                    ChessPiece temp = Pieces[i][j];
                    Pieces[i][j] = Pieces[7 - i][j];
                    Pieces[i][j].setPosition(new int[]{i, j});

                    Pieces[7 - i][j] = temp;
                    Pieces[7 - i][j].setPosition(new int[]{7 - i, j});
                } else if (Pieces[i][j] == null && Pieces[7 - i][j] != null){
                    Pieces[i][j] = Pieces[7 - i][j];
                    Pieces[i][j].setPosition(new int[]{i, j});
                    Pieces[7 - i][j] = null;
                } else if (Pieces[7 - i][j] == null && Pieces[i][j] != null) {
                    Pieces[7 - i][j] = Pieces[i][j];
                    Pieces[7 - i][j].setPosition(new int[]{(7-i), j});
                    Pieces[i][j] = null;
                }
            }
        }
        flipped = !flipped;
    }

    public int[] convertToCoords(String tile) {
        if (tile.length() != 2 || !Character.isLetter(tile.charAt(0)) || Character.isLetter(tile.charAt(1))) return new int[] {-1, -1};
        tile = tile.toLowerCase();
        int row = 8 - Character.getNumericValue(tile.charAt(1));
        char c = Character.toLowerCase(tile.charAt(0));
        int column = c % 97;

        if ((row < 0 || row > 7) || (column < 0 || column > 7)) return new int[]{-1, -1};

        return new int[] {row, column};
    }

    public static ChessPiece[][] getPieces() {
        return Pieces;
    }

    public static String convertToTiles(int[] coords) {
        char[] files = new char[]{'a', 'b' , 'c', 'd', 'e', 'f', 'g', 'h'};
        int row = 8 - coords[0];

        String tile = files[coords[1]] + Integer.toString(row);
        return tile;
    }

    public static void pawnPromotion(boolean whitesTurn, Scanner sc) {
        if (whitesTurn) {
            for (int i = 0; i < whitePieces.size(); i++) {
                ChessPiece piece = whitePieces.get(i);
                int[] currentPosition = piece.getCurrentPosition();
                if (piece instanceof Pawn && currentPosition[0] == 0) {
                    System.out.println("1) Queen, 2) Rook, 3) Bishop, 4)Knight");
                    int selection = sc.nextInt();
                    while (!(selection > 0 && selection < 5)) {
                        System.out.println("1) Queen, 2) Rook, 3) Bishop, 4) Knight");
                        selection = sc.nextInt();
                    }
                    switch (selection) {
                        case 1:
                            piece = new Queen(currentPosition, "white");
                            break;
                        case 2:
                            piece = new Rook(currentPosition, "white");
                            break;
                        case 3:
                            piece = new Bishop(currentPosition, "white");
                            break;
                        case 4:
                            piece = new Knight(currentPosition, "white");
                            break;
                    }
                    whitePieces.remove(whitePieces.get(i));
                    whitePieces.add(piece);
                    int row = currentPosition[0];
                    int col = currentPosition[1];
                    Board.getPieces()[row][col] = piece;
                    sc.nextLine();
                    break;
                }
            }
        } else {
            for (int i = 0; i < blackPieces.size(); i++) {
                ChessPiece piece = blackPieces.get(i);
                int[] currentPosition = piece.getCurrentPosition();
                if (piece instanceof Pawn && currentPosition[0] == 7) {
                    System.out.println("1) Queen, 2) Rook, 3) Bishop, 4)Knight");
                    int selection = sc.nextInt();
                    while (!(selection > 0 && selection < 5)) {
                        System.out.println("1) Queen, 2) Rook, 3) Bishop, 4) Knight");
                        selection = sc.nextInt();
                    }
                    switch (selection) {
                        case 1:
                            piece = new Queen(currentPosition, "black");
                            break;
                        case 2:
                            piece = new Rook(currentPosition, "black");
                            break;
                        case 3:
                            piece = new Bishop(currentPosition, "black");
                            break;
                        case 4:
                            piece = new Knight(currentPosition, "black");
                            break;
                    }
                    blackPieces.remove(blackPieces.get(i));
                    blackPieces.add(piece);
                    int row = currentPosition[0];
                    int col = currentPosition[1];
                    Board.getPieces()[row][col] = piece;
                    sc.nextLine();
                    break;
                }
            }
        }
    }

    public static boolean isFlipped() {
        return flipped;
    }

    public static BoardScanner[][] getBoardScanner() { return boardScanner; }

    public static void reInitialize() {
        for (int i = 0; i < boardScanner.length; i++) {
            for (int j = 0; j < boardScanner[i].length; j++) {
                boardScanner[i][j] = new BoardScanner();
            }
        }
    }

    public static void scanWhiteAttacks() {
        for (ChessPiece piece : whitePieces) {
            piece.findPositions();

            ArrayList<int[]> availablePositions = piece.getAvailablePositions();

            if (piece instanceof Pawn) {
                for (int[] position: availablePositions) {
                    int r = position[0];
                    int c = position[1];
                    if (c != piece.getCurrentPosition()[1]) { //captures sideways
                        Board.getBoardScanner()[r][c].whiteMove();
                    }
                }
            } else if ((piece instanceof King) && ((King) piece).castle()) {
                for (int[] position: availablePositions) {
                    int r = position[0];
                    int c = position[1];

                    if (Math.abs(piece.getCurrentPosition()[1] - c) < 2) {
                        Board.getBoardScanner()[r][c].whiteMove();
                    }
                }

            } else {
                for (int[] position: availablePositions) {
                    int r = position[0];
                    int c = position[1];
                    Board.getBoardScanner()[r][c].whiteMove();
                }
            }
        }
    }

    public static void scanBlackAttacks() {
        for (ChessPiece piece : blackPieces) {
            piece.findPositions();
            ArrayList<int[]> availablePositions = piece.getAvailablePositions();

            //just add special case for pawns and king and you should be finished
            if (piece instanceof Pawn) {
                for (int[] position: availablePositions) {
                    int r = position[0];
                    int c = position[1];
                    if (c != piece.getCurrentPosition()[1]) { //captures sideways
                        Board.getBoardScanner()[r][c].blackMove();
                    }
                }
            } else if (piece instanceof King && ((King) piece).castle()) {
                for (int[] position: availablePositions) {
                    int r = position[0];
                    int c = position[1];

                    if (Math.abs(piece.getCurrentPosition()[1] - c) < 2) {
                        Board.getBoardScanner()[r][c].blackMove();
                    }
                }

            } else {
                for (int[] position: availablePositions) {
                    int r = position[0];
                    int c = position[1];
                    Board.getBoardScanner()[r][c].blackMove();
                }
            }
        }
    }

    public static void scanWhitePiece(ChessPiece piece, int[] position) {
        PieceMoves.scanningSwitch();
        switch (piece.getName()) {
            case "♔":
                ((King) piece).scanning(); //start scanning
                piece.move(position, null);
                ((King) piece).scanning(); //stop scanning
                break;
            case "♖":
                ((Rook) piece).scanning(); //start scanning
                piece.move(position, null);
                ((Rook) piece).scanning(); //stop scanning
                break;
            case "♙":
                ((Pawn) piece).scanning(); //start scanning
                piece.move(position, null);
                ((Pawn) piece).scanning(); //stop scanning
                break;
            default:
                piece.move(position, null);
                break;
        }
        PieceMoves.scanningSwitch();
    }

    public static void scanBlackPiece(ChessPiece piece, int[] position) {
        PieceMoves.scanningSwitch();
        switch (piece.getName()) {
            case "♚":
                ((King) piece).scanning(); //start scanning
                piece.move(position, null);
                ((King) piece).scanning(); //stop scanning
                break;
            case "♜":
                ((Rook) piece).scanning(); //start scanning
                piece.move(position, null);
                ((Rook) piece).scanning(); //stop scanning
                break;
            case "♟":
                ((Pawn) piece).scanning(); //start scanning
                piece.move(position, null);
                ((Pawn) piece).scanning(); //stop scanning
                break;
            default:
                piece.move(position, null);
                break;
        }
        PieceMoves.scanningSwitch();
    }

    public static void saveCurrentBoard() {
        copyBoard();
        copyBoardScanner();
    }

    public static void revertToPreviousBoard() {
        setPieces(boardCopy);
        setBoardScanner(boardScannerCopy);
        setWhitePieces(copyWhitePieces);
        setBlackPieces(copyBlackPieces);
    }

    private static void copyBoard() {
        copyWhitePieces = new ArrayList<ChessPiece>();
        copyBlackPieces = new ArrayList<ChessPiece>();
        boardCopy = new ChessPiece[8][8];

        int whiteSize = whitePieces.size();
        int blackSize = blackPieces.size();

        for (int i = 0; i < whiteSize; i++) {
            int copyRow = whitePieces.get(i).getCurrentPosition()[0];
            int copyCol = whitePieces.get(i).getCurrentPosition()[1];

            switch (whitePieces.get(i).getName()) {
                case "♔":
                    boardCopy[copyRow][copyCol] = new King((King) whitePieces.get(i));
                    break;
                case "♕":
                    boardCopy[copyRow][copyCol] = new Queen((Queen) whitePieces.get(i));
                    break;
                case "♗":
                    boardCopy[copyRow][copyCol] = new Bishop((Bishop) whitePieces.get(i));
                    break;
                case "♘":
                    boardCopy[copyRow][copyCol] = new Knight((Knight) whitePieces.get(i));
                    break;
                case "♖":
                    boardCopy[copyRow][copyCol] = new Rook((Rook) whitePieces.get(i));
                    break;
                case "♙":
                    boardCopy[copyRow][copyCol] = new Pawn((Pawn) whitePieces.get(i));
                    break;
            }

            copyWhitePieces.add(boardCopy[copyRow][copyCol]);
        }

        for (int i = 0; i < blackSize; i++) {
            int copyRow = blackPieces.get(i).getCurrentPosition()[0];
            int copyCol = blackPieces.get(i).getCurrentPosition()[1];

            switch (blackPieces.get(i).getName()) {
                case "♚":
                    boardCopy[copyRow][copyCol] = new King((King) blackPieces.get(i));
                    break;
                case "♛":
                    boardCopy[copyRow][copyCol] = new Queen((Queen) blackPieces.get(i));
                    break;
                case "♝":
                    boardCopy[copyRow][copyCol] = new Bishop((Bishop) blackPieces.get(i));
                    break;
                case "♞":
                    boardCopy[copyRow][copyCol] = new Knight((Knight) blackPieces.get(i));
                    break;
                case "♜":
                    boardCopy[copyRow][copyCol] = new Rook((Rook) blackPieces.get(i));
                    break;
                case "♟":
                    boardCopy[copyRow][copyCol] = new Pawn((Pawn) blackPieces.get(i));
                    break;
            }

            copyBlackPieces.add(boardCopy[copyRow][copyCol]);
        }
    }

    private static void copyBoardScanner() {
        boardScannerCopy = new BoardScanner[8][8];

        for (int i = 0; i < boardScanner.length; i++) {
            for (int j = 0; j < boardScanner[i].length; j++) {
                boardScannerCopy[i][j] = new BoardScanner(boardScanner[i][j]);
            }
        }
    }

    private static void setPieces(ChessPiece[][] newBoard) {
        Pieces = newBoard;
    }

    private static void setBoardScanner(BoardScanner[][] newScanner) {
        boardScanner = newScanner;
    }
    //im gonna time how fast my program runs with/ without multithreading.
    //board can find the originating source of the attack and return that arraylist of positions, because the board is the one showing the tiles to the pieces

    //go across the board +r, same c; -r, same c; -c, same r; c, same r; +c, +c; -c + c

    //multithread potentially calculate the +c, -c, +r, -r,

    //could add multithreading to scanning all the pieces too.

    //could make a mathematical equation to find the origin of the attack of the piece
    public static ArrayList<ChessPiece> getWhitePieces() {
        return whitePieces;
    }

    public static ArrayList<ChessPiece> getBlackPieces() {
        return blackPieces;
    }

    public static ArrayList<ChessPiece> getCopyWhitePieces() {
        return copyWhitePieces;
    }

    public static ArrayList<ChessPiece> getCopyBlackPieces() {
        return copyBlackPieces;
    }

    private static void setWhitePieces(ArrayList<ChessPiece> wP) {
        whitePieces = wP;
    }

    private static void setBlackPieces(ArrayList<ChessPiece> bP) {
        blackPieces = bP;
    }

    public static int[] getWhiteKing() {
        return whitePieces.get(0).getCurrentPosition();
    }

    public static int[] getBlackKing() {
        return blackPieces.get(0).getCurrentPosition();
    }
}