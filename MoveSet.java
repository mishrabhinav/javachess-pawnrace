public class MoveSet {
	
	public static Square oneForward(Board board, Square currentPawn, Color playerColor) {
		
		int x = 0;
		
		if (playerColor == Color.WHITE) {
		  x = 2;
		} else {
			x = 0;
		}
		
		Square squareAhead = board.getSquare(currentPawn.getX() + x, currentPawn.getY() + 1);
		
	  if (squareAhead.occupiedBy() == Color.NONE)
		  return squareAhead;
	  else
		  return null;
	  
	}
	
	public static Square twoForward(Board board, Square currentPawn, Color playerColor) {
		
    int x = -1;
    int y = 1;
		
		if (playerColor == Color.WHITE) {
		  x = 3;
		  y = 1;
		} else {
			x = -1;
			y = 6;
		}
		
		Square twoAhead = board.getSquare(currentPawn.getX() + x,  currentPawn.getY() + 1);
		
		if (currentPawn.getX() == y 
		      && twoAhead.occupiedBy() == Color.NONE)
		  return twoAhead;
		else
		  return null;
		  
	}
	
	public static Square[] killDiagonal(Board board, Square currentPawn, Color playerColor) {
		
		Square[] returnSq = new Square[2];
		Square returnSq1 = null;
		Square returnSq2 = null;
		
		int currentX = currentPawn.getX();
		int currentY = currentPawn.getY();
		
		if (currentY == 0) {
			
			switch (playerColor) {
			  case WHITE:
			  	
			  	if (checkSquare(board.getSquare(currentX + 2, currentY + 2), playerColor))
			  		returnSq1 = board.getSquare(currentX + 2, currentY + 2);
			  	
			  	break;
			  	
			  case BLACK: 
			  	
			  	if (checkSquare(board.getSquare(currentX, currentY + 2), playerColor))
			  		returnSq1 = board.getSquare(currentX, currentY + 2);
			  	
			  	break;
			  	
			  case NONE: break;
			}
			
		} else if (currentY == 7) {
			
			switch (playerColor) {
			  case WHITE: 
			  	
			  	if (checkSquare(board.getSquare(currentX + 2, currentY), playerColor))
			  		returnSq1 = board.getSquare(currentX + 2, currentY);
			  	
			  	break;
			  	
			  case BLACK:
			  	
			  	if (checkSquare(board.getSquare(currentX, currentY), playerColor))
			  		returnSq1 = board.getSquare(currentX, currentY);
			  	
			  	break;
			  	
			  case NONE: break;
			}
			
		} else {
			
			switch (playerColor) {
			  case WHITE:
			  	
			  	if (checkSquare(board.getSquare(currentX + 2, currentY + 2), playerColor))
			  		returnSq1 = board.getSquare(currentX + 2, currentY + 2);
			  	
			  	if (checkSquare(board.getSquare(currentX + 2, currentY), playerColor))
			  		returnSq2 = board.getSquare(currentX + 2, currentY);
			  	
			  	break;
			  	
			  case BLACK:
			  	
			  	if (checkSquare(board.getSquare(currentX, currentY + 2), playerColor))
			  		returnSq1 = board.getSquare(currentX, currentY + 2);
			  	
			  	if (checkSquare(board.getSquare(currentX, currentY), playerColor))
			  		returnSq2 = board.getSquare(currentX, currentY);
			  	
			  	break;
			  	
			  case NONE: break;
			}
		}
				
		returnSq[0] = returnSq1;
		returnSq[1] = returnSq2;
		
		return returnSq;
	}
	
	private static boolean checkSquare(Square square, Color playerColor) {
		
		Color squareColor = square.occupiedBy();
		
		if ((squareColor == Color.BLACK) && (playerColor == Color.WHITE)) {
			
			return true;
			
		} else if ((squareColor == Color.WHITE) && (playerColor == Color.BLACK)) {
			
			return true;
			
		} else {
		
			return false;
			
		}
	}
}
