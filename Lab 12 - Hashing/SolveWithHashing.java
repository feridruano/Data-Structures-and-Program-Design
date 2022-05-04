import java.util.*;

/**
 * A class that implements solutions to several problems using hashing
 *
 * @author Ferid Ruano
 * @version 4/16/2019
 */
public class SolveWithHashing
{
    private DictionaryInterface< Integer, Integer > hashedDictionary;
    private final int DEFAULT_CAPACITY = 5;

    /**
     * Default constructor
     */
    public SolveWithHashing()
    {
        createHashedDictionary();
    }

    /**
     * Initialize hashedDictionary field
     */
    public void createHashedDictionary()
    {
        this.hashedDictionary = new HashedDictionary<>( DEFAULT_CAPACITY );
    }

    /**
     * Code for testing displayHashTable method.
     */
    private void testDisplayHashTable()
    {
        System.out.println( "displaying empty dictionary" );
        this.hashedDictionary.displayHashTable();

        this.hashedDictionary.add( 1, 1 );
        this.hashedDictionary.add( 7, 7 );
        System.out.println( "displaying dictionary after 2 entries have been added" );
        this.hashedDictionary.displayHashTable();

        this.hashedDictionary.add( 13, 13 );
        this.hashedDictionary.add( 17, 17 );
        this.hashedDictionary.add( 8, 8 );
        System.out.println( "displaying dictionary after 3 additional entries have been added" );
        this.hashedDictionary.displayHashTable();

        this.hashedDictionary.remove( 1 );
        this.hashedDictionary.remove( 17 );
        System.out.println( "displaying dictionary after 2 entries have been removed" );
        this.hashedDictionary.displayHashTable();
    }

    /**
     * Get the first repeating element within an array.
     *
     * @param a Array with possible repeating elements.
     * @return First repeating element.
     */
    public Integer getFirstRepeatedElement( int[] a )
    {
        // TODO Project1 #1 - Done

        Integer result = null;

        // Add key and value
        for ( int aIndex = 0; aIndex < a.length; aIndex++ )
        {
            Integer position = this.hashedDictionary.getValue( a[aIndex] ); // Get value

            // Check if key exists
            if ( position != null )
            {
                if ( position > 0 ) // Only negate positive values
                    this.hashedDictionary.add( a[aIndex], -position );
            }
            else
            {
                position = aIndex + 1; // One based array indices
                this.hashedDictionary.add( a[aIndex], position );
            }
        }

        // Get first repeating element value
        Integer highestNegPos = Integer.MIN_VALUE;
        Iterator< Integer > vIterator = this.hashedDictionary.getValueIterator();
        while ( vIterator.hasNext() )
        {
            Integer value = vIterator.next();
            if ( value < 0 && value > highestNegPos )
                highestNegPos = value;
        }

        // Get first repeating element using value
        if ( highestNegPos > Integer.MIN_VALUE )
        {
            result = -highestNegPos - 1;
            result = a[result];
        }

        // Output a[] and hash table
        System.out.println( "The content of the hash table for array: " + Arrays.toString( a ) );
        this.hashedDictionary.displayHashTable();

        return result;
    }

    /**
     * Look through two sets for two values that add to a specified sum.
     *
     * @param a First set with values.
     * @param b Second set with values.
     * @param k Specified sum to find.
     * @return True if a pair (two values) were found to add up to k, otherwise false.
     */
    public boolean lookForPair( int[] a, int[] b, int k )
    {
        // TODO Project1 #2 - Done

        boolean pair = false;
        // Fill hash table with element as key and value

        // Get smallest set
        if ( a.length > b.length )
        {
            int[] temp = a;
            a = b;
            b = temp;
        }

        System.out.println( "toPutInHashTable = " + Arrays.toString( a ) );
        System.out.println( "toCheck = " + Arrays.toString( b ) );

        // Make a hash table from smallest set
        Hashtable< Integer, Integer > tempSet = new Hashtable<>( a.length );
        for ( int index = 0; index < a.length; index++ )
            tempSet.put( a[index], a[index] );

        // Iterate through b[] while searching hash table for computed delta value
        for ( int index = 0; !pair && index < b.length; index++ )
        {
            int delta = k - b[index];

            // Check if hash table contains key
            if ( tempSet.containsKey( delta ) )
            {
                pair = true;
                System.out.printf( "The pair {%d,%d} adds to %d%n", delta, b[index], k );
            }
        }

        return pair;
    }

    /**
     * Main method
     *
     * @param args Arguments
     */
    public static void main( String[] args )
    {
        ArrayList< int[] > toCheck = new ArrayList<>();
        toCheck.add( new int[] { 9, 3, 5, 1, 2, 2, 5, 3 } );
        toCheck.add( new int[] { 6, 6, 3, 2, 1, 2, 2, 3 } );
        toCheck.add( new int[] { 2, 1, 6, 2, 3, 2, 3, 6 } );
        toCheck.add( new int[] { 3, 2, 1, 2, 2, 3, 6, 6 } );
        toCheck.add( new int[] { 9, 3, 4, 4, 4, 1, 2, 2, 5, 3 } );
        toCheck.add( new int[] { 3, 3, 3, 3, 3, 3, 3 } );
        toCheck.add( new int[] { 1, 2, 3, 4, 5, 6, 7, 8 } );
        toCheck.add( new int[] { 8, 1, 2, 3, 4, 5, 6, 7 } );

        SolveWithHashing solver = new SolveWithHashing();

        System.out.println( "\n\t*** Testing displayHashTable ***" );
        solver.testDisplayHashTable();

        System.out.println( "\n\t*** Find The First Element With Duplicate ***" );
        Integer firstDuplicate;
        for ( int[] array : toCheck )
        {
            solver.createHashedDictionary();
            firstDuplicate = solver.getFirstRepeatedElement( array );
            if ( firstDuplicate != null )
                System.out.println( "--> the first element that is repeated is: " + firstDuplicate );
            else
                System.out.println( "--> duplicates not found" );
            System.out.println();
        }

        System.out.println( "\n\t*** Check If There Exists A Pair Of Elements That Add Up To k ***" );
        boolean found;
        for ( int k = 2; k < 10; k++ )
        {
            System.out.println( "k = " + k );
            for ( int i = 1; i < toCheck.size(); i++ )
            {
                solver.createHashedDictionary();
                found = solver.lookForPair( toCheck.get( i - 1 ), toCheck.get( i ), k );
                System.out.println( "--> pair that add up to " + k + ( found ? "" : " NOT" ) + " found." );
            }
            System.out.println();
        }
        System.out.println( "\nBye!" );
    }  // end main
}
