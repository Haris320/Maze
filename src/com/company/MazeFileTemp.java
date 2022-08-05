package com.company;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;


public class MazeFileTemp extends JPanel implements KeyListener{

    private JFrame frame;
    private Explorer explorer;
    private int col=0, row=0,dir = 1; //Location and direction of explorer
    private int size = 40;  		  //size of grid square in pixels
    private boolean debug = true;     //Set to true to print debug info
    private int numRows = 7, numCols=20; // set based on uploaded data
    private char[][] maze=new char[numRows][numCols];
    private boolean won = false;
    private Location endLoc;  //Finish Line of maze
    private boolean is3D = false;


    public MazeFileTemp()
    {
        setBoard();  //write this method to read board from file
        frame=new JFrame("A-Mazing Program");
        frame.setSize(1000,1000);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    public void paintComponent(Graphics g)
    {
        //Make Background Blank
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,frame.getWidth(),frame.getHeight());


        if(is3D) {
            g2.setColor(Color.CYAN);
            g2.setFont(new Font("Comic Sans", Font.BOLD, 20));
            g2.drawString("3D Maze!", 100, 30);

            //for
            int r = explorer.getLoc().getRow();
            int c = explorer.getLoc().getCol();
            int distance = explorer.getDistance(maze);
            if(r<numRows-1&&c<numCols-1) {
                if ((explorer.getDir() == 0 && maze[r - 1][c] == '#') || (explorer.getDir() == 1 && maze[r][c + 1] == '#') ||
                        (explorer.getDir() == 2 && maze[r + 1][c] == '#') || (explorer.getDir() == 3 && maze[r][c - 1] == '#')) {
                    int[] xLocs = {150, 150, 650, 650};
                    int[] yLocs = {150, 650, 650, 150};
                    Polygon leftWall = new Polygon(xLocs, yLocs, xLocs.length);
                    g2.setColor(Color.white);
                    g2.fill(leftWall);
                    g2.draw(leftWall);
                }

                    for(int i = 0; i < distance; i++){

                    if(((explorer.getDir()==0 && maze[r-i-1][c]=='#'&&maze[r][c]!='E')||(explorer.getDir()==1 && maze[r][c+i+1]=='#' &&
                            maze[r][c+1]!='#'))||(explorer.getDir()==2 && maze[r+i+1][c]=='#' && maze[r+1][c]!='#')||(explorer.getDir()==3 &&
                            maze[r][c-i-1]=='#'&& maze[r][c-1]!='#')&&maze[r][c]!='F'){
                        int[] xLocs = {100+50*i,100+50*i,700-50*i,700-50*i};
                        int[] yLocs = {100+50*i,700-50*i,700-50*i,100+50*i};
                        Polygon walls = new Polygon(xLocs,yLocs,xLocs.length);
                        g2.setColor(Color.DARK_GRAY);
                        g2.fill(walls);
                        g2.setColor(Color.BLACK);
                        g2.draw(walls);
                        break;
                    }

                    if((explorer.getDir()==0 && maze[r-i][c]=='E')){
                        int[] xLocs = {150+50*i,150+50*i,650-50*i,650-50*i};
                        int[] yLocs = {150+50*i,650-50*i,650-50*i,150+50*i};
                        Polygon walls = new Polygon(xLocs,yLocs,xLocs.length);
                        g2.setColor(Color.DARK_GRAY);
                        g2.fill(walls);
                        g2.setColor(Color.BLACK);
                        g2.draw(walls);
                        break;
                    }
                }
                    for (int i = 0; i < distance; i++) {
                        int[] xLocs = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                        int[] yLocs = {100 + 50 * i, 150 + 50 * i, 650 - 50 * i, 700 - 50 * i};
                        Polygon leftWall = new Polygon(xLocs, yLocs, xLocs.length);
                        int num = 0;
                        int num2 = 0;
                        switch (explorer.getDir()) {
                            case 0:
                                num = -i;
                                num2 = -1;
                                break;
                            case 1:
                                num = -1;
                                num2 = i;
                                if(num2+c>19)
                                    num2 -= endLoc.getCol() - explorer.getLoc().getCol();
                                break;
                            case 2:
                                num = i;
                                num2 = 1;
                                break;
                            case 3:
                                num = 1;
                                num2 = -i;
                                break;
                        }

                            if (maze[r][c + num2] == 'F')
                                g2.setColor(Color.GREEN);
                            else if (maze[r + num][c + num2] == ' ')
                                g2.setColor(Color.DARK_GRAY);
                            else
                                g2.setColor(Color.WHITE);



                        g2.fill(leftWall);
                        g2.setColor(Color.BLACK);
                        g2.draw(leftWall);
                        try {
                            if (maze[r + num][c + num2] == ' ') {
                                int[] xLoc = {100 + 50 * i, 100 + 50 * i, 150 + 50 * i, 150 + 50 * i};
                                int[] yLoc = {150 + 50 * i, 650 - 50 * i, 650 - 50 * i, 150 + 50 * i};
                                Polygon leftWallShade = new Polygon(xLoc, yLoc, xLoc.length);
                                g2.setColor(Color.LIGHT_GRAY);
                                g2.fill(leftWallShade);
                                g2.setColor(Color.BLACK);
                                g2.draw(leftWallShade);
                            }
                        } catch (Exception e) {
                            distance--;
                        }

                    }
                    for (int i = 0; i < distance; i++) {
                        int[] xLocs = {100 + 50 * i, 700 - 50 * i, 650 - 50 * i, 150 + 50 * i};
                        int[] yLocs = {100 + 50 * i, 100 + 50 * i, 150 + 50 * i, 150 + 50 * i};
                        Polygon ceilingWall = new Polygon(xLocs, yLocs, xLocs.length);
                        int num = 0;
                        int num2 = 0;
                        switch (explorer.getDir()) {
                            case 1:
                                num = 1;
                                num2 = i;
                                break;
                            case 3:
                                num = -1;
                                num2 = -i;
                                break;
                        }
                        try {
                            if (maze[r][c + num2] == 'F') {
                                int[] xLoc = {350, 450, 450, 350};
                                int[] yLoc = {350, 350, 450, 450};
                                Polygon poly = new Polygon(xLoc, yLoc, xLoc.length);
                                g2.setColor(Color.GREEN);
                                g2.fill(poly);
                                g2.setColor(Color.BLACK);
                                g2.draw(poly);
                                g2.setColor(Color.GREEN);
                            } else
                                g2.setColor(Color.WHITE);
                        } catch (Exception e) {
                            distance--;
                        }

                        g2.fill(ceilingWall);
                        g2.setColor(Color.BLACK);
                        g2.draw(ceilingWall);
                    }
                    for (int i = 0; i < distance; i++) {
                        int[] xLocs = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                        int[] yLocs = {100 + 50 * i, 150 + 50 * i, 650 - 50 * i, 700 - 50 * i};
                        Polygon rightWall = new Polygon(xLocs, yLocs, xLocs.length);
                        int num = 0;
                        int num2 = 0;
                        switch (explorer.getDir()) {
                            case 0:
                                num = -i;
                                num2 = -1;
                                break;
                            case 1:
                                num = 1;
                                num2 = i;
                                if (num2 + c > 19)
                                    num2 -= endLoc.getCol() - explorer.getLoc().getCol();
                                break;
                            case 2:
                                num = i;
                                num2 = 1;
                                break;
                            case 3:
                                num = -1;
                                num2 = -i;
                                break;
                        }
                        if (maze[r][c + num2] == 'F')
                            g2.setColor(Color.GREEN);
                        else if (maze[r + num][c + num2] == ' ')
                            g2.setColor(Color.DARK_GRAY);
                        else
                            g2.setColor(Color.WHITE);
                        g2.fill(rightWall);
                        g2.setColor(Color.BLACK);
                        g2.draw(rightWall);
                        if (maze[r + num][c + num2] == ' ') {
                            int[] xLoc = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] yLoc = {150 + 50 * i, 150 + 50 * i, 650 - 50 * i, 650 - 50 * i};
                            Polygon rightWallShade = new Polygon(xLoc, yLoc, xLoc.length);
                            g2.setColor(Color.LIGHT_GRAY);
                            g2.fill(rightWallShade);
                            g2.setColor(Color.BLACK);
                            g2.draw(rightWallShade);
                        }
                    }
                    for (int i = 0; i < distance; i++) {
                        int[] xLocs = {100 + 50 * i, 150 + 50 * i, 650 - 50 * i, 700 - 50 * i};
                        int[] yLocs = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                        Polygon floorWall = new Polygon(xLocs, yLocs, xLocs.length);
                        int num = 0;
                        int num2 = 0;
                        switch (explorer.getDir()) {
                            case 1:
                                num = 1;
                                num2 = i;
                                if(num2+c>19)
                                    num2 -= endLoc.getCol() - explorer.getLoc().getCol();
                                break;
                            case 3:
                                num = -1;
                                num2 = -i;
                                break;
                        }
                        if (maze[r][c + num2] == 'F')
                            g2.setColor(Color.GREEN);
                        else
                            g2.setColor(Color.WHITE);
                        g2.fill(floorWall);
                        g2.setColor(Color.BLACK);
                        g2.draw(floorWall);
                    }

            }



            if (debug){ // PRINT EXTRA INFO TO HELP DEBUG
                g2.setColor(Color.YELLOW);
                g2.drawString("Dir: "+explorer.getDir(),100, numRows*size+13*size-25);
                g2.drawString("Steps: "+explorer.getSteps(),100, numRows*size+14*size-25);
                g2.drawString("Turns: "+explorer.getTurns(),100, numRows*size+15*size-25);
                g2.setColor(Color.GRAY);
            }
            if(won){
                g2.setColor(Color.RED);
                System.out.println("You Win!");
                g2.drawString("Congratulations! You Won!",40, numRows*size+16*size-25);
                g2.setColor(Color.GRAY);
            }
        }
        //2D
        else{
            // DRAW MAZE
            g2.setColor(Color.GRAY);
            for(int c=0;c<maze[0].length;c++)
                for(int r=0;r<maze.length;r++){
                    if (explorer != null && explorer.atLocation(r,c)){
                        //EXPLORER
                        g2.fillRect(c*size+size,r*size+size,size,size);
                        g2.setColor(explorer.getColor());
                        g2.fill(explorer.getPoly());
                        g2.setColor(Color.GRAY);
                    }
                    else if (maze[r][c] == 'F'){
                        //SET FINISH OR GOAL SQUARE
                        g2.setColor(Color.GREEN);
                        g2.fillRect(c*size+size,r*size+size,size,size);
                        g2.setColor(Color.GRAY);
                    }
                    else if(maze[r][c]==' ') // OPEN SQUARE GRAY
                        g2.fillRect(c*size+size,r*size+size,size,size);
                    else // BLOCKED SQUARE GRAY OUTLINE
                        g2.drawRect(c*size+size,r*size+size,size,size);
                }
            if (debug){
                g2.setColor(Color.YELLOW);
                g2.drawString("Dir: "+explorer.getDir(),100, numRows*size+13*size-25);
                g2.drawString("Steps: "+explorer.getSteps(),100, numRows*size+14*size-25);
                g2.drawString("Turns: "+explorer.getTurns(),100, numRows*size+15*size-25);
                g2.setColor(Color.GRAY);
            }
            if(won){
                g2.setColor(Color.RED);
                g2.drawString("Congratulations! You Won!",40, numRows*size+16*size-25);
                g2.setColor(Color.GRAY);

            }
        }

