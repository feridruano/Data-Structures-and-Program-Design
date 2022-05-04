import java.util.*;

/**
 * @author Ferid Ruano
 * @version 4/9/2019
 */

public class SetDictionary< K extends Comparable< ? super K > > implements SetInterface< K >, Iterable< K >
{
    private TreeMap< K, Boolean > items;

    /**
     * Default constructor
     */
    public SetDictionary()
    {
        // TODO Project 1 - Done
        this.items = new TreeMap<>();
    } // end default constructor

    /**
     * Add a new entry to 'items' TreeMap.
     *
     * @param newEntry The object to be added as a new entry.
     * @return True if addition was successful, otherwise check for null and return false.
     */
    public boolean add( K newEntry )
    {
        // TODO Project 1 - Done
        return this.items.put( newEntry, false ) != null;
    } // end add

    /**
     * Remove an entry from 'items' TreeMap.
     *
     * @param anEntry The entry to be removed.
     * @return True if removal was successful, otherwise check for null and return false.
     */
    public boolean remove( K anEntry )
    {
        // TODO Project 1 - Done
        return this.items.remove( anEntry ) != null;
    } // end remove

    /**
     * Remove all entries
     */
    public void clear()
    {
        // TODO Project 1 - Done
        this.items.clear();
    } // end clear

    /**
     * Check if 'items' TreeMap contains a specific entry.
     *
     * @param anEntry The entry to locate.
     * @return True if entry is found, otherwise false.
     */
    public boolean contains( K anEntry )
    {
        // TODO Project 1 - Done
        return this.items.containsKey( anEntry );
    } // end contains

    /**
     * Return the size of 'items' TreeMap.
     *
     * @return Size of 'items' TreeMap.
     */
    public int getCurrentSize()
    {
        // TODO Project 1 - Done
        return this.items.size();
    } // end getCurrentSize

    /**
     * Check if 'items' TreeMap is empty.
     *
     * @return True if empty, otherwise false.
     */
    public boolean isEmpty()
    {
        // TODO Project 1 - Done
        return this.items.isEmpty();
    } // end isEmpty

    /**
     * Compare SetDictionary objects.
     *
     * @param o The set to compare this set with.
     * @return True if objects are equal, otherwise false.
     */
    public boolean equals( Object o )
    {
        // TODO Project 1 - Done
        boolean same;
        if ( o == this )
            same = true;
        else if ( !( o instanceof SetDictionary ) )
            same = false;
        else
            same = this.items.equals( ( ( SetDictionary ) o ).items );
        return same;
    } // equals

    /**
     * Create Iterator to traverse 'items' key set by calling Iterator().
     *
     * @return Key set Iterator.
     */
    public Iterator< K > getIterator()
    {
        // TODO Project 1 - Done
        return iterator();
    } // end getIterator

    /**
     * Create Iterator to traverse 'items' key set.
     *
     * @return Key set Iterator.
     */
    public Iterator< K > iterator()
    {
        // TODO Project 1 - Done
        return this.items.keySet().iterator();
    } // end iterator

    /**
     * Convert 'items' TreeMap into a generic array.
     *
     * @return Generic array.
     */
    public K[] toArray()
    {
        // TODO Project 1 - Done
        // the cast is safe because the new array contains null entries
        @SuppressWarnings( "unchecked" )

        K[] result = ( K[] ) new Comparable[getCurrentSize()]; // unchecked cast

        int index = 0;
        Iterator< K > iter = iterator();
        //MUST BE IMPLEMENTED WITH ITERATOR
        while ( iter.hasNext() )
            result[index++] = iter.next();
        return result;
    } // end toArray

    /**
     * Union two SetDictionary's 'items' TreeMap together.
     *
     * @param otherSet The set to union with.
     * @return Set with all entries.
     */
    public SetInterface< K > union( SetInterface< K > otherSet )
    {
        // TODO Project 1 - Done
        SetInterface< K > result = new SetDictionary<>();

        //MUST BE IMPLEMENTED WITH ITERATORS USING forEach lambda CONSTRUCT
        // AS SHOWN IN LectureDictionary EXAMPLES
        iterator().forEachRemaining( key -> result.add( key ) );
        otherSet.getIterator().forEachRemaining( key -> result.add( key ) );

        return result;
    } // end union

