
public class Piece {
    private String symbol;
    private boolean isPlayer1;

    public Piece(String symbol, boolean isPlayer1) {
        this.symbol = symbol;
        this.isPlayer1 = isPlayer1;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }
}