        if (debug){
            g2.setColor(Color.YELLOW);
            g2.drawString("Dir: "+explorer.getDir(),100, numRows*size+13*size-25);
            g2.drawString("Steps: "+explorer.getSteps(),100, numRows*size+14*size-25);
            g2.drawString("Turns: "+explorer.getTurns(),100, numRows*size+15*size-25);
            g2.setColor(Color.GRAY);
        }
        if(won){
            g2.setColor(Color.RED);
            g2.drawString("Congratulations! You Won!",40, numRows*size+16*size-25);
            g2.setColor(Color.GRAY);

        }

    }





    public void keyPressed(KeyEvent e)
    {
        System.out.println(e.getKeyCode());

        if(e.getKeyCode()==32)
            is3D = !is3D;

        explorer.move(e.getKeyCode(),maze);
        if(endLoc.equals(explorer.getLoc()))
            won = true;
        System.out.println(won);
        repaint();
    }
    public void keyReleased(KeyEvent e) //Required for interface, leave empty
    {
    }
    public void keyTyped(KeyEvent e) // Required for interface, leave open
    {
    }

    public void setBoard()
    {
        // Read maze from file and set to maze 2d array
        try{
            BufferedReader input = new BufferedReader(new FileReader("maze0.txt"));
            String text = "";
            int r=0;
            while ((text=input.readLine())!=null){
                //if (debug) System.out.println("len ->"+text.length());
                for(int c=0; c<text.length();c++){
                    maze[r][c]=text.charAt(c);
                    if(text.charAt(c)=='E'){
                        explorer = new Explorer(new Location(r,c),0,size,Color.red);
                    }
                    if(text.charAt(c)=='F')
                        endLoc = new Location(r,c);
                }
                r++;
            }

            System.out.println(Arrays.deepToString(maze));

        }
        catch (IOException ioException){
            System.err.println("File does not exist");
        }
    }

    public static void main(String[] args)
    {
        new MazeFileTemp();
    }

}
