import java.util.ArrayList;

public class MoveGenAI2 {

  //-------------------------------------------------------------------------//
	
	// Self Attributes
	protected Player self;
  //private Color selfColor;
	//private boolean myPassedPawnBool;
	//private Square myPassedPawn;
	
	// Opponent Attributes
	protected Player opponent;
  //private Color opponentColor;
	//private Move[] oppMoves;
	//private boolean oppPassedPawnBool;
	//private Square oppPassedPawn;
	
	// Global Attributes
	private Game game;
	protected Board board;
	private int homeRowWhite = 1;
	private int homeRowBlack = 6;
	private int finishRow;
	protected ArrayList<Double> ratedPawns;
	private int estnow;         // base estimation
  private int est_cost;       // Cost estimation value
  private int est_attackCost;
	//private Node<Move> miniMaxTree;
	
  //-----Constructor---------------------------------------------------------//
	
	public MoveGenAI2(Game game, Board board, Player self, Player opponent) {
		
		this.game = game;
		this.board = board;
		this.self = self;
		this.opponent = opponent;
		this.finishRow = self.getColor() == Color.WHITE ? 7 : 0;
		rateAttacks(self, opponent);
//		this.selfColor = self.getColor();
//		this.opponentColor = opponent.getColor();
	}
	
  //-----Move Generator------------------------------------------------------//
	
	 public Move moveGen(Move[] validMoves, int moveCounter) {
		
			return null;
		}
	 
	 private void setDefaults() {
		 estnow = 0;
		 est_cost = 0;
		 est_attackCost = 0;
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
	  
	  public double evaluate() {
	  	
	  	double dc = ratePawns(self, opponent) - est_cost;;
	  	double dac = rateAttacks(self, opponent) - est_attackCost;
	  	return dc*10 + dac;
	  }
	  
	  private double estimateBase() {

	    est_cost = 0;
	    est_attackCost = 0;
	    double out = evaluate();
	    est_cost = getCost();
	    est_attackCost = getAttackCost();
	    return out;
	  }
	  
	  public double ratePawns(Player self, Player opp) {
	  	
	  	return this.cost(self, self.listOfPawns()) - this.cost(opp, opp.listOfPawns());
	  }
	  
	  public double rateAttacks(Player self, Player opp) {
	  	
	  	return this.costAttacks(self, self.listOfValidMoves()) - this.costAttacks(opp, opp.listOfValidMoves());
	  }
	  
	  public double cost(Player player, ArrayList<Square> pawns) {
	  	
	  	int finishRow = player.getColor() == Color.WHITE ? 7 : 0;
	  	int result = 0;
	  	
	  	for (int i = 0; i < pawns.size(); i++) {
	  		double j = player.isPassedPawn(pawns.get(i)) ? (4.0) : (1.0);
	  		j = pawns.get(i).getX() == finishRow ? (10.0) : j;
	  		if ( j == 1 )
	  			j = Math.abs(pawns.get(i).getX() - Math.abs(finishRow - 6))/2.0;
	  		result += j;
	  	}
	  	
	  	return (player.getColor() == Color.WHITE ? 1 : -1)*result;
	  }
	  
	  public double costAttacks(Player player, ArrayList<Move> moves) {
	  	
	  	int result = 0;
	  	
	  	for(int i = 0; i < moves.size(); i++) {
	  		result += moves.get(i).isCapture() ? 5 : 0; 
	  	}
	  	
	  	return (player.getColor() == Color.WHITE ? 1 : -1)*result;
	  }
}
