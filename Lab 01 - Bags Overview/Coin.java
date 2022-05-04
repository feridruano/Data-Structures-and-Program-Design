/**
 * A subclass of Money, which is allowed to instantiate Money objects.
 *
 * @author Ferid Ruano
 * @version 1/28/2019
 */
public class Coin extends Money
{
    // Instance Variables
    public final static int[] DENOMINATION_VALUE = { 1, 5, 10, 25, 50 };
    public final static String[] DENOMINATION_NAME = { "PENNY", "NICKEL", "DIME", "QUARTER", "HALF_DOllAR" };
    private final static int NUMBER_OF_DENOMINATIONS = 5;

    /**
     * Constructor instantiates a super object.
     */
    public Coin()
    {
        super( NUMBER_OF_DENOMINATIONS );
    } // End Coin constructor

    /**
     * Gets the denomination value of a coin.
     *
     * @return The denomination value of a coin.
     */
    public double getValue()
    {
        return ( double ) DENOMINATION_VALUE[getDenomination()] / 100; // Super.getDenomination
    } // End getValue

    /**
     * Display information about the coin object.
     *
     * @return String of coin denomination name.
     */
    public String toString()
    {
        return DENOMINATION_NAME[getDenomination()]; // super.getDenomination
    } //  End toString
} // End Coin
