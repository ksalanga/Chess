import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile implements ActionListener {
    private ChessPiece piece;
    private JButton b;
    private boolean selected;

    public Tile(ChessPiece piece) {
        this.piece = piece;
        selected = false;
        b.addActionListener(this);
    }

    public boolean isNull() {
        return piece == null;
    }

    public String getPiece() {
        return piece.getName();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
