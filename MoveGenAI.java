import java.util.Hashtable;
import java.util.Random;

public class MoveGenAI {

	private Game game;
	private Board board;
	private Player self;
	private Color selfColor;
	private Player opponent;
	private Hashtable<Integer, Move> weightedMoves;
	
	public MoveGenAI(Game game, Board board, Player self, Player opponent) {
		
		this.game = game;
		this.board = board;
		this.self = self;
		this.opponent = opponent;
		this.selfColor = self.getColor();
	}
	
	private Move checkForWinningMove(Move[] validMoves, int moveCounter) {
		
		weightedMoves = new Hashtable<>(moveCounter);
		
		int finishX = selfColor == Color.WHITE ? 7 : 0;
		 
		for (int i = 0; i < moveCounter ; i++) {
			if (validMoves[i].getTo().getX() == finishX)
				weightedMoves.put(10, validMoves[i]);
			else
				weightedMoves.put(5, validMoves[i]);
		}
		
		Move returnMove = null;
		
		returnMove = weightedMoves.get(10);
			
		return returnMove;
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
	
	public Move moveGen(Move[] validMoves, int moveCounter) {
		
		checkForWinningMove(validMoves, moveCounter);
		
		if (weightedMoves.containsKey(10)){
			return weightedMoves.get(10);
		}
			
		Random randomGen = new Random();
		//Move[] moveSet = this.getAllValidMoves();
		int m = randomGen.nextInt(moveCounter);
		while (game.getLastMove() == null && validMoves[m].isEnPassantCapture())
			m = randomGen.nextInt(moveCounter);
		
		return validMoves[m];
	}
}
