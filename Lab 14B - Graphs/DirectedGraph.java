import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class that implements the ADT directed graph.
 *
 * @author Frank M. Carrano
 * @version 4/30/2019
 * @modifiedBy atb
 * @modifiedBy Ferid Ruano
 */
public class DirectedGraph< T > implements GraphInterface< T >
{
    private Map< T, VertexInterface< T > > vertices;
    private int edgeCount;

    public DirectedGraph()
    {
        this.vertices = new TreeMap<>();
        this.edgeCount = 0;
    } // end default constructor

    public boolean addVertex( T vertexLabel )
    {
        VertexInterface< T > isDuplicate = this.vertices.put( vertexLabel, new Vertex< T >( vertexLabel ) );
        return isDuplicate == null; // was add to dictionary successful?
    } // end addVertex

    public boolean addEdge( T begin, T end, double edgeWeight )
    {
        boolean result = false;

        VertexInterface< T > beginVertex = this.vertices.get( begin );
        VertexInterface< T > endVertex = this.vertices.get( end );

        if ( ( beginVertex != null ) && ( endVertex != null ) )
            result = beginVertex.connect( endVertex, edgeWeight );

        if ( result )
            this.edgeCount++;

        return result;
    } // end addEdge

    public boolean addEdge( T begin, T end )
    {
        return addEdge( begin, end, 0 );
    } // end addEdge

    public boolean hasEdge( T begin, T end )
    {
        boolean found = false;

        VertexInterface< T > beginVertex = this.vertices.get( begin );
        VertexInterface< T > endVertex = this.vertices.get( end );

        if ( ( beginVertex != null ) && ( endVertex != null ) )
        {
            Iterator< VertexInterface< T > > neighbors =
                    beginVertex.getNeighborIterator();
            while ( !found && neighbors.hasNext() )
            {
                VertexInterface< T > nextNeighbor = neighbors.next();
                if ( endVertex.equals( nextNeighbor ) )
                    found = true;
            }
        }

        return found;
    } // end hasEdge

    public boolean isEmpty()
    {
        return this.vertices.isEmpty();
    } // end isEmpty

    public void clear()
    {
        this.vertices.clear();
        this.edgeCount = 0;
    } // end clear

    public int getNumberOfVertices()
    {
        return this.vertices.size();
    } // end getNumberOfVertices

    public int getNumberOfEdges()
    {
        return this.edgeCount;
    } // end getNumberOfEdges

    protected void resetVertices()
    {
        Collection< VertexInterface< T > > values = this.vertices.values();
        Iterator< VertexInterface< T > > vertexIterator = values.iterator();
        while ( vertexIterator.hasNext() )
        {
            VertexInterface< T > nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost( 0 );
            nextVertex.setPredecessor( null );
        }
    } // end resetVertices

    public Queue< T > getBreadthFirstTraversal( T origin )
    {
        resetVertices();
        Queue< T > traversalOrder = new LinkedBlockingQueue<>();
        Queue< VertexInterface< T > > vertexQueue =
                new LinkedBlockingQueue<>();
        VertexInterface< T > originVertex = this.vertices.get( origin );
        originVertex.visit();
        traversalOrder.offer( origin );    // enqueue vertex label
        vertexQueue.offer( originVertex ); // enqueue vertex

        while ( !vertexQueue.isEmpty() )
        {
            VertexInterface< T > frontVertex = vertexQueue.poll();

            Iterator< VertexInterface< T > > neighbors = frontVertex.getNeighborIterator();
            while ( neighbors.hasNext() )
            {
                VertexInterface< T > nextNeighbor = neighbors.next();
                if ( !nextNeighbor.isVisited() )
                {
                    nextNeighbor.visit();
                    traversalOrder.offer( nextNeighbor.getLabel() );
                    vertexQueue.offer( nextNeighbor );
                }
            }
        }

        return traversalOrder;
    } // end getBreadthFirstTraversal

