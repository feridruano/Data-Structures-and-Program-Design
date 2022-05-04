import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class that implements a solution
 * utilizing greedy algorithm for graph coloring
 * The undirected graph is represented by adjacency list
 *
 * @author Anna Bieszczad
 * @version 4/30/2019
 * @modified Ferid Ruano
 */

class HowManyQuestions
{
    private int numberOfVertices;
    private LinkedList< Integer > adjacencyList[]; //graph represented as Adjacency List

    /**
     * Takes the input matrix and creates the graph represented as adjacency list
     *
     * @param graph two dimensional array of booleans, true indicates
     *              that the corresponding vertexes are neighbors
     */
    public HowManyQuestions( boolean[][] graph )
    {
        //TODO Lab14b #3.1 - Done

        this.numberOfVertices = graph.length;
        // Initialize adjacencyList array - Objects should be null at initialization
        this.adjacencyList = new LinkedList[graph.length];

        // Initialize adjacencyList array objects as individual LinkedList
        for ( int i = 0; i < graph.length; i++ )
            this.adjacencyList[i] = new LinkedList<>();

        // Create adjacencyLists from neighboring vertices
        for ( int row = 0; row < graph.length; row++ )
            for ( int col = 0; col < graph.length; col++ )
                if ( graph[row][col] ) // Check for neighboring vertices
                    this.adjacencyList[row].add( col );
    }

    /**
     * Traverses the adjacencyList and displays all neighbors of each vertex
     */
    public void displayNeighbors()
    {
        //TODO Lab14b #3.2 - Done

        System.out.printf( "The graph has %d vertexes with the following neighbors:\n", this.numberOfVertices );
        for ( int vertex = 0; vertex < this.adjacencyList.length; vertex++ )
        {
            System.out.printf( "Vertex %d has neighbors: ", vertex );
            this.adjacencyList[vertex].forEach( N -> System.out.print( N + " " ) );
            System.out.println();
        }
        System.out.println( "=================\n" );
    }

    /**
     * Assigns questions to all vertices and
     * prints the assignment of questions
     * If the solution is not possible the method throws an Exception
     * with appropriate message. The possible exception is handled by this method as well
     */
    public boolean greedyQuestionChooser( int numberOfQuestions )
    {
        boolean solved = false;

        int assignedQuesions[] = new int[numberOfVertices];
        // Initializes all vertices as unassigned -1
        Arrays.fill( assignedQuesions, -1 );

        // A temporary array to store the available questions.
        // Initially, all questions are not taken
        boolean taken[] = new boolean[numberOfQuestions];

        // Assign the first question to first vertex - first question is 0
        assignedQuesions[0] = 0;
        try
        {
            //TODO Lab14b #3.3 - Done

            // For remaining V-1 vertices
            for ( int vertex = 1; vertex < this.numberOfVertices; vertex++ )
            {
                // Check vertex's adjacent vertices for used questions
                this.adjacencyList[vertex].forEach( V -> {
                    if ( assignedQuesions[V] != -1 )
                        taken[assignedQuesions[V]] = true; // Set question as taken
                } );

                boolean assigned = false;
                // Assign vertex with lowest numbered question not previously used
                for ( int question = 0; !assigned && question < taken.length; question++ )
                    if ( !taken[question] )
                    {
                        assignedQuesions[vertex] = question;
                        assigned = true; // Terminate loop
                    }

                // Check that no questions were assigned
                if ( !assigned )
                    throw new Exception( "--> The solution does not exist - not enough choices" );

                // Reset temporary array to store the available questions
                Arrays.fill( taken, false );
            }
            solved = true; // No exception thrown, therefore, solved

            System.out.printf( "--> The solution exists with %d questions:\n", numberOfQuestions );
            for ( int student = 0; student < this.numberOfVertices; student++ )
                System.out.printf( "Student %d --->  Question %d\n", student, assignedQuesions[student] );
            System.out.println();
        }
        catch ( Exception e )
        {
            System.out.println( e.getMessage() );
            System.out.println();
        }
        return solved;
    }

