/**
 * A subclass of Money, which is allowed to instantiate Money objects.
 *
 * @author Ferid Ruano
 * @version 1/28/2019
 */
public class Bill extends Money
{
    // Instance Variables
    public final static int[] DENOMINATION_VALUE = { 1, 2, 5, 10, 20, 50, 100 };
    public final static String[] DENOMINATION_NAME = { "WASHINGTON", "JEFFERSON", "LINCOLN", "HAMILTON", "JACKSON",
                                                       "GRANT", "JEFFERSON" };
    private final static int NUMBER_OF_DENOMINATIONS = 7;

    /**
     * Constructor instantiates a super object.
     */
    public Bill()
    {
        super( NUMBER_OF_DENOMINATIONS );
    } // End Bill constructor

    /**
     * Gets the denomination value of a paper bill.
     *
     * @return The denomination value of a paper bill.
     */
    public double getValue()
    {
        return ( double ) DENOMINATION_VALUE[getDenomination()];
    } // End getValue

    /**
     * Display information about the paper bill object.
     *
     * @return String of paper bill denomination name.
     */
    public String toString()
    {
        return DENOMINATION_NAME[getDenomination()];
    } // End toString
} // End Bill
