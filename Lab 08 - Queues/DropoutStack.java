import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * A class of stacks whose entries are stored in a deque.
 *
 * @author Ferid Ruano
 * @version 3/12/2019
 */

public class DropoutStack< T >
{
    // TODO Project 1 - Done

    // Instance Variables
    private ArrayDeque< T > stack;
    private final int capacity;
    private final static int DEFAULT_CAPACITY = 20;

    /**
     * Default constructor
     */
    public DropoutStack()
    {
        this( DEFAULT_CAPACITY ); // Secondary constructor call
    }

    /**
     * Secondary constructor
     *
     * @param capacity The size of the ArrayDeque list
     */
    public DropoutStack( int capacity )
    {
        this.capacity = capacity; // Set capacity field
        stack = new ArrayDeque<>( capacity ); // Initialize ArrayDeque with capacity
    }

    /**
     * Pushes an element onto the stack represented by this deque.  In other
     * words, inserts the element at the front of this deque.
     *
     * @param entry the element to push
     * @throws NullPointerException if the specified element is null
     */
    public void push( T entry )
    {
        // Check if stack is full
        if ( stack.size() == capacity )
            stack.removeLast();
        stack.push( entry ); // Method call throws exception if entry is null
    }

    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque, or returns null if this deque is empty.
     *
     * @return the head of the queue represented by this deque, or
     * null if this deque is empty
     */
    public T peek()
    {
        return stack.peek();
    }

    /**
     * Pops an element from the stack represented by this deque.  In other
     * words, removes and returns the first element of this deque.
     *
     * @return the element at the front of this deque (which is the top
     * of the stack represented by this deque)
     */
    public T pop()
    {
        T result = null;
        if ( !isEmpty() )
            result = stack.pop();
        return result;
    }

    /**
     * Returns true if this deque contains no elements.
     *
     * @return True if this deque contains no elements
     */
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    /**
     * Removes all of the elements from this deque.
     * The deque will be empty after this call returns.
     */
    public void clear()
    {
        stack.clear();
    }

    /**
     * METHOD display implemented for debugging purposes
     */
    public void display()
    {
        if ( isEmpty() )
            System.out.println( "The stack is empty" );
        else
            System.out.println( Arrays.toString( this.stack.toArray() ) );
    }

    public static void main( String args[] )
    {
        System.out.println( "**************  TESTING DROPOUT STACK  **************\n" );
        DropoutStack< Integer > dropoutStack = new DropoutStack< Integer >();

        System.out.println( "---->  Adding 20 items to empty stack of capacity of 20" );
        for ( int i = 0; i < 20; i++ )
        {
            dropoutStack.push( i );
        }
        System.out.println( "---->  The content of the stack is:" );
        dropoutStack.display();
        System.out.println( "----> The top of the stack is: " + dropoutStack.peek() );
        System.out.println( "\n----> Adding 5 more items to full stack" );
        for ( int i = 20; i < 25; i++ )
        {
            System.out.println( "push " + i );
            dropoutStack.push( i );
        }

        System.out.println( "---->  The content of the stack is:" );
        dropoutStack.display();
        System.out.println( "----> The top of the stack is: " + dropoutStack.peek() );

        System.out.println( "\n---->  Removing all elements from the stack:" );
        while ( !dropoutStack.isEmpty() )
        {
            System.out.println( "----> pop " + dropoutStack.pop() );
        }

        dropoutStack.display();
        System.out.println( "----> The top of the stack is: " + dropoutStack.peek() );

        System.out.println( "\n----> Trying to pop from the empty stack" );
        System.out.println( "----> Got back " + dropoutStack.pop() );

        System.out.println( "\n----> Trying to peek at the top of the empty stack" );
        System.out.println( "----> Got back " + dropoutStack.peek() );

        System.out.println( "\n----> Adding 22 items to empty stack of capacity of 20" );
        for ( int i = 0; i < 22; i++ )
        {
            dropoutStack.push( i );
        }
        System.out.println( "---->  The content of the stack is:" );
        dropoutStack.display();

        System.out.println( "\n---->  Clearing the stack with the clear method" );
        dropoutStack.clear();
        System.out.println( "---->  The content of the stack is:" );
        dropoutStack.display();
    }
}