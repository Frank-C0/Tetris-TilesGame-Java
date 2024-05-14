package epis.unsa;

public enum STRS {
  STICK("0 0  0 1   0 2  0 3"),
  L1("0 0  0 1   0 2  1 0"),
  L2("0 0  1 0 1 1   1 2"),
  S1("0 0  1 0   1 1  2 1"),
  S2("0 1  1 1  1 0  2 0"),
  SQUARE("0 0  0 1  1 0  1 1"),
  PYRAMID("0 0  1 0  1 1  2 0");
  private String str;

  STRS(String str){ this.str = str; }

  public String toString(){
    return str;
  }
}

