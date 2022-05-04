import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * A class for testing binaryRadixSort method
 *
 * @author Ferid Ruano
 * @version 3/8/2019
 */

public class CheckBinaryRadixSort
{
    public static void main( String[] args )
    {
        int arraySize = 0;
        int trials = 0;
        int maxNumber = 0;
        boolean invalidInput;

        // Get user input
        do
        {
            try
            {
                invalidInput = false;
                Scanner keyboard = new Scanner( System.in );
                System.out.println( "What size array should be used?" +
                                    "\n It should be an integer value greater than or equal to 1." );
                arraySize = keyboard.nextInt();

                System.out.println( "How many arrays should be used (number of trials)?" +
                                    "\n It should be an integer value greater than or equal to 1." );
                trials = keyboard.nextInt();

                System.out.println( "What maximum number should be generated?" +
                                    "\n It should be an integer value greater than or equal to 1." );
                maxNumber = keyboard.nextInt();
            }
            catch ( InputMismatchException ime )
            {
                System.out.println( "Could not convert input to an integer" );
                invalidInput = true;
            }
            catch ( Exception e )
            {
                System.out.println( "There was an error with System.in" );
                System.out.println( e.getMessage() );
                invalidInput = true;
            }
        }
        while ( invalidInput );

        // Begin testing
        Integer[] data;
        Integer[] forTesting;

        for ( int i = 1; i <= trials; i++ )
        {
            System.out.println( "\nTRIAL #" + i );
            Random generator = new Random();
            data = new Integer[arraySize];
            for ( int j = 0; j < arraySize; j++ )
                data[j] = generator.nextInt( maxNumber + 1 );

            System.out.println( "The original array is: " );
            System.out.println( Arrays.toString( data ) );
            // Copy of original array to sort and compare
            forTesting = Arrays.copyOf( data, arraySize );
            Arrays.sort( forTesting );
            SortArray.binaryRadixSort( data, maxNumber );
            System.out.println( "The original array sorted with binaryRadixSort: " );
            System.out.println( Arrays.toString( data ) );
            System.out.println( Arrays.equals( data, forTesting ) ? "  passes" : "   fails" );
        } // End for
    } // End PSVM
}
