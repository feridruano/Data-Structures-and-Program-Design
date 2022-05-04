/**
 * A class that implements a cipher with repeating key algorithm using a Queue Interface.
 *
 * @author Ferid Ruano
 * @version 3/12/2019
 */

public class CircularArrayQueue< T > implements QueueInterface< T >
{
    private T[] queue; // Circular array of queue entries and one unused location
    private int frontIndex; // Index of front entry
    private int backIndex;  // Index of back entry
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 3;
    private static final int MAX_CAPACITY = 10000;

    /**
     * Default constructor
     */
    public CircularArrayQueue()
    {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings( "unchecked" )
        T[] tempQueue = ( T[] ) new Object[DEFAULT_CAPACITY + 1];
        this.queue = tempQueue;
        this.frontIndex = 0;
        this.backIndex = DEFAULT_CAPACITY;
        this.initialized = true;
    } // end constructor

    /**
     * Secondary constructor
     *
     * @param initialContent Generic array with objects.
     */
    public CircularArrayQueue( T[] initialContent )
    {
        // TODO Project 2A - Done
        int initialCapacity = initialContent.length;
        checkCapacity( initialCapacity );
        @SuppressWarnings( "unchecked" )
        T[] tempQueue = ( T[] ) new Object[initialCapacity + 1]; // Add unused location
        System.arraycopy( initialContent, 0, tempQueue, 0, initialCapacity );
        this.queue = tempQueue;
        this.frontIndex = 0;
        this.backIndex = initialCapacity - 1;
        this.initialized = true;
    } // end constructor

    /**
     * Add an object to the back of the queue.
     *
     * @param newEntry An object to be added.
     */
    public void enqueue( T newEntry )
    {
        // TODO Project 2A - Done
        checkInitialization();
        ensureCapacity();
        this.backIndex = ( this.backIndex + 1 ) % this.queue.length; // Set new backIndex
        this.queue[this.backIndex] = newEntry; // Add object to queue at backIndex
    } // end enqueue

    /**
     * Returns the front object of queue.
     *
     * @return Front object of queue.
     * @throws EmptyQueueException Indicate that a queue is empty.
     */
    public T getFront() throws EmptyQueueException
    {
        // TODO Project 2A - Done
        checkInitialization();
        if ( isEmpty() )
            throw new EmptyQueueException(); // Throw exception if queue is empty
        return this.queue[this.frontIndex]; // Return front object of the queue
    } // end getFront

    /**
     * Return and remove front object of queue.
     *
     * @return Front object of queue.
     * @throws EmptyQueueException Indicates that a queue is empty.
     */
    public T dequeue() throws EmptyQueueException
    {
        // TODO Project 2A - Done
        checkInitialization();
        if ( isEmpty() )
            throw new EmptyQueueException(); // Throw exception if queue is empty
        T result = this.queue[this.frontIndex]; // Return front object
        this.queue[this.frontIndex] = null; // Set object location to null
        this.frontIndex = ( this.frontIndex + 1 ) % this.queue.length; // Set new frontIndex
        return result;
    } // end dequeue

    /**
     * Check if queue is empty.
     *
     * @return True if queue is empty, otherwise false.
     */
    public boolean isEmpty()
    {
        // TODO Project 2A - Done
        return this.frontIndex == ( ( this.backIndex + 1 ) % this.queue.length );
    } // end isEmpty

    /**
     * Clear all objects from queue.
     */
    public void clear()
    {
        // TODO Project 2A - Done
        checkInitialization();
        while ( !isEmpty() )
        {
            this.queue[this.frontIndex] = null; // Set object location to null
            this.frontIndex = ( this.frontIndex + 1 ) % this.queue.length; // Set new frontIndex
        }
    } // end clear


    // Throws an exception if this object is not initialized.
    private void checkInitialization()
    {
        if ( !this.initialized )
            throw new SecurityException( "CircularArrayQueue object is not initialized properly." );
    } // end checkInitialization

    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity( int capacity )
    {
        if ( capacity > MAX_CAPACITY )
            throw new IllegalStateException( "Attempt to create a queue " +
                                             "whose capacity exceeds " +
                                             "allowed maximum." );
    } // end checkCapacity

    // Doubles the size of the array queue if it is full.
    // Precondition: checkInitialization has been called.
    private void ensureCapacity()
    {
        // TODO Project 2A - Done
        if ( this.frontIndex == ( ( this.backIndex + 2 ) % this.queue.length ) )
        {
            T[] oldQueue = this.queue;
            int oldSize = oldQueue.length;
            int newSize = 2 * oldSize;
            checkCapacity( newSize - 1 );
            // Copy entries from old queue and reset indexes
            @SuppressWarnings( "unchecked" )
            T[] tempQueue = ( T[] ) new Object[newSize];
            this.queue = tempQueue;
            for ( int index = 0; index < oldSize - 1; index++ )
            {
                queue[index] = oldQueue[this.frontIndex];
                this.frontIndex = ( this.frontIndex + 1 ) % oldSize;
            }
            frontIndex = 0;
            backIndex = oldSize - 2;
        }
    } // end ensureCapacity
}
