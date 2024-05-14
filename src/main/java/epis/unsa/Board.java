package epis.unsa;

import java.util.*;
import java.lang.RuntimeException;
// Board.java

/**
 * Tetris Board.
 * Represents a Tetris board -- essentially a 2-d grid
 * of booleans. Supports tetris pieces and row clearing.
 * Has an "undo" feature that allows clients to add and remove pieces
 * efficiently.
 * Does not do any drawing or have any idea of pixels. Instead,
 * just represents the abstract 2-d board.
 */
public class Board {
  // Some ivars are stubbed out for you:
  private int width;
  private int height;
  private boolean[][] grid;
  private boolean DEBUG = true;
  boolean committed = true;

  // Here a few trivial methods are provided:

  /**
   * Creates an empty board of the given width and height
   * measured in blocks.
   */
  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    grid = new boolean[width][height];

    grid_backup = new boolean[width][height];
  }

  /**
   * Returns the width of the board in blocks.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of the board in blocks.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the max column height present in the board.
   * For an empty board this is 0.
   */
  public int getMaxHeight() {
    int max = 0;
    for (int i = 0; i < width; i++) {
      if (getColumnHeight(i) > max) {
        max = getColumnHeight(i);
      }
    }
    return max;
  }

  /**
   * Checks the board for internal consistency -- used
   * for debugging.
   */
  public void sanityCheck() {
    if (DEBUG) {
      if (grid == null) {
        throw new RuntimeException("grid is null");
      } else if (grid.length != width) {
        throw new RuntimeException("wrong width");
      } else if (grid[0].length != height) {
        throw new RuntimeException("wrong height");
      } else if (getMaxHeight() > height && getMaxHeight() < 0) {
        throw new RuntimeException("wrong max Height");
      }
    }
  }

  /**
   * Given a piece and an x, returns the y
   * value where the piece would come to rest
   * if it were dropped straight down at that x.
   * 
   * <p>
   * Implementation: use the skirt and the col heights
   * to compute this fast -- O(skirt length).
   */
  public int dropHeight(Piece piece, int x) {
    int max_pos = 0;

    int[] sk = piece.getSkirt();
    for (int x_p = 0; x_p < sk.length; x_p++) {
      int new_y = (getColumnHeight(x + x_p) - piece.getSkirt()[x_p]);
      if (new_y > max_pos) {
        max_pos = new_y;
      }
    }

    return max_pos; // YOUR CODE HERE
  }

  /**
   * Returns the height of the given column --
   * i.e. the y value of the highest block + 1.
   * The height is 0 if the column contains no blocks.
   */
  public int getColumnHeight(int x) {
    for (int i = height - 1; i >= 0; i--) {
      if (grid[x][i])
        return i + 1;
    }
    return 0;
  }

  /**
   * Returns the number of filled blocks in
   * the given row.
   */
  public int getRowWidth(int y) {
    int cont = 0;
    for (int i = 0; i < width; i++) {
      if (grid[i][y] == true) {
        cont++;
      }
    }
    return cont;
  }

  /**
   * Returns true if the given block is filled in the board.
   * Blocks outside of the valid width/height area
   * always return true.
   */
  public boolean getGrid(int x, int y) {
    return grid[x][y];
  }

  public static final int PLACE_OK = 0;
  public static final int PLACE_ROW_FILLED = 1;
  public static final int PLACE_OUT_BOUNDS = 2;
  public static final int PLACE_BAD = 3;

  /**
   * Attempts to add the body of a piece to the board.
   * Copies the piece blocks into the board grid.
   * Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
   * for a regular placement that causes at least one row to be filled.
   * 
   * <p>
   * Error cases:
   * A placement may fail in two ways. First, if part of the piece may falls out
   * of bounds of the board, PLACE_OUT_BOUNDS is returned.
   * Or the placement may collide with existing blocks in the grid
   * in which case PLACE_BAD is returned.
   * In both error cases, the board may be left in an invalid
   * state. The client can use undo(), to recover the valid, pre-place state.
   */
  public int place(Piece piece, int x, int y) {
    // x= columna
    // y = fila
    int result;
    // flag !committed problem
    if (!committed)
      throw new RuntimeException("place commit problem");

    result = PLACE_OK;
    for (TPoint a : piece.getBody()) {
      int posx = a.x + x;
      int posy = a.y + y;
      // PLACE_OUT_BOUNDS -x-> PLACE_BAD
      if (posx >= width || posy >= height || posx < 0 || posy < 0) {
        result = PLACE_OUT_BOUNDS;
      }
      if (result != PLACE_OUT_BOUNDS) {
        if (grid[posx][posy]) {
          result = PLACE_BAD;
        }
        grid[posx][posy] = true;
      }
    }
    if (result == PLACE_OK) {
      for (int i = y; i < i + piece.getHeight() && i < height; i++) {
        if (getRowWidth(i) == width) {
          result = PLACE_ROW_FILLED;
          break;
        }
      }
      sanityCheck();
    }
    committed = false;
    return result;
  }

  private void clearRow(int row, int top) { // getMaxHeight
    for (int col = 0; col < width; col++) {
      for (int r = row; r < top - 1; r++) {
        grid[col][r] = grid[col][r + 1];
      }
      grid[col][top - 1] = false;
    }
  }

  /**
   * Deletes rows that are filled all the way across, moving
   * things above down. Returns the number of rows cleared.
   */
  public int clearRows() {
    int rowsCleared = 0;

    // int top = getMaxHeight();
    // for (int i = 0; i < height; i++) {
    // if (getRowWidth(i) == width) {
    // clearRow(i, top);
    // // top--;
    // rowsCleared++;
    // }
    // }

    int top = getMaxHeight();
    int to_row = 0;
    int from_row = 0;

    for (;; to_row++, from_row++) {
      if (getRowWidth(from_row) == width) {
        from_row++;
        rowsCleared++;
      }
      if (from_row > top) {
        break;
      }
      for (int col = 0; col < width; col++) {
        grid[col][to_row] = grid[col][from_row];
      }
    }
    if (rowsCleared > 0)
      for (int col = 0; col < width; col++) {
        grid[col][top - 1] = false;
      }

    committed = false;
    sanityCheck();
    return rowsCleared;
  }

  /*
   * 
   * Reverts the board to its state
   * before up to one place and one
   * 
   * clearRows();*
   * 
   * If the conditions for
   * 
   * undo() are not met, such as
   *
   * 
   * calling undo() twice in a row, then the second undo() does nothing.
   * See the overview docs.
   */
  boolean[][] grid_backup;

  public void undo() {
    if (!committed) {

      for (int i = 0; i < width; i++)
        System.arraycopy(grid_backup[i], 0, grid[i], 0, height);

      // boolean[][] temp = grid_backup;
      // grid_backup = grid;
      // grid = temp;

      committed = true;
    }
  }

  /**
   * Puts the board in the committed state.
   */
  public void commit() {
    for (int i = 0; i < width; i++) {
      System.arraycopy(grid[i], 0, grid_backup[i], 0, height);
    }
    committed = true;
  }

  /*
   * Renders the board state as a big String, suitable for printing.
   * This is the sort of print-obj-state utility that can help see complex
   * state change over time.
   * (provided debugging utility)
   */
  public String toString() {
    StringBuilder buff = new StringBuilder();
    for (int y = height - 1; y >= 0; y--) {
      buff.append('|');
      for (int x = 0; x < width; x++) {
        if (getGrid(x, y))
          buff.append('+');
        else
          buff.append(' ');
      }
      buff.append("|\n");
    }
    for (int x = 0; x < width + 2; x++)
      buff.append('-');
    return (buff.toString());
  }
}
