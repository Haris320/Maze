package com.company;

import java.awt.*;

public class Explorer {

    private Location loc;
    private int dir = 1;
    private int size, steps,turns;
    private Color color;

    public Explorer(Location loc, int dif, int size, Color color) {
        this.loc = loc;
        this.dir = dif;
        this.size = size;
        this.color = color;
    }
    //Returns # of steps
    public int getSteps() {
        return steps;
    }

    //Returns # of turns
    public int getTurns() {
        return turns;
    }

    //Returns Location
    public Location getLoc() {
        return loc;
    }

    //Returns direction
    public int getDir() {
        return dir;
    }

    //Returns size
    public int getSize() {
        return size;
    }

    //Returns color
    public Color getColor() {
        return color;
    }

    //Checks if explorer is at given location
    public boolean atLocation(int r, int c) {
        return r == loc.getRow() && c == loc.getCol();
    }

    //Moves the explorer
    public void move(int key, char[][] maze){
        int r = getLoc().getRow();
        int c = getLoc().getCol();
        //moves forward
        if(key==38)
        {
            //facing up
            if(dir==0 && r > 0 &&maze[r-1][c] != '#')
                getLoc().setR(-1);
            //right
            if(dir==1 && c<maze[r].length-1 && maze[r][c+1] != '#')
                getLoc().setC(+1);
            //down
            if(dir==2 && r<maze[r].length-1 && maze[r+1][c] != '#')
                getLoc().setR(+1);
            //left
            if(dir==3 && c > 0 &&maze[r][c-1] != '#')
                getLoc().setC(-1);

            steps++;
        }
        if(key==39) //rotate right
        {
            dir++;
            if(dir>3)
                dir=0;
            turns++;
        }
        if(key==37) //rotate left
        {
            dir--;
            if(dir<0)
                dir=3;
            turns++;
        }
    }

    //makes explorer arrow shaped
    public Polygon getPoly(){
        int r=getLoc().getRow();
        int c=getLoc().getCol();
        Polygon arrowHead = new Polygon();
        if (dir == 0){
            arrowHead.addPoint( c*size+size,r*size+2*size);
            arrowHead.addPoint( (int)(c*size+size*1.5), (r*size+size));
            arrowHead.addPoint( c*size+size*2,r*size+size*2);
        }
        if (dir == 1 ){
            arrowHead.addPoint( c*size+size,r*size+size);
            arrowHead.addPoint( c*size+size*2, (int)(r*size+size*1.5));
            arrowHead.addPoint( c*size+size,r*size+size*2);
        }
        if (dir == 2 ){
            arrowHead.addPoint( c*size+size,r*size+size);
            arrowHead.addPoint( (int)(c*size+size*1.5), (r*size+size*2));
            arrowHead.addPoint( c*size+2*size,r*size+size);
        }
        if (dir == 3 ){
            arrowHead.addPoint( c*size+2*size,r*size+size);
            arrowHead.addPoint( c*size+size, (int)(r*size+size*1.5));
            arrowHead.addPoint( c*size+2*size,r*size+2*size);
        }
        return arrowHead;
    }

    //Gets distance to the nearest wall
    public int getDistance(char [][] maze){
        int distance = 5;
        int r = getLoc().getRow();
        int c = getLoc().getCol();
        int row = maze.length;
        int col = maze[0].length;

            if (dir == 0) {
                distance = 0;
                for (int i = r; i > 0; i--) {
                    if (maze[i][c] != '#' && maze[i][c] != 'F')
                        distance++;
                    else
                        break;

                }
                System.out.println("Wall Dis: " + distance);
            }


            if (dir == 1) {
                distance = 0;
                for (int i = c; i < maze[0].length; i++) {
                    if (maze[r][i] != '#' && maze[r][i] != 'F')
                        distance++;
                    else
                        break;

                }
                System.out.println("Wall Dis: " + distance);
            }

            if (dir == 2) {
                distance = 0;
                for (int i = r; i < maze.length; i++) {
                    if (maze[i][c] != '#' && maze[i][c] != 'F')
                        distance++;
                    else
                        break;

                }
                System.out.println("Wall Dis: " + distance);
            }


            if (dir == 3) {
                distance = 0;
                for (int i = c; i > 0; i--) {
                    if (maze[r][i] != '#' && maze[r][i] != 'F')
                        distance++;
                    else
                        break;

                }
                System.out.println("Wall Dis: " + distance);

            }


            if (distance > 5)
                distance = 5;
            System.out.println("Distance" + distance);



        return distance;
    }

}
