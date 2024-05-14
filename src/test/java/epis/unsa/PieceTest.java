package epis.unsa;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

@DisplayName("Unit test for Piece class.")
public class PieceTest {
  // You can create data to be used in the your
  // test cases like this. For each run of a test method,
  // a new PieceTest object is created and setUp() is called
  // automatically by JUnit.
  // For example, the code below sets up some
  // pyramid and s pieces in instance variables
  // that can be used in tests.

  // private Piece pyr1, pyr2, pyr3, pyr4;
  // private Piece s, sRotated;
  @BeforeEach
  public void setUp() {
  }

  // Test pieces size
  @Test
  @DisplayName("Check size of Pyramid piece")
  public void testPyramidePieceSize() {
    Piece piece = Piece.getPieces()[STRS.PYRAMID.ordinal()];
    assertEquals(3, piece.getWidth());
    assertEquals(2, piece.getHeight());

    Piece piece_r1 = piece.computeNextRotation();
    assertEquals(2, piece_r1.getWidth());
    assertEquals(3, piece_r1.getHeight());
  }

  @Test
  @DisplayName("Check size of Square piece")
  public void testSquarePieceSize() {
    Piece piece = Piece.getPieces()[STRS.SQUARE.ordinal()];
    assertEquals(2, piece.getWidth());
    assertEquals(2, piece.getHeight());

    Piece piece_r1 = piece.computeNextRotation();
    assertEquals(2, piece_r1.getWidth());
    assertEquals(2, piece_r1.getHeight());
  }

  @Test
  @DisplayName("Check size of Stick piece")
  public void testStickPieceSize() {
    Piece piece = Piece.getPieces()[STRS.STICK.ordinal()];
    assertEquals(1, piece.getWidth());
    assertEquals(4, piece.getHeight());

    Piece piece_r1 = piece.computeNextRotation();
    assertEquals(4, piece_r1.getWidth());
    assertEquals(1, piece_r1.getHeight());
  }

  @Test
  @DisplayName("Check size of L (left) piece")
  public void testLLeftPieceSize() {
    Piece piece = Piece.getPieces()[STRS.L1.ordinal()];
    assertEquals(2, piece.getWidth());
    assertEquals(3, piece.getHeight());

    Piece piece_r1 = piece.computeNextRotation();
    assertEquals(3, piece_r1.getWidth());
    assertEquals(2, piece_r1.getHeight());
  }

  @Test
  @DisplayName("Check size of L (right) piece")
  public void testLRightPieceSize() {
    Piece piece = Piece.getPieces()[STRS.L2.ordinal()];
    assertEquals(2, piece.getWidth());
    assertEquals(3, piece.getHeight());

    Piece piece_r1 = piece.computeNextRotation();
    assertEquals(3, piece_r1.getWidth());
    assertEquals(2, piece_r1.getHeight());
  }

  @Test
  @DisplayName("Check size of Dog (left) piece")
  public void testDogLeftPieceSize() {
    Piece piece = Piece.getPieces()[STRS.S1.ordinal()];
    assertEquals(3, piece.getWidth());
    assertEquals(2, piece.getHeight());

    Piece piece_r1 = piece.computeNextRotation();
    assertEquals(2, piece_r1.getWidth());
    assertEquals(3, piece_r1.getHeight());
  }

  @Test
  @DisplayName("Check size of Dog (right) piece")
  public void testDogRightPieceSize() {
    Piece piece = Piece.getPieces()[STRS.S2.ordinal()];
    assertEquals(3, piece.getWidth());
    assertEquals(2, piece.getHeight());

    Piece piece_r1 = piece.computeNextRotation();
    assertEquals(2, piece_r1.getWidth());
    assertEquals(3, piece_r1.getHeight());
  }

  // Test

  // Skirt Test
  @Test
  @DisplayName("Check skirt of Pyramid piece")
  public void testSkirtPyramid() {
    Piece piece = Piece.getPieces()[STRS.PYRAMID.ordinal()];
    assertArrayEquals(new int[] { 0, 0, 0 }, piece.getSkirt());

    Piece piece_r1 = piece.fastRotation();
    assertArrayEquals(new int[] { 1, 0 }, piece_r1.getSkirt());

    Piece piece_r2 = piece_r1.fastRotation();
    assertArrayEquals(new int[] { 1, 0, 1 }, piece_r2.getSkirt());

    Piece piece_r3 = piece_r2.fastRotation();
    assertArrayEquals(new int[] { 0, 1 }, piece_r3.getSkirt());

    assertEquals(piece, piece_r3.computeNextRotation());
  }

  @Test
  @DisplayName("Check skirt of Square piece")
  public void testSkirtSquare() {
    Piece piece = Piece.getPieces()[STRS.SQUARE.ordinal()];
    assertArrayEquals(new int[] { 0, 0 }, piece.getSkirt());

    Piece piece_r1 = piece.fastRotation();
    assertArrayEquals(new int[] { 0, 0 }, piece_r1.getSkirt());

    Piece piece_r2 = piece_r1.fastRotation();
    assertArrayEquals(new int[] { 0, 0 }, piece_r2.getSkirt());

    Piece piece_r3 = piece_r2.fastRotation();
    assertArrayEquals(new int[] { 0, 0 }, piece_r3.getSkirt());

    assertEquals(piece, piece_r3.computeNextRotation());
  }

