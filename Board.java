public class Board {

  private Square[][] board = new Square[8][8];

  public Board(char whiteGap, char blackGap) {

    for (int i = 0; i < 8; i++) {

      for (int j = 0; j < 8; j++)
        board[i][j] = new Square(i, j);
    }

    for (int i = 0; i < 7; i++) {

      board[6][i+1].setOccupier(Color.BLACK);
      board[1][i].setOccupier(Color.WHITE);
    }

  }
 
  public Square getSquare(int x, int y) {

    return board[x-1][y-1];
  }
  
  public void applyMove(Move move) {

    Square initialSq = move.getFrom();
    int iX = initialSq.getX();
    int iY = initialSq.getY();

    Square finalSq = move.getTo();
    int fX = finalSq.getX();
    int fY = finalSq.getY();

    board[fX][fY].setOccupier(initialSq.occupiedBy());
    board[iX][iY].setOccupier(Color.NONE);
  }

  public void unapplyMove(Move move) {

    Square initialSq = move.getFrom();
    int iX = initialSq.getX();
    int iY = initialSq.getY();

    Square finalSq = move.getTo();
    int fX = finalSq.getX();
    int fY = finalSq.getY();

    board[iX][iY].setOccupier(finalSq.occupiedBy());
    board[fX][fY].setOccupier(Color.NONE);
  }

  public void display() {

    printLetters();

    System.out.println();

    for (int i = 7; i >= 0; i--) {

        System.out.printf("%d  ", i + 1);

      for (int j = 0; j < 8; j++) {
        Color c = board[i][j].occupiedBy();
        String s = getSquareElementCode(c);
        System.out.print(s + " ");
      }

        System.out.printf(" %d\n", i + 1);
    }

    printLetters();
  }

  public String getSquareElementCode(Color c) {

    switch(c) {
      case BLACK: return "B";
      case WHITE: return "W";
      default: return ".";
    }
  }

  public void printLetters() {
    System.out.println();

    System.out.print("   ");

    for (int i = 0; i < 8; i++)
      System.out.print(((char) (65 + i)) + " ");

    System.out.println();
  }
}
