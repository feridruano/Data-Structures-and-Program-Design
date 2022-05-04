/**
 * @author Ferid Ruano
 * @version 4/9/2019
 */

import java.util.TreeSet;

public class Player
{
    private TreeSet< Character > bingoChars;
    private BingoCard bingoCard;

    /**
     * Default constructor
     */
    public Player()
    {
        // TODO 2.3 - Done
        this.bingoChars = new TreeSet<>();
        this.bingoCard = new BingoCard();
        printCard();
    }

    /**
     * Determine if player's bingoChars object has winning bingo key.
     *
     * @return True if player's bingoChars has bingo key, otherwise false.
     */
    public boolean isWinner()
    {
        // TODO 2.3 - Done
        return this.bingoChars.size() == BingoCard.BINGO_KEYS.length();
    }

    /**
     * Check if BingoCard contains randomly selected BingoChip number.
     * If BingoCard contains number, then add BingoChip letter to bingoChars.
     *
     * @param chip BingoChip object containing random number and associated letter.
     */
    public void checkCard( BingoChip chip )
    {
        // TODO 2.3 - Done
        if ( this.bingoCard.hasNumber( chip ) )
            this.bingoChars.add( chip.getLetter() );
    }

    /**
     * Print Player's bingoCard.
     */
    public void printCard()
    {
        // TODO 2.3 - Done
        System.out.println( this.bingoCard );
    }

}
