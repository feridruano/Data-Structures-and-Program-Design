import java.util.Arrays;

/**
 * A class that implements a bag of objects by using an array.
 * The bag is never full.
 *
 * @author Ferid Ruano
 * @version 2/19/2019
 */
public class ResizableArrayBag< T extends Comparable< ? super T > > implements BagInterface< T >
{
    private T[] bag; // Cannot be final due to doubling
    private int numberOfEntries;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25; // Initial capacity of bag
    private static final int MAX_CAPACITY = 10000;

    /**
     * Creates an empty bag whose initial capacity is 25.
     */
    public ResizableArrayBag()
    {
        this( DEFAULT_CAPACITY );
    } // end default constructor

    /**
     * Creates an empty bag having a given initial capacity.
     *
     * @param initialCapacity The integer capacity desired.
     */
    public ResizableArrayBag( int initialCapacity )
    {
        checkCapacity( initialCapacity );

// The cast is safe because the new array contains null entries
        @SuppressWarnings( "unchecked" )
        T[] tempBag = ( T[] ) new Comparable< ? >[initialCapacity]; // Unchecked cast
        this.bag = tempBag;
        this.numberOfEntries = 0;
        this.initialized = true;
    } // end constructor

    /**
     * Creates a bag containing given entries.
     *
     * @param contents         An array of objects.
     * @param numberOfElements - the number of entries we want to copy starting at index 0
     */
    public ResizableArrayBag( T[] contents, int numberOfElements )
    {
        checkCapacity( numberOfElements );
        this.bag = Arrays.copyOf( contents, numberOfElements );
        this.numberOfEntries = numberOfElements;
        this.initialized = true;
    } // end constructor

    /**
     * Adds a new entry to this bag.
     *
     * @param newEntry The object to be added as a new entry.
     * @return True.
     */
    public boolean add( T newEntry )
    {
        checkInitialization();
        if ( isArrayFull() )
        {
            doubleCapacity();
        } // end if

        this.bag[this.numberOfEntries] = newEntry;
        this.numberOfEntries++;

        return true;
    } // end add

    /**
     * Retrieves all entries that are in this bag.
     *
     * @return A newly allocated array of all the entries in this bag.
     */
    public T[] toArray()
    {
        checkInitialization();

// The cast is safe because the new array contains null entries.
        @SuppressWarnings( "unchecked" )
        T[] result = ( T[] ) new Comparable< ? >[this.numberOfEntries]; // Unchecked cast
        for ( int index = 0; index < this.numberOfEntries; index++ )
        {
            result[index] = this.bag[index];
        } // end for

        return result;
    } // end toArray

    /**
     * Sees whether this bag is empty.
     *
     * @return True if this bag is empty, or false if not.
     */
    public boolean isEmpty()
    {
        return this.numberOfEntries == 0;
    } // end isEmpty

    /**
     * Gets the current number of entries in this bag.
     *
     * @return The integer number of entries currently in this bag.
     */
    public int getCurrentSize()
    {
        return this.numberOfEntries;
    } // end getCurrentSize

    /**
     * Counts the number of times a given entry appears in this bag.
     *
     * @param anEntry The entry to be counted.
     * @return The number of times anEntry appears in this ba.
     */
//    public int getFrequencyOf(T anEntry)
//    {
//        checkInitialization();
//        int counter = 0;
//
//        for (int index = 0; index < this.numberOfEntries; index++)
//        {
//            if (anEntry.equals(this.bag[index]))
//            {
//                counter++;
//            } // end if
//        } // end for
//
//        return counter;
//    } // end getFrequencyOf
    public int getFrequencyOf( T anEntry )
    {
        return getFrequencyOf( anEntry, this.numberOfEntries - 1 );
    }

