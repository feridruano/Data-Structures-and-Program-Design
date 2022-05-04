import java.util.*;

/**
 * @author Ferid Ruano
 * @version 2/19/2019
 * https://www.cs.bu.edu/teaching/alg/maze/
 */
public class MazeSolver
{
    private char[][] maze;

    /**
     * Default constructor
     *
     * @param grid Two dimensional array.
     */
    public MazeSolver( char[][] grid )
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
     * @param r Starting or specific row.
     * @param c Starting or specific column.
     * @return True, if path to goal is possible. False otherwise.
     */
    private boolean findPath( int r, int c )
    {
        // TODO Project #5 - Done

        if ( !isInsideMaze( r, c ) )
            return false;
        if ( isGoal( r, c ) )
            return true;
        if ( !isOpen( r, c ) )
            return false;

        // Add to path
        this.maze[r][c] = '+';

        // Check North
        if ( findPath( r - 1, c ) )
            return true;
        //Check East
        if ( findPath( r, c + 1 ) )
            return true;
        // Check South
        if ( findPath( r + 1, c ) )
            return true;
        // Check West
        if ( findPath( r, c - 1 ) )
            return true;
        // No open path available then move backwards
        return false;
    }

    /**
     * Checks if row and column location are within grid.
     *
     * @param r Specific row.
     * @param c Specific column.
     * @return True, if row and column are within grid. False otherwise.
     */
    private boolean isInsideMaze( int r, int c )
    {
        // TODO Project #5 - Done
        return ( ( c >= 0 ) && ( c < this.maze.length ) && ( r >= 0 ) && (r < this.maze[0].length )); // Assumed nxn
    }

    /**
     * Checks if row and column location are the goal coordinates.
     *
     * @param r Specific row.
     * @param c Specific column.
     * @return True, if row and column are the goal coordinates.
     */
    private boolean isGoal( int r, int c )
    {
        return ( this.maze[r][c] == 'G' );
    }

    /**
     * Checks if a coordinate is open or closed.
     *
     * @param r Specific row.
     * @param c Specific column.
     * @return True, if coordinate is open. False otherwise.
     */
    private boolean isOpen( int r, int c )
    {
        // TODO Project #5 - Done
        // ., S, or G would be considered open
        return ( ( this.maze[r][c] != '#' ) && ( this.maze[r][c] != '+' ) );
    }

    /**
     * Sets the goal location within grid.
     *
     * @param r Specific row.
     * @param c Specific column
     * @return True, if goal location was set correctly. False otherwise.
     */
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

    /**
     * Sets the starting coordinates.
     *
     * @param r Specific row.
     * @param c Specific column.
     * @return True, if starting coordinates were set correctly. False otherwise.
     */
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

    /**
     * Resets the starting coordinates.
     *
     * @param r Specific row.
     * @param c Specific column.
     */
    private void resetStart( int r, int c )
    {
        this.maze[r][c] = 'S';
    }

    /**
     * Display the two dimensional grid maze.
     */
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

    public static void main( String args[] )
    {
        char[][] grid = { { '.', '#', '#', '#', '#', '#' },
                          { '.', '.', '.', '.', '.', '#' },
                          { '#', '.', '#', '#', '#', '#' },
                          { '#', '.', '#', '.', '#', '#' },
                          { '.', '.', '.', '#', '.', '.' },
                          { '#', '#', '.', '.', '.', '#' } };

        MazeSolver searchGrid = new MazeSolver( grid );
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
