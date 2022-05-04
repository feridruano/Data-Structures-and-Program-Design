import java.io.File;
import java.util.*;


/**
 * A class that implements text encoding using Huffman compression algorithm
 *
 * @author Ferid Ruano
 * @version 4/23/2019
 */
public class EncodeApplication
{
    //  contains all the lines included in the given text file
    private Message myMessage;
    //  contains the myMessage encoded with Huffman code
    private Code myCode;
    //  contains Huffman trees for each unique character in myMessage
    private HuffmanTree[] myTrees;
    //  initialized to the number of unique characters in the message
    private int numberOfTrees;
    //   allowing ASCII character set
    private final int MAX_NUMBER_OF_CHARS = 128;

    /**
     * Constructor for objects of class EncodeApplication
     */
    public EncodeApplication()
    {
        this.myMessage = new Message();
        this.myCode = new Code();
        this.myTrees = null;
        this.numberOfTrees = 0;
    }


    /**
     * count the number of times each character appears in the message.
     *
     * @return an array with the count for the number of times each character occurs.
     */
    public int[] getCounts()
    {
        // TODO Project 4 - Done
        int[] result = new int[MAX_NUMBER_OF_CHARS + 1];

        // Get characters from message
        while ( this.myMessage.hasNext() )
            result[this.myMessage.next()]++;

        this.myMessage.reset(); // Reset myMessage

        // Print the character count
        for ( int index = 0; index < result.length; index++ )
            if ( result[index] > 0 )
            {
                System.out.printf( "---> Count of character %c is %d\n", index, result[index] );
                this.numberOfTrees++;
            }
        System.out.println();

        return result;
    }

    /**
     * create the initial forrest of trees.
     *
     * @param count the frequency of each character
     *              to be encoded - essentially the array returned by getCounts method.
     * @return the forest of trees for each single letter
     */
    public HuffmanTree[] createInitialTrees( int[] count )
    {
        // TODO Project 4 - Done
        System.out.println( "Creating " + this.numberOfTrees + " initial trees" );
        HuffmanTree[] result = new HuffmanTree[this.numberOfTrees];

        int resultIndex = 0;
        ArrayList< Character > symbolList;
        for ( int index = 0; index < count.length; index++ )
            if ( count[index] > 0 )
            {
                symbolList = new ArrayList<>(); // ArrayList to store symbol
                symbolList.add( ( char ) index ); // Index is ASCII value
                result[resultIndex++] = new HuffmanTree( new HuffmanCode( symbolList, count[index] ) );
            }

        return result;
    }


    /**
     * Creates final Huffman Tree by combing two "smallest trees" at a time.
     * The smallest tree is the tree with the smallest frequency
     * Utilizes findSmallest and swap methods
     * Operates on the forest of trees contained in the variable myTrees.
     */
    public void createHuffmanTree()
    {
        // TODO Project 4 - Issue
        while ( this.numberOfTrees > 1 )
        {
            // swap smallest to the end
            int smallestTree = findSmallest( this.numberOfTrees - 1 );
            swap( smallestTree, this.numberOfTrees - 1 );
            System.out.println( "--->Smallest tree moved to the position " + ( this.numberOfTrees - 1 ) );


            // swap second smallest to the end
            smallestTree = findSmallest( this.numberOfTrees - 2 );
            swap( smallestTree, this.numberOfTrees - 2 );
            System.out.println( "--->Second smallest tree moved to the position " + ( this.numberOfTrees - 2 ) );

            // Construct a new combined tree and put in place of the second last tree
            List< Character > symbolList;
            HuffmanCode smallest = this.myTrees[this.numberOfTrees - 1].getCurrentData();
            HuffmanCode secondSmallest = ( this.myTrees[this.numberOfTrees - 2].getCurrentData() );
            symbolList = smallest.getSymbols();
            symbolList.addAll( secondSmallest.getSymbols() );

            int combinedCount = smallest.getFrequency() + secondSmallest.getFrequency();
            this.myTrees[this.numberOfTrees - 2] = new HuffmanTree( new HuffmanCode( symbolList, combinedCount ),
                                                                    this.myTrees[this.numberOfTrees - 1],
                                                                    this.myTrees[this.numberOfTrees - 2] );
            System.out.println( "--->Combined tree created: " + symbolList + " -> " + combinedCount );

            System.out.println( "--->Combined tree added at position " + ( this.numberOfTrees - 2 ) );
            this.numberOfTrees--;

        }
    }

