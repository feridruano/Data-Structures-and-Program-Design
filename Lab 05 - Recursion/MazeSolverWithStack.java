import java.util.Scanner;
import java.util.Stack;

/**
 * @author Ferid Ruano
 * @version 2/19/2019
 */
public class MazeSolverWithStack
{

    private char[][] maze;

    /**
     * Default constructor
     *
     * @param grid Two dimensional array.
     */
    public MazeSolverWithStack( char[][] grid )
    {
        setMaze( grid );
    }

    /**
     * Create a maze using a two dimensional array.
     *
     * @param grid Two dimensional array maze.
     */
    public void setMaze( char[][] grid )
    {
        this.maze = new char[grid.length][];
        for ( int r = 0; r < grid.length; r++ )
        {
            this.maze[r] = new char[grid[r].length];
            for ( int c = 0; c < grid[r].length; c++ )
                this.maze[r][c] = grid[r][c];
        }
    }

    /**
     * Trace a path within a two dimensional array from starting point to goal point.
     *
     * @param startRow Starting or specific row.
     * @param startCol Starting or specific column.
     * @return True, if path to goal is possible. False otherwise.
     */
    private boolean findPath( int startRow, int startCol )
    {
        // TODO Project #6
        boolean result = false;
        Stack< MazeFrame > stack = new Stack<>();
        stack.push( new MazeFrame( startRow, startCol ) );

        MazeFrame current = null;


        // when you pop from the stack check for the goal first
        // if not the goal:
        // try moving up (NORTH), next try moving right (EAST),
        // next try moving down (SOUTH), and finally try moving left (WEST)
        // push only valid moves on the stack

        while ( !result && !stack.isEmpty())
        {
            current = stack.pop();
            int row = current.row;
            int col = current.col;

            this.maze[row][col] = '+';

             if ( !isGoal( row, col ) )
             {

                 if ( isInsideMaze( row - 1, col ) && isOpen( row - 1, col ) )
                 {
                     stack.push( new MazeFrame( row - 1, col ) );
                     current = stack.peek();
                 }
                 else if ( isInsideMaze( row, col + 1 ) && isOpen( row, col + 1 ) )
                 {
                     stack.push( new MazeFrame( row, col + 1 ) );
                     current = stack.peek();
                 }
                 else if ( isInsideMaze( row + 1, col ) && isOpen( row + 1, col ) )
                 {
                     stack.push( new MazeFrame( row + 1, col ) );
                     current = stack.peek();

                 }
                 else if ( isInsideMaze( row, col - 1 ) && isOpen( row, col - 1 ) )
                 {
                     stack.push( new MazeFrame( row, col - 1 ) );
                     current = stack.peek();
                 }
                 else
                     result = true;
             }
        }
        return result;
    }

    private boolean isInsideMaze( int r, int c )
    {
        // TODO Project #6 - Done
        return c >= 0 && c < this.maze.length && r >= 0 && r < this.maze[0].length;
    }

    private boolean isGoal( int r, int c )
    {
        return ( this.maze[r][c] == 'G' );
    }

    private boolean isOpen( int r, int c )
    {
        // TODO Project #6 - Done
        // ., S, or G would be considered open
        return ((this.maze[r][c] != '#') && (this.maze[r][c] != '+'));
    }

    private boolean setGoal( int r, int c )
    {
        boolean goalOK = false;
        if ( isInsideMaze( r, c ) && isOpen( r, c ) )
        {
            this.maze[r][c] = 'G';
            goalOK = true;
        }
        return goalOK;
    }

    private boolean setStart( int r, int c )
    {
        boolean startOK = false;
        if ( isInsideMaze( r, c ) && isOpen( r, c ) )
        {
            this.maze[r][c] = 'S';
            startOK = true;
        }
        return startOK;
    }

    private void resetStart( int r, int c )
    {
        this.maze[r][c] = 'S';
    }

    public void displayMaze()
    {
        System.out.printf( "      " );
        for ( int c = 0; c < this.maze[0].length; c++ )
        {
            System.out.printf( "[%1$2s] ", c );
        }
        System.out.println();
        for ( int r = 0; r < this.maze.length; r++ )
        {
            System.out.printf( "[%1$2s]", r );
            for ( int c = 0; c < this.maze[0].length; c++ )
            {
                System.out.printf( "%1$5s", this.maze[r][c] );
            }
            System.out.println();
        }
    }

    private class MazeFrame
    {
        private int row;
        private int col;

        public MazeFrame( int r, int c )
        {
            this.row = r;
            this.col = c;
        }

        public String toString()
        {
            return "[" + row + "," + col + "]";
        }
    }

    public static void main( String args[] )
    {
        char[][] grid = { { '.', '#', '#', '#', '#', '#' },
                          { '.', '.', '.', '.', '.', '#' },
                          { '#', '.', '#', '#', '#', '#' },
                          { '#', '.', '#', '.', '#', '#' },
                          { '.', '.', '.', '#', '.', '.' },
                          { '#', '#', '.', '.', '.', '#' } };

        MazeSolverWithStack searchGrid = new MazeSolverWithStack( grid );
        System.out.print( "\n        *** SEARCH THE MAZE ***\n" );
        searchGrid.displayMaze();
        Scanner keyboard = new Scanner( System.in );

        int rGoal;
        int cGoal;
        int rStart;
        int cStart;
        boolean inputOK;

        do
        {
            inputOK = true;
            System.out.println( "Enter the START row" );
            rStart = keyboard.nextInt();
            System.out.println( "Enter the START column" );
            cStart = keyboard.nextInt();
            if ( !searchGrid.setStart( rStart, cStart ) )
            {
                System.out.println( "Incorrect START coordinates, please try again." );
                inputOK = false;
            }
        }
        while ( !inputOK );

        do
        {
            inputOK = true;
            System.out.println( "Enter the GOAL row" );
            rGoal = keyboard.nextInt();
            System.out.println( "Enter the GOAL column" );
            cGoal = keyboard.nextInt();
            if ( rGoal == rStart && cGoal == cStart )
            {
                System.out.println( "GOAL is the same as START, try different coordinates." );
                inputOK = false;
            }
            else if ( !searchGrid.setGoal( rGoal, cGoal ) )
            {
                System.out.println( "Incorrect GOAL coordinates, please try again." );
                inputOK = false;
            }
        }
        while ( !inputOK );

        searchGrid.displayMaze();
        if ( searchGrid.findPath( rStart, cStart ) )
            System.out.println( "\n---> The GOAL [" + rGoal + "," + cGoal + "] was found!" );
        else
            System.out.println( "\n---> The GOAL [" + rGoal + "," + cGoal + "]  was not reached!" );
        searchGrid.resetStart( rStart, cStart );
        System.out.println( "\nThe search results:" );
        searchGrid.displayMaze();
    }
}