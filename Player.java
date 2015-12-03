public class Player {
	
	private Game game;
	private Board board;
	private Color color;
	private boolean isComputerPlayer;
	private Player opponent;
	private Square[] currentPawns = new Square[7];
	//private Square[] initialPawns = new Square[7];
	//private Move[] validMoves = new Move[30];
	
  public Player(Game game, Board board, Color color, boolean isComputerPlayer) {

  	this.game = game;
  	this.board = board;
  	this.color = color;
  	this.isComputerPlayer = isComputerPlayer;
  	//initialPawns = this.getAllPawns();
  	currentPawns = this.getAllPawns();
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

  	int c = 0;
  	Square[] tempPawns = new Square[7];
  	
  	for (int i = 1; i <= 8; i++)
  		for (int j = 1; j <= 8; j++) {
  			Square temp = board.getSquare(i, j);
  			if (temp.occupiedBy() == color) {
  				//System.out.println(Notations.toSAN(temp));
  				tempPawns[c] = temp;
  				c++;
  			}
  		}
  	
  	currentPawns = tempPawns;
  	return tempPawns;
  }

  public Move[] getAllValidMoves() {
  	
  	int moveCounter = 0;
  	Move[] tempMoves = new Move[30];
  	
  	Square[] newPawns = this.getAllPawns();
  	currentPawns = newPawns;
  	
  	for (int i = 0; i < 7 ; i++) {
  		
  		Square oneF = MoveSet.oneForward(board, currentPawns[i], color);
  		Square twoF = MoveSet.twoForward(board, currentPawns[i], color);
  		Square[] diag = MoveSet.killDiagonal(board, currentPawns[i], color);;
  		
  		if (!(oneF == null)) {
  		  tempMoves[moveCounter] = new Move(currentPawns[i], oneF, false);
  		  moveCounter++;
  		}
  		
  		if (!(twoF == null)) {
  		  tempMoves[moveCounter] = new Move(currentPawns[i], twoF, false);
  		  moveCounter++;
  		}
  		
  		for (int j = 0; j < 2; j++) {
  			if (!(diag[j] == null)) {
  		    tempMoves[moveCounter] = new Move(currentPawns[i], diag[j], false);
  		    moveCounter++;
  			}
  		}
  	}
  	for (int i = moveCounter; i < tempMoves.length; i++)
  		tempMoves[moveCounter] = null;
  	
  	
  	return tempMoves;
  }

  public boolean isPassedPawn(Square square) {
  	
  	return PassedValidator.isPassedPawn(board, square, color);
  }
  
  public void makeMove() {

  	if (isComputerPlayer) {
  		
  	} else {
  		
  	}
  }
}
