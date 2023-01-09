package game;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.*;

import java.awt.*;
import java.util.ArrayList;

// represents a BridgIt game world
public class BridgItGame extends World {

    public ArrayList<ArrayList<Cell>> cells;
    public int n;
    public boolean player1Turn;
    public int movesP1;
    public int movesP2;
    public ArrayList<Cell> checkedCells;

    public BridgItGame(int n) {
        if ((n < 3) || (n % 2 == 0)) {
            throw new IllegalArgumentException("Invalid game grid size.");
        }
        this.n = n;
        this.cells = buildBoard();
        this.player1Turn = true; // Player 1 (pink) starts
        this.movesP1 = 0;
        this.movesP2 = 0;
        this.checkedCells = new ArrayList<Cell>();
    }

    // builds the game board
    public ArrayList<ArrayList<Cell>> buildBoard() {
        ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < n; i++) {
            board.add(new ArrayList<Cell>());
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Cell cell = new Cell(new Posn(i, j));
                board.get(i).add(cell);
            }
        }
        return board;
    }

    // draws the game
    public WorldScene makeScene() {
        int worldSize = WorldConstants.CELLSIZE * n;
        WorldScene w1 = new WorldScene(worldSize, worldSize);
        WorldImage image = new RectangleImage(worldSize * 2, worldSize * 2, OutlineMode.OUTLINE,
                Color.white);

        // EXTRA CREDIT (enhance graphics, keep score, allow player to choose board size):
        WorldImage playerTurn = this.drawPlayerTurn();
        WorldImage playerMoves = new TextImage("# Moves "
                + "(P1: " + Integer.toString(this.movesP1) + " | "
                + "P2: " + Integer.toString(this.movesP2) + ")", Color.black);
        WorldImage message = new OverlayOffsetImage(
                new TextImage("Current Game Size: " + Integer.toString(n), Color.black),
                65, 35,
                new AboveAlignImage(AlignModeX.LEFT,
                        new TextImage("Change board size with odd number keys.", Color.black),
                        new TextImage("Use 'r' key to reset game board.", Color.black)));

        // creates game board image
        for (int i = 0; i < n; i++) {
            ArrayList<Cell> cellRow = this.cells.get(i);
            for (int j = 0; j < n; j++) {
                image = cellRow.get(j).drawAt(i, j, image);
            }
        }

        // determines if a player has beat the game (if so, end game displayed)
        if (player1Win()) {
            return this.lastScene("Player 1 (Pink) Wins!");
        }
        if (player2Win()) {
            return this.lastScene("Player 2 (Purple) Wins!");
        }

        int placePt = worldSize - (WorldConstants.CELLSIZE / 2);
        w1.placeImageXY(image, placePt, placePt);
        w1.placeImageXY(playerTurn, worldSize / 2, worldSize + 25);
        w1.placeImageXY(playerMoves, worldSize / 2, worldSize + 55);
        w1.placeImageXY(message, worldSize + 150, worldSize / 2);
        return w1;
    }

    // draw player turn world image
    public WorldImage drawPlayerTurn() {
        WorldImage playerTurn;
        if (player1Turn) {
            playerTurn = new TextImage("Player 1 (Pink) Turn", Color.black);
        } else {
            playerTurn = new TextImage("Player 2 (Purple) Turn", Color.black);
        }
        return playerTurn;
    }

    // determines if player 1 has beat the game
    public boolean player1Win() {
        for (int i = 1; i < n; i += 2) {
            for (int j = 1; j < n; j += 2) {
                Cell from = this.cells.get(i).get(0);
                Cell to = this.cells.get(j).get(n - 1);
                this.checkedCells = new ArrayList<Cell>();
                if (this.findPath(from, to, Color.pink)) {
                    return true;
                }
            }
        }
        return false;
    }

    // determines if player 2 has beat the game
    public boolean player2Win() {
        for (int i = 1; i < n; i += 2) {
            for (int j = 1; j < n; j += 2) {
                Cell from = this.cells.get(0).get(i);
                Cell to = this.cells.get(n - 1).get(j);
                this.checkedCells = new ArrayList<Cell>();
                if (this.findPath(from, to, Color.magenta)) {
                    return true;
                }
            }
        }
        return false;
    }

    // determines if either player has made a winning path
    public boolean findPath(Cell from, Cell to, Color c) {
        if (from.equals(to)) {
            return true;
        }

        if (from.color.equals(c) && !(this.checkedCells.contains(from))) {
            this.checkedCells.add(from);

            if (from.top.x >= 0) {
                Cell fromTop = this.cells.get(from.top.x).get(from.top.y);
                if (fromTop.color == c) {
                    if (this.findPath(fromTop, to, c)) {
                        return true;
                    }
                }
            }
            if (from.bottom.x <= n - 1) {
                Cell fromBottom = this.cells.get(from.bottom.x).get(from.bottom.y);
                if (fromBottom.color == c) {
                    if (this.findPath(fromBottom, to, c)) {
                        return true;
                    }
                }
            }
            if (from.left.y >= 0) {
                Cell fromLeft = this.cells.get(from.left.x).get(from.left.y);
                if (fromLeft.color == c) {
                    if (this.findPath(fromLeft, to, c)) {
                        return true;
                    }
                }
            }
            if (from.right.y <= n - 1) {
                Cell fromRight = this.cells.get(from.right.x).get(from.right.y);
                if (fromRight.color == c) {
                    if (this.findPath(fromRight, to, c)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // handles key inputs for resetting game board
    public void onKeyEvent(String ke) {
        if (ke.equals("r")) {
            this.cells = buildBoard();
            this.player1Turn = true;
            this.movesP1 = 0;
            this.movesP2 = 0;
        }

        // EXTRA CREDIT (allow player to choose game board size with key input):
        // handles user key input for game board size
        for (int i = 3; i < 10; i++) {
            if (i % 2 == 1 && ke.equals(Integer.toString(i))) {
                n = i;
                this.cells = buildBoard();
                this.player1Turn = true;
                this.movesP1 = 0;
                this.movesP2 = 0;
            }
        }
    }

    // handles mouse clicks
    public void onMouseClicked(Posn pos) {
        int posX = pos.x / WorldConstants.CELLSIZE;
        int posY = pos.y / WorldConstants.CELLSIZE;

        if (posX < n && posY < n) {
            Posn mousePos = this.cells.get(posX).get(posY).p;
            Cell boardCell = this.cells.get(n - 1 - mousePos.y).get(n - 1 - mousePos.x);

            if (boardCell.color.equals(Color.white)
                    && posX != 0 && posX != n - 1
                    && posY != 0 && posY != n - 1) {
                if (player1Turn) {
                    boardCell.color = Color.pink;
                    player1Turn = false;
                    this.movesP1++;
                } else {
                    boardCell.color = Color.magenta;
                    player1Turn = true;
                    this.movesP2++;
                }
            }
        }
    }

    // displays the end scene with a centered text message
    public WorldScene lastScene(String msg) {
        int worldSize = WorldConstants.CELLSIZE * n;
        WorldScene w1 = new WorldScene(worldSize, worldSize);
        w1.placeImageXY(new TextImage(msg, Color.black), worldSize / 2, worldSize / 2);
        return w1;
    }
}
