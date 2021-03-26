import java.util.*;

public class test {
    private static final HashSet<Long> seenBoards = new HashSet<Long>();
    private static final ArrayList<Long> solution = new ArrayList<Long>();

    private static final long GOAL_BOARD = 16777216L;
    private static final long INITIAL_BOARD = 124141717933596L;
    private static final long VALID_BOARD_CELLS = 124141734710812L;

    private static final long[][] moves = new long[76][];

    private static void printBoard(long board) {
        for (int i = 0; i < 49; i++) {
            boolean validCell = ((1L << i) & VALID_BOARD_CELLS) != 0L;
            System.out.print(validCell ? (((1L << i) & board) != 0L ? "X " : "O ") : "  ");
            if (i % 7 == 6) System.out.println();
        }
        System.out.println("-------------");
    }

    private static void createMoves(int bit1, int bit2, int bit3, ArrayList<long[]> moves) {
        moves.add(new long[]{(1L << bit1), (1L << bit2) | (1L << bit3), (1L << bit1) | (1L << bit2) | (1L << bit3)});
        moves.add(new long[]{(1L << bit3), (1L << bit2) | (1L << bit1), (1L << bit1) | (1L << bit2) | (1L << bit3)});
    }

    private static boolean search(long board) {
        for (long[] move : moves) {
            if ((move[1] & board) == 0L && (move[0] & board) != 0L) {
                long newBoard = board ^ move[2];
                if (!seenBoards.contains(newBoard)) {
                    seenBoards.add(newBoard);
                    if (newBoard == INITIAL_BOARD || search(newBoard)) {
                        solution.add(board);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        solution.add(INITIAL_BOARD);

        ArrayList<long[]> moves = new ArrayList<long[]>();
        int[] starts = new int[] {2,9,14,15,16,17,18,21,22,23,24,25,28,29,30,31,32,37,44};
        for (int start : starts) {
            createMoves(start, start + 1, start + 2, moves);
            createMoves((start%7) * 7 + start/7, (start%7 + 1) * 7 + start/7, (start%7 + 2) * 7 + start/7, moves);
        }
        Collections.shuffle(moves);
        moves.toArray(test.moves);

        search(GOAL_BOARD);

        for (long step : solution)
            printBoard(step);
        System.out.println("Completed in " + (System.currentTimeMillis() - time) + " ms.");
    }
}