    // indexToExamine starts at the last occupied index in the array
    private int getFrequencyOf( T anEntry, int indexToExamine )
    {
        // TODO #1a - Done
        checkInitialization();
        // Base case
        if ( indexToExamine == -1 )
            return 0;
        else
        {
            // Comparing elements
            if ( anEntry.equals( this.bag[indexToExamine] ) )
            {
                // Equivalent of incrementing counter by 1
                return 1 + getFrequencyOf( anEntry, indexToExamine - 1 );
            }
            // Else don't increment counter by 1 and decrement indexToExamine by 1
            return getFrequencyOf( anEntry, indexToExamine - 1 );
        }
    }

    /**
     * Tests whether this bag contains a given entry.
     *
     * @param anEntry The entry to locate.
     * @return True if this bag contains anEntry, or false otherwise.
     */
    public boolean contains( T anEntry )
    {
        checkInitialization();
        return getIndexOf( anEntry ) > -1; // or >= 0
    } // end contains

    // Locates a given entry within the array bag.
// Returns the index of the entry, if located,
// or -1 otherwise.
// Precondition: checkInitialization has been called.
    private int getIndexOf( T anEntry )
    {
        int where = -1;
        boolean stillLooking = true;

        for ( int index = 0; stillLooking && ( index < this.numberOfEntries ); index++ )
        {
            if ( anEntry.equals( this.bag[index] ) )
            {
                stillLooking = false;
                where = index;
            } // end if
        } // end for

        return where;
    } // end getIndexOf

    /**
     * Removes all entries from this bag.
     */
    public void clear()
    {
        while ( !isEmpty() )
            remove();
    } // end clear

    /**
     * Removes one unspecified entry from this bag, if possible.
     *
     * @return Either the removed entry, if the removal
     * was successful, or null.
     */
    public T remove()
    {
        checkInitialization();
        T result = removeEntry( this.numberOfEntries - 1 );
        return result;
    } // end remove

    /**
     * Removes one occurrence of a given entry from this bag.
     *
     * @param anEntry The entry to be removed.
     * @return True if the removal was successful, or false if not.
     */
    public boolean remove( T anEntry )
    {
        checkInitialization();
        int index = getIndexOf( anEntry );
        T result = removeEntry( index );
        return anEntry.equals( result );
    } // end remove

    /**
     * Removes one occurrence of a given entry from this bag.
     *
     * @param anElement The entry to be removed.
     * @return True if the removal was successful, or false if not.
     */
    public boolean removeElement( T anElement )
    {
        checkInitialization();
        int index = getIndexOf( anElement );
        T result = removeEntry( index );
        return anElement.equals( result );
    } // end remove

    // Removes and returns the entry at a given index within the array.
// If no such entry exists, returns null.
// Precondition: 0 <= givenIndex < numberOfEntries.
// Precondition: checkInitialization has been called.
    private T removeEntry( int givenIndex )
    {
        T result = null;

        if ( !isEmpty() && ( givenIndex >= 0 ) )
        {
            result = this.bag[givenIndex];          // Entry to remove
            this.numberOfEntries--;
            this.bag[givenIndex] = this.bag[this.numberOfEntries];  // Replace entry to remove with last entry
            this.bag[this.numberOfEntries] = null;             // Remove reference to last entry
        } // end if

        return result;
    } // end removeEntry

    // Returns true if the array bag is full, or false if not.
    private boolean isArrayFull()
    {
        return this.numberOfEntries >= this.bag.length;
    } // end isArrayFull

    // Doubles the size of the array bag.
// Precondition: checkInitialization has been called.
    private void doubleCapacity()
    {
        int newLength = 2 * this.bag.length;
        checkCapacity( newLength );
        this.bag = Arrays.copyOf( this.bag, newLength );
    } // end doubleCapacity

    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity( int capacity )
    {
        if ( capacity > MAX_CAPACITY )
            throw new IllegalStateException( "Attempt to create a bag whose capacity exceeds " +
                                             "allowed maximum of " + MAX_CAPACITY );
    } // end checkCapacity

