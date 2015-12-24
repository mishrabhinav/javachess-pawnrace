import java.util.ArrayList;

public class Game {

  private Color currentPlayer;
  private ArrayList<Move> movesList = new ArrayList<Move>(10);
  private Board board;

  public Game(Board board) {

  	this.board = board;
    this.currentPlayer = Color.WHITE;
  }

  public Color getCurrentplayer() {

	return currentPlayer;
  }

  public Move getLastMove() {

  	Move lastMove = movesList.size() > 0 ? movesList.get(movesList.size() - 1) : null;
  	return lastMove;
  }

  public void applyMove(Move move) {

  	board.applyMove(move);
  	movesList.add(move);
  	if (move.isEnPassantCapture()) {
  		int i = currentPlayer == Color.WHITE ? 0 : 2;
  		board.getSquare(move.getTo().getX() + i, move.getTo().getY() + 1).setOccupier(Color.NONE);
  	}
  	System.out.println("\nPlayer performed: " + Notations.moveToString(move));
  	changePlayer();
  
  }

  public void unapplyMove(Move move) {

  	board.unapplyMove(move);;
  	movesList.remove(movesList.size() - 1);
  	changePlayer();
  
  }

  public boolean isFinished(Player white, Player black) {
  	
  	boolean gameOver = false;
  	
  	for (int i = 1; i <= 8; i++) {
  		if (board.getSquare(1, i).occupiedBy() != Color.NONE
  				|| board.getSquare(8, i).occupiedBy() != Color.NONE
  				|| white.numValidMoves() != 0 || black.numValidMoves() != 0)
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
