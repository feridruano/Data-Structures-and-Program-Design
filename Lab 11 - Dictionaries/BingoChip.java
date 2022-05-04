/**
 * @author Ferid Ruano
 * @version 4/9/2019
 */

public class BingoChip
{
    private char letter;
    private int number;

    /**
     * Secondary constructor
     *
     * @param number Randomly selected number.
     */
    public BingoChip( int number )
    {
        setNumber( number );
        setLetter();
    }

    /**
     * Mutator method to set number field.
     *
     * @param number Randomly selected number.
     */
    private void setNumber( int number )
    {
        // TODO Project 2.1 - Done
        this.number = number;
    }

    /**
     * Mutator method to calculate and set letter
     * field based on number field.
     */
    private void setLetter()
    {
        // TODO Project 2.1 - Done
        this.letter = BingoCard.BINGO_KEYS.charAt( ( this.number - 1 ) / BingoCard.MAX_VALUES_PER_LETTER );
    }

    /**
     * Accessor method to get number field.
     *
     * @return Randomly selected number.
     */
    public int getNumber()
    {
        // TODO Project 2.1 - Done
        return this.number;
    }

    /**
     * Accessor method to get letter field.
     *
     * @return Character calculated from number field.
     */
    public char getLetter()
    {
        // TODO Project 2.1 - Done
        return this.letter;
    }

    /**
     * Output BingoChip data.
     *
     * @return String of BingoChip data.
     */
    public String toString()
    {
        // TODO Project 2.1 - Done
        return "Bingo Chip: Letter " + this.letter + " and number " + this.number;
    }
}