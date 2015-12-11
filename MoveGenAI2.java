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
	private double estnow;         // base estimation
  private double est_cost;       // Cost estimation value
  private double est_attackCost;
  private boolean white;
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
	 	 
	  public double evaluate() {
	  	
	  	double dc = ratePawns(self, opponent) - est_cost;
	  	double dac = rateAttacks(self, opponent) - est_attackCost;
	  	return dc*10 + dac;
	  }
	  
	  public double estimateBase(Color color) {

	    est_cost = 0;
	    est_attackCost = 0;
	    double out = evaluate();
	    est_cost = this.cost(color);
	    est_attackCost = this.costAttacks(color);
	    return out;
	  }
	  
	  public double ratePawns(Player self, Player opp) {
	  	
	  	return this.cost(self.getColor()) + this.cost(opponent.getColor());
	  }
	  
	  public double rateAttacks(Player self, Player opp) {
	  	
	  	return this.costAttacks(self.getColor()) + this.costAttacks(opponent.getColor());
	  }
	  
	  public double cost(Color color) {
	  	
	  	Player player = this.getPlayerFromColor(color);
	  	ArrayList<Square> pawns = player.listOfPawns();
	  	
	  	int finishRow = player.getColor() == Color.WHITE ? 7 : 0;
	  	int result = 0;
	  	
	  	for (int i = 0; i < pawns.size(); i++) {
	  		double j = player.isPassedPawn(pawns.get(i)) ? (4.0) : (1.0);
	  		j = pawns.get(i).getX() == finishRow ? (10.0) : j;
	  		if ( j == 1 )
	  			j = Math.abs(pawns.get(i).getX() - Math.abs(finishRow - 6))/2.0;
	  		result += j;
	  	}
	  	
	  	return (color == Color.WHITE ? 1 : -1)*result;
	  }
	  
	  public double costAttacks(Color color) {
	  	
	  	Player player = this.getPlayerFromColor(color);
	  	ArrayList<Move> moves = player.listOfValidMoves();
	  	
	  	int result = 0;
	  	
	  	for(int i = 0; i < moves.size(); i++) {
	  		result += moves.get(i).isCapture() ? 5 : 0; 
	  	}
	  	
	  	return (player.getColor() == Color.WHITE ? 1 : -1)*result;
	  }
	  
	  public Player getPlayerFromColor(Color playerColor) {
	  	
	  	if (playerColor == self.getColor())
	  		return self;
	  	else
	  		return opponent;
	  }
}