    /**
     * swaps two elements in myTrees
     *
     * @param index1
     * @param index2
     */
    private void swap( int index1, int index2 )
    {
        HuffmanTree temp = this.myTrees[index1];
        this.myTrees[index1] = this.myTrees[index2];
        this.myTrees[index2] = temp;
    }

    /**
     * finds the Huffman tree with the smallest frequency
     *
     * @param last - the index of the last tree to check
     * @return - the index of the "smallest" tree in the given range
     */
    private int findSmallest( int last )
    {
        int smallestAt = 0;
        for ( int i = 1; i < last; i++ )
        {
            if ( ( this.myTrees[smallestAt].getRootData() ).compareTo( this.myTrees[i].getRootData() ) > 0 )
            {
                smallestAt = i;
            }
        }
        return smallestAt;
    }

    /**
     * encode a single symbol using a Huffman tree and append
     * the Huffman code to myCode  object
     *
     * @param c the symbol to be encoded.
     */
    public void encodeCharacter( Character c )
    {
        // TODO Project 4 - Done
        // make sure that we point to the root
        // at this point we only have one tree
        this.myTrees[0].reset();

        while ( !this.myTrees[0].isSingleSymbol() ) // <-- only look at the trees where the symbol list contains one element
        {
            // Check for symbol to the left
            if ( this.myTrees[0].checkLeft( c ) )
            {
                this.myTrees[0].advanceLeft(); // Traverse left of Huffman Tree
                this.myCode.addCharacter( '0' );
            }
            // Check for symbol to the right
            if ( this.myTrees[0].checkRight( c ) )
            {
                this.myTrees[0].advanceRight(); // Traverse right of Huffman Tree
                this.myCode.addCharacter( '1' );
            }
        }
        this.myCode.addCharacter( ' ' ); // Separate character in Huffman Code
    }

    /**
     * encodes myMessage using the generated Huffman Code by calling
     * the encodeCharacter method for each character in myMessage.
     */
    private void encodeMessage()
    {
        this.myMessage.reset();
        while ( this.myMessage.hasNext() )
        {
            //fill myCode with the code message
            encodeCharacter( this.myMessage.next() );
        }
    }

    /**
     * load the words into the Message
     *
     * @param theFileName the name of the file holding the message
     */
    public boolean loadMessage( String theFileName )
    {
        Scanner input;
        boolean loaded = false;
        try
        {
            input = new Scanner( new File( theFileName ) );

            while ( input.hasNextLine() )  // read until  end of file
            {
                this.myMessage.addLine( input.nextLine() );
            }
            System.out.println( this.myMessage );
            loaded = true;
        }
        catch ( Exception e )
        {
            System.out.println( "There was an error in reading or opening the file: " + theFileName );
            System.out.println( e.getMessage() );
        }
        return loaded;
    }

    public void run()
    {
        if ( loadMessage( "message4.txt" ) )
        {
            this.myTrees = createInitialTrees( getCounts() );
            this.numberOfTrees = this.myTrees.length;

            System.out.println( "Building Huffman Tree" );
            createHuffmanTree();

            //the tree is created so let's display it using preOrder
            System.out.println( "\nHuffman Tree:" );
            this.myTrees[0].preOrderTraverse();
            System.out.println();
            //we can encode now
            encodeMessage();
            System.out.println( this.myCode );
        }
    }


    public static void main( String[] args )
    {
        EncodeApplication encodeApp = new EncodeApplication();
        encodeApp.run();
    }
}