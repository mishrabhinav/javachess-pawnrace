import java.util.Scanner;

public class PawnRace {

	private static Board board;
	private static Game game;
	private static Player white;
	private static Player black;
	private static Player current;
	private static Scanner input = new Scanner(System.in);

  public static void main(String[] args) {

  	printHigh();
  	
  	initiateObjects();
  	
  	printHigh();
  	
  	board.display();
  	
  	
  	while(!game.isFinished(white, black)) {
  		askForMove();
    	board.display();	
  	}
  	
  	//board.display();
  	
  	game.changePlayer();
  	
  	if (game.isFinished(white, black))
  		System.out.println("Hoorayy! We have a winner " + Notations.colorToString(game.getCurrentplayer()));
  	else
  		System.out.println("Game finished. Stalemate!");
  	
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
  	
  	boolean isWhiteComputer = input.next().equals("y");
  	
  	System.out.println("-- Hello Black Player! You get to move first. "
                      + "But will the computer take your chances(y/n)?");
  	
  	boolean isBlackComputer = input.next().equals("y");
  	
  	System.out.println("Black please enter the gaps:\tNote: Enter the two gaps in the form 'a' and then 'h'.");
  	
  	char gapWhite = Character.toUpperCase(input.next().charAt(0));
  	char gapBlack = Character.toUpperCase(input.next().charAt(0));
  	
  	board = new Board(gapWhite, gapBlack);
  	game = new Game(board);
  	
  	white = new Player(game, board, Color.WHITE, isWhiteComputer);
  	//printValidMoves(white.getAllValidMoves());
  	black = new Player(game, board, Color.BLACK, isBlackComputer);

  	white.setOpponent(black);
  	black.setOpponent(black);
  	
  	current = white;
  	//Player Introduction Ends.
  	
  }
  
  private static void askForMove() {
    
  	boolean b = true;
  	String x = "";
  	
  	if (current.isComputerPlayer()) {
  		  Move mm = current.makeMove();
  			  game.applyMove(mm);
  	} else {
  	
  	while (b) {

    	System.out.println(x+ "\nCurrent Player: " + Notations.colorToString(game.getCurrentplayer()) + "\nPlease enter your move: ");
    	
    	printValidMoves(current.getAllValidMoves());
  	
    	String nextMove = input.next();
  	
    	Move tempMove = Notations.stringToMove(board, game.getCurrentplayer(), nextMove);
  	
    	try {
    		
  	    if(current.isValidMove(tempMove)) {
  		    game.applyMove(tempMove);
    		  b = false;
    	  }  
  	  } 
  	  catch (Exception e) {
  	  	x = "\nSorry! Move not valid. Enter again.\n";
    	}
    }
  }
  	current = current == white ? black : white;
  }
}
