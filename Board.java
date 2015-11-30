public class Board {
  
  private Color color;
  private Square[][] board = new Square[8][8];

  public Board(char whiteGap, char blackGap) {

    for(int i = 0; i < 7; i++) {
     board[1][i] = new Square(1,i);
     board[1][i].setOccupier(color.WHITE); 
    } 

    for(int i = 1; i < 8; i++) {
     board[6][i] = new Square(6,i);
     board[6][i].setOccupier(color.BLACK); 
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

    boolean isCapture = move.isCapture();

    board[fX][fY].setOccupier(initialSq.occupiedBy());
    board[iX][iY].setOccupier(color.NONE);
  }

  public void unapplyMove(Move move) {

    Square initialSq = move.getFrom();
    int iX = initialSq.getX();
    int iY = initialSq.getY();

    Square finalSq = move.getTo();
    int fX = finalSq.getX();
    int fY = finalSq.getY();

    boolean isCapture = move.isCapture();

    board[iX][iY].setOccupier(finalSq.occupiedBy());
    board[fX][fY].setOccupier(color.NONE);
  }

  public void display() {

    printLetters();
    printLetters();
  }

  private void printLetters() {
    System.out.println();
    
    for (int i = 0; i < 8; i++)
      System.out.print(((char) (65 + i)) + " ");
    
    System.out.println();
  }
}
