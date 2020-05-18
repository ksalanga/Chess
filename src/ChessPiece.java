public interface ChessPiece {

    public String move(String inputPosition, ChessPiece[][] boardPositions);

    public String getCurrentPosition();

    public String getColor();

    public void setPosition(String position);

    public void setColor(String color);

}
