package test;

import game.BridgItGame;
import game.Cell;
import game.WorldConstants;
import javalib.impworld.WorldScene;
import javalib.worldimages.*;
import tester.Tester;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

// represents examples and tests for game.BridgItGame world
class ExamplesBridgIt {

    // examples of BridgIt game worlds
    BridgItGame game3 = new BridgItGame(3);
    BridgItGame game5 = new BridgItGame(5);

    // examples of cells
    Cell cellBlank = new Cell(new Posn(0, 0));
    Cell cellPurple = new Cell(new Posn(0, 1));
    Cell cellPink = new Cell(new Posn(1, 0));

    // test game.BridgItGame constructor
    void testBridgItGameConstructor(Tester t) {
        t.checkConstructorException(new IllegalArgumentException("Invalid game grid size."),
                "game.BridgItGame", 1);
        t.checkConstructorException(new IllegalArgumentException("Invalid game grid size."),
                "game.BridgItGame", 2);
        t.checkConstructorException(new IllegalArgumentException("Invalid game grid size."),
                "game.BridgItGame", 4);
        t.checkConstructorException(new IllegalArgumentException("Invalid game grid size."),
                "game.BridgItGame", 6);
        t.checkConstructorException(new IllegalArgumentException("Invalid game grid size."),
                "game.BridgItGame", 8);
    }

    // test method drawAt
    void testDrawAt(Tester t) {
        WorldImage bg = new RectangleImage(200, 200, OutlineMode.OUTLINE, Color.white);

        WorldImage imgBlank = new RectangleImage(WorldConstants.CELLSIZE, WorldConstants.CELLSIZE, OutlineMode.SOLID, Color.white);
        WorldImage imgPurple = new RectangleImage(WorldConstants.CELLSIZE, WorldConstants.CELLSIZE, OutlineMode.SOLID, Color.magenta);
        WorldImage imgPink = new RectangleImage(WorldConstants.CELLSIZE, WorldConstants.CELLSIZE, OutlineMode.SOLID, Color.pink);

        t.checkExpect(this.cellBlank.drawAt(0, 0, bg),
                new OverlayOffsetImage(imgBlank, 0 * WorldConstants.CELLSIZE, 0 * WorldConstants.CELLSIZE, bg));
        t.checkExpect(this.cellPurple.drawAt(50, 50, bg),
                new OverlayOffsetImage(imgPurple, 50 * WorldConstants.CELLSIZE, 50 * WorldConstants.CELLSIZE, bg));
        t.checkExpect(this.cellPink.drawAt(100, 100, bg),
                new OverlayOffsetImage(imgPink, 100 * WorldConstants.CELLSIZE, 100 * WorldConstants.CELLSIZE, bg));
    }

    // test method cellColor
    void testCellColor(Tester t) {
        t.checkExpect(this.cellBlank.cellColor(), Color.white);
        t.checkExpect(this.cellPurple.cellColor(), Color.magenta);
        t.checkExpect(this.cellPink.cellColor(), Color.pink);
        t.checkExpect(new Cell(new Posn(1, 2)).color, Color.pink);
        t.checkExpect(new Cell(new Posn(2, 1)).color, Color.magenta);
        t.checkExpect(new Cell(new Posn(4, 2)).color, Color.white);
    }

