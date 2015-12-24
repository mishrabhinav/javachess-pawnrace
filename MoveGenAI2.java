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
	private int initialCost;         // base estimation
  private int initialPawnCost;       // Cost estimation value
  private int initialAttackCost;
  private int INF = 1000;
  private Move bestOption;
	//private Node<Move> miniMaxTree;
	
	
	public MoveGenAI2(Game game, Board board, Player self, Player opponent) {
		
		this.game = game;
		this.board = board;
		this.self = self;
		this.opponent = opponent;
		this.finishRow = self.getColor() == Color.WHITE ? 7 : 0;
	}
	
	public Move generator(Player player,int depth){
		minimax(player, depth);
		return bestOption;
	}
	 	 
	public int baseEvaluate(Player player){
		
		initialPawnCost = 0;
		initialAttackCost = 0;
		int out = evaluate(player);
		initialPawnCost = ratePawns(player);
		initialAttackCost = rateAttacks(player);
		return out;
	}
	
	private int ratePawns(Player player) {
		
		int pawnScore = 0;
		
		/*for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++){
				Square focus = board.getSquare(i+1, j+1);
				if (focus.occupiedBy() != Color.NONE) {
				  if (player.isPassedPawn(focus) || player.getOpponent().isPassedPawn(focus))
				    pawnScore += focus.occupiedBy() == player.getColor() ? 3 : -3;
				  pawnScore += focus.occupiedBy() == player.getColor() ? 1 : -1;
				}
			}*/
		
		ArrayList<Square> myPawns = player.listOfPawns();
		ArrayList<Square> otherPawns = player.getOpponent().listOfPawns();
		
		for(Square focus : myPawns) {
			pawnScore += player.isPassedPawn(focus) ? 3 : 1;
		}
		
		for(Square focus : otherPawns) {
			pawnScore -= player.getOpponent().isPassedPawn(focus) ? 3 : 1;
		}
		
		return pawnScore;
	}
	
	private int rateAttacks(Player player) {
		
		int attackScore = 0;
		
		ArrayList<Move> playerMoves = player.listOfValidMoves();
		ArrayList<Move> oppMoves    = player.getOpponent().listOfValidMoves();
		  
		for (int i = 0; i < playerMoves.size(); i++){
			if (playerMoves.get(i).isCapture()){
			  attackScore += 2;	
			}
			if (oppMoves.get(i).isCapture()) {
				attackScore -= 2;
			}
		}
		return attackScore;
	}
	
	private int evaluate(Player player) {
		
		int pc = ratePawns(player) - initialPawnCost;
		int ac = rateAttacks(player) - initialAttackCost;

		return pc*10 + ac;
	}
	
	private int Max(Player player, int depth) {
		
		if (depth == 0 || game.isFinished(player, player.getOpponent()))
			return evaluate(player);
		
		int best = -INF;
		ArrayList<Move> moves = player.listOfValidMoves();
		while (moves.size() > 0) {
			Move tempChoice = moves.remove(0);
			board.applyMove(tempChoice);
			int val = Min(player.getOpponent(), depth-1);
			if (val > best) {
				best = val;
				bestOption = tempChoice;
			}
			board.unapplyMove(tempChoice);
		}
		return best;
	}
	
	private int Min(Player player, int depth) {
		
		if (depth == 0 || game.isFinished(player, player.getOpponent()))
			return evaluate(player);
		
		int best = INF;
		ArrayList<Move> moves = player.listOfValidMoves();
		while (moves.size() > 0) {
			Move tempChoice = moves.remove(0);
			board.applyMove(tempChoice);
			int val = Max(player.getOpponent(), depth-1);
			if (val < best) {
				best = val;
				bestOption = tempChoice;
			}
			board.unapplyMove(tempChoice);
		}
		return best;
	}
	
	private void minimax(Player player, int depth) {
		
		int val = Max(player, depth);
	}
}
