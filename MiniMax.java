import java.util.ArrayList;

public class MiniMax extends MoveGenAI2{

	private double INF = 10000;
	private Move choice;
	public MiniMax(Game game, Board board, Player self, Player opponent) {
		super(game, board, self, opponent);
		// TODO Auto-generated constructor stub
	}

	public void minimax(int depth, boolean white) {
		double val = 0.0;
		val = white ? Max(depth) : Min(depth);
	}
	
	public Move getBestMove(Color player, int depth) {
		boolean white = player == Color.WHITE;
		minimax(depth, white);
		return choice;
	}
	
	private double Max(int depth) {
		
		if (depth == 0)
			return evaluate();
		
		double best = -INF;
		ArrayList<Move> moves = self.listOfValidMoves();
		while (moves.size() > 0) {
			Move tempChoice = moves.remove(0);
			board.applyMove(tempChoice);
			double val = -Min(depth-1);
			if (val > best) {
				best = val;
				choice = tempChoice;
			}
			board.unapplyMove(tempChoice);
		}
		return best;
	}
	
	private double Min(int depth) {
		
		if (depth == 0)
			return evaluate();
		
		double best = -INF;
		ArrayList<Move> moves = opponent.listOfValidMoves();
		while (moves.size() > 0) {
			Move tempChoice = moves.remove(0);
			board.applyMove(tempChoice);
			double val = -Max(depth-1);
			if (val > best) {
				best = val;
				choice = tempChoice;
			}
			board.unapplyMove(tempChoice);
		}
		return best;
	}
}
