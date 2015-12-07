import java.util.ArrayList;

/* File: MoveSet.java
 * 
 * This file contains the valid move generator. It is split up into 3 parts.
 * Function 1: oneForward() -> checks whether a step forward move is possible
 *             and if possible gives back the move.
 * Function 2: twoForward() -> if the pawn is at it's initial position then
 *             a two step forward move is possible. This function checks it.
 * Function 3: killDiagonal() -> If there is a pawn of another color on the
 *             diagonal this function finds it and returns the valid move.
 *             It uses a helper function called checkSquare() which checks
 *             whether the desired square and the pawn square have different
 *             colors or not and hence returns a boolean.  
 *                        
 */


//Use a direction variable to shorten your if's. 
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
		} else {
			y = 6;
		}
		
		Square twoAhead;
		
		if (((currentPawn.getX() == 1 && currentPawn.occupiedBy() == Color.WHITE)
				|| (currentPawn.getX() == 6 && currentPawn.occupiedBy() == Color.BLACK))
	      && board.getSquare(currentPawn.getX() + 2, currentPawn.getY() + 1).occupiedBy() == Color.NONE) {
		  twoAhead = board.getSquare(currentPawn.getX() + x,  currentPawn.getY() + 1);
		  if(twoAhead.occupiedBy() == Color.NONE)
				return twoAhead;
		}
		
		return null;

		/*else
			return null;
		
		if ((currentPawn.getX() == 1 && currentPawn.occupiedBy() == Color.WHITE)
				&& (currentPawn.getX() == 6 && currentPawn.occupiedBy() == Color.BLACK)
		    && twoAhead.occupiedBy() == Color.NONE
		    && board.getSquare(currentPawn.getX() + 2, currentPawn.getY() + 1).occupiedBy() == Color.NONE)
		  return twoAhead;
		else
		  return null;
		  */
	}
	
	public static Square[] diagonalKill(Board board, Square currentPawn, Color playerColor) {
		
		Square[] returnSq = new Square[2];
		Square returnSq1 = null;
		Square returnSq2 = null;
		//Square returnSq3 = null;
		
		int currentX = currentPawn.getX();
		int currentY = currentPawn.getY();
		
		int i = playerColor == Color.WHITE ? 2 : 0;
			
		if (currentY < 7 && checkSquare(board.getSquare(currentX + i, currentY + 2), playerColor))
			returnSq1 = board.getSquare(currentX + i, currentY + 2);
			  	
		if (currentY > 0 && checkSquare(board.getSquare(currentX + i, currentY), playerColor))
			returnSq2 = board.getSquare(currentX + i, currentY);
    
		//for En-passant rule to be applied.
			  		
		returnSq[0] = returnSq1;
		returnSq[1] = returnSq2;
		
		return returnSq;
	}
	
	public static Square enPassantKill(Board board, Square currentPawn, Move lastMove, Color playerColor) {
		
		Square initialSq = lastMove.getFrom();
		Square finalSq = lastMove.getTo();
		
		if (Math.abs(initialSq.getX() - finalSq.getX()) == 2) {
  		int midSquareX = (initialSq.getX() + finalSq.getX())/2;
	  	int midSquareY = (initialSq.getY() + finalSq.getY())/2;
		
		  Square midSq = board.getSquare(midSquareX + 1, midSquareY + 1);
		
	  	int i = playerColor == Color.WHITE ? 2 : 0;
		
		  if (midSquareY < 7 && (board.getSquare(midSquareX + i, midSquareY + 2) == currentPawn))
			  return midSq;
			  	
		  if (midSquareY > 0 && (board.getSquare(midSquareX + i, midSquareY) == currentPawn))
			  return midSq;
		}
		
		return null;
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
