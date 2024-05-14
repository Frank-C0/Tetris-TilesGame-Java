package epis.unsa;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

@DisplayName("Unit test for Piece class.")
public class BoardTest {
    // Board b;
    // Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

    // This shows how to build things in setUp() to re-use
    // across tests.

    // In this case, setUp() makes shapes,
    // and also a 3X6 board, with pyr placed at the bottom,
    // ready to be used by tests.

    @BeforeEach
    protected void setUp() throws Exception {

        // b = new Board(3, 6);

        // pyr1 = Piece.getPieces()[STRS.PYRAMID.ordinal()];
        // pyr2 = pyr1.computeNextRotation();
        // pyr3 = pyr2.computeNextRotation();
        // pyr4 = pyr3.computeNextRotation();

        // s = Piece.getPieces()[STRS.S1.ordinal()];
        // sRotated = s.computeNextRotation();

        // b.place(pyr1, 0, 0);
    }

    @Test
    @DisplayName("Testing grid pyr1 in 0 0")
    public void testPlacePYR1() {
        Board board = new Board(3, 6);
        Piece pyr1 = Piece.getPieces()[STRS.PYRAMID.ordinal()];
        board.place(pyr1, 0, 0);

        assertTrue(board.getGrid(0, 0));

        assertFalse(board.getGrid(0, 1));
        assertFalse(board.getGrid(0, 2));
        assertFalse(board.getGrid(0, 3));
        assertFalse(board.getGrid(0, 4));
        assertFalse(board.getGrid(0, 5));

        assertTrue(board.getGrid(1, 0));
        assertTrue(board.getGrid(1, 1));

        assertFalse(board.getGrid(1, 2));
        assertFalse(board.getGrid(1, 3));
        assertFalse(board.getGrid(1, 4));
        assertFalse(board.getGrid(1, 5));
    }

    /*
     * ---
     * ---
     * ---
     * ---
     * -XX
     * XX-
     */
    @Test
    @DisplayName("Testing grid piece S1 in 0 0")
    public void testPlacePiece1() {
        Board board = new Board(3, 6);
        Piece piece1 = Piece.getPieces()[STRS.S1.ordinal()];

        board.place(piece1, 0, 0);

        assertTrue(board.getGrid(0, 0));
        assertTrue(board.getGrid(1, 0));
        assertTrue(board.getGrid(1, 1));
        assertTrue(board.getGrid(2, 1));

        assertFalse(board.getGrid(0, 1));
        assertFalse(board.getGrid(0, 2));
        assertFalse(board.getGrid(0, 3));
        assertFalse(board.getGrid(0, 4));
        assertFalse(board.getGrid(0, 5));
        assertFalse(board.getGrid(1, 2));
        assertFalse(board.getGrid(1, 3));
        assertFalse(board.getGrid(1, 4));
        assertFalse(board.getGrid(1, 5));
    }

    /*
     * ----
     * ----
     * ----
     * ----
     * -XX-
     * XX--
     */
    @Test
    @DisplayName("Check the basic width/height/max after the one placement")
    public void testBoardSize() {

        Board board = new Board(4, 6);

        Piece s = Piece.getPieces()[STRS.S1.ordinal()];

        assertEquals(Board.PLACE_OK, board.place(s, 0, 0));

        assertEquals(1, board.getColumnHeight(0));
        assertEquals(2, board.getColumnHeight(1));
        assertEquals(2, board.getColumnHeight(2));
        assertEquals(0, board.getColumnHeight(3));

        assertEquals(2, board.getMaxHeight());

        assertEquals(2, board.getRowWidth(0));
        assertEquals(2, board.getRowWidth(1));
        assertEquals(0, board.getRowWidth(2));
    }