    // Throws an exception if receiving object is not initialized.
    private void checkInitialization()
    {
        if ( !this.initialized )
            throw new SecurityException( "Uninitialized object used " +
                                         "to call an ArrayBag method." );
    } // end checkInitialization

    /**
     * Displays all the elements in the bag
     */
    public void display()
    {
        System.out.print( "There are " + this.numberOfEntries + " element(s): " );
        for ( int index = 0; index < this.numberOfEntries; index++ )
        {
            System.out.print( this.bag[index] + " " );
        } // end for
        System.out.println();
    } // end display


    /**
     * isBagSorted - a method that checks if the elements in this.bag are sorted
     */
    public boolean isBagSorted()
    {
        return isBagSorted( this.numberOfEntries - 1 );
    }

    /**
     * isBagSorted - a private helper recursive method that checks if the elements in this.bag are sorted
     *
     * @param index index of the current element in this.bag
     */
    private boolean isBagSorted( int index )
    {
        // TODO #1b - Done
        checkInitialization();
        // Base case
        if ( index <= 0 )
            return true;
        else
        {
            // Compare elements
            if ( this.bag[index].compareTo( this.bag[index - 1] ) < 0 )
                return false; // Elements not in ascending order
        }
        return isBagSorted( index - 1 ); // Recursive call
    }

    public static void main( String[] args )
    {
        ResizableArrayBag< String > bag = new ResizableArrayBag<>();

        bag.add( "Y" );
        bag.add( "A" );
        bag.add( "B" );
        bag.add( "A" );
        bag.add( "C" );
        bag.add( "B" );
        bag.add( "A" );
        bag.add( "B" );
        bag.add( "A" );
        bag.add( "X" );
        bag.add( "Z" );

        System.out.println( "\n***Testing getFrequencyOf method***" );
        System.out.println( "Bag contains:" );
        bag.display();
        System.out.println( "There are " + bag.getFrequencyOf( "A" ) + " of \"A\"" );
        System.out.println( "There are " + bag.getFrequencyOf( "B" ) + " of \"B\"" );
        System.out.println( "There are " + bag.getFrequencyOf( "C" ) + " of \"C\"" );
        System.out.println( "There are " + bag.getFrequencyOf( "K" ) + " of \"K\"" );
        System.out.println( "There are " + bag.getFrequencyOf( "X" ) + " of \"X\"" );
        System.out.println( "There are " + bag.getFrequencyOf( "Y" ) + " of \"Y\"" );
        System.out.println( "There are " + bag.getFrequencyOf( "Z" ) + " of \"Z\"" );

        System.out.println( "\n***Testing isBagSorted method***" );
        bag.display();
        System.out.println( "Bag is " + ( bag.isBagSorted() ? "" : "NOT" ) + " sorted" );
        System.out.println();
        int size = bag.getCurrentSize();

        System.out.println( "Changing the content of the bag" );
        bag.clear();
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();

        System.out.println( "Changing the content of the bag" );
        bag.add( "X" );
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();

        System.out.println( "Changing the content of the bag" );
        bag.add( "Y" );
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();

        System.out.println( "Changing the content of the bag" );
        bag.clear();
        bag.add( "Y" );
        bag.add( "X" );
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();

        System.out.println( "Changing the content of the bag" );
        final int ASCII_A = 65;
        bag.clear();
        for ( int i = 0; i < size; i++ )
        {
            bag.add( ( char ) ( ASCII_A + i ) + "" );
        }
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();

        System.out.println( "Changing the content of the bag" );
        bag.add( "D" );
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();

        System.out.println( "Changing the content of the bag" );
        bag.removeElement( "A" );
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();

        System.out.println( "Changing the content of the bag" );
        bag.clear();
        for ( int i = 0; i < size; i++ )
        {
            bag.add( ( char ) ( ASCII_A + size - i ) + "" );
        }
        bag.display();
        System.out.println( "Bag is" + ( bag.isBagSorted() ? "" : " NOT" ) + " sorted" );
        System.out.println();
    } // end main
} // end ResizableArrayBag