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
  private int INF = -1000;
  private Move bestOption;
	//private Node<Move> miniMaxTree;
	
  //-----Constructor---------------------------------------------------------//
	
	public MoveGenAI2(Game game, Board board, Player self, Player opponent) {
		
		this.game = game;
		this.board = board;
		this.self = self;
		this.opponent = opponent;
		this.finishRow = self.getColor() == Color.WHITE ? 7 : 0;
	}
	
  //-----Move Generator------------------------------------------------------//
	 	 
	private int ratePawns(Player player) {
		
		int pawnScore = 0;
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++){
				pawnScore += board.getSquare(i,j).occupiedBy() == player.getColor() ? 1 : -1;
			}
		return pawnScore;
	}
	
	private int rateAttacks(Player player) {
		
		int attackScore = 0;
		
		ArrayList<Move> playerMoves = player.listOfValidMoves();
		//ArrayList<Move> oppMoves    = player.getOpponent().listOfValidMoves();
		  
		for (int i = 0; i < playerMoves.size(); i++){
			if (playerMoves.get(i).isCapture()){
			  attackScore += 2;	
			}
		}
		return attackScore;
	}
	
	private int evaluate() {
		return 0;
	}
	
	private int Max(int depth) {
		
		if (depth == 0)
			return evaluate();
		
		int best = -INF;
		ArrayList<Move> moves = self.listOfValidMoves();
		while (moves.size() > 0) {
			Move tempChoice = moves.remove(0);
			board.applyMove(tempChoice);
			int val = -Min(depth-1);
			if (val > best) {
				best = val;
				bestOption = tempChoice;
			}
			board.unapplyMove(tempChoice);
		}
		return best;
	}
	
	private int Min(int depth) {
		
		if (depth == 0)
			return evaluate();
		
		int best = -INF;
		ArrayList<Move> moves = opponent.listOfValidMoves();
		while (moves.size() > 0) {
			Move tempChoice = moves.remove(0);
			board.applyMove(tempChoice);
			int val = -Max(depth-1);
			if (val > best) {
				best = val;
				bestOption = tempChoice;
			}
			board.unapplyMove(tempChoice);
		}
		return best;
	}
}
