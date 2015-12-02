public class Square {

  private int x;
  private int y;
  private Color color;

  public Square(int x, int y) {

    this.x = x;
    this.y = y;
    this.color = Color.NONE;
  }
  
  public int getX() {

    return x;
  }

  public int getY() {

    return y;
  }

  public Color occupiedBy() {

    return color;
  }

  public void setOccupier(Color color) {

    this.color = color;
  }

  public String toString() {
  
    return(x + "" + y);
  }
  
  public String toSAN() {
  	
  	return Notations.toSAN(this);
  }

}
