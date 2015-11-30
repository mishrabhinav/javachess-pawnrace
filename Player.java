public class Player {
	
	private Game game;
	private Board board;
	private Color color;
	private boolean isComputerPlayer;
	private Player opponent;
	
  public Player(Game game, Board board, Color color, boolean isComputerPlayer) {

  	this.game = game;
  	this.board = board;
  	this.color = color;
  	this.isComputerPlayer = isComputerPlayer;
  }

  public void setOpponent(Player opponent) {

  	this.opponent = opponent;
  }

  public Color getColor() {

  	return color;
  }

  public boolean isComputerPlayer() {

  	return isComputerPlayer;
  }

  public Square[] getAllPawns() {

  	return null;
  }

  public Move[] getAllValidMoves() {

  	return null;
  }

  public boolean isPassedPawn(Square square) {

  	return false;
  }

  public void makeMove() {

  	if (isComputerPlayer) {
  		
  	} else {
  		
  	}
  }
}
