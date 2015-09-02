package com.frt.selectseats;

public class Seat {

    public static final int INVISIBLE_SEAT = 0;
    public static final int FREE_SEAT = 1;
    public static final int HIRED_SEAT = 2;
    public static final int CHILDREN_SEAT = 3;
    public static final int INVALID_SEAT = 4;

    int row;
    int column;

    // There are 3 states
    // - 0 for invisible seat
    // - 1 free seats
    // - 2 hired seats
    // - 3 children seats
    // - 4 invalid seats
    int state;
    int position;

    boolean isSelected;
    boolean isOverHiredSeat;

    public boolean isOverHiredSeat() {
        return isOverHiredSeat;
    }

    public void setIsOverHiredSeat(boolean isOverHiredSeat) {
        this.isOverHiredSeat = isOverHiredSeat;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + (row + 1) +
                ", column=" + (column + 1) +
                '}';
    }
}