package game;

import javalib.worldimages.*;

import java.awt.*;

// represents a colored cell
public class Cell {

    Posn p;
    public Color color;
    public Posn top;
    public Posn bottom;
    public Posn left;
    public Posn right;

    // (cell.x, cell.y) refers to (row, column)
    public Cell(Posn p) {
        this.p = p;
        this.color = cellColor();
        this.top = new Posn(p.x - 1, p.y);
        this.bottom = new Posn(p.x + 1, p.y);
        this.left = new Posn(p.x, p.y - 1);
        this.right = new Posn(p.x, p.y + 1);
    }

    // draws the cell onto the background at the specified position
    public WorldImage drawAt(int row, int col, WorldImage background) {
        WorldImage cell = new RectangleImage(WorldConstants.CELLSIZE, WorldConstants.CELLSIZE, OutlineMode.SOLID, this.color);
        return new OverlayOffsetImage(cell, col * WorldConstants.CELLSIZE, row * WorldConstants.CELLSIZE, background);
    }

    // returns the color of this cell based on the position of this cell
    public Color cellColor() {
        Color c;
        if ((this.p.x + this.p.y) % 2 == 0) {
            c = Color.white;
        } else {
            if (this.p.x % 2 == 0) {
                c = Color.magenta;
            } else {
                c = Color.pink;
            }
        }
        return c;
    }
}
