package com.frt.selectseats;

public class Seat {

    public static final int INVISIBLE_SEAT = 0;
    public static final int FREE_SEAT = 1;
    public static final int HIRED_SEAT = 2;

    int row;
    int column;

    // There are 3 states
    // - 0 for invisible seat
    // - 1 free seats
    // - 2 hired seats
    int state;
    int position;

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
}