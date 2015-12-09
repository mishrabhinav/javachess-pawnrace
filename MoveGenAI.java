import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class MoveGenAI {

  //-------------------------------------------------------------------------//
	
	// Self Attributes
	private Player self;
	private Color selfColor;
	private boolean myPassedPawnBool;
	private Square myPassedPawn;
	private Hashtable<Integer, Move> myWMoves;
	
	// Opponent Attributes
	private Player opponent;
	private Color opponentColor;
	private Move[] oppMoves;
	private boolean oppPassedPawnBool;
	private Square oppPassedPawn;
	private Hashtable<Integer, Move> opponentWMoves;
	
	// Global Attributes
	private Game game;
	private Board board;
	private int penRow = selfColor == Color.WHITE ? 1 : 6;
	private int finishRow = selfColor == Color.WHITE ? 7 : 0;
	
  //-----Constructor---------------------------------------------------------//
	
	public MoveGenAI(Game game, Board board, Player self, Player opponent) {
		
		this.game = game;
		this.board = board;
		this.self = self;
		this.opponent = opponent;
		this.selfColor = self.getColor();
		this.opponentColor = opponent.getColor();
	}
	
  //-----Move Generator------------------------------------------------------//
	
	 public Move moveGen(Move[] validMoves, int moveCounter) {
		
		  if (moveCounter == 0)
		  	return null;
		  this.myWMoves = new Hashtable<>(moveCounter);
		  myWMoves.clear();
		
			this.winningMove(validMoves, moveCounter, myWMoves);
			this.hasPassedPawn(self, myPassedPawnBool, myPassedPawn);
			this.killMove(validMoves, moveCounter, myWMoves);
			this.stayThere(validMoves, moveCounter, myWMoves);
			
			if (myWMoves.containsKey(10)){
				return myWMoves.get(10);
			}
			
			if (myPassedPawnBool) {
				
				if (myWMoves.containsKey(8) 
						&& myWMoves.get(8).getFrom().getX() == penRow 
						&& myWMoves.get(8).isCapture())
					if (lookIntoTheFuture(myWMoves.get(8)))
					  return myWMoves.get(8);
				
				for (int i = 0; i < moveCounter; i++)
					if (validMoves[i].getFrom().getX() == myPassedPawn.getX() 
					    && validMoves[i].getFrom().getY() == myPassedPawn.getY())
						return validMoves[i];
			}
			
			if (myWMoves.containsKey(8))
				return myWMoves.get(8);

			Random randomGen = new Random();
			int m = randomGen.nextInt(moveCounter);
			while (game.getLastMove() == null && validMoves[m].isEnPassantCapture() 
					   && myWMoves.get(-1) != validMoves[m])
				m = randomGen.nextInt(moveCounter);
			
			return validMoves[m];
		}
		
	//-Look in future----------------------------------------------------------//
	 
	 private boolean lookIntoTheFuture(Move move) {

		board.applyMove(move);
		this.rateOppMoves();
		// If the move generates a passed pawn just don't apply the move.
		if (oppPassedPawnBool) {
			this.moveGen(self.getAllValidMoves(), self.numValidMoves());
		  if (myWMoves.containsKey(10) || (myPassedPawnBool && finishRow - myPassedPawn.getX() < oppPassedPawn.getX() - 7 - finishRow)) {
		  	board.unapplyMove(move);
		    return true;
		  }
		  else {
		  	board.unapplyMove(move);
		  	return false;
		  }
		}
		
		return false;
	}

	public void rateMoves(Move[] validMoves, int moveCounter, Hashtable<Integer, Move> weightedMoves) {
		 
		 this.winningMove(validMoves, moveCounter, weightedMoves);
		 this.hasPassedPawn(self, myPassedPawnBool, myPassedPawn);
		 this.hasPassedPawn(opponent, oppPassedPawnBool, oppPassedPawn);
		 this.killMove(validMoves, moveCounter, weightedMoves);
		 this.stayThere(validMoves, moveCounter, weightedMoves);
		 this.seeDoubleMoves(validMoves, moveCounter, weightedMoves);
		 
	 }
	 
  /* **************************************************************************
   * 
   * Self Analysis.
   * 
   * *********************************************************************** */
  
	
  
  //-------------------------------------------------------------------------//
  
  private void stayThere(Move[] validMoves, int moveCounter, Hashtable<Integer, Move> weightedMoves) {
  	
  	int u = selfColor == Color.WHITE ? 3 : -1;
  	
  	for (int i = 0; i < moveCounter; i++) {
  		Move vMove = validMoves[i];
  		if (vMove != null 
  				&& ((vMove.getFrom().getX() == 1 && vMove.getFrom().occupiedBy() == Color.WHITE) 
  						|| (vMove.getFrom().getX() == 6 && vMove.getFrom().occupiedBy() == Color.BLACK))) {
  			Square initialSq = vMove.getFrom();
  			int X = initialSq.getX();
  			int Y = initialSq.getY();
  			if (Y > 0 && (board.getSquare(X + u, Y).occupiedBy() == opponentColor)
  					|| Y < 7 && (board.getSquare(X + u, Y + 2).occupiedBy() == opponentColor)){
  			  weightedMoves.remove(0, vMove);
  			  weightedMoves.put(-1, vMove);
  			}
  		}
  	}
  }
	
  //-------------------------------------------------------------------------//
	
	

  /* **************************************************************************
   * 
   * Opponent Analysis.
   * 
   * *********************************************************************** */

  private void setOppMoves() {
  	oppMoves = opponent.getAllValidMoves();
  }
  
  private void rateOppMoves() {
 
  	opponentWMoves = new Hashtable<>(opponent.numValidMoves());
 
  	this.setOppMoves();
  	this.winningMove(oppMoves, opponent.numValidMoves(), opponentWMoves);
  	this.hasPassedPawn(opponent, oppPassedPawnBool, oppPassedPawn);
  	this.killMove(oppMoves, opponent.numValidMoves(), opponentWMoves);
  	this.seeDoubleMoves(oppMoves, opponent.numValidMoves(), opponentWMoves);
  }
  
  //--Functions for analysis-------------------------------------------------//
  
  private void winningMove(Move[] validMoves, int moveCounter, Hashtable<Integer, Move> weightedMoves) {
		 
		for (int i = 0; i < moveCounter ; i++) {
			if (validMoves[i].getTo().getX() == finishRow)
				weightedMoves.put(10, validMoves[i]);
			else
				weightedMoves.put(0, validMoves[i]);
		}
	}
	
  private void killMove(Move[] validMoves, int moveCounter, Hashtable<Integer, Move> weightedMoves) {
		
  	for (int i = 0; i < moveCounter ; i++) {
			if (validMoves[i].isCapture()) {
				weightedMoves.remove(0, validMoves[i]);
				weightedMoves.put(8, validMoves[i]);
			}
		}
	}
  
  private boolean detectPawnChains() {
	
	  Square[] opponentPawns = opponent.getAllPawns();
	  int len = opponentPawns.length;
	
	  for (int i = 0; i < len; i++)
		  for (int j = 0; j < len; j++) {
			  if (opponentPawns[i] == null || opponentPawns[j] == null)
				  continue;
			
			  if (opponentPawns[i].getX() + 1 == opponentPawns[j].getX())
				  return true;
		  }
	
	  return false;
  }
  
  private void hasPassedPawn(Player player, Boolean hasPassedPawn, Square passedPawn) {

		hasPassedPawn = false;
		Square[] allPawns = player.getAllPawns();
		List<Square> passedPawns = new ArrayList<>();
		
		for (Square pawn : allPawns) {
			if (pawn != null && player.isPassedPawn(pawn)) {
	  		hasPassedPawn = true;
		  	passedPawns.add(pawn);
		  }
		}
		
		if (selfColor == Color.WHITE) {
	  	if (passedPawns.size() > 0) {
	  		passedPawn = passedPawns.get(0);
	  		for (int i = 1; i < passedPawns.size(); i++)
		  		passedPawn = passedPawn.getX() < passedPawns.get(i).getX() ? passedPawns.get(i) : passedPawn;
		  }
		}
	  else {
	  		if (passedPawns.size() > 0) {
	  			passedPawn = passedPawns.get(0);
		  		for (int i = 1; i < passedPawns.size(); i++)
			  		passedPawn = passedPawn.getX() > passedPawns.get(i).getX() ? passedPawns.get(i) : passedPawn;
			  }
	  }
	}
  
  private void seeDoubleMoves(Move[] validMoves, int moveCounter, Hashtable<Integer, Move> weightedMoves) {
  	
  	for (int i = 0 ; i < moveCounter; i++)
  		if(Math.abs(validMoves[i].getFrom().getX() - validMoves[i].getFrom().getX()) == 2
  		   && board.getSquare(validMoves[i].getTo().getX()+1,validMoves[i].getTo().getY()+2).occupiedBy() == Color.NONE) {
  			weightedMoves.replace(0, validMoves[i]);
  			weightedMoves.put(4, validMoves[i]);
  		}
  }
  
  private void rateMoves(Move[] validMoves, int moveCounter) {
  	
  }
}
