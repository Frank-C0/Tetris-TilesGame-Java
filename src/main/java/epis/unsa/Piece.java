package epis.unsa;

// Piece.java

import java.util.*;

/**
 * An immutable representation of a tetris piece in a particular rotation.
 * Each piece is defined by the blocks that make up its body.
 * 
 * Typical client code looks like...
 * 
 * <pre>
 * Piece pyra = Piece.getPieces()[STRS.PYRAMID.ordinal()]
 * int width = pyra.getWidth();      // 3
 * Piece pyra2 = pyramid.computeNextRotation(); // get rotation, slow way

 * Piece[] pieces = Piece.getPieces();  // the array of root pieces
 * Piece stick = pieces[STRS.STICK.ordinal()];
 * int width = stick.getWidth();    // get its width
 * Piece stick2 = stick.fastRotation();  // get the next rotation, fast way
 * </pre>
 * 
 * @author Nick Parlante
 * @version 1.0, Mar 1, 2001
 */
public final class Piece {
  // Starter code specs out a few basic things, leaving
  // the algorithms to be done.
  // Do not assume there are 4 points in the body -- use array.length to keep the
  // code general
  private TPoint[] body;
  private int[] skirt;
  private int width;
  private int height;
  private Piece next; // "next" rotation

  static private Piece[] pieces; // singleton static array of first rotations

  /**
   * Defines a new piece given a TPoint[] array of its body.
   * Makes its own copy of the array and the TPoints inside it.
   * *Does not set up the rotations.
   * 
   * This constructor is PRIVATE -- if a client
   * wants a piece object, they must use Piece.getPieces().
   */
  private Piece(TPoint[] points) {
    // YOUR CODE HERE

    body = new TPoint[points.length];
    int w = 0;
    int h = 0;

    for (int i = 0; i < points.length; i++) {
      body[i] = new TPoint(points[i].x, points[i].y);

      if (w < points[i].x) {
        w = points[i].x;
      }
      if (h < points[i].y) {
        h = points[i].y;
      }
    }
    width = w + 1;
    height = h + 1;

    skirt = new int[width]; // { 2,2 }
    Arrays.fill(skirt, this.height);

    for (TPoint p : body) {
      if (p.y < skirt[p.x]) { // allar el minimo y de cada columna (x)
        skirt[p.x] = p.y;
      }
    }
  }

  /**
   * Alternate constructor, takes a String with the x,y body points
   * all separated by spaces, such as "0 0 1 0 2 0 1 1".
   * (provided)
   */
  private Piece(String points) {
    this(parsePoints(points));
  }

  /**
   * Returns the width of the piece measured in blocks.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the height of the piece measured in blocks.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns a pointer to the piece's body. The caller
   * should not modify this array.
   */
  public TPoint[] getBody() {
    return body;
  }

  /**
   * Returns a pointer to the piece's skirt. For each x value
   * across the piece, the skirt gives the lowest y value in the body.
   * This is useful for computing where the piece will land.
   * The caller should not modify this array.
   */
  public int[] getSkirt() {
    return skirt;
  }

  /**
   * Returns a new piece that is 90 degrees counter-clockwise
   * rotated from the receiver.
   */
  public Piece computeNextRotation() {
    TPoint[] npiece = new TPoint[body.length];

    for (int i = 0; i < body.length; i++) {
      int nx = height - body[i].y - 1;
      int ny = body[i].x;

      npiece[i] = new TPoint(nx, ny);
    }
    return new Piece(npiece);
  }

  /**
   * Returns a pre-computed piece that is 90 degrees counter-clockwise
   * rotated from the receiver. Fast because the piece is pre-computed.
   * This only works on pieces set up by makeFastRotations(), and otherwise
   * just returns null.
   */
  public Piece fastRotation() {
    return next;
  }

  /**
   * Returns true if two pieces are the same --
   * their bodies contain the same points.
   * Interestingly, this is not the same as having exactly the
   * same body arrays, since the points may not be
   * in the same order in the bodies. Used internally to detect
   * if two rotations are effectively the same.
   */
  public boolean equals(Object obj) {
    // standard equals() technique 1
    if (obj == this)
      return true;

    // standard equals() technique 2
    // (null will be false)
    if (!(obj instanceof Piece))
      return false;

    Piece other = (Piece) obj;

    for (TPoint point : body) {
      boolean exist_point = false;
      for (TPoint other_point : other.getBody()) {
        if (point.equals(other_point)) {
          exist_point = true;
          break;
        }
      }
      if (exist_point == false)
        return false;
    }
    return true;
  }

  /**
   * Returns an array containing the first rotation of
   * each of the 7 standard tetris pieces in the order
   * STICK, L1, L2, S1, S2, SQUARE, PYRAMID.
   * The next (counterclockwise) rotation can be obtained
   * from each piece with the {@link #fastRotation()} message.
   * In this way, the client can iterate through all the rotations
   * until eventually getting back to the first rotation.
   * (provided code)
   */
  public static Piece[] getPieces() {
    // lazy evaluation -- create static array if needed
    if (Piece.pieces == null) {
      // use makeFastRotations() to compute all the rotations for each piece
      Piece.pieces = new Piece[STRS.values().length];
      for (STRS str : STRS.values())
        Piece.pieces[str.ordinal()] = makeFastRotations(new Piece(str.toString())); // YOUR CODE HERE
    }
    return Piece.pieces;
  }

  /**
   * Given the "first" root rotation of a piece, computes all
   * the other rotations and links them all together
   * in a circular list. The list loops back to the root as s/comoon
   * as possible. Returns the root piece. fastRotation() relies on the
   * pointer structure setup here.
   */
  /*
   * Implementation: uses computeNextRotation()
   * and Piece.equals() to detect when the rotations have gotten us back
   * to the first piece.
   */
  private static Piece makeFastRotations(Piece root) {
    Piece iter_piece = root;

    while (true) {
      iter_piece.next = iter_piece.computeNextRotation();

      if (root.equals(iter_piece.next)) {
        iter_piece.next = root;
        break;
      } else {
        iter_piece = iter_piece.next;
      }
    }
    return root; // YOUR CODE HERE
  }

  /**
   * Given a string of x,y pairs ("0 0 0 1 0 2 1 0"), parses
   * the points into a TPoint[] array.
   * (Provided code)
   */
  private static TPoint[] parsePoints(String string) {
    List<TPoint> points = new ArrayList<TPoint>();
    StringTokenizer tok = new StringTokenizer(string);
    try {
      while (tok.hasMoreTokens()) {
        int x = Integer.parseInt(tok.nextToken());
        int y = Integer.parseInt(tok.nextToken());

        points.add(new TPoint(x, y));
      }
    } catch (NumberFormatException e) {
      throw new RuntimeException("Could not parse x,y string:" + string);
    }

    // Make an array out of the collection
    TPoint[] array = points.toArray(new TPoint[0]);
    return array;
  }

}
