/**
 * @author Ferid Ruano
 * @version 4/16/2019
 */

public class CarID implements Comparable< CarID >
{
    // Fields
    private String characterSequence;
    private long numericSequence;
    public static final int CHARACTER_SEQUENCE_LENGTH = 3;
    public static final int NUMERIC_SEQUENCE_LENGTH = 14;
    private final String DEFAULT_CHARACTER_SEQUENCE = "???";
    private final long DEFAULT_NUMERIC_SEQUENCE = 100000000000000L;

    /**
     * Default constructor
     *
     * @param characterSequence Letter sequence code.
     * @param numericSequence   Digit sequence code.
     */
    public CarID( String characterSequence, long numericSequence )
    {
        // Calls mutator methods to validate passed CarID fields
        setCharacterSequence( characterSequence );
        setNumericSequence( numericSequence );
    }

    /**
     * Mutator method to set characterSequence field.
     *
     * @param characterSequence Letter sequence code.
     */
    public void setCharacterSequence( String characterSequence )
    {
        // Check for valid charSequence of 3 uppercase letters
        String validRegex = "[A-Z]{3}";
        if ( characterSequence.matches( validRegex ) )
            this.characterSequence = characterSequence;
        else
            this.characterSequence = DEFAULT_CHARACTER_SEQUENCE;
    }

    /**
     * Mutator method to set numericSequence field.
     *
     * @param numericSequence Digit sequence code.
     */
    public void setNumericSequence( long numericSequence )
    {
        // Check for valid numericSequence of 14 digits
        if ( ( int ) Math.log10( numericSequence ) + 1 == NUMERIC_SEQUENCE_LENGTH )
            this.numericSequence = numericSequence;
        else
            this.numericSequence = DEFAULT_NUMERIC_SEQUENCE;
    }

    /**
     * Accessor method to get characterSequence field.
     *
     * @return characterSequence
     */
    public String getCharacterSequence()
    {
        return this.characterSequence;
    }

    /**
     * Accessor method to get numericSequence field.
     *
     * @return numericSequence
     */
    public long getNumericSequence()
    {
        return this.numericSequence;
    }

    /**
     * Compare two objects for equality.
     *
     * @param obj Object to compare with.
     * @return True if equal objects, otherwise false.
     */
    public boolean equals( Object obj )
    {
        boolean same;
        if ( this == obj )
            same = true;
        else if ( !( obj instanceof CarID ) )
            same = false;
        else
        {
            same = this.characterSequence.equals( ( ( CarID ) obj ).characterSequence )
                   && this.numericSequence == ( ( CarID ) obj ).numericSequence;
        }
        return same;
    }

    /**
     * Compare CarIDs for organization.
     *
     * @param o CarID to compare with.
     * @return 0 if equal CarIDs, >1 if greater than, or <1 if less than.
     */
    public int compareTo( CarID o )
    {
        int result = this.characterSequence.compareTo( o.characterSequence );
        if ( result == 0 )
            result = Long.compare( this.numericSequence, o.numericSequence );
        return result;
    }

    /**
     * Create a hash for CarID.
     *
     * @return Hashcode for CarID.
     */
    public int hashCode()
    {
        int G = 31;
        int shift = 32;
        return G * this.characterSequence.hashCode() +
               ( int ) ( this.numericSequence ^ ( this.numericSequence >> shift ) );
        // Or ... + Long.hashcode(this.numericSequence) - Above is one less method call.
    }

    /**
     * Output CarID data.
     *
     * @return String of CarID data.
     */
    public String toString()
    {
        return "CarID{" + this.characterSequence + " " + this.numericSequence + "}";
    }
}
