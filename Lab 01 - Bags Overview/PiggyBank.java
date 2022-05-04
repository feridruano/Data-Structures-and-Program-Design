/**
 * Main class for storing data as a resizableArrayBag as well as handles the
 * addition, removal, and exception check for the bag.
 *
 * @author Ferid Ruano
 * @version 1/28/2019
 */

import java.util.Arrays;
import java.util.Random;

public class PiggyBank
{
    // Instance Variables
    private BagInterface< Money > bucket;
    private int capacity;

    /**
     * Constructor creates a resizeableArrayBag based on parameters.
     *
     * @param numberOfMonies The number of Money objects to create.
     * @param capacity       The size of this.bucket.
     */
    PiggyBank( int numberOfMonies, int capacity )
    {
        // Setting instance variables
        this.bucket = new ResizableArrayBag<>(); // Bag creation to store Money objects
        this.capacity = capacity;

        System.out.printf( ">> Adding %d monies to your piggy bank <<%n", numberOfMonies );

        // Fill in this.bucket with given number of monies
        for ( int i = 0; i < numberOfMonies; i++ )
        {
            Random random = new Random();

            // True = Coin
            if ( random.nextBoolean() )
            {
                Money coin = new Coin();
                System.out.printf( "Added $%.2f to the piggy bank.%n", coin.getValue() );
                this.bucket.add( coin );
            }
            // False = Paper Bill
            else
            {
                Money bill = new Bill();
                System.out.printf( "Added $%.2f to the piggy bank.%n", bill.getValue() );
                this.bucket.add( bill );
            }
        } // End for loop
    } // End PiggyBank

    /**
     * Add money this.bucket one at a time.
     *
     * @param currency The type of Money object.
     */
    public void add( Money currency )
    {
        // Check for more room in this.bucket
        if ( isFull() )
        {
            throw new PiggyBankFullException( "<!-- Piggy Bank is Full --!>" );
        }
        // Add to this.bucket
        else
        {
            System.out.printf( "Added $%.2f to the piggy bank.%n", currency.getValue() );
            this.bucket.add( currency );
        }
    } // End add

    /**
     * Remove Money object from this.bucket randomly.
     *
     * @return Random Money object from this.bucket.
     */
    public Money remove()
    {
        Money currency;

        // Check if this.bucket is empty
        if ( isEmpty() )
        {
            throw new PiggyBankEmptyException( "<!-- Piggy Bank is Empty --!>" );
        }
        // Randomly remove form this.bucket
        else
        {
            currency = this.bucket.remove();
        }
        return currency;
    } // End return

    /**
     * Check if this.bucket is empty.
     *
     * @return True if capacity is equal to 0, false otherwise.
     */
    public boolean isEmpty()
    {
        return this.capacity == 0;
    } // End isEmpty

    /**
     * Check if this.bucket is full.
     *
     * @return True if capacity is equal to number of monies, false otherwise.
     */
    public boolean isFull()
    {
        return this.capacity == getNumberOfMonies(); // super.getNumberOfMonies
    } // End isFull

    /**
     * Get capacity of this.bucket instance variable.
     *
     * @return Capacity of this.bucket.
     */
    public int getCapacity()
    {
        return this.capacity;
    } // End

    /**
     * Get the number of monies the user asked for.
     *
     * @return Total number of Monies.
     */
    public int getNumberOfMonies()
    {
        return this.bucket.getCurrentSize();
    } // End getNumberOfMonies

    /**
     * Rearrange the order of this.bucket by "shaking" the piggy bank
     */
    public void shake()
    {
        System.out.println( "\n>> Shaking your piggy bank <<" );
        System.out.printf( "The piggy bank can hold %d coins/bills.%n", this.capacity );

        // Shake the piggy bank
        Object[] shakeArray = this.bucket.toArray();
        // Swap elements randomly
        for ( int i = 0; i < this.bucket.getCurrentSize(); i++ )
        {
            Random random = new Random();
            int randomIndex = random.nextInt( this.bucket.getCurrentSize() - i ) + i;
            Object temp = shakeArray[randomIndex];
            shakeArray[randomIndex] = shakeArray[i];
            shakeArray[i] = temp;
        }
        // Clear bucket
        this.bucket.clear();
        // Add new elements
        for ( Object currency : shakeArray )
        {
            this.bucket.add( ( Money ) currency );
            ( ( Money ) currency ).toss();
        }
        System.out.printf( "There are %d coins/bills in the piggy bank: %s%n", this.bucket.getCurrentSize(), toString() );
    } // End shake

    /**
     * Empty this.bucket while counting the number of heads up Money.
     * Display the count and the dollar value.
     *
     * @return Total number of heads up Money.
     */
    public int emptyAndCountHeads()
    {
        System.out.println( "\n>> Emptying your piggy bank <<" );
        int numberOfHeads = 0;
        int numberOfMonies = this.bucket.getCurrentSize();
        double totalOfHeadsValue = 0;
        // Remove Money object and count heads up
        while ( !this.bucket.isEmpty() )
        {
            Money currency = this.bucket.remove();
            if ( currency.isHeads() )
            {
                numberOfHeads++;
                totalOfHeadsValue += currency.getValue();
            }
            // Display if object landed heads or tails
            System.out.println( currency.toString() + " landed " + ( currency.isHeads() ? "Heads" : "Tails" ) );
        }
        System.out.printf( "%n%d out of %d coins/bills landed \"HEADS\"", numberOfHeads, numberOfMonies );
        System.out.printf( "%nThe total value of \"Heads\" is: $%.2f %n", totalOfHeadsValue );
        return numberOfHeads;
    } // End emptyAndCountHeads

    public String toString()
    {
        // Calls toArray to get the elements
        Object[] bucketArray = this.bucket.toArray();

        // Uses a for loop over the resulted array to compute the total
        double total = 0;
        for ( int i = 0; i < getNumberOfMonies(); i++ )
        {
            total += ( ( Money ) bucketArray[i] ).getValue();
        }

        // Displays an array of this.bucket with heads up or tails up
        String[] contents = new String[bucketArray.length];
        for ( int i = 0; i < bucketArray.length; i++ )
        {
            contents[i] = bucketArray[i].toString() + " landed " +
                          ( ( ( Money ) bucketArray[i] ).isHeads() ? "HEADS" : "TAILS" );
        }

        // Calls Arrays.toString to get the content of the objects
        return Arrays.toString( contents ) + "\nThe total of $" + String.format( "%.2f", total );
    } // End toString
} // End PiggyBank
