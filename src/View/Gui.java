package View;

import Controller.RunGame;
import Model.Board;
import Model.Piece;
import Model.Team;
import Model.TeamType;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class Gui implements ActionListener  {
  public Board gameBoard; // Board object for this Tic-Tac-Toe game
  private static GameSquare[][] tiles; // 2D Array of GameSquares (JButtons)
  private static int buttonDimension; // Dimensions of each tile
  private JFrame window; // Full user-visible window

  /**
   * Global constructor for GUI. Sets up initial board with proper dimensions
   * and clickable buttons as tiles. Also sets up a menu bar to restart,
   * forfeit, and create a new game.
   */
  public Gui() {
    int dimension = askSizeBoard();
    gameBoard = new Board(dimension);
    // TODO: Determine max dimensions for computer so that minimax isn't too
    // long and document on README
    tiles = new GameSquare[dimension][dimension];
    initialGuiSetup();

    window = new JFrame("Tic-Tac-Toe Game");
    window.setSize(650, 600);
    window.setLayout(new BoxLayout(window, BoxLayout.X_AXIS));
    JMenuBar menubar = setUpMenu();
    window.setJMenuBar(menubar);
    JPanel fullWindow = initializeWindow(dimension);
    window.setContentPane(fullWindow);
    window.setVisible(true);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Sets up the team names on the main window based on whether they are
   * human or computer players.
   */
  private void initialGuiSetup() {
    boolean isTeamXComputer = askPlayerIsComputer(TeamType.X);
    gameBoard.setxIsComputer(isTeamXComputer);
    boolean isTeamOComputer = askPlayerIsComputer(TeamType.O);
    gameBoard.setoIsComputer(isTeamOComputer);

    if (!isTeamXComputer) {
      setTeamNames(TeamType.X);
    } else {
      Team team = gameBoard.getTeam(TeamType.X);
      team.setTeamName("Computer X");
    }

    if (!isTeamOComputer) {
      setTeamNames(TeamType.O);
    } else {
      Team team = gameBoard.getTeam(TeamType.O);
      team.setTeamName("Computer O");
    }
  }

  /**
   * Get the dimensions of the board from the user.
   * @return board dimensions
   */
  private int askSizeBoard() {
    // TODO: Check for valid input. For now, we assume an integer > 0
    // TODO: Get Computers to work for boards greater than 3x3
    String userInput =
      JOptionPane.showInputDialog(
        null,
        "What size board do you want to use? (Expected: 3)");
    return Integer.parseInt(userInput);
  }

  /**
   * Ask the user which team to make a computer player
   * @param teamType: Team we are asking about
   * @return whether team is a computer
   */
  private boolean askPlayerIsComputer(TeamType teamType) {
    int userInput =
      JOptionPane.showConfirmDialog(
        null,
        "Is Player " + teamType + " a Computer Player?");
    // 0 means yes
    return (userInput == 0);
  }

  /**
   * Dialog boxes to get the names of the two teams from the user input.
   * @param teamType Whether this is X or O team
   */
  private void setTeamNames(TeamType teamType)
  {
    String teamName = JOptionPane.showInputDialog("Team " + teamType + " Name");
    Team team = gameBoard.getTeam(teamType);
    team.setTeamName(teamName);
  }

  /**
   * Function to initialize the board by creating the correct JPanels
   * @param dimension: Dimensions of square Tic-Tac-Toe board
   */
  private JPanel initializeBoard(int dimension) {
    JPanel boardPanels = new JPanel();
    LayoutManager layout = new GridLayout(dimension, dimension);
    boardPanels.setLayout(layout);
    int boardDimension = 500;
    buttonDimension = boardDimension/dimension;
    int x, y;
    for (y = 0; y < dimension; y++) {
      for (x = 0; x < dimension; x++) {
        GameSquare button = new GameSquare(y, x);
        button.setLayout(new GridBagLayout());
        button.setBackground(Color.WHITE);
        button.setUI(new BasicButtonUI());
        button.addActionListener(this);
        button.setPreferredSize(
          new Dimension (buttonDimension, buttonDimension));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tiles[y][x] = button;
        boardPanels.add(button);
      }
    }
    return boardPanels;
  }

  /**
   * Initializes the window each time anything is reset. Puts up the proper
   * score, game board, and team names.
   * @param dimension: Dimensions of Board
   * @return JPanel corresponding to window
   */
  private JPanel initializeWindow(int dimension) {
    JPanel fullWindow = new JPanel();
    JPanel textPanel = new JPanel();
    Team teamX = gameBoard.getTeam(TeamType.X);
    Team teamO = gameBoard.getTeam(TeamType.O);
    String teamXName = teamX.getTeamName();
    int teamXScore = teamX.getScore();
    String teamOName = teamO.getTeamName();
    int teamOScore = teamO.getScore();
    JLabel label =
      new JLabel(
        "Score: " + teamXScore +  " (" + teamXName + ") - " + teamOScore +
          " (" + teamOName + ")" + " - Draws " + gameBoard.getNumDraws());
    textPanel.add(label);
    JPanel boardPanels = initializeBoard(dimension);
    fullWindow.add(boardPanels);
    fullWindow.add(textPanel);
    return fullWindow;
  }

  /**
   * Sets up the File menu bar on the window
   */
  private JMenuBar setUpMenu() {
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("File");
    menubar.add(file);
    addMenuItem(file, "Restart");
    addMenuItem(file, "Forfeit");
    addMenuItem(file, "New Game");
    return menubar;
  }

  /**
   * Adds each specific menu item with an action listener
   * @param file: JMenu File Dropdown
   * @param menuItem: Menu Option String
   */
  private void addMenuItem(JMenu file, String menuItem) {
    JMenuItem jmenuItem = new JMenuItem(menuItem);
    jmenuItem.addActionListener(this);
    file.add(jmenuItem);
  }

  /**
   * Function to print error messages for invalid clicks.
   * @param message: Message to show the User
   */
  public void errorMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  /**
   * Notify the user that the game is over and start a new game.
   * @param teamType: Which team won
   */
  public void isGameOver(TeamType teamType) {
    Team winningTeam = gameBoard.getTeam(teamType);
    String teamName = winningTeam.getTeamName();
    JOptionPane.showMessageDialog(null, teamName + " wins!");
    winningTeam.incrementTeamScore();
    restartGui();
  }

  /**
   * Notify the user that the game ended in a draw and start a new game.
   */
  public void isDraw() {
    JOptionPane.showMessageDialog(null, "Game resulted in a draw");
    restartGui();
  }

  /**
   * Make a move on the front-end given a piece object. This is called by the
   * controller after validity of the move is confirmed.
   * @param piece: Piece that is being added in this move
   */
  public static void makeMove(Piece piece) {
    GameSquare button = tiles[piece.getyCoordinate()][piece.getxCoordinate()];
    Icon icon = null;
    try {
      String image;
      if (piece.getTeamType() == TeamType.X) {
        image = "x.png";
      } else {
        image = "o.png";
      }
      String currentDirectory = System.getProperty("user.dir");
      BufferedImage pieceImage =
        ImageIO.read(new File(currentDirectory + "/src/View/Pieces/" + image));
      Image scaledPiece =
        pieceImage.getScaledInstance(
          buttonDimension, buttonDimension, Image.SCALE_SMOOTH);
      icon = new ImageIcon(scaledPiece);
    } catch (IOException e) {
      e.printStackTrace();
    }
    button.setIcon(icon);
  }

  /**
   * Restart the GUI by clearing the tiles.
   */
  private void restartGui() {
    gameBoard.fillInitialPositions();
    int dimension = gameBoard.getDimension();
    tiles = new GameSquare[dimension][dimension];
    JPanel fullWindow = initializeWindow(dimension);
    window.setContentPane(fullWindow);
    window.setVisible(true);
  }

  /**
   * Function to process a forfeit on the gui by a team including
   * incrementing score and restarting the board.
   */
  private void forfeitGui() {
    gameBoard.fillInitialPositions();
    int dimension = gameBoard.getDimension();
    RunGame.forfeitGame();
    JPanel fullWindow = initializeWindow(dimension);
    RunGame.resetTeamTurn();
    window.setContentPane(fullWindow);
    window.setVisible(true);
  }

  /**
   * Function to create a new game, including new dimensions, players, and
   * resetting all scores.
   */
  private void newGameGui() {
    int dimension = askSizeBoard();
    gameBoard = new Board(dimension);
    tiles = new GameSquare[dimension][dimension];
    initialGuiSetup();
    JPanel fullWindow = initializeWindow(dimension);
    RunGame.resetTeamTurn();
    window.setContentPane(fullWindow);
    window.setVisible(true);
  }

  /**
   * Captures the actions performed by the User.
   * @param e: The ActionEvent object that holds the User's action information
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    switch (action) {
      case "Restart":
        restartGui();
        RunGame.resetTeamTurn();
        break;
      case "Forfeit":
        forfeitGui();
        break;
      case "New Game":
        newGameGui();
        break;
      default:
        GameSquare sourceButton = (GameSquare)e.getSource();
        int xCoordinate = sourceButton.getXValue();
        int yCoordinate = sourceButton.getYValue();
        RunGame.makeMove(xCoordinate, yCoordinate);
    }
  }
}
