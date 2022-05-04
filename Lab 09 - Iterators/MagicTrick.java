import java.util.*;

/**
 * MagicTrick is a program that will guess a number that user is thinking of.
 *
 * @author atb
 * @version 3/26/2019
 * @name Ferid Ruano
 */
public class MagicTrick
{
    public final int NUM_OF_SEQUENCES = 5;
    public final int NUMBERS_PER_SEQUENCE = 16;
    private ArrayList< Integer >[] sequences;

    /**
     * Default constructor
     */
    public MagicTrick()
    {
        // TODO Project 5 - Done
        // Create this.sequences object - From whiteboard
        this.sequences = new ArrayList[this.NUM_OF_SEQUENCES];

        int bitMask = 1;
        // Create ArrayList objects
        for ( int seqLoc = 0; seqLoc < this.NUM_OF_SEQUENCES; seqLoc++ )
        {
            // Initialize new ArrayList
            this.sequences[seqLoc] = new ArrayList<>( this.NUMBERS_PER_SEQUENCE );
            // Add numbers [1..31]
            for ( int entry = 1; entry < 32; entry++ )
                // Isolate least significant bit
                if ( ( ( entry & bitMask ) >> seqLoc ) == 1 )
                    // Add number to appropriate sequence
                    this.sequences[seqLoc].add( entry );
            // Shift bitMask
            bitMask = Integer.rotateLeft( bitMask, 1 );
        }
    }

    /**
     * Display ArrayLists of sequences.
     */
    public void displaySequences()
    {
        // TODO Project 5 - Done
        int seqNum = 1;
        for ( ArrayList< Integer > sequence : this.sequences )
            System.out.printf( "Sequence %d : %s%n", seqNum++, sequence ); // Displays sequence
    }

    /**
     * Guess a number by adding powers of two from specific sequences using a client's answer.
     *
     * @param answer Client's specified sequence numbers.
     */
    public void guessNumber( String[] answer )
    {
        // TODO Project 5 - Done
        int guess = 0;
        // Iterate through answers array
        for ( String appearsIn : answer )
        {
            int seqNum = Integer.parseInt( appearsIn );
            // Throws an error if sequence does not exist
            if ( seqNum < 1 || seqNum > 5 )
                throw new IllegalArgumentException( "Sequence " + seqNum + " does not exist. " +
                                                    "Run again and select a sequence between 1 and 5." );
            // Create iterator at user specified sequence
            Iterator< Integer > seqListIter = this.sequences[seqNum - 1].iterator();
            guess += seqListIter.next(); // Get first entry of sequence
        }
        System.out.printf( "Your number is: %d :)%n", guess );
    }

    public static void main( String[] args )
    {
        MagicTrick magic = new MagicTrick();
        System.out.println( "Think of a number between 1 and 31\n" );
        magic.displaySequences();

        System.out.println( "\nList all the sequences that your number is in (ie. 1 3)" );
        Scanner scan = new Scanner( System.in );
        magic.guessNumber( scan.nextLine().split( " " ) );
    }
}