    // test method buildBoard
    void testBuildBoard(Tester t) {
        // reset cells
        game3.onKeyEvent("r");
        game5.onKeyEvent("r");

        ArrayList<ArrayList<Cell>> boardGame3 = new ArrayList<ArrayList<Cell>>(Arrays.asList(
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(0, 0)),
                        new Cell(new Posn(0, 1)),
                        new Cell(new Posn(0, 2)))),
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(1, 0)),
                        new Cell(new Posn(1, 1)),
                        new Cell(new Posn(1, 2)))),
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(2, 0)),
                        new Cell(new Posn(2, 1)),
                        new Cell(new Posn(2, 2))))));

        ArrayList<ArrayList<Cell>> boardGame5 = new ArrayList<ArrayList<Cell>>(Arrays.asList(
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(0, 0)),
                        new Cell(new Posn(0, 1)),
                        new Cell(new Posn(0, 2)),
                        new Cell(new Posn(0, 3)),
                        new Cell(new Posn(0, 4)))),
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(1, 0)),
                        new Cell(new Posn(1, 1)),
                        new Cell(new Posn(1, 2)),
                        new Cell(new Posn(1, 3)),
                        new Cell(new Posn(1, 4)))),
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(2, 0)),
                        new Cell(new Posn(2, 1)),
                        new Cell(new Posn(2, 2)),
                        new Cell(new Posn(2, 3)),
                        new Cell(new Posn(2, 4)))),
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(3, 0)),
                        new Cell(new Posn(3, 1)),
                        new Cell(new Posn(3, 2)),
                        new Cell(new Posn(3, 3)),
                        new Cell(new Posn(3, 4)))),
                new ArrayList<Cell>(Arrays.asList(
                        new Cell(new Posn(4, 0)),
                        new Cell(new Posn(4, 1)),
                        new Cell(new Posn(4, 2)),
                        new Cell(new Posn(4, 3)),
                        new Cell(new Posn(4, 4))))));

        t.checkExpect(this.game3.buildBoard(), boardGame3);
        t.checkExpect(this.game3.cells, boardGame3);
        t.checkExpect(this.game5.buildBoard(), boardGame5);
        t.checkExpect(this.game5.cells, boardGame5);

        // test cyclic structure
        t.checkExpect(boardGame3.get(0).get(0).bottom, new Posn(1, 0));
        t.checkExpect(boardGame3.get(2).get(1).top, new Posn(1, 1));
        t.checkExpect(boardGame3.get(0).get(1).right, new Posn(0, 2));
        t.checkExpect(boardGame3.get(1).get(2).left, new Posn(1, 1));

        t.checkExpect(boardGame5.get(1).get(4).bottom, new Posn(2, 4));
        t.checkExpect(boardGame5.get(3).get(3).top, new Posn(2, 3));
        t.checkExpect(boardGame5.get(2).get(3).right, new Posn(2, 4));
        t.checkExpect(boardGame5.get(4).get(2).left, new Posn(4, 1));
    }

    // test makeScene
    void testMakeScene(Tester t) {
        // reset cells
        game3.onKeyEvent("r");
        game5.onKeyEvent("r");

        WorldImage playerTurn = new TextImage("Player 1 (Pink) Turn", Color.black);
        WorldImage playerMoves = new TextImage("# Moves (P1: 0 | P2: 0)", Color.black);
        WorldImage message3 = new OverlayOffsetImage(
                new TextImage("Current Game Size: 3", Color.black),
                65, 35,
                new AboveAlignImage(AlignModeX.LEFT,
                        new TextImage("Change board size with odd number keys.", Color.black),
                        new TextImage("Use 'r' key to reset game board.", Color.black)));
        WorldImage message5 = new OverlayOffsetImage(
                new TextImage("Current Game Size: 5", Color.black),
                65, 35,
                new AboveAlignImage(AlignModeX.LEFT,
                        new TextImage("Change board size with odd number keys.", Color.black),
                        new TextImage("Use 'r' key to reset game board.", Color.black)));

        // BridgIt game for n = 3
        int n = 3;
        int size = WorldConstants.CELLSIZE * n;
        WorldScene w1 = new WorldScene(size, size);
        WorldImage image = new RectangleImage(size * 2, size * 2, OutlineMode.OUTLINE,
                Color.white);

        for (int i = 0; i < n; i++) {
            ArrayList<Cell> cellRow = this.game3.buildBoard().get(i);
            for (int j = 0; j < n; j++) {
                image = cellRow.get(j).drawAt(i, j, image);
            }
        }

        int placePt = size - (WorldConstants.CELLSIZE / 2);
        w1.placeImageXY(image, placePt, placePt);
        w1.placeImageXY(playerTurn, size / 2, size + 25);
        w1.placeImageXY(playerMoves, size / 2, size + 55);
        w1.placeImageXY(message3, size / 2, size + 130);

        t.checkExpect(this.game3.makeScene(), w1);

        // BridgIt game for n = 5
        int n2 = 5;
        int size2 = WorldConstants.CELLSIZE * n2;
        WorldScene w2 = new WorldScene(size2, size2);
        WorldImage image2 = new RectangleImage(size2 * 2, size2 * 2, OutlineMode.OUTLINE,
                Color.white);

        for (int i = 0; i < n2; i++) {
            ArrayList<Cell> cellRow = this.game5.buildBoard().get(i);
            for (int j = 0; j < n2; j++) {
                image2 = cellRow.get(j).drawAt(i, j, image2);
            }
        }

        int placePt2 = size2 - (WorldConstants.CELLSIZE / 2);
        w2.placeImageXY(image2, placePt2, placePt2);
        w2.placeImageXY(playerTurn, size2 / 2, size2 + 25);
        w2.placeImageXY(playerMoves, size2 / 2, size2 + 55);
        w2.placeImageXY(message5, size / 2, size + 130);

        t.checkExpect(this.game5.makeScene(), w2);

        // test player win scenario
        game3.cells.get(1).get(1).color = Color.pink;
        t.checkExpect(game3.makeScene(), game3.lastScene("Player 1 (Pink) Wins!"));

        game5.cells.get(1).get(1).color = Color.magenta;
        game5.cells.get(3).get(1).color = Color.magenta;
        t.checkExpect(game5.makeScene(), game5.lastScene("Player 2 (Purple) Wins!"));
    }

    // test method drawPlayerTurn
    void testDrawPlayerTurn(Tester t) {
        // reset cells
        game3.onKeyEvent("r");
        game5.onKeyEvent("r");

        game3.player1Turn = true;
        game5.player1Turn = true;

        t.checkExpect(game3.drawPlayerTurn(), new TextImage("Player 1 (Pink) Turn", Color.black));
        t.checkExpect(game5.drawPlayerTurn(), new TextImage("Player 1 (Pink) Turn", Color.black));

        game3.player1Turn = false;
        game5.player1Turn = false;

        t.checkExpect(game3.drawPlayerTurn(), new TextImage("Player 2 (Purple) Turn", Color.black));
        t.checkExpect(game5.drawPlayerTurn(), new TextImage("Player 2 (Purple) Turn", Color.black));
    }

    // test method onKeyEvent
    void testOnKeyEvent(Tester t) {
        // reset cells
        game3.onKeyEvent("r");
        game5.onKeyEvent("r");

        // test reset game board key input
        game3.cells.get(0).get(0).color = Color.pink;
        game3.cells.get(1).get(1).color = Color.magenta;
        game3.cells.get(2).get(2).color = Color.pink;

        game3.player1Turn = false;
        game3.movesP1 = 7;
        game3.movesP2 = 3;

        t.checkExpect(game3.cells.get(0).get(0).color, Color.pink);
        t.checkExpect(game3.cells.get(1).get(1).color, Color.magenta);
        t.checkExpect(game3.cells.get(2).get(2).color, Color.pink);

        t.checkExpect(game3.player1Turn, false);
        t.checkExpect(game3.movesP1 + game3.movesP2, 10);

        game3.onKeyEvent("k");

        t.checkExpect(game3.cells.get(0).get(0).color, Color.pink);
        t.checkExpect(game3.cells.get(1).get(1).color, Color.magenta);
        t.checkExpect(game3.cells.get(2).get(2).color, Color.pink);

        t.checkExpect(game3.player1Turn, false);
        t.checkExpect(game3.movesP1 + game3.movesP2, 10);

        game3.onKeyEvent("r");

        t.checkExpect(game3.cells.get(0).get(0).color, Color.white);
        t.checkExpect(game3.cells.get(1).get(1).color, Color.white);
        t.checkExpect(game3.cells.get(2).get(2).color, Color.white);

        t.checkExpect(game3.player1Turn, true);
        t.checkExpect(game3.movesP1 + game3.movesP2, 0);

        game5.cells.get(2).get(2).color = Color.pink;
        game5.cells.get(3).get(3).color = Color.magenta;
        game5.cells.get(4).get(4).color = Color.pink;

        t.checkExpect(game5.cells.get(2).get(2).color, Color.pink);
        t.checkExpect(game5.cells.get(3).get(3).color, Color.magenta);
        t.checkExpect(game5.cells.get(4).get(4).color, Color.pink);

        game5.player1Turn = true;
        game5.movesP1 = 5;
        game5.movesP2 = 13;

        t.checkExpect(game5.cells.get(2).get(2).color, Color.pink);
        t.checkExpect(game5.cells.get(3).get(3).color, Color.magenta);
        t.checkExpect(game5.cells.get(4).get(4).color, Color.pink);

        t.checkExpect(game5.player1Turn, true);
        t.checkExpect(game5.movesP1 + game5.movesP2, 18);

        game5.onKeyEvent("q");

        t.checkExpect(game5.cells.get(2).get(2).color, Color.pink);
        t.checkExpect(game5.cells.get(3).get(3).color, Color.magenta);
        t.checkExpect(game5.cells.get(4).get(4).color, Color.pink);

        t.checkExpect(game5.player1Turn, true);
        t.checkExpect(game5.movesP1 + game5.movesP2, 18);

        game5.onKeyEvent("r");

        t.checkExpect(game5.cells.get(2).get(2).color, Color.white);
        t.checkExpect(game5.cells.get(3).get(3).color, Color.white);
        t.checkExpect(game5.cells.get(4).get(4).color, Color.white);

        t.checkExpect(game5.player1Turn, true);
        t.checkExpect(game5.movesP1 + game5.movesP2, 0);

        // test change board game size key input
        game3.player1Turn = false;
        game3.movesP1 = 7;
        game3.movesP2 = 3;

        t.checkExpect(game3.n, 3);
        game3.onKeyEvent("6");

        t.checkExpect(game3.player1Turn, false);
        t.checkExpect(game3.movesP1 + game3.movesP2, 10);

        t.checkExpect(game3.n, 3);
        game3.onKeyEvent("5");

        t.checkExpect(game3.player1Turn, true);
        t.checkExpect(game3.movesP1 + game3.movesP2, 0);

        t.checkExpect(game3.n, 5);

        game3.onKeyEvent("3");
        t.checkExpect(game3.n, 3);

        game5.player1Turn = true;
        game5.movesP1 = 5;
        game5.movesP2 = 13;

        t.checkExpect(game5.n, 5);
        game5.onKeyEvent("1");

        t.checkExpect(game5.player1Turn, true);
        t.checkExpect(game5.movesP1 + game5.movesP2, 18);

        t.checkExpect(game5.n, 5);
        game5.onKeyEvent("9");

        t.checkExpect(game5.player1Turn, true);
        t.checkExpect(game5.movesP1 + game5.movesP2, 0);

        t.checkExpect(game5.n, 9);

        game5.onKeyEvent("5");
        t.checkExpect(game5.n, 5);
    }

    // test method onMouseClicked
    void testOnMouseClicked(Tester t) {
        // reset cells
        game3.onKeyEvent("r");
        game5.onKeyEvent("r");

        game3.player1Turn = true;
        game3.movesP1 = 0;

        t.checkExpect(game3.cells.get(1).get(1).color, Color.white);
        t.checkExpect(game3.movesP1, 0);
        game3.onMouseClicked(new Posn(53, 52));
        t.checkExpect(game3.cells.get(1).get(1).color, Color.pink);
        t.checkExpect(game3.movesP1, 1);

        // game edge white cells cannot be changed
        t.checkExpect(game3.cells.get(2).get(2).color, Color.white);
        t.checkExpect(game3.movesP1, 1);
        game3.onMouseClicked(new Posn(120, 130));
        t.checkExpect(game3.cells.get(2).get(2).color, Color.white);
        t.checkExpect(game3.movesP1, 1);

        // white cell color can only be changed once
        t.checkExpect(game3.cells.get(1).get(1).color, Color.pink);
        t.checkExpect(game3.movesP1, 1);
        game3.onMouseClicked(new Posn(52, 53));
        t.checkExpect(game3.cells.get(1).get(1).color, Color.pink);
        t.checkExpect(game3.movesP1, 1);

        game5.player1Turn = false;
        t.checkExpect(game5.cells.get(3).get(1).color, Color.white);
        game5.onMouseClicked(new Posn(190, 52));
        t.checkExpect(game5.cells.get(3).get(1).color, Color.magenta);

        // game edge white cells cannot be changed
        t.checkExpect(game5.cells.get(4).get(2).color, Color.white);
        game5.onMouseClicked(new Posn(202, 220));
        t.checkExpect(game5.cells.get(4).get(2).color, Color.white);

        // white cell color can only be changed once
        game5.player1Turn = false;
        t.checkExpect(game5.cells.get(3).get(1).color, Color.magenta);
        game5.onMouseClicked(new Posn(185, 53));
        t.checkExpect(game5.cells.get(3).get(1).color, Color.magenta);
    }

    // test method player1Win, player2Win
    void testPlayerWin(Tester t) {
        // reset cells
        game3.onKeyEvent("r");
        game5.onKeyEvent("r");

        t.checkExpect(game3.player1Win(), false);
        t.checkExpect(game3.player2Win(), false);

        game3.cells.get(1).get(1).color = Color.magenta;
        t.checkExpect(game3.player1Win(), false);
        t.checkExpect(game3.player2Win(), true);

        game3.onKeyEvent("r");

        game3.cells.get(1).get(1).color = Color.pink;
        t.checkExpect(game3.player1Win(), true);
        t.checkExpect(game3.player2Win(), false);

        t.checkExpect(game5.player1Win(), false);
        t.checkExpect(game5.player2Win(), false);

        game5.cells.get(1).get(1).color = Color.magenta;
        game5.cells.get(3).get(1).color = Color.magenta;
        t.checkExpect(game5.player1Win(), false);
        t.checkExpect(game5.player2Win(), true);

        game5.onKeyEvent("r");

        game5.cells.get(1).get(1).color = Color.pink;
        game5.cells.get(1).get(3).color = Color.pink;
        t.checkExpect(game5.player1Win(), true);
        t.checkExpect(game5.player2Win(), false);

        game5.onKeyEvent("r");

        game5.cells.get(1).get(1).color = Color.magenta;
        game5.cells.get(1).get(3).color = Color.magenta;
        game5.cells.get(2).get(2).color = Color.magenta;
        t.checkExpect(game5.player1Win(), false);
        t.checkExpect(game5.player2Win(), false);

        game5.cells.get(3).get(1).color = Color.magenta;
        t.checkExpect(game5.player1Win(), false);
        t.checkExpect(game5.player2Win(), true);
    }

    // test method findPath
    void testFindPath(Tester t) {
        // reset cells
        game3.onKeyEvent("r");
        game5.onKeyEvent("r");

        game3.checkedCells = new ArrayList<Cell>();
        game5.checkedCells = new ArrayList<Cell>();

        // +1 change means findPath is true
        // no change means findPath is false
        int count = 0;

        // test player 1 findPath (n = 3)
        for (int i = 1; i < 3; i += 2) {
            for (int j = 1; j < 3; j += 2) {
                Cell from = game3.cells.get(i).get(0);
                Cell to = game3.cells.get(j).get(3 - 1);
                game3.checkedCells = new ArrayList<Cell>();
                if (game3.findPath(from, to, Color.pink)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 0);

        game3.cells.get(1).get(1).color = Color.pink;

        for (int i = 1; i < 3; i += 2) {
            for (int j = 1; j < 3; j += 2) {
                Cell from = game3.cells.get(i).get(0);
                Cell to = game3.cells.get(j).get(3 - 1);
                game3.checkedCells = new ArrayList<Cell>();
                if (game3.findPath(from, to, Color.pink)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 1);

        game3.onKeyEvent("r");

        // test player 2 findPath (n = 3)
        for (int i = 1; i < 3; i += 2) {
            for (int j = 1; j < 3; j += 2) {
                Cell from = game3.cells.get(0).get(i);
                Cell to = game3.cells.get(3 - 1).get(j);
                game3.checkedCells = new ArrayList<Cell>();
                if (game3.findPath(from, to, Color.magenta)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 1);

        game3.cells.get(1).get(1).color = Color.magenta;

        for (int i = 1; i < 3; i += 2) {
            for (int j = 1; j < 3; j += 2) {
                Cell from = game3.cells.get(0).get(i);
                Cell to = game3.cells.get(3 - 1).get(j);
                game3.checkedCells = new ArrayList<Cell>();
                if (game3.findPath(from, to, Color.magenta)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 2);

        // test player 1 findPath (n = 5)
        for (int i = 1; i < 5; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                Cell from = game5.cells.get(i).get(0);
                Cell to = game5.cells.get(j).get(5 - 1);
                game5.checkedCells = new ArrayList<Cell>();
                if (game5.findPath(from, to, Color.pink)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 2);

        game5.cells.get(1).get(1).color = Color.pink;
        game5.cells.get(2).get(2).color = Color.pink;
        game5.cells.get(3).get(3).color = Color.pink;

        for (int i = 1; i < 5; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                Cell from = game5.cells.get(i).get(0);
                Cell to = game5.cells.get(j).get(5 - 1);
                game5.checkedCells = new ArrayList<Cell>();
                if (game5.findPath(from, to, Color.pink)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 3);

        game5.onKeyEvent("r");

        game5.cells.get(1).get(1).color = Color.pink;
        game5.cells.get(3).get(1).color = Color.pink;
        game5.cells.get(1).get(3).color = Color.pink;

        for (int i = 1; i < 5; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                Cell from = game5.cells.get(i).get(0);
                Cell to = game5.cells.get(j).get(5 - 1);
                game5.checkedCells = new ArrayList<Cell>();
                if (game5.findPath(from, to, Color.pink)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 4);

        game5.onKeyEvent("r");

        // test player 2 findPath (n = 5)
        for (int i = 1; i < 5; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                Cell from = game5.cells.get(0).get(i);
                Cell to = game5.cells.get(5 - 1).get(j);
                game5.checkedCells = new ArrayList<Cell>();
                if (game5.findPath(from, to, Color.magenta)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 4);

        game5.cells.get(1).get(1).color = Color.magenta;
        game5.cells.get(2).get(2).color = Color.magenta;
        game5.cells.get(3).get(3).color = Color.magenta;

        for (int i = 1; i < 5; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                Cell from = game5.cells.get(0).get(i);
                Cell to = game5.cells.get(5 - 1).get(j);
                game5.checkedCells = new ArrayList<Cell>();
                if (game5.findPath(from, to, Color.magenta)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 5);

        game5.onKeyEvent("r");

        game5.cells.get(1).get(1).color = Color.magenta;
        game5.cells.get(1).get(3).color = Color.magenta;
        game5.cells.get(3).get(3).color = Color.magenta;

        for (int i = 1; i < 5; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                Cell from = game5.cells.get(0).get(i);
                Cell to = game5.cells.get(5 - 1).get(j);
                game5.checkedCells = new ArrayList<Cell>();
                if (game5.findPath(from, to, Color.magenta)) {
                    count++;
                }
            }
        }
        t.checkExpect(count, 6);
    }

    // test method lastScene
    void testLastScene(Tester t) {
        WorldScene w1 = new WorldScene(150, 150);
        WorldScene w2 = new WorldScene(250, 250);

        w1.placeImageXY(new TextImage("Player 1 (Pink) Wins!", Color.black), 75, 75);
        w2.placeImageXY(new TextImage("Player 2 (Purple) Wins!", Color.black), 125, 125);

        t.checkExpect(game3.lastScene("Player 1 (Pink) Wins!"), w1);
        t.checkExpect(game5.lastScene("Player 2 (Purple) Wins!"), w2);
    }
}
