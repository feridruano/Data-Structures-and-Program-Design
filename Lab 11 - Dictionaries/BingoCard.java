/**
 * @author Ferid Ruano
 * @version 4/9/2019
 */

import java.util.*;

public class BingoCard
{
    private HashMap< Character, TreeSet< Integer > > card;
    public final static String BINGO_KEYS = "BINGO";
    public final static int MAX_VALUES_PER_LETTER = 15;
    public final static int NUMBERS_PER_LETTER = 5;

    /**
     * Default constructor
     */
    public BingoCard()
    {
        // TODO Project 2.2 - Done
        this.card = new HashMap<>();
        Random numGen = new Random();
        int keyLength = BINGO_KEYS.length();
        for ( int charIndex = 0; charIndex < keyLength; charIndex++ )
        {
            // Create a temporary TreeSet
            TreeSet< Integer > values = new TreeSet<>();
            // Add entries to TreeSet
            while ( values.size() < NUMBERS_PER_LETTER )
            {
                // Calculate ranges based on character from BINGO_KEYS
                int max = MAX_VALUES_PER_LETTER * ( charIndex + 1 );
                int min = max - MAX_VALUES_PER_LETTER + 1;
                int number = numGen.nextInt( max - min + 1 ) + min;
                values.add( number );
            }
            card.put( BINGO_KEYS.charAt( charIndex ), values );
        }
    }

    /**
     * Search bingo card at chip letter for chip number.
     *
     * @param chip Object with letter and number to search for.
     * @return True if found, otherwise false.
     */
    public boolean hasNumber( BingoChip chip )
    {
        // TODO Project 2.2 - Done
        boolean found = false;
        int chipNum = chip.getNumber();
        char chipLet = chip.getLetter();

        int hasLetter = 0; // Used to check if player has letter
        if ( this.card.get( chipLet ).first() != hasLetter )
        {
            // Create temporary TreeSet and compare values with chip number
            TreeSet< Integer > values = this.card.get( chipLet );
            // foreach loop is faster than converting to array and using a
            // for loop with a terminating condition because there are only 5 comparisons
            for ( Integer value : values )
                if ( value == chipNum )
                    found = true;

            // Add visual 'found' indicator to during bingo card output
            // and prevents ConcurrentModificationException from occurring
            if ( found )
            {
                values.add( 0 ); // Player now has letter
                this.card.put( chipLet, values );
            }
        }

        return found;
    }

    /**
     * Output BingoCard data.
     *
     * @return String of BingoCard Data.
     */
    public String toString()
    {
        // TODO Project 2.2 - Done
        StringBuffer output = new StringBuffer();

        // Iterate through HashMap card
        this.card.forEach( ( key, values ) ->
                           {
                               output.append( key );
                               // Iterate through TreeSet of values
                               values.forEach( number -> output.append( String.format( "%3d", number ) ) );
                               output.append( "\n" );
                           } );

//        int keyLength = BINGO_KEYS.length();
//        for ( int charIndex = 0; charIndex < keyLength; charIndex++ )
//        {
//            char bingoChar = BINGO_KEYS.charAt( charIndex );
//            output.append( bingoChar );
//            this.card.get( bingoChar ).forEach( number -> output.append( String.format( "%3d", number ) ) );
//            output.append( "\n" );
//        }

        return output.toString();
    }

    /**
     * Compare BingoCard objects.
     *
     * @param o Other BingoCard to compare with.
     * @return True if same, otherwise false.
     */
    public boolean equals( Object o )
    {
        // TODO Project 2.2 - Done
        boolean same;
        if ( o == this )
            same = true;
        else if ( !( o instanceof BingoCard ) )
            same = false;
        else
            same = this.card.equals( ( ( BingoCard ) o ).card );
        return same;
    }
}