package View;

import Controller.RunGame;
import Model.Board;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements ActionListener  {
  private static Board gameBoard;

  public Gui(Board board) {
    gameBoard = board;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    GameSquare sourceButton = (GameSquare)e.getSource();
    int xCoordinate = sourceButton.getXValue();
    int yCoordinate = sourceButton.getYValue();
    RunGame.makeMove(xCoordinate, yCoordinate, gameBoard);
  }
}
