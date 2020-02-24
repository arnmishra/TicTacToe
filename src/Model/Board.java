package Model;

public class Board {
  private int dimension; // Dimension of Board (size Dimension x Dimension)
  private Team teamX = new Team(TeamType.X); // X team
  private Team teamO = new Team(TeamType.O); // O team
  private Piece[][] positions; // Tracks the board and where each piece is
  private int numDraws = 0; // Number of times there has been a Draw in a game
  private int numMovesAvailable;
  private boolean xIsComputer;
  private boolean oIsComputer;

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
    this.numMovesAvailable = dimension * dimension;
    fillInitialPositions();
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

  public int getNumDraws() {
    return this.numDraws;
  }

  public void incrementNumDraws() {
    this.numDraws++;
  }

  public void setxIsComputer(boolean xIsComputer) {
    this.xIsComputer = xIsComputer;
  }

  public void setoIsComputer(boolean oIsComputer) {
    this.oIsComputer = oIsComputer;
  }

  public boolean isTeamComputer(TeamType teamType) {
    if (teamType == TeamType.X) {
      return this.xIsComputer;
    } else {
      return this.oIsComputer;
    }
  }

  public int getNumMovesAvailable() {
    return this.numMovesAvailable;
  }

  public void resetNumMovesAvailable() {
    this.numMovesAvailable = this.dimension * this.dimension;
  }

  /**
   * Adds a new piece to the board. Also decrements the number of moves still
   * available. This is used to determine draws quickly.
   * @param piece: New piece to add
   */
  public void addPieceToBoard(Piece piece) {
    int xCoordinate = piece.getxCoordinate();
    int yCoordinate = piece.getyCoordinate();
    positions[yCoordinate][xCoordinate] = piece;
    numMovesAvailable--;
  }

