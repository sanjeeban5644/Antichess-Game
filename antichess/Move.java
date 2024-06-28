public class Move {
    private int startX, startY, endX, endY;
    private boolean player1Turn;

    public Move(String move, boolean player1Turn) {
        this.startX = move.charAt(1) - '1';
        this.startY = move.charAt(0) - 'A';
        this.endX = move.charAt(4) - '1';
        this.endY = move.charAt(3) - 'A';
        this.player1Turn = player1Turn;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }
}