    /**
     * Intersect two SetDictionary's 'items' TreeMap together.
     *
     * @param otherSet The set to intersect with.
     * @return Set with all common entries.
     */
    public SetInterface< K > intersection( SetInterface< K > otherSet )
    {
        // TODO Project 1 - Done
        SetInterface< K > result = new SetDictionary<>();

        //MUST BE IMPLEMENTED WITH ITERATORS
        // UTILIZE TRY_CATCH BLOCK
        try
        {
            Iterator< K > thisIter = iterator();
            while ( true )
            {
                K thisEntry = thisIter.next();
                Iterator< K > otherIter = otherSet.getIterator();
                boolean notFound = true;
                while ( notFound && otherIter.hasNext() )
                    if ( otherIter.next().equals( thisEntry ) )
                    {
                        result.add( thisEntry );
                        notFound = false;
                    }

            } // end while
        }
        catch ( NoSuchElementException nsee )
        {
            System.out.println( "Finished creating intersection." );
        }

        return result;
    } // end intersection

    public static void main( String args[] )
    {
        System.out.println( "CREATING set1" );
        SetInterface< Integer > set1 = new SetDictionary<>();
        set1.add( 1 );
        set1.add( 3 );
        set1.add( 2 );
        set1.add( 0 );
        set1.add( -1 );

        System.out.println( "--> set1 has " + set1.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set1.toArray() ) );
        System.out.println();

        System.out.println( "--> contains for -1 yields " + set1.contains( -1 ) );
        System.out.println( "--> contains for -2 yields " + set1.contains( -2 ) );
        System.out.println( "--> contains for 3 yields " + set1.contains( 3 ) );
        System.out.println( "--> contains for 4 yields " + set1.contains( 4 ) );