  @Test
  @DisplayName("Check skirt of Stick piece")
  public void testSkirtStick() {
    Piece piece = Piece.getPieces()[STRS.STICK.ordinal()];
    assertArrayEquals(new int[] { 0 }, piece.getSkirt());

    Piece piece_r1 = piece.fastRotation();
    assertArrayEquals(new int[] { 0, 0, 0, 0 }, piece_r1.getSkirt());

    Piece piece_r2 = piece_r1.fastRotation();
    assertArrayEquals(new int[] { 0 }, piece_r2.getSkirt());

    Piece piece_r3 = piece_r2.fastRotation();
    assertArrayEquals(new int[] { 0, 0, 0, 0 }, piece_r3.getSkirt());

    assertEquals(piece, piece_r3.computeNextRotation());
  }

  @Test
  @DisplayName("Check skirt of L (right) piece")
  public void testLLeftPieceSkirt() {
    Piece piece = Piece.getPieces()[STRS.L1.ordinal()];
    assertArrayEquals(new int[] { 0, 0 }, piece.getSkirt());

    Piece piece_r1 = piece.fastRotation();
    assertArrayEquals(new int[] { 0, 0, 0 }, piece_r1.getSkirt());

    Piece piece_r2 = piece_r1.fastRotation();
    assertArrayEquals(new int[] { 2, 0 }, piece_r2.getSkirt());

    Piece piece_r3 = piece_r2.fastRotation();
    assertArrayEquals(new int[] { 0, 1, 1 }, piece_r3.getSkirt());

    assertEquals(piece, piece_r3.computeNextRotation());
  }

  @Test
  @DisplayName("Check skirt of L (left) piece")
  public void testLRightPieceSkirt() {
    Piece piece = Piece.getPieces()[STRS.L2.ordinal()];
    assertArrayEquals(new int[] { 0, 0 }, piece.getSkirt());

    Piece piece_r1 = piece.fastRotation();
    assertArrayEquals(new int[] { 1, 1, 0 }, piece_r1.getSkirt());

    Piece piece_r2 = piece_r1.fastRotation();
    assertArrayEquals(new int[] { 0, 2 }, piece_r2.getSkirt());

    Piece piece_r3 = piece_r2.fastRotation();
    assertArrayEquals(new int[] { 0, 0, 0 }, piece_r3.getSkirt());

    assertEquals(piece, piece_r3.computeNextRotation());
  }

  @Test
  @DisplayName("Check skirt of Dog (right) piece")
  public void testDogLeftPieceSkirt() {
    Piece piece = Piece.getPieces()[STRS.S1.ordinal()];
    assertArrayEquals(new int[] { 0, 0, 1 }, piece.getSkirt());

    Piece piece_r1 = piece.fastRotation();
    assertArrayEquals(new int[] { 1, 0 }, piece_r1.getSkirt());

    Piece piece_r2 = piece_r1.fastRotation();
    assertArrayEquals(new int[] { 0, 0, 1 }, piece_r2.getSkirt());

    Piece piece_r3 = piece_r2.fastRotation();
    assertArrayEquals(new int[] { 1, 0 }, piece_r3.getSkirt());

    assertEquals(piece, piece_r3.computeNextRotation());
  }

  @Test
  @DisplayName("Check skirt of Dog (left) piece")
  public void testDogRightPieceSkirt() {
    Piece piece = Piece.getPieces()[STRS.S2.ordinal()];
    assertArrayEquals(new int[] { 1, 0, 0 }, piece.getSkirt());

    Piece piece_r1 = piece.fastRotation();
    assertArrayEquals(new int[] { 0, 1 }, piece_r1.getSkirt());

    Piece piece_r2 = piece_r1.fastRotation();
    assertArrayEquals(new int[] { 1, 0, 0 }, piece_r2.getSkirt());

    Piece piece_r3 = piece_r2.fastRotation();
    assertArrayEquals(new int[] { 0, 1 }, piece_r3.getSkirt());

    assertEquals(piece, piece_r3.computeNextRotation());
  }
  // end test skirt

  // @Test
  // @DisplayName("Testing mirror L1")
  // public void testMirrorL1() {
  // Piece l1 = Piece.getPieces()[STRS.L1.ordinal()];
  // // TPoint[] ml1 = new TPoint[4];
  // // ml1[0] = new TPoint(1, 0);
  // // ml1[1] = new TPoint(1, 1);
  // // ml1[2] = new TPoint(1, 2);
  // // ml1[3] = new TPoint(0, 0);
  // // assertArrayEquals(ml1,
  // // l1.mirror(l1.getBody(), l1.getWidth()));
  // }

  // @Test
  // @DisplayName("Testing mirror Dog1")
  // public void testMirrorS1() {
  // /*
  // * Piece s1 = Piece.getPieces()[STRS.S1.ordinal()];
  // * TPoint[] ms1 = new TPoint[4];
  // * ms1[0] = new TPoint(2, 0);
  // * ms1[1] = new TPoint(1, 0);
  // * ms1[2] = new TPoint(1, 1);
  // * ms1[3] = new TPoint(0, 1);
  // * assertArrayEquals(ms1,
  // * s1.mirror(s1.getBody(), s1.getWidth()));
  // */
  // }

}
