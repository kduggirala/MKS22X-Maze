import java.util.*;
import java.io.*;
public class Maze{

    private char[][]maze;
    private boolean animate;//false by default
    private int startr;
    private int startc;

    /*Constructor loads a maze text file, and sets animate to false by default.
      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)

      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then: 

         throw a FileNotFoundException or IllegalStateException

    */

    public Maze(String filename) throws FileNotFoundException{
        //COMPLETE CONSTRUCTOR
    	animate = false;
    	
    	File mazefile = new File(filename);
		Scanner read1 = new Scanner(mazefile);
		String line1 = read1.nextLine();
		int numRows = 1;
		int numCols = line1.length();
		while(read1.hasNextLine()) {
			numRows++;
			read1.nextLine();
		}
		read1.close();
		
		maze = new char[numRows][numCols];
		Scanner read2 = new Scanner(mazefile);
		boolean s = false;
		boolean e = false;
		for (int i = 0; i < numRows; i++) {
			String thisLine = read2.nextLine();
			char[] thisLineArray = thisLine.toCharArray();
			for (int j = 0; j < numCols; j++) {
				if (thisLineArray[j] == 'S') {
					if (s) {
						read2.close();
						throw new IllegalStateException();
					}
					s = true;
					startr = i;
					startc = j;
				}
				else if (thisLineArray[j] == 'E') {
					if(e) {
						read2.close();
						throw new IllegalStateException();
					}
					e = true;
				}
				maze[i][j] = thisLineArray[j];
			}
		}
		read2.close();
		if (!(s && e)) {
			throw new IllegalStateException();
		}
    }
    
    public String toString() {
    	StringBuffer s = new StringBuffer();
    	for (char[] row : maze) {
    		for (char c : row) {
    			s.append(c);
    		}
    		s.append("\n");
    	}
    	return s.toString();
    }
    
    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }


    public void setAnimate(boolean b){
        animate = b;
    }


    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }



    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
    public int solve(){

  
    		maze[startr][startc] = '@';
    		return solve(startr, startc);

    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.


      Postcondition:

        The S is replaced with '@' but the 'E' is not.

        All visited spots that were not part of the solution are changed to '.'

        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col){ //you can add more parameters since this is private
    	
    	
    	for (int j = -1; j <= 1; j += 2) { //j is one or negative one
    		char c1 = maze[row + j][col];
    		char c2 = maze[row][col + j];
    		if (c1 == 'E') {
    			return 1;
    		}
    		if (c1 == ' ') {
    			maze[row + j][col] = '@';
    			int followingPath = solve(row + j, col);
    			if (followingPath == -1) {
    				maze[row + j][col] = '.';
    			}
    			else {
    				return ++followingPath;
    			}
    		}
    		if (c2 == 'E') {
    			return 1;
    		}
    		if (c2 == ' ') {
    			maze[row][col + j] = '@';
    			int followingPath = solve(row, col + j);
    			if (followingPath == -1) {
    				maze[row][col + j] = '.';
    			}
    			else {
    				return ++followingPath;
    			}
    		}
    	}

        //automatic animation! You are welcome.
        if(animate){

            clearTerminal();
            System.out.println(this);

            wait(20);
        }

        //COMPLETE SOLVE

        return -1; //so it compiles
    }


}