    /*
     * ---
     * ---
     * ---
     * ---
     * --XX ?
     * -XX
     */
    @Test
    @DisplayName("Out of bounds on place in the board")
    public void testBoardPlaceOutOfBounds() {
        Board board = new Board(3, 6);

        Piece s = Piece.getPieces()[STRS.S1.ordinal()];

        int result = board.place(s, 1, 0);
        assertEquals(result, Board.PLACE_OUT_BOUNDS);
    }

    @Test
    @DisplayName(" one row to be filled")
    public void testBoardOneRowToBeFilled() {
        Board board = new Board(4, 6);

        Piece s = Piece.getPieces()[STRS.STICK.ordinal()].fastRotation();

        int result = board.place(s, 0, 0);
        assertEquals(result, Board.PLACE_ROW_FILLED);
    }

    /*
     * ----
     * ----
     * ----
     * ----
     * xxX-
     * xx--
     */
    @Test
    @DisplayName("One row to be filled")
    public void testBoardOneRowToBeFilled2() {
        Board board = new Board(4, 6);
        Piece s = Piece.getPieces()[STRS.S1.ordinal()];
        Piece square = Piece.getPieces()[STRS.SQUARE.ordinal()];

        int result = board.place(s, 0, 0);
        board.commit();
        assertEquals(Board.PLACE_BAD, board.place(square, 0, 0));
    }

