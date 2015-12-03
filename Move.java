public class Move {

  private Square from;
  private Square to;
  private boolean capture;

  public Move(Square from, Square to, boolean isCapture) {

    this.from = from;
    this.to   = to;
    this.capture = isCapture;
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

  public String getSAN() {

  	if (capture) {
  		return (Notations.intToString(from.getY()) + "x" + Notations.toSAN(to));
  	} else
      return Notations.toSAN(to);
  }
}
