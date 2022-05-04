// TODO Project #2a #2b

/**
 * If n is a positive integer in Java, n%10 is its rightmost digit and n/10 is the integer
 * obtained by dropping the rightmost digit from n.
 * #1a Using these facts, write a recursive method that displays an integer n in decimal
 * #1b Now observe that you can display n in any base between 2 and 9 by replacing 10
 * with the new base. Write another method to accommodate a given base.
 *
 * @author Ferid Ruano
 * @version 2/19/2019
 */
public class BaseToDecimal
{
    /**
     * Task: Recursively displays any decimal integer.
     * For example 123 would be displayed as 1 2 3
     * -123 would be displayed as - 1 2 3
     *
     * @param n an integer to be displayed
     */
    public static String displayDigits( int n )
    {
        // TODO Project #2a - Done

        // STEP#1 Base case: We have a single positive digit
        if ( n >= 0 && n < 10 ) // From 0 to 9
            return n + ""; // Print single digit
        // STEP#2 recursive case: negative number
        if ( n < 0 )
            return "- " + displayDigits( -n ); // Recursive call to positive version
        // STEP#3 recursive case: positive number with more than one digit
        return displayDigits( n / 10 ) + " " + displayDigits( n % 10 );

    } // end displayDigits

    /**
     * Task: Recursively displays any integer in a given base.
     * For example 5 in base 2 would be displayed as 1 0 1
     * 345 in base 8 would be displayed as 5 3 1
     *
     * @param n    an integer to be displayed
     * @param base an integer that is the new base of n, where 2 <= base < 10
     **/
    public static String displayDigits2to9Base( int n, int base )
    {
        // TODO Project #2b - Done

        // STEP#1 Base case: We have a single positive digit lower or equal to base
        if ( n >= 0 && n < base ) // From 0 to base
        {
            return n + ""; // Print single digit
        }
        // STEP#2 recursive case: negative number
        if ( n < 0 )
            return "- " + displayDigits2to9Base( -n, base ); // Recursive call to positive version
        // STEP#3 recursive case: positive number with more than one digit
        return displayDigits2to9Base( n / base, base ) + " " + n % base;
    } // end displayDigits

    public static void main( String args[] )
    {
        BaseToDecimal tester = new BaseToDecimal();
        tester.testValues();
    }

    public void testValues()
    {
        System.out.println( "TESTING TO SEE IF IT GETS THE CORRECT RESULTS IN BASE 10" );

        BaseToDecimal rd = new BaseToDecimal();
        int number = 5;
        String result;

        System.out.println( "Trying a 1 digit integer in base 10" );
        result = rd.displayDigits( number );
        if ( result.equals( "5" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of 5" );

        number = -5;
        System.out.println( "Trying a 1 digit negative integer in base 10" );
        result = rd.displayDigits( number );
        if ( result.equals( "- 5" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of - 5" );

        number = 345;
        System.out.println( "Trying a 3 digit positive integer in base 10" );
        result = rd.displayDigits( number );
        if ( result.equals( "3 4 5" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of 3 4 5" );

        number = -345;
        System.out.println( "Trying a 3 digit negative integer in base 10" );
        result = rd.displayDigits( number );
        if ( result.equals( "- 3 4 5" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of - 3 4 5" );

        System.out.println();
        System.out.println( "TESTING TO SEE IF IT GETS THE CORRECT RESULTS IN BASE 8" );
        number = 5;
        System.out.println( "Trying a 1 digit integer in base 8" );
        result = rd.displayDigits2to9Base( number, 8 );
        if ( result.equals( "5" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of 5" );

        number = -5;
        System.out.println( "Trying a 1 digit negative integer in base 8" );
        result = rd.displayDigits2to9Base( number, 8 );
        if ( result.equals( "- 5" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of - 5" );

        number = 345;
        System.out.println( "Trying a 3 digit positive integer in base 8" );
        result = rd.displayDigits2to9Base( number, 8 );
        if ( result.equals( "5 3 1" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of 5 3 1" );

        number = -345;
        System.out.println( "Trying a 3 digit negative integer in base 8" );
        result = rd.displayDigits2to9Base( number, 8 );
        if ( result.equals( "- 5 3 1" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of - 5 3 1" );

        System.out.println();
        System.out.println( "TESTING TO SEE IF IT GETS THE CORRECT RESULTS IN BASE 2" );
        number = 1;
        System.out.println( "Trying a 1 digit integer in base 2" );
        result = rd.displayDigits2to9Base( number, 2 );
        if ( result.equals( "1" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of 1" );

        number = 5;
        System.out.println( "Trying a 1 digit integer in base 2" );
        result = rd.displayDigits2to9Base( number, 2 );
        if ( result.equals( "1 0 1" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of 1 0 1" );

        number = 345;
        System.out.println( "Trying a 3 digit integer in base 2" );
        result = rd.displayDigits2to9Base( number, 2 );
        if ( result.equals( "1 0 1 0 1 1 0 0 1" ) )
            System.out.println( "    Passed test" );
        else
            System.out.println( "*** Failed test: returned " + result + " is not expected value of 1 0 1 0 1 1 0 0 1" );

        System.out.println();
        System.out.println();
    }
}