    @Test
    @DisplayName("Testing dropHeight for S1 rotate at column 1")
    /*
     * -X-
     * -XX
     * - X
     * ---
     * -X-
     * XXX
     *
     * skirt = {1, 0}
     * heigths = {1, 2, 1}
     */
    public void testDropHeightS1() {
        Board b = new Board(3, 6);

        Piece s = Piece.getPieces()[STRS.PYRAMID.ordinal()];
        Piece s2 = Piece.getPieces()[STRS.S1.ordinal()];
        s2 = s2.computeNextRotation();
        b.place(s, 0, 0);

        assertEquals(1, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(1, b.getColumnHeight(2));

        assertEquals(1, b.dropHeight(s2, 1));
        assertEquals(2, b.dropHeight(s2, 0));

    }

    // Make more tests, by putting together longer series of
    // place, clearRows, undo, place ... checking a few col/row/max
    // numbers that the board looks right after the operations.

    /*
     * ----
     * ----
     * ----
     * XX--
     * XX--
     * XXXX
     */

    @Test
    @DisplayName("Clear Rows 1 row")
    public void testBoardClearRows() {
        Board board = new Board(4, 6);

        Piece s = Piece.getPieces()[STRS.STICK.ordinal()];
        Piece s2 = s.computeNextRotation();

        board.place(s2, 0, 0);

        Piece sq = Piece.getPieces()[STRS.SQUARE.ordinal()];
        board.commit();
        board.place(sq, 0, 1);

        assertEquals(3, board.getColumnHeight(0));
        assertEquals(3, board.getColumnHeight(1));
        assertEquals(1, board.getColumnHeight(2));
        assertEquals(1, board.getColumnHeight(3));

        int r = board.clearRows();
        assertEquals(1, r);

        assertEquals(2, board.getColumnHeight(0));
        assertEquals(2, board.getColumnHeight(1));
        assertEquals(0, board.getColumnHeight(2));
        assertEquals(0, board.getColumnHeight(3));
    }

    @Test
    @DisplayName("Undo board")
    public void testBoardUndoPlace() {
        Board b = new Board(3, 6);

        Piece s = Piece.getPieces()[STRS.PYRAMID.ordinal()];

        // b.commit(); // comiteado en el contructor??
        assertTrue(b.committed);

        b.place(s, 0, 0);
        assertFalse(b.committed);

        assertEquals(1, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(1, b.getColumnHeight(2));

        b.undo();
        assertTrue(b.committed);
        assertEquals(0, b.getColumnHeight(0));
        assertEquals(0, b.getColumnHeight(1));
        assertEquals(0, b.getColumnHeight(2));
    }

    @Test
    @DisplayName("Undo Board and clear rows")
    public void testBoardUndoPlaceAndClearRows() {
        Board b = new Board(3, 6);

        Piece s = Piece.getPieces()[STRS.PYRAMID.ordinal()];

        assertTrue(b.committed);

        b.place(s, 0, 0);
        assertFalse(b.committed);

        /*
         * ---
         * ---
         * ---
         * ---
         * -X-
         * XXX
         */
        assertEquals(1, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(1, b.getColumnHeight(2));

        b.commit();
        assertTrue(b.committed);

        Piece sq = Piece.getPieces()[STRS.SQUARE.ordinal()];
        b.place(sq, 0, 2);
        assertFalse(b.committed);
        /*
         * ---
         * ---
         * XX-
         * XX-
         * -X-
         * XXX
         */
        b.clearRows();
        assertFalse(b.committed);
        /*
         * ---
         * ---
         * ---
         * XX-
         * XX-
         * -X-
         */

        b.undo();
        assertTrue(b.committed);
        // establecer
        assertEquals(1, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(1, b.getColumnHeight(2));

    }

    @Test
    @DisplayName("board and dropheight")
    public void testBoardPlacewithDropHeight() {
        Board b = new Board(10, 20);

        Piece f = Piece.getPieces()[STRS.PYRAMID.ordinal()];

        assertEquals(Board.PLACE_OK, b.place(f, 3, 17));
        assertEquals(18, b.getColumnHeight(3));
        assertEquals(19, b.getColumnHeight(4));
        assertEquals(18, b.getColumnHeight(5));

        b.undo();

        assertEquals(0, b.getColumnHeight(3));
        assertEquals(0, b.getColumnHeight(4));
        assertEquals(0, b.getColumnHeight(5));

        assertEquals(Board.PLACE_OK, b.place(f, 3, 16));

        assertEquals(17, b.getColumnHeight(3));
        assertEquals(18, b.getColumnHeight(4));
        assertEquals(17, b.getColumnHeight(5));

        b.undo();

        assertEquals(Board.PLACE_OK, b.place(f, 3, 15));

        assertEquals(16, b.getColumnHeight(3));
        assertEquals(17, b.getColumnHeight(4));
        assertEquals(16, b.getColumnHeight(5));

        b.undo();

        assertEquals(0, b.dropHeight(f, 3));

    }

    /*
     * ----
     * -----
     * -ww-o
     * zzwwo
     * zz--o
     * XXXXo
     */
    @Test
    @DisplayName("clear Rows, 2 rows")
    public void testBoardClearRows2() {
        Board board = new Board(5, 6);

        Piece st = Piece.getPieces()[STRS.STICK.ordinal()];

        board.place(st.computeNextRotation(), 0, 0);
        board.commit();

        Piece sq = Piece.getPieces()[STRS.SQUARE.ordinal()];

        board.place(sq, 0, 1);
        board.commit();

        Piece s2 = Piece.getPieces()[STRS.S2.ordinal()];
        board.place(s2, 1, 2);
        board.commit();

        Piece st2 = Piece.getPieces()[STRS.STICK.ordinal()];
        board.place(st2, 4, 0);
        board.commit();
        /*
         * ----
         * -----
         * -ww-o
         * zzwwo
         * zz--o
         * XXXXo
         */ 
        
         assertEquals(3, board.getColumnHeight(0));
        assertEquals(4, board.getColumnHeight(1));
        assertEquals(4, board.getColumnHeight(2));
        assertEquals(3, board.getColumnHeight(3));
        assertEquals(4, board.getColumnHeight(4));

        int r = board.clearRows();
        assertEquals(2, r);

        /*
         * ----
         * -----
         * -ww-o
         * zz--o
         */

        assertEquals(1, board.getColumnHeight(0));
        assertEquals(2, board.getColumnHeight(1));
        assertEquals(2, board.getColumnHeight(2));
        assertEquals(0, board.getColumnHeight(3));
        assertEquals(2, board.getColumnHeight(4));
        assertFalse(board.getGrid(2, 0));

    }

}
