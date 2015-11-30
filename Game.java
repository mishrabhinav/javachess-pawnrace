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

  	return false;
  }

  public Color getGameResult() {

  	return currentPlayer;
  }

  public Move parseMove(String san) {

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
