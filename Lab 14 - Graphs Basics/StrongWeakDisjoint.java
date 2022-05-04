import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class that checks connectivity in a directed unweighted graph
 * The graph is represented by adjacency matrix
 *
 * @author atb
 * @version 4/30/2019
 * @modified Ferid Ruano
 */
public class StrongWeakDisjoint
{
    private int[][] adjMatrixDirected;
    private int[][] adjMatrixUndirected;
    private int[][] reachabilityMatrix;
    private int numberOfNodes;

    public StrongWeakDisjoint( int numberOfNodes, int[][] graph )
    {
        this.numberOfNodes = numberOfNodes;
        this.adjMatrixDirected = new int[numberOfNodes][numberOfNodes];
        this.reachabilityMatrix = new int[this.numberOfNodes][this.numberOfNodes];

        for ( int row = 0; row < this.numberOfNodes; row++ )
            for ( int col = 0; col < this.numberOfNodes; col++ )
            {
                this.adjMatrixDirected[row][col] = graph[row][col];
                this.reachabilityMatrix[row][col] = this.adjMatrixDirected[row][col];
            }
        buildReachabilityMatrix();
    }

    private void buildReachabilityMatrix()
    {
        //TODO Lab14b #2.1 - Done

        // Warshall's Algorithm (Transitive )
        // Traverse through each node
        for ( int b = 0; b < this.numberOfNodes; b++ )
            // Find a path from b to c
            for ( int c = 0; c < this.numberOfNodes; c++ )
                // Only if there exists a path from b to c
                if ( this.reachabilityMatrix[b][c] != 0 )
                    // Find path to b from a
                    for ( int a = 0; a < this.numberOfNodes; a++ )
                        // Only if there exists a path to b from a
                        if ( this.reachabilityMatrix[a][b] != 0 && a != c )
                            // Mark path from a to c
                            this.reachabilityMatrix[a][c] = 1;
    }

    public boolean isGraphStronglyConnected()
    {
        //TODO Lab14b #2.2 - Done
        final int PATH = 1;
        boolean result = true;

        // Traverse reachAbilityMatrix and check for all 1s except main diagonal
        for ( int row = 0; result && row < this.numberOfNodes; row++ )
            for ( int col = 0; col < this.numberOfNodes; col++ )
                if ( row != col && this.reachabilityMatrix[row][col] != PATH )
                    result = false;

        return result;
    }

    private boolean isUndirectedGraphConnected()
    {
        //TODO Lab14b #2.4 - Done
        // utilizes breath-first-traversal algorithm - the same implementation as in Lab14b #1

        boolean result = true;
        final int ORIGIN = 0;

        // Utilizing a boolean array to mark nodes as visited
        boolean[] visited = new boolean[this.numberOfNodes];
        Queue< Integer > nodeQueue = new LinkedBlockingQueue<>();
        visited[ORIGIN] = true; // Visit origin node
        nodeQueue.offer( ORIGIN );// Add origin node

        // Traverse adjacency matrix
        while ( !nodeQueue.isEmpty() )
        {
            int node = nodeQueue.poll(); // Dequeue source node
            for ( int currNode = ORIGIN; currNode < this.numberOfNodes; currNode++ )
                if ( !visited[currNode] && this.adjMatrixUndirected[node][currNode] != 0 )
                {
                    visited[currNode] = true; // Mark neighbor as visited
                    nodeQueue.offer( currNode ); // Enqueue neighbor
                }
        }

        // Check for all nodes were visited
        for ( int i = 0; result && i < this.numberOfNodes; i++ )
            if ( !visited[i] )
                result = false;

        return result;
    }

    public boolean isGraphWeaklyConnected()
    {
        this.adjMatrixUndirected = new int[this.numberOfNodes][this.numberOfNodes];
        // TODO Lab14b #2.3 - Done
        //  builds adjMatrixUndirected

        // Traverse directed graph while searching for connections
        for ( int row = 0; row < this.numberOfNodes; row++ )
            for ( int col = 0; col < this.numberOfNodes; col++ )
                if ( this.adjMatrixDirected[row][col] != 0 )
                {
                    // Add directed path as undirected path
                    this.adjMatrixUndirected[row][col] = 1;
                    this.adjMatrixUndirected[col][row] = 1;
                }

        return isUndirectedGraphConnected();
    }

    public void displayGraph()
    {
        System.out.println( "\n***** GRAPH TO CHECK *****" );
        displayMatrix( this.adjMatrixDirected );
    }

    public void displayReachabilityMatrix()
    {
        System.out.println( "\n***** REACHABILITY MATRIX *****" );
        displayMatrix( this.reachabilityMatrix );
    }


    private void displayMatrix( int[][] matrix )
    {
        System.out.print( "     " );
        for ( int c = 0; c < this.numberOfNodes; c++ )
        {
            System.out.printf( "[%1$2d]", c );
        }
        System.out.println();
        for ( int r = 0; r < this.numberOfNodes; r++ )
        {
            System.out.printf( "[%1$2d]", r );
            for ( int c = 0; c < this.numberOfNodes; c++ )
            {
                System.out.printf( "%1$4d", matrix[r][c] );
            }
            System.out.println();
        }
    }

    public static void main( String[] args )
    {
        System.out.println( "\n*** Checking graphs' connectivity ***" );
        int[][] matrix = new int[5][5]; // setting the matrix to the matrix shown in the project description
        matrix[0][1] = 1;
        matrix[0][3] = 1;
        matrix[1][2] = 1;
        matrix[1][3] = 1;
        matrix[3][4] = 1;
        matrix[4][0] = 1;
        matrix[4][3] = 1;
        StrongWeakDisjoint graph = new StrongWeakDisjoint( 5, matrix );
        graph.displayGraph();
        graph.displayReachabilityMatrix();

        if ( graph.isGraphStronglyConnected() )
            System.out.println( "-->The graph is strongly connected.\n\n" );
        else if ( graph.isGraphWeaklyConnected() )
            System.out.println( "-->The graph is weakly connected.\n\n" );
        else
            System.out.println( "-->The graph is disjoint.\n\n" );

        matrix = new int[5][5];
        matrix[0][1] = 1;
        matrix[0][3] = 1;
        matrix[1][2] = 1;
        matrix[1][3] = 1;
        matrix[2][1] = 1;
        matrix[3][4] = 1;
        matrix[4][0] = 1;
        matrix[4][3] = 1;

        graph = new StrongWeakDisjoint( 5, matrix );
        graph.displayGraph();
        graph.displayReachabilityMatrix();
        if ( graph.isGraphStronglyConnected() )
            System.out.println( "-->The graph is strongly connected.\n\n" );
        else if ( graph.isGraphWeaklyConnected() )
            System.out.println( "-->The graph is weakly connected.\n\n" );
        else
            System.out.println( "-->The graph is disjoint.\n\n" );


        matrix = new int[5][5];
        matrix[0][1] = 1;
        matrix[0][3] = 1;
        matrix[1][3] = 1;
        matrix[3][4] = 1;
        matrix[4][0] = 1;
        matrix[4][3] = 1;

        graph = new StrongWeakDisjoint( 5, matrix );
        graph.displayGraph();
        graph.displayReachabilityMatrix();
        if ( graph.isGraphStronglyConnected() )
            System.out.println( "-->The graph is strongly connected.\n\n" );
        else if ( graph.isGraphWeaklyConnected() )
            System.out.println( "-->The graph is weakly connected.\n\n" );
        else
            System.out.println( "-->The graph is disjoint.\n\n" );
    } // end main
}
