
public class PassedValidator {
	
	private static Color oppositeColor;

	public static boolean isPassedPawn(Board board, Square square, Color color) {
		
		switch(color) {
		case WHITE: oppositeColor = Color.BLACK; break;
		case BLACK: oppositeColor = Color.WHITE; break;
		default: oppositeColor = null; break;
		}
		
		int columnNumber = square.getY();
  	int rowNumber    = square.getX();
  	
  	boolean returnBool = false;
  	
  	if (color == Color.WHITE) {
    	switch (columnNumber) {
    	case 0:
  	  	returnBool = checkColumnUp(board, columnNumber + 2, rowNumber + 2) 
    		             && checkColumnUp(board, columnNumber + 1, rowNumber + 2);
    		break;
    	case 7:
    		returnBool = checkColumnUp(board, columnNumber, rowNumber + 2) 
                     && checkColumnUp(board, columnNumber + 1, rowNumber + 2);
    		break;
    	default:
    		returnBool = checkColumnUp(board, columnNumber, rowNumber + 2) 
                     && checkColumnUp(board, columnNumber + 1, rowNumber + 2)
    		             && checkColumnUp(board, columnNumber + 2, rowNumber + 2);
    		break;
  	  }
  	} else {
  		switch (columnNumber) {
    	case 0:
  	  	returnBool = checkColumnDown(board, columnNumber + 2, rowNumber) 
    		             && checkColumnDown(board, columnNumber + 1, rowNumber);
    		break;
    	case 7:
    		returnBool = checkColumnDown(board, columnNumber, rowNumber) 
                     && checkColumnDown(board, columnNumber + 1, rowNumber);
    		break;
    	default:
    		returnBool = checkColumnDown(board, columnNumber, rowNumber) 
                     && checkColumnDown(board, columnNumber + 1, rowNumber)
                     && checkColumnDown(board, columnNumber + 2, rowNumber);
        break;
  	  }
  	}
  	
    return returnBool;
	}
	
	private static boolean checkColumnUp(Board board, int colNum, int rowNum) {
		boolean result = true;
		for (int i = rowNum; i < 8; i++) {
			result = result && (board.getSquare(i, colNum).occupiedBy() != oppositeColor);
		}
		return result;
	}
	
	private static boolean checkColumnDown(Board board, int colNum, int rowNum) {
		boolean result = true;
		for (int i = rowNum; i > 0; i--) {
			result = result && (board.getSquare(i, colNum).occupiedBy() != oppositeColor);
		}
		return result;
	}
}
