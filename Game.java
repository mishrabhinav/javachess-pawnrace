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
  	changePlayer();
  
  }

  public void unapplyMove(Move move) {

  	board.unapplyMove(move);;
  	movesList.remove(movesList.size() - 1);
  	changePlayer();
  
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
 	
  	return Notations.stringToMove(board, currentPlayer, san);
  } 
  
  public void changePlayer() {
  	
  	currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;

  }
}
