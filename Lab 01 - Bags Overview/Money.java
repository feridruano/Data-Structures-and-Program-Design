import java.util.Random;

/**
 * An abstract class that acts as the parent class for creating Money objects.
 *
 * @author Ferid Ruano
 * @version 1/28/2019
 */
public abstract class Money
{
    // Instance Variables
    private int denomination;
    private boolean heads;

    /**
     * Generates the denomination randomly based on number of denominations passed.
     * Set heads to false.
     *
     * @param numberOfDenominations A range to select a value for coin or paper bill.
     */
    public Money( int numberOfDenominations )
    {
        //TODO Project3 - Done
        Random random = new Random();
        this.denomination = random.nextInt( numberOfDenominations ); // Range = 0 - numberOfDenominations
        this.heads = false;
    } // End Money constructor

    /**
     * Gets the denomination value of a coin or paper bill.
     *
     * @return The denomination value of a coin or paper bill.
     */
    public int getDenomination()
    {
        //TODO Project3 - Done
        return this.denomination;
    } // End getDenomination

    /**
     * Creates an abstract method, which subclasses implement.
     * Gets denomination value of coin/paper bill.
     */
    public abstract double getValue();

    /**
     * Simulates a money toss in which the coin/paper bill lands either heads up or tails up.
     */
    public void toss()
    {
        //TODO Project3 - Done
        Random random = new Random();
        this.heads = random.nextBoolean();
    } // End toss

    /**
     * Gets the boolean value of this.heads instance variable.
     *
     * @return True if heads, false otherwise.
     */
    public boolean isHeads()
    {
        //TODO Project3 - Done
        return this.heads;
    } // End isHeads

    /**
     * Creates an abstract method, which subclasses implement.
     */
    public abstract String toString();
} // End Money
