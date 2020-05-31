import java.util.ArrayList;

public class Board {

    private ChessPiece[][] Pieces;

    public Board() {
        Pieces = new ChessPiece[8][8];
    }

    public void setPositions() {
        //setting black and white pawns
        for (int i = 0; i < 8; i++) {
            Pieces[1][i] = new Pawn(new int[] {1, i}, "black");
            Pieces[6][i] = new Pawn(new int[] {6, i}, "white");
        }

        //White Minor & Major Pieces
        Pieces[7][0] = new Rook(new int[]{7, 0}, "white");
        Pieces[7][1] = new Knight(new int[]{7, 1}, "white");
        Pieces[7][2] = new Bishop(new int[]{7, 2}, "white");
        Pieces[7][3] = new Queen(new int[] {7, 3}, "white");
        Pieces[7][4] = new King(new int[] {7, 4}, "white");
        Pieces[7][5] = new Bishop(new int[]{7, 5}, "white");
        Pieces[7][6] = new Knight(new int[]{7, 6}, "white");
        Pieces[7][7] = new Rook(new int[]{7, 7}, "white");

        //Black Minor & Major Pieces
        Pieces[0][0] = new Rook(new int[]{0, 0}, "black");
        Pieces[0][1] = new Knight(new int[]{0, 1}, "black");
        Pieces[0][2] = new Bishop(new int[]{0, 2}, "black");
        Pieces[0][3] = new Queen(new int[] {0, 3}, "black");
        Pieces[0][4] = new King(new int[] {0, 4}, "black");
        Pieces[0][5] = new Bishop(new int[]{0, 5}, "black");
        Pieces[0][6] = new Knight(new int[]{0, 6}, "black");
        Pieces[0][7] = new Rook(new int[]{0, 7}, "black");

    }

    public void printBoard() {
        char[] alph = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
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
        for (int i = 0; i < alph.length; i++) {
            System.out.format("%s\t",alph[i]);
        }
    }

    public int[] convertToCoords(String tile) {
        tile = tile.toLowerCase();
        int row = 8 - Character.getNumericValue(tile.charAt(1));
        char c = Character.toLowerCase(tile.charAt(0));
        int column = c % 97;

        return new int[] {row, column};
    }

    public ChessPiece[][] getPieces() {
        return Pieces;
    }

    public String convertToTiles(int[] coords) {
        char[] files = new char[]{'a', 'b' , 'c', 'd', 'e', 'f', 'g', 'h'};
        int row = 8 - Character.getNumericValue(coords[0]);

        String tile = files[coords[1]] + Integer.toString(row);
        return tile;
    }
}
