public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupBoard();
    }

    private void setupBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece("P", true); //white pawns
            board[6][i] = new Piece("p", false); // black pawns
        }
        //white
        board[0][0] = new Piece("R", true); //  Rook
        board[0][1] = new Piece("N", true); // Knight
        board[0][2] = new Piece("B", true); //  Bishop
        board[0][3] = new Piece("Q", true); //  Queen
        board[0][4] = new Piece("K", true); // King
        board[0][5] = new Piece("B", true); // Bishop
        board[0][6] = new Piece("N", true); // Knight
        board[0][7] = new Piece("R", true); // Rook

        //black

        board[7][0] = new Piece("r", false); // Rook
        board[7][1] = new Piece("n", false); //  Knight
        board[7][2] = new Piece("b", false); // Bishop
        board[7][3] = new Piece("q", false); //  Queen
        board[7][4] = new Piece("k", false); //  King
        board[7][5] = new Piece("b", false); //  Bishop
        board[7][6] = new Piece("n", false); // Knight
        board[7][7] = new Piece("r", false); //Rook
    }

    public void displayBoard(boolean player1Turn) {
        System.out.println("  A B C D E F G H");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].getSymbol() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        String currentPlayer = player1Turn ? "Player 1" : "Player 2";
        System.out.println("\nCurrent turn: \033[1m" + currentPlayer + "\033[0m\n");
    }

    public boolean isValidMove(Move move) {
        Piece piece = board[move.getStartX()][move.getStartY()];
        if (piece == null || piece.isPlayer1() != (move.isPlayer1Turn())) {
            return false;
        }
        
        int startX = move.getStartX();
        int startY = move.getStartY();
        int endX = move.getEndX();
        int endY = move.getEndY();
        String symbol = piece.getSymbol();

        //rules
        if (startX == endX && startY == endY) {
            return false; // Can't move to the same square
        }
        if (board[endX][endY] != null && board[endX][endY].isPlayer1() == piece.isPlayer1()) {
            return false; // Can't capture own pieces
        }

        // Piece-specific movement rules
        switch (symbol.toUpperCase()) {
            case "P": // pawn
                return isValidPawnMove(startX, startY, endX, endY, piece.isPlayer1());
            case "R": // Rrook
                return isValidRookMove(startX, startY, endX, endY);
            case "N": // Knight
                return isValidKnightMove(startX, startY, endX, endY);
            case "B": // bishop
                return isValidBishopMove(startX, startY, endX, endY);
            case "Q": // Queen
                return isValidQueenMove(startX, startY, endX, endY);
            case "K": // king
                return isValidKingMove(startX, startY, endX, endY);
            default:
                return false;
        }
    }

    private boolean isValidPawnMove(int startX, int startY, int endX, int endY, boolean isPlayer1) {
        int direction = isPlayer1 ? 1 : -1;
        int startRow = isPlayer1 ? 1 : 6;
       
        if (startY == endY) { //forward move
            if (startX + direction == endX && board[endX][endY] == null) {
                return true;
            }
            if (startX + 2 * direction == endX && startX == startRow && board[endX][endY] == null && board[startX + direction][startY] == null) {
                return true;
            }
        } else if (Math.abs(startY - endY) == 1 && startX + direction == endX) { //diagonal capture
            if (board[endX][endY] != null && board[endX][endY].isPlayer1() != isPlayer1) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidRookMove(int startX, int startY, int endX, int endY) {
        if (startX != endX && startY != endY) {
            return false;
        }
        return isPathClear(startX, startY, endX, endY);
    }

    private boolean isValidKnightMove(int startX, int startY, int endX, int endY) {
        int dx = Math.abs(startX - endX);
        int dy = Math.abs(startY - endY);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    private boolean isValidBishopMove(int startX, int startY, int endX, int endY) {
        if (Math.abs(startX - endX) != Math.abs(startY - endY)) {
            return false;
        }
        return isPathClear(startX, startY, endX, endY);
    }

    private boolean isValidQueenMove(int startX, int startY, int endX, int endY) {
        return isValidRookMove(startX, startY, endX, endY) || isValidBishopMove(startX, startY, endX, endY);
    }

    private boolean isValidKingMove(int startX, int startY, int endX, int endY) {
        int dx = Math.abs(startX - endX);
        int dy = Math.abs(startY - endY);
        return dx <= 1 && dy <= 1;
    }

    private boolean isPathClear(int startX, int startY, int endX, int endY) {
        int dx = Integer.compare(endX, startX);
        int dy = Integer.compare(endY, startY);
        int x = startX + dx;
        int y = startY + dy;
        while (x != endX || y != endY) {
            if (board[x][y] != null) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }

    public void makeMove(Move move) {
        Piece piece = board[move.getStartX()][move.getStartY()];
        board[move.getEndX()][move.getEndY()] = piece;
        board[move.getStartX()][move.getStartY()] = null;
    }

    public boolean hasNoPieces(boolean player1Turn) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.isPlayer1() == player1Turn) {
                    return false;
                }
            }
        }
        return true;
    }
}
