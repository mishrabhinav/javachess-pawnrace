public class PawnRace {

	private static Board b = new Board('H', 'A');
	private static Game g = new Game(b);

  public static void main(String[] args) {

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
}
