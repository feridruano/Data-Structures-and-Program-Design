import java.util.*;
import java.io.*;

/**
 * @author atb
 * @version 4/16/2019
 * @modifiedBy Ferid Ruano
 */
public class CheckInventory
{
    // TODO Project 2 Part1 - implement CardID class - Done
    // TODO Project 2 Part1 - implement CheckInventory class - Done

    // Fields
    private DictionaryInterface< CarID, Integer > hashDictionary;
    private final int DEFAULT_CAPACITY = 5;
    private Random random;

    /**
     * Default constructor
     */
    public CheckInventory()
    {
        createHashedDictionary();
        random = new Random( 101 );
    }

    /**
     * Initialize hashedDictionary field
     */
    public void createHashedDictionary()
    {
        this.hashDictionary = new HashedDictionary<>( DEFAULT_CAPACITY );
    }

    /**
     * Check if two inventory files contain only the same CarIDs
     *
     * @param receivedFile Received file.
     * @param sentFile     Sent file.
     * @return True if only the same CarIDs, otherwise false.
     * @throws IOException Throws and passes FileNotFoundException to client
     */
    public boolean compareInventory( String receivedFile, String sentFile ) throws IOException
    {

        boolean same = true;
        int valueFlag = 1;
        // A receivedFile does not contain duplicate CarIDs based on 'THIS' program logic.
        // However, by user error, if the receivedFile was corrupted then we would need to
        // check for corruption (duplicates or invalid keys).

        String nextLine; // Store line tokens
        Scanner lineScanner; // Scan line tokens
        Scanner fileScanner = new Scanner( new File( receivedFile ) );

        // Scan the receivedFile and add data to hashDictionary
        while ( fileScanner.hasNextLine() )
        {
            nextLine = fileScanner.nextLine();
            lineScanner = new Scanner( nextLine );
            CarID carID = new CarID( lineScanner.next(), lineScanner.nextLong() );
            if ( hashDictionary.getValue( carID ) == null )
                this.hashDictionary.add( carID, valueFlag );
            else // In case corruptedFile is passed as parameter (has duplicates or invalid key)
                throw new InputMismatchException(); // Terminate, pass exception to main
        }
        System.out.printf( "The content of the hash table after file %s was processed:\n", receivedFile );
        this.hashDictionary.displayHashTable();

        // Check hashDictionary data using sent file data
        fileScanner = new Scanner( new File( sentFile ) );
        while ( same && fileScanner.hasNextLine() )
        {
            nextLine = fileScanner.nextLine();
            lineScanner = new Scanner( nextLine );
            CarID carID = new CarID( lineScanner.next(), lineScanner.nextLong() );

            // The program created corruptedFile with valid (in use) keys
            // However, just in case, we still need to check for not in use keys
            // i.e - Received file does not contain a specific key from the sent file
            if ( this.hashDictionary.remove( carID ) == null )
                same = false; // Terminate loop, different inventories
        }

        // Check for left over keys. The sent file did not contain all the keys
        if ( same && this.hashDictionary.getSize() > 0 )
            same = false;

        System.out.printf( "The content of the hash table after file %s was processed:\n", sentFile );
        this.hashDictionary.displayHashTable();
        return same;
    }

    /**
     * Generate CarIDs and output them to a file.
     *
     * @param amount       Number of CarIDs to generate.
     * @param receivedFile File to save CarIDs.
     * @return TreeSet of ordered CarIDs.
     * @throws IOException Throws and passes FileNotFoundException to client.
     */
    public TreeSet< CarID > generateContentAndSaveToRandomFile( int amount, String receivedFile ) throws IOException
    {
        final int ASCII_Z = 90;
        final int ASCII_A = 65;
        TreeSet< CarID > inventory = new TreeSet<>();
        PrintWriter printWriter = new PrintWriter( receivedFile );

        // Create a certain amount of CarIDs
        int numOfCarIDs = 0;
        while ( numOfCarIDs != amount )
        {
            // Generate random character sequence
            String charSeq = "";
            for ( int charIndex = 0; charIndex < CarID.CHARACTER_SEQUENCE_LENGTH; charIndex++ )
                charSeq += ( char ) ( random.nextInt( ASCII_Z - ASCII_A + 1 ) + ASCII_A );

            // Generate random numeric sequence
            long numSeq;
            do
            {
                numSeq = Math.abs( random.nextLong() );
            }
            while ( ( int ) Math.log10( numSeq ) + 1 != CarID.NUMERIC_SEQUENCE_LENGTH );

            printWriter.println( charSeq + " " + numSeq ); // Print randomly generate CarID to a txt file

            // Add to inventory - Has boolean return value to check if not present in TreeSet
            if ( inventory.add( new CarID( charSeq, numSeq ) ) )
                numOfCarIDs++; // Only increment if CarID was distinct
        }
        printWriter.close();
        return inventory;
    }

