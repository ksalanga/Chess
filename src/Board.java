import java.util.ArrayList;

public class Board {

    private ChessPiece[][] Pieces;
    private ArrayList<ChessPiece> whiteCaptures;
    private ArrayList<ChessPiece> blackCaptures;

//    8 0 1 2 3 4 5 6 7
//    7 0 1 2 3 4 5 6 7
//    6 0 1 2 3 4 5 6 7
//    5 0 1 2 3 4 5 6 7
//    4 0 1 2 3 4 5 6 7
//    3 0 1 2 3 4 5 6 7
//    2 0 1 2 3 4 5 6 7
//    1 0 1 2 3 4 5 6 7
//      A B C D E F G H
//
//    A - 0, B - 1, C - 2, D - 3, E - 4, F - 5, G - 6, H - 7
//
//            0 - 8
//            1 - 7
//            2 - 6
//            3 - 5
//            4 - 4
//            5 - 3
//            6 - 2
//            7 - 1

    public Board() {
        Pieces = new ChessPiece[8][8];

        Pieces[6][4] = new Pawn("64", "white");
        Pieces[6][4].move(Pieces[6][4].getCurrentPosition(), Pieces, whiteCaptures);
    }

    public void setPositions() {

    }

    public String convertToCoords(String tile) {
        int row = 8 - Character.getNumericValue(tile.charAt(1));
        char c = Character.toLowerCase(tile.charAt(0));
        int column = c % 97;

        String coords = Integer.toString(row) + column;
        return coords;
    }

    public String convertToTiles(String coords) {
        char[] files = new char[]{'a', 'b' , 'c', 'd', 'e', 'f', 'g', 'h'};
        int row = 8 - Character.getNumericValue(coords.charAt(0));

        String tile = files[Integer.parseInt(String.valueOf(coords.charAt(1)))] + Integer.toString(row);
        return tile;
    }
}
