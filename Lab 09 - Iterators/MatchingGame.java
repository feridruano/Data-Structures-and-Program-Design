import java.util.*;

/**
 * @author atb
 * @version 3/26/2019
 * @name Ferid Ruano
 */
public class MatchingGame
{
    private ArrayList< Integer > theNumbers;
    private final int MAX_NUMBER_OF_SHUFFLES = 5;
    private final int MIN_NUMBER = 10;
    private final int MAX_NUMBER = 99;
    private Random generator;

    public MatchingGame( int numberAmount )
    {
        this.theNumbers = new ArrayList<>();
        initializeList( numberAmount );
    }

    /**
     * Initialize the list with the count of random 2 digit numbers.
     */
    private void initializeList( int numberAmount )
    {
        // TODO Project 6a - Done
        this.generator = new Random( 11 );
        ListIterator< Integer > iter = this.theNumbers.listIterator();

        // generate the numbers and add them to theNumbers using iterator
        while ( numberAmount > 0 )
        {
            iter.add( this.generator.nextInt( MAX_NUMBER - MIN_NUMBER + 1 ) + MIN_NUMBER );
            numberAmount--;
        }
    }

    /**
     * See whether two numbers are removable.
     *
     * @param first  the first 2 digit integer value
     * @param second the second 2 digit integer value
     * @return true if the first and second match
     */
    private boolean removablePair( Integer first, Integer second )
    {
        // TODO Project 6c - Done
        return ( ( first / 10 == second / 10 ) || ( first / 10 == second % 10 ) ) ||
               ( ( first % 10 == second / 10 ) || ( first % 10 == second % 10 ) );
    }

    /**
     * Implements one pass when called by play method
     * Scans over the list and removes any pairs of values that are removable.
     *
     * @return true if any pair of integers was removed
     */
    private boolean scanAndRemovePairs()
    {
        // TODO Project 6d - Done
        boolean removedAPair = false;

        // Odd arrayLists automatically are false
        if ( this.theNumbers.size() % 2 == 0 )
        {
            // Create ListIterator
            ListIterator< Integer > scan = this.theNumbers.listIterator();
            // Iterate through arrayList
            while ( scan.hasNext() )
            {
                // Get value
                Integer first = scan.next();
                // Check if at the end of arrayList
                if ( scan.hasNext() )
                {
                    Integer second = scan.next();
                    // Check if values contain a pair
                    if ( removablePair( first, second ) )
                    {
                        scan.previous(); // Step iterator back
                        scan.remove(); // Remove value at iterator index
                        scan.previous();
                        scan.remove();
                        System.out.printf( "\t Removed: %d %d%n ", first, second );
                        removedAPair = true;
                    }
                    else
                        scan.previous(); // Step iterator back
                }
            }
        }
        return removedAPair;
    }

    /**
     * Display theNumbers ArrayList.
     */
    private void displayTheNumbers()
    {
        // TODO Project 6b - Done
        // using an instance of Iterator display the content of theNumbers
        // notify the user if the list is empty
        Iterator< Integer > iter = theNumbers.iterator();
        if ( !iter.hasNext() )
            System.out.print( "The list is empty." );
        else
            while ( iter.hasNext() )
                System.out.print( iter.next() + " " );
        System.out.println();
    }

    public void play()
    {
        int pass = 0;
        int numberOfShuffles = 0;
        boolean repeat;

        System.out.println( "Starting with: " );
        displayTheNumbers();

        do
        {
            repeat = false;
            while ( scanAndRemovePairs() )
            {
                pass++;
                System.out.println( "The list after pass #" + pass );
                displayTheNumbers();
            }
            System.out.println( "No more pairs to remove." );
            // do we have at least 3 numbers in the list?
            if ( this.theNumbers.size() > 2 )
            {
                if ( numberOfShuffles < MAX_NUMBER_OF_SHUFFLES )
                {
                    numberOfShuffles++;
                    System.out.println( "Shuffling the numbers." );
                    Collections.shuffle( this.theNumbers, this.generator );
                    System.out.println( "The list after shuffling #" + numberOfShuffles );
                    displayTheNumbers();
                    repeat = true;
                }
            }
        }
        while ( repeat );

        if ( this.theNumbers.isEmpty() )
        {
            System.out.println( "\n*** Winner!!! ***" );
        }
        else
        {
            System.out.println( "\n*** Better luck next time! ***" );
        }
    }

    public static void main( String[] args )
    {
        final int MIN_NUMBER_OF_ELEMENTS = 10;
        Scanner scan = new Scanner( System.in );
        int numberAmount;

        do
        {
            System.out.println( "How many numbers (no less than " + MIN_NUMBER_OF_ELEMENTS + ")?" );
            numberAmount = scan.nextInt();
        }
        while ( numberAmount < MIN_NUMBER_OF_ELEMENTS );

        MatchingGame game = new MatchingGame( numberAmount );
        game.play();
    }
}
