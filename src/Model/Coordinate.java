package Model;

public class Coordinate {
  private int xCoordinate;
  private int yCoordinate;

  public Coordinate(int xCoordinate, int yCoordinate) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
  }
  public int getxCoordinate() {
    return xCoordinate;
  }

  public int getyCoordinate() {
    return yCoordinate;
  }

  public void setxCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public void setyCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate;
  }
}
