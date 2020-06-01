public class Main {

    public static void main(String[] args) {
//	Board b = new Board();
//	b.setPositions();
//	b.printBoard();
//
//        Game g = new Game();
//        g.start();

        BoardScanner[][] b = new BoardScanner[8][8];

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                if (b[i][j].whiteMove() || b[i][j].blackMove()) System.out.println("Hi");
            }
        }
    }
}