    /**
     * Output ordered CarIDs to a sent file.
     *
     * @param inventory TreeSet of CarIDs in order.
     * @param sentFile  File to save ordered CarIDs.
     * @throws IOException Throws and passes FileNotFoundException to client.
     */
    public void saveSortedContentToSortedFile( TreeSet< CarID > inventory, String sentFile ) throws IOException
    {
        PrintWriter printWriter = new PrintWriter( sentFile );
        // Calling accessor methods is required. Otherwise we would call CarID.toString
        // implicitly which would write CarID{CCC DDDDDDDDDDDD} to file.
        inventory.forEach( CarID -> printWriter.println(
                CarID.getCharacterSequence() + " " + CarID.getNumericSequence() ) );
        printWriter.close();
    }

    /**
     * Create a corrupted file with missing CarIDs from a TreeSet.
     *
     * @param inventory     TreeSet of CarIDs in order.
     * @param corruptedFile File to save certain CarIDs.
     * @throws IOException Throws and passes FileNotFoundException to client.
     */
    public void createCorruptedFile( TreeSet< CarID > inventory, String corruptedFile ) throws IOException
    {
        PrintWriter printWriter = new PrintWriter( corruptedFile );
        inventory.forEach( carID ->
                           {
                               if ( random.nextBoolean() )
                                   printWriter.println(
                                           carID.getCharacterSequence() + " " + carID.getNumericSequence() );
                           } );
        printWriter.println( "??? 10000000000000" ); // Add a default CarID just to make sure file is corrupted
        printWriter.close();
    }

    /**
     * Main Method
     *
     * @param args Arguments
     */
    public static void main( String[] args )
    {
        String receivedFile = "randomFile.txt";
        String sentFile = "sortedFile.txt";
        String corruptedFile = "corruptedFile.txt";
        CheckInventory checker = new CheckInventory();

        try
        {
            System.out.println( "How many CarIDs to generate?" );
            Scanner keyboard = new Scanner( System.in );
            int amount = keyboard.nextInt();
            TreeSet< CarID > sortedSet = checker.generateContentAndSaveToRandomFile( amount, receivedFile );
            checker.saveSortedContentToSortedFile( sortedSet, sentFile );
            checker.createCorruptedFile( sortedSet, corruptedFile );
            System.out.println(
                    "\n*** Checking if \"" + sentFile + "\" and \"" + receivedFile + "\" have the same elements ***" );
            boolean same = checker.compareInventory( receivedFile, sentFile );
            System.out.println( "--> the elements in files \"" + receivedFile
                                + "\" and \"" + sentFile
                                + " are " + ( same ? "" : "NOT " ) + "the same" );


            System.out.println(
                    "\n*** Checking if \"" + sentFile + "\" and \"" + corruptedFile + "\" have the same elements ***" );
            checker.createHashedDictionary();
            same = checker.compareInventory( sentFile, corruptedFile );
            System.out.println( "--> the elements in files \"" + corruptedFile
                                + "\" and \"" + sentFile
                                + " are " + ( same ? "" : "NOT " ) + "the same" );

        }
        catch ( IOException ioe )
        {
            System.out.println( "There was an error in reading or opening the file: " );
            System.out.println( ioe.getMessage() );
        }
        catch ( InputMismatchException ime )
        {
            System.out.println( ime.getMessage() );
        }
        System.out.println( "\nBye!" );
    }  // end main
}
