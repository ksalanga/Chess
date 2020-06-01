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

    public boolean whiteMove() {
        return whiteMove;
    }

    public boolean blackMove() {
        return blackMove;
    }
}
