package app.entities.Cell.BloodCell;

import app.entities.Cell.Cell;

public abstract class BloodCell extends Cell {
    public BloodCell(String id, int health, int positionRow, int positionCol) {
        super(id, health, positionRow, positionCol);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
