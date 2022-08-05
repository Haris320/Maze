package com.company;

public class Location {
    private int row, col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }
    //Getter methods
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    //Checks if a row and column equal a certain location
    public boolean equals(Location other){
        return this.row == other.getRow() && this.col == other.getCol();
    }

    //Checks if a row and column equals the current location
    public boolean equals(int r, int c){
        return this.row == r && this.col == c;
    }

    //Setter Methods
    public void setR(int row) {
        this.row += row;
    }

    public void setC(int col) {
        this.col += col;
    }


    @Override
    public String toString() {
        return "Location: ("+row+","+col+")";
    }
}
