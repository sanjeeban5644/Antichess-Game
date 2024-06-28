
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean player1Turn;

    public Game() {
        this.board = new Board();
        this.player1Turn = true;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOn = true;

        while (gameOn) {
            board.displayBoard(player1Turn);
            System.out.println("Enter your move (or type 'quit' to exit): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                gameOn = false;
                System.out.println("Player " + (player1Turn ? "1" : "2") + " wins!");
            } else if (input.equalsIgnoreCase("display")) {
                board.displayBoard(player1Turn);
            } else {
                try {
                    makeMove(input);
                    if (board.hasNoPieces(!player1Turn)) {
                        System.out.println("Player " + (player1Turn ? "2" : "1") + " wins!");
                        gameOn = false;
                    } else {
                        player1Turn = !player1Turn;
                    }
                } catch (InvalidMoveException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        scanner.close();
    }

    public void makeMove(String moveStr) throws InvalidMoveException {
        Move move = new Move(moveStr, player1Turn);
        if (!board.isValidMove(move)) {
            throw new InvalidMoveException("Invalid move. Try again.");
        }
        board.makeMove(move);
    }
}