    public Queue< T > getDepthFirstTraversal( T origin )
    {
        //TODO Lab14a - Done
        resetVertices();

        Queue< T > traversalOrder = new LinkedBlockingQueue<>();
        Stack< VertexInterface< T > > vertexStack = new Stack<>();

        VertexInterface< T > originVertex = this.vertices.get( origin );
        originVertex.visit(); // Mark originVertex as visited
        traversalOrder.offer( origin ); // Enqueue vertex label
        vertexStack.push( originVertex ); // Push vertex

        // Traverse graph by checking neighbors using vertexStack
        while ( !vertexStack.isEmpty() )
        {
            VertexInterface< T > topVertex = vertexStack.peek();
            VertexInterface< T > nextNeighbor = topVertex.getUnvisitedNeighbor();

            // Check if unvisited neighbor
            if ( nextNeighbor != null )
            {
                nextNeighbor.visit(); // Mark nextNeighbor as visited
                traversalOrder.offer( nextNeighbor.getLabel() ); // Enqueue nextNeighbor label
                vertexStack.push( nextNeighbor ); // Push nextNeighbor
            }
            else
                vertexStack.pop();
        }

        return traversalOrder;
    } // end getDepthFirstTraversal

    public Stack< T > getTopologicalOrder()
    {
        resetVertices();

        Stack< T > vertexStack = new Stack<>();
        int numberOfVertices = getNumberOfVertices();
        for ( int counter = 1; counter <= numberOfVertices; counter++ )
        {
            VertexInterface< T > nextVertex = findTerminal();
            nextVertex.visit();
            vertexStack.push( nextVertex.getLabel() );
        }

        return vertexStack;
    } // end getTopologicalOrder

    /**
     * Precondition: path is an empty stack (NOT null)
     */
    public int getShortestPath( T begin, T end, Stack< T > path )
    {
        resetVertices();
        boolean done = false;
        Queue< VertexInterface< T > > vertexQueue =
                new LinkedBlockingQueue<>();
        VertexInterface< T > originVertex = this.vertices.get( begin );
        VertexInterface< T > endVertex = this.vertices.get( end );

        originVertex.visit();
        // Assertion: resetVertices() has executed setCost(0)
        // and setPredecessor(null) for originVertex

        vertexQueue.offer( originVertex );

        while ( !done && !vertexQueue.isEmpty() )
        {
            VertexInterface< T > frontVertex = vertexQueue.poll();

            Iterator< VertexInterface< T > > neighbors =
                    frontVertex.getNeighborIterator();
            while ( !done && neighbors.hasNext() )
            {
                VertexInterface< T > nextNeighbor = neighbors.next();

                if ( !nextNeighbor.isVisited() )
                {
                    nextNeighbor.visit();
                    nextNeighbor.setCost( 1 + frontVertex.getCost() );
                    nextNeighbor.setPredecessor( frontVertex );
                    vertexQueue.offer( nextNeighbor );
                }

                if ( nextNeighbor.equals( endVertex ) )
                    done = true;
            }
        }

        // traversal ends; construct shortest path
        int pathLength = ( int ) endVertex.getCost();
        path.push( endVertex.getLabel() );

        VertexInterface< T > vertex = endVertex;
        while ( vertex.hasPredecessor() )
        {
            vertex = vertex.getPredecessor();
            path.push( vertex.getLabel() );
        }

        return pathLength;
    } // end getShortestPath

