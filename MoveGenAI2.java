import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class MoveGenAI2 {

  //-------------------------------------------------------------------------//
	
	// Self Attributes
	private Player self;
	private Color selfColor;
	private boolean myPassedPawnBool;
	private Square myPassedPawn;
	
	// Opponent Attributes
	private Player opponent;
	private Color opponentColor;
	private Move[] oppMoves;
	private boolean oppPassedPawnBool;
	private Square oppPassedPawn;
	
	// Global Attributes
	private Game game;
	private Board board;
	private int homeRowWhite = 1;
	private int homeRowBlack = 6;
	private int finishRow = selfColor == Color.WHITE ? 7 : 0;
	private Node<Move> miniMaxTree;
	
  //-----Constructor---------------------------------------------------------//
	
	public MoveGenAI2(Game game, Board board, Player self, Player opponent) {
		
		this.game = game;
		this.board = board;
		this.self = self;
		this.opponent = opponent;
		this.selfColor = self.getColor();
		this.opponentColor = opponent.getColor();
	}
	
  //-----Move Generator------------------------------------------------------//
	
	 public Move moveGen(Move[] validMoves, int moveCounter) {
		
			return null;
		}
	 	 
	 private int rateMove(Move move) {
	  	
	  		if (moveToWin(move)) {
	  			return 10;
	  		}
	  		else if (createsPassedPawn(move)) {
	  			return 9;
	  		}
	  		else if(isNonEnPassant(move)) {
	  			return 7;
	  		}
	  		else if(isEnPassant(move)) { 
	  			return 6;
	  		}
	  		else if (isTwoForward(move)) {
	  			return 5;
	  		}
	  		else {
	  			return 4;
	  		}
 }
	  
	  private boolean moveToWin(Move move) {
	  	
	  	return move.getTo().getX() == finishRow;
	  }
	  
	  private boolean createsPassedPawn(Move move) {
	  	
	  	board.applyMove(move);
	  	boolean isTrue = self.isPassedPawn(move.getTo());
	  	board.unapplyMove(move);
	  	
	  	return isTrue;
	  }
	  
	  private boolean isNonEnPassant(Move move) {
	  	
	  	return move.isCapture() && !move.isEnPassantCapture();
	  }
	  
	  private boolean isEnPassant(Move move) {
	  	
	  	return move.isEnPassantCapture();
	  }
	  
	  private boolean isTwoForward(Move move) {
	  	
	  	return Math.abs(move.getFrom().getX() - move.getTo().getX()) == 2;
	  }
	  
	  private void ABPruning(Node<Move> moveTree, Move[] moves, int moveCounter) {
	  	
	  }
	  
	  private void miniMax(Node<Move> moveTree, Move[] moves, int moveCounter) {
	  	miniMaxTree = new Node<>();
	  	
	  	//Creating my layer of Moves.
	  	for(int i = 0; i < moveCounter; i++) {
	  		int k = rateMove(moves[i]);
	  		new Node<Move>(moves[i], rateMove(moves[i]), miniMaxTree);
	  	}
	  	
	  	List<Node<Move>> myCurrentMoves = miniMaxTree.getChildren();
	  	
	  	//Generate the possible opponent moves of all your previous moves.
	  }
	  
	  public double evaluateBoard(Color color, boolean forSelf) {
	  	
	  	int homeRow = color == Color.WHITE ? homeRowWhite : homeRowBlack;
	  	int finishROW = color == Color.WHITE ? 7 : 0;
	  	double boardVal = 0;
	  	Square[] pawns = forSelf ? self.getAllPawns() : opponent.getAllPawns();
	  	Square[] oppPawns = forSelf ? opponent.getAllPawns() : self.getAllPawns();
	  	Player player = color == self.getColor() ? self : opponent;
	  	Player otherPlayer = color == self.getColor() ? opponent : self;
	  	Move[] moves = player.getAllValidMoves();
	  	Move[] otherMoves = otherPlayer.getAllValidMoves();
	  	
	  	if (game.getLastMove() == null)
  			return 3;
	  	
	  	for (int i = 0; i < pawns.length; i++) {
	  		//TODO : write board evaluation here.
	  		// In the initial starting state every board has the same value. i.e: 3
	  		// For every pawn on the home line give it a value 1
	  		// For every pawn which is not on home line give it a value (|n-home row|)/2.
	  		// For every extra pawn one player has give it +2.
	  		// For every kill possible on the board give +4.
	  		// If after the move your pawn is going to be killed give it -1.
	  		// If you are going to win the game give the board the value INTEGER.MAX_VALUE.
	  		// En-passant kill is valued 2.

	  		if (pawns[i].getX() == homeRow)
	  			boardVal += 1;
	  		else
	  			boardVal += (double) Math.abs(pawns[i].getX() - homeRow)/2.0;
	  		
	  		boardVal += Math.abs(pawns.length - oppPawns.length)*2;
	  		
	  		for (int j = 0; j < moves.length; j++) {
	  			boardVal += moves[i].isCapture() && !moves[i].isEnPassantCapture() ? 4 : 0;
	  			boardVal += moves[i].isEnPassantCapture() ? 2 : 0;
	  		}
	  		
	  		for (Move otherMove : otherMoves) {
	  			if (otherMove != null)
	  				boardVal -= otherMove.isCapture() ? 1 : 0;
	  		}
	  		
	  		boardVal = pawns[i].getX() == finishROW ? Integer.MAX_VALUE : boardVal;
	  		
	  	}
			return boardVal;
	  }
}
