/**
 * A client class that implements a piggy bank functionality.
 *
 * @author Ferid Ruano
 * @version 1/28/2019
 */
public class Round
{
    // Instance Variables
    private PiggyBank myPiggyBank; //commented out so the class compiles for now

    /**
     * Constructor instantiates a super object
     */
    public Round( int numberOfMonies, int capacity )
    {
        //TODO Project3
        this.myPiggyBank = new PiggyBank( numberOfMonies, capacity );

        // Display contents
        System.out.println( "\n>>The content of your piggy bank <<" );
        System.out.printf( "The piggy bank can hold %d coins/bills.%n", capacity );
        System.out.printf( "There are %d coins/bills in the piggy bank: %s%n", numberOfMonies, this.myPiggyBank.toString() );
    }

    /**
     * Creates and adds two Money objects to this.bucket.
     */
    public void addTwoMonies()
    {
        //TODO Project3
        System.out.println( "\n--> Adding additional monies:" );

        // New Money objects
        Money coin = new Coin();
        Money bill = new Bill();

        // Check for a full piggy bank
        if ( this.myPiggyBank.isFull() )
        {
            throw new PiggyBankFullException( "<!-- Piggy Bank is Full --!>" );
        }
        else
        {
            this.myPiggyBank.add( coin );
            this.myPiggyBank.add( bill );
        }
    } // End addTwoMonies

    /**
     * Adds two Money objects by calling addTwoMonies.
     * Shakes the piggy bank and prints its content.
     * Asks the piggy bank to empty and count heads.
     *
     * @return Total number of heads up Money objects.
     */
    public int run()
    {
        //TODO Project3
        addTwoMonies();
        this.myPiggyBank.shake();
        this.myPiggyBank.toString();
        return this.myPiggyBank.emptyAndCountHeads();
    } // End run
} // End Round