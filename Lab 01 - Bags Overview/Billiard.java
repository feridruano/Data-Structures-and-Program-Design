import java.util.*;

/**
 * This class determines how long it would take to remove billiard balls from a table,
 * where ball n is replaced by n balls with randomly generated numbers between 1 and n-1.
 *
 * @version 1/25/2019
 * @updatedBy Ferid Ruano
 */
public class Billiard
{
    private BagInterface< Integer > table;

    /**
     * constructor creates this.table object as ResizableArrayBag
     */
    public Billiard()
    {
        this.table = new ResizableArrayBag<>();
    }

    /**
     * prompts the user for the first numbered ball and adds it to this.table
     */
    public void addFirstElement()
    {
        final int SMALLEST_BALL = 1;
        final int LARGEST_BALL = 6;
        Scanner keyboard = new Scanner( System.in );
        int start;
        do
        {
            System.out.println( "What is the first numbered ball to start with? (must be between " + SMALLEST_BALL
                                + " and " + LARGEST_BALL + " inclusive)" );
            start = keyboard.nextInt();
        }
        while ( !( start >= SMALLEST_BALL && start <= LARGEST_BALL ) );

        System.out.println( "The first ball is: \"" + start + "\"" );
        this.table.add( start );
    }

    /**
     * Removes balls from this.table until all are gone.
     */
    public void removeBallsFromTable()
    {
        System.out.println( "\n*** Removing balls from the table ***\n" );

        //TODO Project1
        Random numGen = new Random();

        // While loop to remove billiard balls
        while ( !table.isEmpty() )
        {
            int billiardBall = table.remove();
            System.out.printf( "--> Removed \"%d\"%n", billiardBall );

            // Check billiard ball number
            if ( billiardBall == 1 )
            {
                System.out.printf( "Removed ball has number \"1\", no new balls will be added - %d balls remaining:%n", this.table.getCurrentSize() );
            }
            // Billiard ball > 1
            else
            {
                // Add to bag (table) n more billiard balls
                for ( int i = 0; i < billiardBall; i++ )
                {
                    this.table.add( numGen.nextInt( billiardBall - 1 ) + 1 ); // Generator between 1 to (billiard ball number - 1)
                }
                System.out.printf( "After adding %d balls, we have %d on the table:%n", billiardBall, this.table.getCurrentSize() );
            }
            displayBag(); // Print contents of the bag
        } // End while loop to remove billiard balls

        System.out.println( "\nThe table is empty!!!" );
    } // end removeBallsFromTable

    /**
     * Displays the content of this.table
     */
    private void displayBag()
    {
        Object[] bagArray = this.table.toArray();
        System.out.println( Arrays.toString( bagArray ) );
        System.out.println();
    } // end displayBag

    public static void main( String args[] )
    {
        Billiard billiard = new Billiard();
        billiard.addFirstElement();

        long startTime = Calendar.getInstance().getTime().getTime(); // get current time in miliseconds

        billiard.removeBallsFromTable();

        long stopTime = Calendar.getInstance().getTime().getTime();

        System.out.println( "\nThe time required was " + ( stopTime - startTime ) + " milliseconds" );
    } // end main
} // end Billiard