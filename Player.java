import java.util.ArrayList;

public class Player {

	private Game game;
	private Board board;
	private Color color;
	private boolean isComputerPlayer;
	private Player opponent;
	private MoveGenAI moveMaster;
	private MiniMax tester;
	private Square[] currentPawns = new Square[7];
	// private Square[] initialPawns = new Square[7];
	private Move[] validMoves = new Move[30];
	private int moveCounter;

	public Player(Game game, Board board, Color color, boolean isComputerPlayer) {

		this.game = game;
		this.board = board;
		this.color = color;
		this.isComputerPlayer = isComputerPlayer;
		// initialPawns = this.getAllPawns();
		currentPawns = this.getAllPawns();
	}

	public void setOpponent(Player opponent) {

		this.opponent = opponent;
		if (isComputerPlayer)
			moveMaster = new MoveGenAI(game, board, this, opponent);
		  tester = new MiniMax(game, board, this, opponent);
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
					// System.out.println(Notations.toSAN(temp));
					tempPawns[c] = temp;
					c++;
				}
			}

		if (c < 7)
			for (int i = c; i < 7; i++)
				tempPawns[i] = null;

		currentPawns = tempPawns;
		return tempPawns;
	}

	public Move[] getAllValidMoves() {

		moveCounter = 0;
		Move[] tempMoves = new Move[30];

		Square[] newPawns = this.getAllPawns();
		currentPawns = newPawns;

		for (int i = 0; i < 7; i++) {
			if (currentPawns[i] != null) {
				Square oneF = MoveSet.oneForward(board, currentPawns[i], color);
				Square twoF = MoveSet.twoForward(board, currentPawns[i], color);
				Square enPassant = game.getLastMove() != null ? MoveSet.enPassantKill(board, currentPawns[i], game.getLastMove(), color): null;
				Square[] diag = MoveSet.diagonalKill(board, currentPawns[i], color);
				;

				if (oneF != null) {
					tempMoves[moveCounter] = new Move(currentPawns[i], oneF, false, false);
					moveCounter++;
				}

				if (twoF != null) {
					tempMoves[moveCounter] = new Move(currentPawns[i], twoF, false, false);
					moveCounter++;
				}
				
				if (enPassant != null) {
					tempMoves[moveCounter] = new Move(currentPawns[i], enPassant, true, true);
					moveCounter++;
				}
				
				for (int j = 0; j < 2; j++) {
					if (diag[j] != null) {
						tempMoves[moveCounter] = new Move(currentPawns[i], diag[j], true, false);
						moveCounter++;
					}
				}
			}
		}

		// for (int i = moveCounter; i < tempMoves.length; i++)
		tempMoves[moveCounter] = null;

		return tempMoves;
	}

	public boolean isValidMove(Move move) {

		validMoves = this.getAllValidMoves();
		int i = 0;

		while (validMoves[i] != null) {
			if (validMoves[i].getFrom().getX() == move.getFrom().getX()
			    && validMoves[i].getFrom().getY() == move.getFrom().getY()
			    && validMoves[i].getTo().getX() == move.getTo().getX() && validMoves[i].getTo().getY() == move.getTo().getY())
				return true;
			i++;
		}
		return false;
	}
	
	public int numValidMoves() {
		return moveCounter;
	}

	public boolean isPassedPawn(Square square) {

		return PassedValidator.isPassedPawn(board, square, color);
	}

	public Move makeMove() {

		/* Getting opponent pawns.
		color = color == Color.WHITE ? Color.BLACK : Color.WHITE;
		Square[] opponentPawns = this.getAllPawns();
		color = color == Color.WHITE ? Color.BLACK : Color.WHITE;
		End */
/*		this.getAllValidMoves();
		try {
		  Move moveSet = moveMaster.moveGen(this.getAllValidMoves(), moveCounter);
		  game.applyMove(moveSet);
		  System.out.println("" + tester.evaluateBoard(this, opponent));
		}
		catch (Exception e) {
			//moveCounter = 0;
		}
*/
		tester.estimateBase(color);
		return (tester.getBestMove(this.color, 3));
		}
	
	public ArrayList<Move> listOfValidMoves() {
		
		ArrayList<Move> result = new ArrayList<>();
		Move[] allValMoves = this.getAllValidMoves();
		int len = allValMoves.length;
		
		for (int i = 0; i < len; i++)
			if (allValMoves[i] != null)
				result.add(allValMoves[i]);
		return result;
	}
	
	public ArrayList<Square> listOfPawns() {
		
		ArrayList<Square> result = new ArrayList<>();
		Square[] allPawns = this.getAllPawns();
		int len = allPawns.length;
		
		for(int i = 0; i < len; i++)
			if (allPawns[i] != null)
				result.add(allPawns[i]);
		
		return result;
	}
}
