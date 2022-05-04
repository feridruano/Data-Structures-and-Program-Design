import java.util.Scanner;

/**
 * The class recursively generates strings of n numbers
 *
 * @author Ferid Ruano
 * @version 2/19/2019
 */

public class StringsOfNumbers
{
    /**
     * bitString - a recursive function that generates strings of n bits
     *
     * @param n   the number of digits
     * @param str one permutation of the string to be constructed and printed
     */
    private static void bitString( String str, int n )
    {
        // TODO Project #4 - Done

        // Base case
        if ( n == 0 )
            System.out.println( str ); // Print string of bits
        else
        {
            bitString( str + "0", n - 1 ); // For all bit strings starting with a 0, then append 0 or 1
            bitString( str + "1", n - 1 ); // For all bit strings starting with a 1, then append 0 or 1
        }
    }

    /**
     * bitString - a recursive function that generates strings of n digits [0..k)
     *
     * @param n   the number of digits
     * @param k   k-1 should be the largest digit in the string str
     * @param str one permutation of the string to be constructed and printed
     */
    private static void kString( String str, int n, int k )
    {
        // TODO Project #4 - Done

        // Base case
        if ( n == 0 )
            System.out.println( str ); // Print string of digits
        else
        {
            // Append digits from [0..k) until k-1
            for ( int digit = 0; digit < k; digit++ )
            {
                kString( str + digit, n - 1, k ); // Recursive call until n = 0
            }
        }
    }

    public static void main( String args[] )
    {
        Scanner input = new Scanner( System.in );
        System.out.println( "Please enter an integer value of n representing the number of digits in a string" );
        int n = input.nextInt();
        System.out.println();
        System.out.println( "Generating binary-Strings:" );
        bitString( "", n );
        System.out.println();
        System.out.println( "Please enter an integer value k; strings of length n will be drown from 0..k-1" );

        int k = input.nextInt();
        System.out.println( "Generating k-Strings:" );
        kString( "", n, k );
    }
}