        set1.add( 1 );
        System.out.println( "\n--> Added 1 again to the set1, should be the same" );
        System.out.println( "--> set1 has " + set1.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set1.toArray() ) );
        System.out.println();

        System.out.println( "--> Iterating over set1 utilizing getIterator method" );
        Iterator< Integer > kIter = set1.getIterator();
        while ( kIter.hasNext() )
        {
            System.out.println( "\t" + kIter.next() );
        } // end while

        System.out.println( "--> Iterating over set1 utilizing iterator method" );
        kIter = ( ( SetDictionary< Integer > ) set1 ).iterator();
        while ( kIter.hasNext() )
        {
            System.out.println( "\t" + kIter.next() );
        } // end while

        System.out.println( "--> Iterating over set1 utilizing forEach lambda" );
        ( ( SetDictionary< Integer > ) set1 ).items.forEach( ( k, v ) -> System.out.println( "\t " + k ) );

        System.out.println( "\n--> Removing -1 20 3 from set1:" );
        boolean result = set1.remove( -1 );
        if ( result )
            System.out.println( "---> -1 was removed - CORRECT" );
        else
            System.out.println( "---> -1 was not removed - INCORRECT" );
        result = set1.remove( 20 );
        if ( result )
            System.out.println( "---> 20 was removed - INCORRECT" );
        else
            System.out.println( "---> 20 was not removed - CORRECT" );
        result = set1.remove( 3 );
        if ( result )
            System.out.println( "---> 3 was removed - CORRECT" );
        else
            System.out.println( "---> 3 was not removed - INCORRECT" );

        System.out.println( "--> Should just have 0 1 and 2 now" );
        System.out.println( "--> set1 has " + set1.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set1.toArray() ) );
        System.out.println();

        System.out.println( "CREATING set2" );
        SetInterface< Integer > set2 = new SetDictionary<>();
        set2.add( 1 );
        set2.add( 3 );
        set2.add( 2 );
        set2.add( -1 );
        set2.add( 5 );
        set2.add( 8 );

        System.out.println( "--> set2 has " + set2.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set2.toArray() ) );
        System.out.println();

        if ( set1.equals( set2 ) )
            System.out.println( "set1 and set2 are the same - INCORRECT" );
        else
            System.out.println( "set1 and set2 are NOT the same - CORRECT" );
        System.out.println();

        System.out.println( "CREATING UNION OF set1 and set2" );
        SetInterface< Integer > testUnion = set1.union( set2 );
        System.out.print( "--> The union of set1 and set2 has " + testUnion.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testUnion.toArray() ) );
        System.out.println();

        System.out.println( "--> set1 should be unchanged" );
        System.out.println( "--> set1 has " + set1.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set1.toArray() ) );
        System.out.println();

        System.out.println( "--> set2 should be unchanged" );
        System.out.println( "--> set2 has " + set2.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set2.toArray() ) );
        System.out.println();

        System.out.println( "CREATING UNION OF set1 and set1" );
        testUnion = set1.union( set1 );
        System.out.print( "--> The union of set1 and set1 has " + testUnion.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testUnion.toArray() ) );
        System.out.println();

        if ( set1.equals( testUnion ) )
            System.out.println( "set1 and testUnion are the same - CORRECT" );
        else
            System.out.println( "set1 and testUnion are NOT the same - INCORRECT" );
        System.out.println();

        System.out.println( "CREATING INTERSECTION OF set1 and set2" );
        SetInterface< Integer > testIntersection = set1.intersection( set2 );
        System.out.print(
                "--> The intersection of set1 and set2 has " + testIntersection.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testIntersection.toArray() ) );
        System.out.println();

        System.out.println( "--> set1 should be unchanged" );
        System.out.println( "--> set1 has " + set1.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set1.toArray() ) );
        System.out.println();

        System.out.println( "--> set2 should be unchanged" );
        System.out.println( "--> set2 has " + set2.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set2.toArray() ) );
        System.out.println();

        System.out.println( "CREATING INTERSECTION OF set2 and set1" );
        testIntersection = set2.intersection( set1 );
        System.out.print(
                "--> The intersection of set2 and set1 has " + testIntersection.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testIntersection.toArray() ) );
        System.out.println();

        System.out.println( "--> set1 should be unchanged" );
        System.out.println( "--> set1 has " + set1.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set1.toArray() ) );
        System.out.println();

        System.out.println( "--> set2 should be unchanged" );
        System.out.println( "--> set2 has " + set2.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set2.toArray() ) );
        System.out.println();

        System.out.println( "CREATING INTERSECTION OF set2 and set2" );
        testIntersection = set2.intersection( set2 );
        System.out.print(
                "--> The intersection of set2 and set2 has " + testIntersection.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testIntersection.toArray() ) );
        System.out.println();

        System.out.println( "--> set1 should be unchanged" );
        System.out.println( "--> set1 has " + set1.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set1.toArray() ) );
        System.out.println();

        System.out.println( "--> set2 should be unchanged" );
        System.out.println( "--> set2 has " + set2.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set2.toArray() ) );
        System.out.println();

        if ( set2.equals( testIntersection ) )
            System.out.println( "set2 and testIntersection are the same - CORRECT" );
        else
            System.out.println( "set2 and testIntersection are NOT the same - INCORRECT" );
        System.out.println();

        System.out.println( "CREATING INTERSECTION OF testUnion and set2" );
        testIntersection = testUnion.intersection( set2 );
        System.out.print(
                "--> The intersection of testUnion and set2 has " + testIntersection.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testIntersection.toArray() ) );
        System.out.println();

        System.out.println( "--> testUnion should be unchanged" );
        System.out.println( "--> testUnion has " + testUnion.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testUnion.toArray() ) );
        System.out.println();

        System.out.println( "--> set2 should be unchanged" );
        System.out.println( "--> set2 has " + set2.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set2.toArray() ) );
        System.out.println();

        if ( set2.equals( testIntersection ) )
            System.out.println( "set2 and testIntersection are the same - INCORRECT" );
        else
            System.out.println( "set2 and testIntersection are NOT the same - CORRECT" );
        System.out.println();

        System.out.println( "CREATING set3" );
        SetInterface< Integer > set3 = new SetDictionary<>();
        set3.add( -1 );
        set3.add( 0 );
        set3.add( 2 );
        set3.add( 5 );
        set3.add( 7 );
        set3.add( 9 );
        set3.add( 11 );

        System.out.println( "--> set3 has " + set3.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set3.toArray() ) );
        System.out.println();

        System.out.println( "CREATING set4" );
        SetInterface< Integer > set4 = new SetDictionary<>();
        set4.add( 1 );
        set4.add( 2 );
        set4.add( 3 );
        set4.add( 4 );
        set4.add( 5 );
        set4.add( 9 );
        System.out.println( "--> set4 has " + set4.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( set4.toArray() ) );
        System.out.println();

        System.out.println( "CREATING INTERSECTION OF set3 and set4" );
        testIntersection = set3.intersection( set4 );
        System.out.print(
                "--> The intersection of set3 and set4 has " + testIntersection.getCurrentSize() + " items: " );
        System.out.println( Arrays.toString( testIntersection.toArray() ) );
        System.out.println();
    } // end main
}
