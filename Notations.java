public class Notations {

	public static String toSAN(Square s) {
		
		int x = 'a';
		
		return ("" + ((char) (s.getY() + x)) + (s.getX() + 1));
	}
	
	public static int charToInt(char c) {
		
		char cUpper = Character.toUpperCase(c);

		return ((int) cUpper - (int) 'A');
	}
	
	public static String intToString(int i) {
				
	  return ("" + ((char) i));
	}
	
	public static boolean captureMove(String san) {
		
		char x = san.charAt(1);
		
		return (x == 'x');
	}
	
	public static String moveToString(Move move) {
		//pre: Moves are valid.
		if (move.isCapture()) {
			return "" + move.getFrom().toSAN().charAt(0) + "x" + move.getTo().toSAN();
		} else
			return move.getTo().toSAN();
	}
	
	public static Move stringToMove(Board board, Color currentPlayer, String san) {
		
		
		
    if (captureMove(san)) {
    	
    	int initialColumn = Notations.charToInt(san.charAt(0));
  		int targetColumn = Notations.charToInt(san.charAt(2));
  		int targetRow = Character.getNumericValue(san.charAt(3)) - 1;
  		
  		if (board.getSquare(targetRow, initialColumn + 1).occupiedBy() == currentPlayer)
  			return (new Move(board.getSquare(targetRow, initialColumn + 1), board.getSquare(targetRow + 1, targetColumn + 1), true));
 	  
    } else {
    	
  		int targetColumn = Notations.charToInt(san.charAt(0));
  		int targetRow = Character.getNumericValue(san.charAt(1)) - 1;
  		
    	int i = currentPlayer == Color.WHITE ?  0 : 2;
    	int j = currentPlayer == Color.WHITE ? -1 : 3;
    	
    	//For one step move.
    	if (board.getSquare(targetRow + i, targetColumn + 1).occupiedBy() == currentPlayer) {
    		return (new Move(board.getSquare(targetRow + i, targetColumn + 1), board.getSquare(targetRow + 1, targetColumn + 1), false));
    	}
      //For two step move.
    	else if ((targetRow + j == 2 || targetRow + j == 7) && board.getSquare(targetRow + j, targetColumn + 1).occupiedBy() == currentPlayer)
  			return (new Move(board.getSquare(targetRow + j, targetColumn + 1), board.getSquare(targetRow + 1, targetColumn + 1), false));
    }
    
    return null;
	}
}