    // Driver method
    public static void main( String args[] )
    {
        HashMap< String, HowManyQuestions > graphsToCheck = new HashMap<>();

        graphsToCheck.put( "g1",
                           new HowManyQuestions( new boolean[][]{
                                   { false, true, true, false, false },
                                   { true, false, true, true, false },
                                   { true, true, false, true, false },
                                   { false, true, true, false, true },
                                   { false, false, false, true, false } } ) );
        graphsToCheck.put( "g2",
                           new HowManyQuestions( new boolean[][]{
                                   { false, true, true, true, false },
                                   { true, false, true, true, true },
                                   { true, true, false, false, true },
                                   { true, true, false, false, true },
                                   { false, true, true, true, false } } ) );
        graphsToCheck.put( "g3",
                           new HowManyQuestions( new boolean[][]{
                                   { false, true, true, true, false },
                                   { true, false, true, true, true },
                                   { true, true, false, true, false },
                                   { true, true, true, false, true },
                                   { false, true, false, true, false } } ) );

        // circular
        boolean[][] g4 = new boolean[24][24];
        for ( int i = 0; i < 24; i++ )
        {
            g4[i][( i + 1 ) % 24] = true;
            g4[( i + 1 ) % 24][i] = true;
        }
        graphsToCheck.put( "g4", new HowManyQuestions( g4 ) );

        // SIE 1232
        graphsToCheck.put( "g5",
                           new HowManyQuestions( new boolean[][]{
                                   { false, true, false, false, false, false, false, false, false, false, true, true,
                                     false, false, false, false, false, false, false, false, false, false, false,
                                     false },
                                   { true, false, true, false, false, false, false, false, false, true, true, true,
                                     false, false, false, false, false, false, false, false, false, false, false,
                                     false },
                                   { false, true, false, true, false, false, false, false, true, true, true, false,
                                     false, false, false, false, false, false, false, false, false, false, false,
                                     false },
                                   { false, false, true, false, true, false, false, true, true, true, false, false,
                                     false, false, false, false, false, false, false, false, false, false, false,
                                     false },
                                   { false, false, false, true, false, true, true, true, true, false, false, false,
                                     false, false, false, false, false, false, false, false, false, false, false,
                                     false },
                                   { false, false, false, false, true, false, true, true, false, false, false, false,
                                     false, false, false, false, false, false, false, false, false, false, false,
                                     false },
                                   { false, false, false, false, true, true, false, true, false, false, false, false,
                                     false, false, false, false, true, true, false, false, false, false, false, false },
                                   { false, false, false, true, true, true, true, false, true, false, false, false,
                                     false, false, false, true, true, true, false, false, false, false, false, false },
                                   { false, false, true, true, true, false, false, true, false, true, false, false,
                                     false, false, false, true, true, true, false, false, false, false, false, false },
                                   { false, true, true, true, false, false, false, false, true, false, true, false,
                                     false, true, true, true, false, false, false, false, false, false, false, false },
                                   { true, true, true, false, false, false, false, false, false, true, false, true,
                                     true, true, true, false, false, false, false, false, false, false, false, false },
                                   { true, true, false, false, false, false, false, false, false, false, true, false,
                                     true, true, false, false, false, false, false, false, false, false, false, false },
                                   { false, false, false, false, false, false, false, false, false, false, true, true,
                                     false, true, false, false, false, false, false, false, false, false, true, true },
                                   { false, false, false, false, false, false, false, false, false, true, true, true,
                                     true, false, true, false, false, false, false, false, false, true, true, true },
                                   { false, false, false, false, false, false, false, false, true, true, true, false,
                                     false, true, false, true, false, false, false, false, true, true, true, false },
                                   { false, false, false, false, false, false, false, true, true, true, false, false,
                                     false, false, true, false, true, false, false, true, true, true, false, false },
                                   { false, false, false, false, false, false, true, true, true, false, false, false,
                                     false, false, false, true, false, true, true, true, true, false, false, false },
                                   { false, false, false, false, false, false, true, true, false, false, false, false,
                                     false, false, false, false, true, false, true, true, false, false, false, false },
                                   { false, false, false, false, false, false, false, false, false, false, false, false,
                                     false, false, false, false, true, true, false, true, false, false, false, false },
                                   { false, false, false, false, false, false, false, false, false, false, false, false,
                                     false, false, false, true, true, true, true, false, true, false, false, false },
                                   { false, false, false, false, false, false, false, false, false, false, false, false,
                                     false, false, true, true, true, false, false, true, false, true, false, false },
                                   { false, false, false, false, false, false, false, false, false, false, false, false,
                                     false, true, true, true, false, false, false, false, true, false, true, false },
                                   { false, false, false, false, false, false, false, false, false, false, false, false,
                                     true, true, true, false, false, false, false, false, false, true, false, true },
                                   { false, false, false, false, false, false, false, false, false, false, false, false,
                                     true, true, false, false, false, false, false, false, false, false, true,
                                     false } } ) );


        final int NUMBER_OF_GRAPHS = 5;
        for ( int i = 1; i <= NUMBER_OF_GRAPHS; i++ )
        {
            System.out.println( "Created graph " + i + ":" );
            String key = "g" + i;
            graphsToCheck.get( key ).displayNeighbors();
            System.out.println();
        }

        int numberOfQuestions = 1;
        boolean[] solutionFound = new boolean[NUMBER_OF_GRAPHS];
        boolean done = false;
        while ( !done )
        {
            numberOfQuestions++;
            System.out.println( "****** Checking if " + numberOfQuestions + " questions are enough ******" );
            done = true;
            for ( int i = 1; i <= NUMBER_OF_GRAPHS; i++ )
            {
                if ( !solutionFound[i - 1] )
                {
                    System.out.println( "   *** Checking graph " + i + " ***" );
                    String key = "g" + i;
                    if ( graphsToCheck.get( key ).greedyQuestionChooser( numberOfQuestions ) )
                        solutionFound[i - 1] = true;
                    else
                        done = false;
                    System.out.println();
                }
            }
        }

        System.out.println( "***** DONE - all graphs were assigned solutions *****" );
    }
}