import java.util.*;

/**
 * A class that implements the ADT directed graph.
 *
 * @author Ferid Ruano
 * @version 4/30/2019
 */

public class UndirectedGraph< T > extends DirectedGraph< T > implements GraphInterface< T >,
        java.io.Serializable, ConnectedGraphInterface< T >
{

    /**
     * Default Constructor
     * Calls super constructor
     */
    UndirectedGraph()
    {
        super();
    }

    /**
     * Add edges going from begin to end and from end to begin with a weight.
     *
     * @param begin      An object that labels the origin vertex of the edge.
     * @param end        An object, distinct from begin, that labels the end
     *                   vertex of the edge.
     * @param edgeWeight The real value of the edge's weight.
     * @return true if the edge is added, or false if not.
     */
    public boolean addEdge( T begin, T end, double edgeWeight )
    {
        boolean result;

        // Add edges
        result = super.addEdge( begin, end, edgeWeight ); // Begin to end

        if ( result )
            result = super.addEdge( end, begin, edgeWeight ); // End to begin

        return result;
    }

    /**
     * Add edges going from begin to end and from end to begin with default weight.
     *
     * @param begin An object that labels the origin vertex of the edge.
     * @param end   An object, distinct from begin, that labels the end
     *              vertex of the edge.
     * @return true if the edge is added, or false if not.
     */
    public boolean addEdge( T begin, T end )
    {
        return addEdge( begin, end, 0 );
    }

    /**
     * Get the number of edges in an undirected graph. Must take into account
     * the handshaking lemma where the number of edges equals edges / 2.
     *
     * @return Number of edges.
     */
    public int getNumberOfEdges()
    {
        int handshakingLemma = 2;
        return super.getNumberOfEdges() / handshakingLemma;
    }

    /**
     * Get the topological order. However, unsupported in undirected graphs.
     *
     * @return UnsupportedOperationException
     */
    public Stack< T > getTopologicalOrder()
    {
        throw new UnsupportedOperationException( "Topological sort illegal in an undirected graph." );
    }

    /**
     * Sees whether this graph is connected.
     *
     * @param origin any vertex
     * @return true if the graph is connected
     */
    public boolean isConnected( T origin )
    {
        int handshakingLemma = 2;
        Queue< T > traversalOrder = super.getBreadthFirstTraversal( origin );
        return ( super.getNumberOfEdges() / handshakingLemma ) == traversalOrder.size();
    }
}
