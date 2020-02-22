package View;

import javax.swing.*;

public class GameSquare extends JButton {
  private int xValue;
  private int yValue;

  public GameSquare(int yValue, int xValue)
  {
    this.xValue = xValue;
    this.yValue = yValue;
  }

  public int getXValue() {
    return xValue;
  }

  public int getYValue() {
    return yValue;
  }
}
