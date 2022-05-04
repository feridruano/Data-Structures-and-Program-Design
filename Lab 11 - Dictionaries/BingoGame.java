/**
 * @author Ferid Ruano
 * @version 4/9/2019
 */

import java.util.*;

public class BingoGame
{
    public final static int NUMBER_OF_CHIPS = 75;
    private int numberOfPlayers;
    private ArrayList< BingoChip > bingoDrum;
    private Player[] players;

    /**
     * Default constructor
     *
     * @param numOfPlayers The number of Player objects to create.
     */
    public BingoGame( int numOfPlayers )
    {
        // TODO Project 2.4 - Done
        setNumOfPlayers(numOfPlayers);
        this.bingoDrum = new ArrayList<>(NUMBER_OF_CHIPS);
        this.players = new Player[this.numberOfPlayers];
        createBingoDrum();
        createPlayers();
    }

    /**
     * Create BingoDrum with a specified amount of chips based on field data.
     */
    private void createBingoDrum()
    {
        // TODO Project 2.4 - Done
        for ( int chipNum = 1; chipNum <= NUMBER_OF_CHIPS; chipNum++ )
            this.bingoDrum.add( new BingoChip( chipNum ) ); // Create BingoChips [1...75]
    }

    /**
     * Create a specified number of players based on field data.
     */
    private void createPlayers()
    {
        // TODO Project 2.4 - Done
        for ( int index = 0; index < this.numberOfPlayers; index++ )
        {
            System.out.printf( "---> Creating bingo card for Player %d%n", index + 1 );
            this.players[index] = new Player();
        }
    }

    /**
     * Mutator method to set numberOfPlayers field.
     *
     * @param numOfPlayers Client defined number of players.
     */
    private void setNumOfPlayers( int numOfPlayers )
    {
        // TODO Project 2.4 - Done
        this.numberOfPlayers = numOfPlayers;
    }

    /**
     * Get the number of chips pulled from BingoDrum ArrayList.
     *
     * @return
     */
    public int getNumberOfPulledChips()
    {
        // TODO Project 2.4 - Done
        return NUMBER_OF_CHIPS - this.bingoDrum.size();
    }

    /**
     * Pull a random BingoChip object from ArrayList.
     *
     * @return
     */
    public BingoChip pullChip()
    {
        // TODO Project 2.4 - Done
        Random random = new Random();
        return this.bingoDrum.remove( random.nextInt( this.bingoDrum.size() ) );
    }

    /**
     * Play BingoGame until a player wins with bingo.
     */
    public void play()
    {
        // TODO Project 2.4 - Done
        boolean noBingo = true;
        ArrayList< Integer > winningPlayersList = new ArrayList<>();
        // boolean[] winners = new boolean[this.numberOfPlayers];

        // Pull chips, check player cards, and check for winners until BINGO
        do
        {
            BingoChip chip = pullChip();
            System.out.printf( "---> Calling: %c %d%n", chip.getLetter(), chip.getNumber() );

            // Check if player has chip number
            for ( int playerNum = 0; playerNum < this.numberOfPlayers; playerNum++ )
            {
                System.out.printf( "Player's %d card:%n", playerNum + 1 ); // Players array is 0 based
                this.players[playerNum].checkCard( chip );
                this.players[playerNum].printCard();

                if ( this.players[playerNum].isWinner() )
                {
                    winningPlayersList.add( playerNum );
                    noBingo = false;
                    // winners[playerNum] = true;
                }
            }


            // No bingo, skip winners output
            if ( !noBingo )
                winningPlayersList.forEach( playerNum -> System.out.printf( "!!! Player %d says BINGO !!!%n",
                                                                            playerNum + 1 ) );

//                for ( int playerNum = 0; playerNum < this.numberOfPlayers; playerNum++ )
//                    if ( winners[playerNum] )
//                        System.out.printf( "!!! Player %d says BINGO !!!%n", playerNum + 1 );
        }
        while ( noBingo );
        System.out.printf( "%d chips were called.%n", getNumberOfPulledChips() );
    }
}