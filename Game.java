import java.util.ArrayList;

public class Game {

  private Color currentPlayer;
  private ArrayList<Move> movesList = new ArrayList<Move>();
  private Board board;

  public Game(Board board) {

  	this.board = board;
    currentPlayer = Color.WHITE;
  }

  public Color getCurrentplayer() {

	return currentPlayer;
  }

  public Move getLastMove() {

  	return movesList.get(movesList.size()-1);
  }

  public void applyMove(Move move) {

  	board.applyMove(move);
  	movesList.add(move);
  	currentPlayer = changePlayer(currentPlayer);
  
  }

  public void unapplyMove(Move move) {

  	board.unapplyMove(move);;
  	movesList.remove(movesList.size() - 1);
  	currentPlayer = changePlayer(currentPlayer);
  
  }

  public boolean isFinished() {
  	
  	boolean gameOver = false;
  	
  	for (int i = 0; i < 8; i++) {
  		if (board.getSquare(0, i).occupiedBy() != Color.NONE
  				|| board.getSquare(7, i).occupiedBy() != Color.NONE)
  			gameOver = true;
  	}

  	return gameOver;
  }

  public Color getGameResult() {

  	return currentPlayer;
  }

  public Move parseMove(String san) {

  	if (Notations.captureMove(san)) {
  		
  		int initialColumn = Notations.charToInt(san.charAt(0));
  		int targetColumn = Notations.charToInt(san.charAt(2));
  		int targetRow = Character.getNumericValue(san.charAt(3)) - 1;
  		
  		if (board.getSquare(targetRow, initialColumn + 1).occupiedBy() == currentPlayer)
  			return (new Move(board.getSquare(targetRow, initialColumn + 1), board.getSquare(targetRow + 1, targetColumn + 1), true));
 	}
  	
  	return null;
  } 
  
  public Color changePlayer(Color c) {
  	
  	switch(c) {
  	case WHITE: return Color.BLACK;
  	case BLACK: return Color.WHITE;
  	default: return Color.NONE;
  	}
  	
  }
}
