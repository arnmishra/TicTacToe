package Model;

public class Board {
  private int dimension; // Dimension of Board (size Dimension x Dimension)
  private Team teamX = new Team(TeamType.X); // X team
  private Team teamO = new Team(TeamType.O); // O team
  private Piece[][] positions; // Tracks the board and where each piece is

  /**
   * Constructor to make new Board. Originally I considered made the width and
   * height of the board configurable (allowing rectangles); however, the end
   * conditions are unclear with how to win via diagonals. Therefore, only
   * square boards are allowed.
   * @param dimension: Dimensions of the board (size Dimension x Dimension)
   */
  public Board(int dimension) {
    // TODO (not required): Allow 3rd dimension (or Nth dimension?)
    this.dimension = dimension;
    this.positions = new Piece[dimension][dimension];
  }

  public int getDimension() {
    return this.dimension;
  }

  public Piece[][] getPositions() {
    return this.positions;
  }

  public Team getTeam(TeamType teamType) {
    if (teamType == TeamType.O) {
      return this.teamO;
    } else {
      return this.teamX;
    }
  }

  /**
   * Adds a new piece to the board. First checks that no piece already exists
   * at those coordinates.
   * @param piece: New piece to add
   */
  public void addPieceToBoard(Piece piece) {
    int xCoordinate = piece.getxCoordinate();
    int yCoordinate = piece.getyCoordinate();
    // TODO: Improve this assert check to be more detailed
    assert(positions[yCoordinate][xCoordinate] == null);
    positions[yCoordinate][xCoordinate] = piece;
  }

  /**
   * Check the entire column (x-axis) of the new piece to see if the team won
   * @param teamType: Which team we are checking for the win
   * @param xc: X-coordinate of new piece
   * @param yc: Y-coordinate of new piece
   * @return Whether the team won using a column
   */
  private boolean checkColumnWin(TeamType teamType, int xc, int yc) {
    int i;
    for (i = 0; i < dimension; i++) {
      if (i == yc) {
        continue;
      }
      boolean checkTeam = this.positions[i][xc].getTeamType() == teamType;
      if (!checkTeam) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check the entire row (y-axis) of the new piece to see if the team won
   * @param teamType: Which team we are checking for the win
   * @param xc: X-coordinate of new piece
   * @param yc: Y-coordinate of new piece
   * @return Whether the team won using a row
   */
  private boolean checkRowWin(TeamType teamType, int xc, int yc) {
    int i;
    for (i = 0; i < dimension; i++) {
      if (i == xc) {
        continue;
      }
      boolean checkTeam = this.positions[yc][i].getTeamType() == teamType;
      if (!checkTeam) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check the diagonals to see if the team won. Since the board is a square,
   * there are 3 cases:
   * 1. The x-coordinate and y-coordinate are the same. For a 3x3 board the
   * winning diagonal would be [0][0], [1][1], [2][2]
   * 2. The x-coordinate + y-coordinate == dimension - 1. For a 3x3 board the
   * winning diagonal would be [0][2], [1][1], [2][0]
   * 3. All other squares. For 3x3 those would be [0][1], [1][0], [2][1],
   * [1][2] - you cannot win diagonally from these positions
   *
   * First determine which of the three cases the new piece is in, then
   * determine if they won using the relevant diagonal.
   *
   * @param teamType: Which team we are checking for the win
   * @param xc: X-coordinate of new piece
   * @param yc: Y-coordinate of new piece
   * @return Whether the team won using a column
   */
  private boolean checkDiagonalWin(TeamType teamType, int xc, int yc) {
    if (xc == yc) {
      int i;
      for (i = 0; i < dimension; i++) {
        if (i == xc) {
          continue;
        }
        boolean checkTeam = this.positions[i][i].getTeamType() == teamType;
        if (!checkTeam) {
          return false;
        }
      }
      return true;
    } else if (xc + yc == dimension - 1) {
      int i;
      for (i = 0; i < dimension; i++) {
        if (i == xc) {
          continue;
        }
        int checkYc = dimension - 1 - i;
        boolean checkTeam =
          this.positions[checkYc][i].getTeamType() == teamType;
        if (!checkTeam) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * To determine if a team has won the game, we have to check if they have
   * covered all the spots in the same row or column or they have a
   * diagonal of length dimension.
   * @param teamType: Which team to check
   * @param piece: Piece being added in current move
   * @return Boolean indicating whether the team has won
   */
  public boolean isEndGame(TeamType teamType, Piece piece) {
    int xc = piece.getxCoordinate();
    int yc = piece.getyCoordinate();
    if(!checkColumnWin(teamType, xc, yc)) {
      if(!checkRowWin(teamType, xc, yc)) {
        return checkDiagonalWin(teamType, xc, yc);
      }
    }
    return true;
  }

  /**
   * Print Board to debug/visualize prior to creation of GUI
   */
  public void printBoard() {
    int i, j;
    for (i = 0; i < dimension; i++) {
      for (j = 0; j < dimension; j++) {
        Piece piece = this.positions[i][j];
        if (piece == null) {
          System.out.print("- ");
        } else {
          System.out.print(piece.getTeamType() + " ");
        }
      }
      System.out.print("\n");
    }
  }
}
