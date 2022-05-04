/**
 * @author Ferid Ruano
 * @version 4/9/2019
 */

import java.util.*;

public class PlayBingo
{
    public static void main( String[] args )
    {
        System.out.println( "---> Setting up bingo game." );

        Scanner scan = new Scanner( System.in );
        int numOfPlayers;
        do
        {
            System.out.println( "Enter number of players." );
            numOfPlayers = scan.nextInt();

            // Negatives numbers create only 1 play
            if ( numOfPlayers < 0 )
                numOfPlayers = 1;

        }
        while ( numOfPlayers == 0 );

        BingoGame game = new BingoGame( numOfPlayers );

        System.out.println( "---> Starting the game with " + numOfPlayers + " players:" );
        System.out.println( "     *********************************\n" );
        game.play();
    }
}