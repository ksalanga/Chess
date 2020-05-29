import java.util.ArrayList;

public class Board {

    private ChessPiece[][] Pieces;
    private ArrayList<ChessPiece> whiteCaptures;
    private ArrayList<ChessPiece> blackCaptures;

    public Board() {
        Pieces = new ChessPiece[8][8];
    }

    public void setPositions() {
        //setting black and white pawns
        for (int i = 0; i < 8; i++) {
            Pieces[1][i] = new Pawn(new int[] {1, i}, "black");
            Pieces[6][i] = new Pawn(new int[] {1, i}, "white");
        }

        ChessPiece[] MajorPieces = new ChessPiece[] {new Rook(null, ""), new Knight(null, ""), new Bishop(null, "")};
        for (int i = 0; i < 3; i++) {
            Pieces[0][i] = MajorPieces[i]; //black queens side
            Pieces[0][i].setColor("black");
            Pieces[0][i].setPosition(new int[] {0, i});
            Pieces[0][7 - i] = MajorPieces[i]; //black kings side
            Pieces[0][7 - i].setColor("black");
            Pieces[0][7 - i].setPosition(new int[] {0, 7 - i});
            Pieces[7][i] = MajorPieces[i]; //white queens side
            Pieces[7][i].setColor("white");
            Pieces[7][i].setPosition(new int[] {7, i});
            Pieces[7][7 - i] = MajorPieces[i]; //white kings side
            Pieces[7][7 - i].setColor("white");
            Pieces[7][7 - i].setPosition(new int[] {7, 7 - i});
        }

        //King and Queens
        Pieces[0][3] = new Queen(new int[] {0, 3}, "black");
        Pieces[7][3] = new Queen(new int[] {7, 3}, "white");
        Pieces[0][4] = new King(new int[] {0, 4}, "black");
        Pieces[7][4] = new King(new int[] {7, 4}, "white");
    }

    public void printBoard() {
        for (int i = 0; i < Pieces.length; i++) {
            for (int j = 0; j < Pieces[i].length; j++) {
                if (Pieces[i][j] != null) System.out.print(Pieces[i][j].getName() + " ");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

    public int[] convertToCoords(String tile) {
        int row = 8 - Character.getNumericValue(tile.charAt(1));
        char c = Character.toLowerCase(tile.charAt(0));
        int column = c % 97;

        return new int[] {row, column};
    }

    public String convertToTiles(int[] coords) {
        char[] files = new char[]{'a', 'b' , 'c', 'd', 'e', 'f', 'g', 'h'};
        int row = 8 - Character.getNumericValue(coords[0]);

        String tile = files[coords[1]] + Integer.toString(row);
        return tile;
    }
}