  /**
   * Check the entire row (x-axis) of the new piece to see if the team won
   * @param teamType: Which team we are checking for the win
   * @param xc: X-coordinate of new piece
   * @param yc: Y-coordinate of new piece
   * @param checkPositions: The gameboard to check
   * @return Whether the team won using a row
   */
  private boolean checkRowWin(TeamType teamType, int xc, int yc,
                                 Piece[][] checkPositions) {
    int i;
    for (i = 0; i < dimension; i++) {
      if (i == yc) {
        continue;
      }
      boolean checkTeam = checkPositions[i][xc].getTeamType() == teamType;
      if (!checkTeam) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check the entire column (y-axis) of the new piece to see if the team won
   * @param teamType: Which team we are checking for the win
   * @param xc: X-coordinate of new piece
   * @param yc: Y-coordinate of new piece
   * @param checkPositions: The gameboard to check
   * @return Whether the team won using a column
   */
  private boolean checkColumnWin(TeamType teamType, int xc, int yc,
                              Piece[][] checkPositions) {
    int i;
    for (i = 0; i < dimension; i++) {
      if (i == xc) {
        continue;
      }
      boolean checkTeam = checkPositions[yc][i].getTeamType() == teamType;
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
   * @param checkPositions: The gameboard to check
   * @return Whether the team won using a diagonal
   */
  private boolean checkDiagonalWin(TeamType teamType, int xc, int yc,
                                   Piece[][] checkPositions) {
    if (xc == yc) {
      int i;
      for (i = 0; i < dimension; i++) {
        if (i == xc) {
          continue;
        }
        boolean checkTeam = checkPositions[i][i].getTeamType() == teamType;
        if (!checkTeam) {
          return false;
        }
      }
      return true;
    } else if (xc + yc == dimension - 1) {
      int x;
      for (x = 0; x < dimension; x++) {
        if (x == xc) {
          continue;
        }
        int checkYc = dimension - 1 - x;
        boolean checkTeam =
          checkPositions[checkYc][x].getTeamType() == teamType;
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
   * @param checkPositions: Current positions of board
   * @return Integer: -1 for not end game, 0 for draw, 1 for end game
   */
  public int isEndGame(
    TeamType teamType,
    Piece piece,
    Piece[][] checkPositions,
    int checkNumMovesAvailable
  ) {
    int xc = piece.getxCoordinate();
    int yc = piece.getyCoordinate();
    if (!checkColumnWin(teamType, xc, yc, checkPositions)) {
      if (!checkRowWin(teamType, xc, yc, checkPositions)) {
        if (!checkDiagonalWin(teamType, xc, yc, checkPositions)) {
          if (checkNumMovesAvailable == 1) {
            // If this piece isn't resulting in a win and there's only one
            // spot on the board, then it results in a draw.
            return 0;
          }
          return -1;
        }
      }
    }
    return 1;
  }

  /**
   * Fill initial positions on the board with blank pieces that aren't on any
   * team (TeamType = N/None)
   */
  public void fillInitialPositions() {
    int x, y;
    for (x = 0; x < this.dimension; x++) {
      for (y = 0; y < this.dimension; y++) {
        this.positions[y][x] = new Piece(TeamType.N, x, y);
      }
    }
  }

  /**
   * Check if a move is valid by verifying that the coordinates are within
   * the gameboard
   * @param piece: Piece that is being checked
   * @return whether move is valid
   */
  public boolean isValidMove(Piece piece) {
    int xCoordinate = piece.getxCoordinate();
    int yCoordinate = piece.getyCoordinate();
    if(xCoordinate >= dimension || xCoordinate < 0)
    {
      return false; // Ensure that this move doesn't put the piece off the board.
    }
    else if(yCoordinate >= dimension || yCoordinate < 0)
    {
      return false; // Ensure that this move doesn't put the piece off the board.
    }
    // Return valid if the position is currently empty
    return (positions[yCoordinate][xCoordinate].getTeamType() == TeamType.N);
  }

  /**
   * Recursive function to identify the best move to make for a computer.
   * @param checkPositions: Current board that is being checked
   * @param newPiece: New piece being considered
   * @param checkNumMovesAvailable: Number of moves still available on board
   * @param checkTeamType: Which team we are finding the best move for
   * @return -1 for loss, 0 for draw, 1 for win for the team "checkTeamType"
   */
  private int getMiniMaxScore(
    Piece[][] checkPositions,
    Piece newPiece,
    int checkNumMovesAvailable,
    TeamType checkTeamType
  ) {
    TeamType teamType = newPiece.getTeamType();
    // Check if we are currently on a recursive level making a move for the
    // computer's team.
    boolean sameTeam = (newPiece.getTeamType() == checkTeamType);
    int endGameScore =
      isEndGame(teamType, newPiece, checkPositions, checkNumMovesAvailable);
    if (endGameScore == 1) {
      // For wins return 1, for losses (win by opposing team) return -1
      if (sameTeam) {
        return 1;
      } else {
        return -1;
      }
    } else if (endGameScore == 0) {
      return 0;
    }
    checkPositions[newPiece.getyCoordinate()][newPiece.getxCoordinate()] =
      newPiece;
    int bestScore;
    if (sameTeam) {
      // We are finding the minimum (worst case) of all moves possible after
      // the computer's current move (newPiece). Therefore we initialize
      // bestScore to max int.
      bestScore = Integer.MAX_VALUE;
    } else {
      // We are finding the maximum (best case) of all moves possible after
      // the opponent's current move (newPiece). Therefore we initialize
      // bestScore to min int.
      bestScore = Integer.MIN_VALUE;
    }
    int x, y;
    for (x = 0; x < dimension; x++) {
      for (y = 0; y < dimension; y++) {
        if (checkPositions[y][x].getTeamType() != TeamType.N) {
          continue;
        }
        Piece piece = new Piece(teamType.switchTeam(), x, y);
        Piece[][] newPositions = deepCopyPositions(checkPositions);
        int score = getMiniMaxScore(
          newPositions,
          piece,
          checkNumMovesAvailable - 1,
          checkTeamType);
        if (!sameTeam && score > bestScore) {
          bestScore = score;
        } else if (sameTeam && score < bestScore) {
          bestScore = score;
        }
      }
    }
    return bestScore;
  }

  /**
   * Deep copy the 2-D Array of Piece objects, oldPositions
   * @param oldPositions: Array being deep copied
   * @return new deep copied array
   */
  public Piece[][] deepCopyPositions(Piece[][] oldPositions) {
    Piece[][] checkPositions = new Piece[dimension][dimension];
    int x, y;
    for (x = 0; x < dimension; x++) {
      for (y = 0; y < dimension; y++) {
        Piece currPiece = oldPositions[y][x];
        Piece newPiece =
          new Piece(
            currPiece.getTeamType(),
            currPiece.getxCoordinate(),
            currPiece.getxCoordinate());
        checkPositions[y][x] = newPiece;
      }
    }
    return checkPositions;
  }

  /**
   * Identify the best coordinates for the next move by the Computer
   * @param teamType: Computer's team
   * @return coordinate object of x/y coordinates for next best move.
   */
  public Coordinate getBestCoordinates(TeamType teamType) {
    Coordinate bestCoordinate = new Coordinate(-1, -1);
    int bestScore = Integer.MIN_VALUE;
    int x, y;
    for (x = 0; x < dimension; x++) {
      for (y = 0; y < dimension; y++) {
        if (positions[y][x].getTeamType() != TeamType.N) {
          continue;
        }
        Piece piece = new Piece(teamType, x, y);
        Piece[][] checkPositions = deepCopyPositions(positions);
        int score =
          getMiniMaxScore(
            checkPositions,
            piece,
            numMovesAvailable,
            teamType);
        if (score > bestScore) {
          // Greedily find the first set of coordinates that give the best score
          bestCoordinate.setxCoordinate(x);
          bestCoordinate.setyCoordinate(y);
          bestScore = score;
        }
      }
    }
    return bestCoordinate;
  }
}
