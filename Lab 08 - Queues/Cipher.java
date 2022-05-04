/**
 * A class that implements a cipher with repeating key algorithm.
 *
 * @author Ferid Ruano
 * @version 3/12/2019
 */

public class Cipher
{
    // Instance Variables
    private Integer[] key;
    private final int ADD_FACTOR = 1;
    private final int SUBTRACT_FACTOR = -1;

    /**
     * Secondary constructor
     * Sets cipher key
     *
     * @param key Array of Integers.
     */
    public Cipher( Integer... key )
    {
        // TODO Project 2B - Done
        this.key = key; // Set key field
    }

    /**
     * Encode a message by calling helper method code().
     *
     * @param message String of characters.
     * @return Encoded String of characters.
     */
    public String encode( String message )
    {
        // TODO Project 2B - Done
        return code( message, ADD_FACTOR );
    }

    /**
     * Decode a message by calling helper method code().
     *
     * @param encoded Encoded string of characters.
     * @return Decoded String of characters.
     */
    public String decode( String encoded )
    {
        // TODO Project 2B - Done
        return code( encoded, SUBTRACT_FACTOR );
    }

    /**
     * Encode or decode a message.
     *
     * @param message             A string of characters to encode or decode.
     * @param addOrSubtractFactor Set to 1, then encode. Set to -1, then decode.
     * @return Encoded or decoded string of characters.
     */
    private String code( String message, int addOrSubtractFactor )
    {
        StringBuilder encoded = new StringBuilder();
        // TODO Project 2B - Done
        QueueInterface< Integer > repeatingKey = getKeyQueue(); // Get queue for encoding and decoding
        // Encode or decode message characters depending on addOrSubtractFactor
        int codeLength = message.length();
        for ( int codePos = 0; codePos < codeLength; codePos++ )
        {
            int keyValue = repeatingKey.dequeue(); // Return and remove front of queue
            encoded.append( ( char ) ( message.charAt( codePos ) + ( addOrSubtractFactor * keyValue ) ) );
            repeatingKey.enqueue( keyValue ); // Add to back of queue
        }
        return encoded.toString();
    }

    /**
     * Returns queue of QueueInterface of type Integer.
     *
     * @return Integer type queue.
     */
    private QueueInterface< Integer > getKeyQueue()
    {
        // TODO Project 2B - Done
        return new CircularArrayQueue<>( this.key );
    }

    public static void main( String args[] )
    {
        System.out.println( "**************  TESTING THE CIPHER  **************\n" );
        Cipher cipher = new Cipher( 5, 12, -3, 8, -9, 4, 10, 2, 3, 5, 1 );
        String encoded = cipher.encode( "All programmers are playwrights and all computers are lousy actors." );
        System.out.println( "--->The original message encoded is:" );
        System.out.println( encoded );
        String decoded = cipher.decode( encoded );
        System.out.println( "--->The original message decoded is:" );
        System.out.println( decoded );

        encoded = cipher.encode( "There is no elevator to success, You have to take the stairs..." );
        System.out.println( "\n--->The original message encoded is:" );
        System.out.println( encoded );
        decoded = cipher.decode( encoded );
        System.out.println( "--->The original message decoded is:" );
        System.out.println( decoded );

        cipher = new Cipher( 3, 1, 7, 4, 2, 5 );
        encoded = cipher.encode( "Trust but Verify" );
        System.out.println( "\n--->The original message encoded is:" );
        System.out.println( encoded );
        decoded = cipher.decode( encoded );
        System.out.println( "--->The original message decoded is:" );
        System.out.println( decoded );

        encoded = cipher.encode( "race car" );
        System.out.println( "\n--->The original message encoded is:" );
        System.out.println( encoded );
        decoded = cipher.decode( encoded );
        System.out.println( "--->The original message decoded is:" );
        System.out.println( decoded );
    }
}