    /**
     * Precondition: path is an empty stack (NOT null)
     */
    public double getCheapestPath( T begin, T end, Stack< T > path )
    {
        //TODO Lab14a - Done
        resetVertices();
        boolean done = false;

        // use EntryPQ instead of Vertex because multiple entries contain
        // the same vertex but different costs - cost of path to vertex is EntryPQ's priority value
        PriorityQueue< EntryPQ > priorityQueue = new PriorityQueue<>();

        VertexInterface< T > originVertex = this.vertices.get( begin );
        VertexInterface< T > endVertex = this.vertices.get( end );

        priorityQueue.add( new EntryPQ( originVertex, 0, null ) );

        while ( !done && !priorityQueue.isEmpty() )
        {
            EntryPQ frontEntry = priorityQueue.poll();
            VertexInterface< T > frontVertex = frontEntry.vertex; // Direct access to vertex

            if ( !frontVertex.isVisited() )
            {
                // Set frontVertex fields
                frontVertex.visit();
                frontVertex.setCost( frontEntry.getCost() );
                frontVertex.setPredecessor( frontEntry.getPredecessor() );

                // Check if frontVertex is the endVertex
                if ( frontVertex.equals( endVertex ) )
                {
                    done = true;
                }
                else
                {
                    Iterator< VertexInterface< T > > neighbors = frontVertex.getNeighborIterator();
                    Iterator< Double > weightNeighbors = frontVertex.getWeightIterator();

                    // Traverse directed graph
                    while ( neighbors.hasNext() )
                    {
                        VertexInterface< T > nextNeighbor = neighbors.next();
                        Double weightOfEdgeToNeighbor = weightNeighbors.next();

                        // Check if nextNeighbor is unvisited
                        if ( !nextNeighbor.isVisited() )
                        {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
                            priorityQueue.add( new EntryPQ( nextNeighbor, nextCost, frontVertex ) );
                        }
                    }
                }
            }
        }

        // Traversal ends; construct cheapest path
        double pathCost = endVertex.getCost();
        path.push( endVertex.getLabel() );

        VertexInterface< T > vertex = endVertex;
        while ( vertex.hasPredecessor() )
        {
            vertex = vertex.getPredecessor();
            path.push( vertex.getLabel() );
        }

        return pathCost;
    } // end getCheapestPath

    protected VertexInterface< T > findTerminal()
    {
        boolean found = false;
        VertexInterface< T > result = null;
        Collection< VertexInterface< T > > values = this.vertices.values();
        Iterator< VertexInterface< T > > vertexIterator = values.iterator();

        while ( !found && vertexIterator.hasNext() )
        {
            VertexInterface< T > nextVertex = vertexIterator.next();

            // if nextVertex is unvisited AND has only visited neighbors)
            if ( !nextVertex.isVisited() )
            {
                if ( nextVertex.getUnvisitedNeighbor() == null )
                {
                    found = true;
                    result = nextVertex;
                }
            }
        }

        return result;
    } // end findTerminal

    // Used for testing
    public void display()
    {
        System.out.println( "Graph has " + getNumberOfVertices() + " vertices and " +
                                    getNumberOfEdges() + " edges." );

        System.out.println( "\nEdges exist from the first vertex in each line to the other vertices in the line." );
        System.out.println( "(Edge weights are given; weights are zero for unweighted graphs):\n" );
        Collection< VertexInterface< T > > values = this.vertices.values();
        Iterator< VertexInterface< T > > vertexIterator = values.iterator();
        while ( vertexIterator.hasNext() )
        {
            ( ( Vertex< T > ) ( vertexIterator.next() ) ).display();
        }
    } // end display

    private class EntryPQ implements Comparable< EntryPQ >
    {
        private VertexInterface< T > vertex;
        private VertexInterface< T > previousVertex;
        private double cost; // cost to nextVertex

        private EntryPQ( VertexInterface< T > vertex, double cost, VertexInterface< T > previousVertex )
        {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        } // end constructor

        public VertexInterface< T > getVertex()
        {
            return this.vertex;
        } // end getVertex

        public VertexInterface< T > getPredecessor()
        {
            return this.previousVertex;
        } // end getPredecessor

        public double getCost()
        {
            return this.cost;
        } // end getCost

        public int compareTo( EntryPQ otherEntry )
        {
            return ( int ) Math.signum( this.cost - otherEntry.cost );
        } // end compareTo

        public String toString()
        {
            return this.vertex.toString() + " " + this.cost;
        } // end toString
    } // end EntryPQ
}