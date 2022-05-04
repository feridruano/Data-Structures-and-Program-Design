import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

/**
 * This class determines how long it would take to kill a Hydra by cutting off all heads,
 * The process takes a substring from the String "HYDRA" by removing the left most character.
 * Two more heads are added each time the substring length is greater than 0.
 *
 * @version 1/25/2019
 * @author Ferid Ruano
 */
public class Hydra
{
    private BagInterface< String > hydraHeads;

    /**
     * Constructor creates this.hydraHeads object as ResizableArrayBag
     */
    public Hydra()
    {
        this.hydraHeads = new ResizableArrayBag<>();
    }

    /**
     * Prompts the user for the word "HYDRA" and adds it to this.hydraHeads
     */
    public void addFirstElement()
    {
        final String VALID_INPUT = "HYDRA";
        Scanner keyboard = new Scanner( System.in );
        String initial;

        // Prompt user a valid string input
        do
        {
            System.out.println( "What is the initial string? Use \"HYDRA\"" );
            initial = keyboard.next();
        }
        while ( !initial.equals( VALID_INPUT ) );

        System.out.printf( "The initial string \"%s\" has length of %d.%n", initial, initial.length() );
        this.hydraHeads.add( initial );
    } // End addFirstElement

    /**
     * Removes heads from this.hydraHeads until all are gone.
     */
    public void removeHeads()
    {
        System.out.println( "\n*** Removing heads from the Hydra ***\n" );

        // While loop to remove hydra heads
        while ( !this.hydraHeads.isEmpty() )
        {
            String head = hydraHeads.remove();
            System.out.println( "--> Removed \"" + head + "\"" );
            head = head.substring( 1 ); // Remove first character from "HYDRA"

            // Check size (length) of hydra head
            // head.substring(1) removed "A", therefore, making head length = 0
            if ( head.length() == 0 )
            {
                System.out.printf( "The removed head is of length 1, no new heads will be added - %d heads remaining%n", this.hydraHeads.getCurrentSize() );
            }
            // Hydra head length > 0
            else
            {
                this.hydraHeads.add( head );
                this.hydraHeads.add( head );
                System.out.printf( "After adding two, the Hydra has %d heads:%n", this.hydraHeads.getCurrentSize() );
            }
            displayBag(); // Print number of heads (contents of bag)
        } // End while loop to remove hydra head

        System.out.println("The Hydra is no more!!!");
    } // end removeHeads

    /**
     * Displays the number of hydra heads
     */
    private void displayBag()
    {
        Object[] bagArray = this.hydraHeads.toArray();
        System.out.println( Arrays.toString( bagArray ) );
        System.out.println();
    } // end displayBag

    public static void main( String[] args )
    {
        Hydra hydra = new Hydra();
        hydra.addFirstElement();

        long startTime = Calendar.getInstance().getTime().getTime();

        hydra.removeHeads();

        long stopTime = Calendar.getInstance().getTime().getTime();

        System.out.println( "\nThe time required was " + ( stopTime - startTime ) + " milliseconds" );
    }
}
