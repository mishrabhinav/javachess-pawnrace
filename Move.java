public class Move {

  private Square from;
  private Square to;
  private boolean capture;
  private boolean isEnPassantCapture;

  public Move(Square from, Square to, boolean isCapture, boolean isEnPassantCapture) {

    this.from = from;
    this.to   = to;
    this.capture = isCapture;
    this.isEnPassantCapture = isEnPassantCapture;
  }
  
  public Square getFrom() {
  
    return from;
  }

  public Square getTo() {

    return to;
  }

  public boolean isCapture() {

    return capture;
  }
  
  public boolean isEnPassantCapture() {
  	
  	return isEnPassantCapture;
  }

  public String getSAN() {

  	if (capture) {
  		return (Notations.intToString(from.getY()) + "x" + Notations.toSAN(to));
  	} else
      return Notations.toSAN(to);
  }
}
