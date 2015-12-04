import java.util.Scanner;

public class PawnRace {

	private static Board board;
	private static Game game;
	private static Player white;
	private static Player black;
	private static Scanner input = new Scanner(System.in);

  public static void main(String[] args) {

  	printHigh();
  	
  	initiateObjects();
  	
  	printHigh();
  	
  	board.display();
  	
  	continueGame();
  	
/*  	
  	b.display();
  	
  	Player p1 = new Player(g, b, Color.WHITE, false);
//  	printValidMoves(p1.getAllValidMoves());
  	
  	Player p2 = new Player(g, b, Color.BLACK, false);
//  	printValidMoves(p2.getAllValidMoves());
  	
  	b.applyMove(p1.getAllValidMoves()[1]);
  	//b.applyMove(p1.getAllValidMoves()[12]);
  	//b.applyMove(p1.getAllValidMoves()[1]);
  	System.out.println();
  	b.applyMove(p2.getAllValidMoves()[1]);
  	
  	b.display();
  	
  	System.out.println();
  	System.out.println(g.parseMove("b3").getSAN());
  	
  	System.out.println();
  	g.changePlayer();
  	System.out.println(g.parseMove("c6").getSAN());
  	
/*    Square[] p = p1.getAllPawns();
    for (int i = 0; i < 7; i++) {
    	System.out.println(p[i].toSAN() + " " + p1.isPassedPawn(p[i]));
    }
    
    System.out.println();
    
    Square[] q = p2.getAllPawns();
    for (int i = 0; i < 7; i++) {
    	System.out.println(q[i].toSAN() + " " + p2.isPassedPawn(q[i]));
    }
    
    System.out.println();
    System.out.println(g.parseMove("axb5").getSAN());
    //printValidMoves(p1.getAllValidMoves());
*/   	
  }
  
  public static void printValidMoves(Move[] validMoves) {
  	
  	int i = 0;
  	
  	while (true) {
  		try {
  		System.out.print(validMoves[i].getSAN() + " ");
  		if (i%2 == 1)
  			System.out.println();
  		i++;
  		}
  		catch (Exception e) {
  			break;
  		}
  	}
  	System.out.println();;
  }
  
  private static void printHigh() {
  	
  	System.out.println();
  	
  	for (int i = 0; i < 80; i++)
  		System.out.print("-");
  	
  	System.out.println();
  	System.out.println();
  	
  }
  
  private static void initiateObjects() {
  	
    //Player Introduction Starts.
  	
  	System.out.println("-- Hello White Player! You get to move first. "
  			              + "But will the computer take your chances(y/n)?");
  	
  	boolean isWhiteComputer = input.next() == "y";
  	
  	System.out.println("-- Hello Black Player! You get to move first. "
                      + "But will the computer take your chances(y/n)?");
  	
  	boolean isBlackComputer = input.next() == "y";
  	
  	System.out.println("Black please enter the gaps:\tNote: Enter the two gaps in the form 'a' and then 'h'.");
  	
  	char gapWhite = Character.toUpperCase(input.next().charAt(0));
  	char gapBlack = Character.toUpperCase(input.next().charAt(0));
  	
  	board = new Board(gapWhite, gapBlack);
  	game = new Game(board);
  	
  	white = new Player(game, board, Color.WHITE, isWhiteComputer);
  	printValidMoves(white.getAllValidMoves());
  	black = new Player(game, board, Color.BLACK, isBlackComputer);
  	
  	//Player Introduction Ends.
  	
  }
  
  private static void continueGame() {
  	
  	System.out.println("Current Chance: Player " + Notations.colorToString(game.getCurrentplayer()));
  	
  	String nextMove = input.next();
  	
  	game.applyMove(Notations.stringToMove(board, game.getCurrentplayer(), nextMove));
  	
  	board.display();
  	
  	if(game.isFinished()) {
  		
  	} else
  		continueGame();
  		
  }
}
