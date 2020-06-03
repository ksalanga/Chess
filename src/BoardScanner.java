public class BoardScanner {
    private boolean whiteMove;
    private boolean blackMove;

    public BoardScanner() {
        whiteMove = false;
        blackMove = false;
    }

    public void setMove(String color) {
        if (color.equals("white")) whiteMove = true;
        else blackMove = true;
    }

    public void whiteMove() { whiteMove = true; }

    public void blackMove() { blackMove = true; }

    public boolean isWhiteMove() {return whiteMove;}

    public boolean isBlackMove() {return blackMove;}
}
