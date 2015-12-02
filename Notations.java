public class Notations {

	public static String toSAN(Square s) {
		
		char y = 'a';
		
		switch(s.getY()) {
		  case 0: y = 'a'; break;
		  case 1: y = 'b'; break;
		  case 2: y = 'c'; break;
		  case 3: y = 'd'; break;
		  case 4: y = 'e'; break;
		  case 5: y = 'f'; break;
		  case 6: y = 'g'; break;
		  case 7: y = 'h'; break;
		}
		
		return (y + "" + (s.getX() + 1));
	}
	
	public static int charToInt(char c) {
		
		char cUpper = Character.toUpperCase(c);
		
		switch(cUpper) {
	  case 'A': return 0;
	  case 'B': return 1; 
	  case 'C': return 2; 
	  case 'D': return 3; 
	  case 'E': return 4; 
	  case 'F': return 5; 
	  case 'G': return 6; 
	  case 'H': return 7; 
	  default: return -1;
	}
	}
}