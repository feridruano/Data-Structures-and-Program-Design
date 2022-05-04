import java.text.DecimalFormat;
import java.util.*;

/**
 * A class that implements math operations utilizing a stack.
 *
 * @author Ferid Ruano
 * @version 02/16/2019
 */
public class FunWithStack
{
    public void decimalToBinary()
    {
        System.out.println( "DECIMAL TO BINARY CONVERTER" );
        // TODO PROJECT #1 - Done
        Scanner keyboard = new Scanner( System.in );
        Stack< Integer > stack = new Stack<>();
        try
        {
            do
            {
                System.out.println( "\nPlease enter a positive integer, or type \"stop\"" );
                int decimalNumber = keyboard.nextInt();

                System.out.print( decimalNumber + " in binary is --> " );

                // YOUR CODE GOES HERE
                while ( decimalNumber != 0 )
                {
                    stack.add( decimalNumber % 2 ); // Get remainder, add to stack
                    decimalNumber /= 2; // Divide by 2
                }

                // Print Binary Number
                while ( !stack.isEmpty() )
                    System.out.print( stack.pop() ); // Pop and print remainders
                System.out.println(); // Newline
            }
            while ( true );
        }
        catch ( InputMismatchException ime )
        {
            System.out.println( "Done with conversion.\n" );
        }
    }

    public void ancientMultiplier()
    {
        // TODO PROJECT #1
        // http://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
        Stack< Integer > op1 = new Stack<>();
        Stack< Integer > op2 = new Stack<>();

        System.out.println( "Ancient Multiplier" );
        // TODO PROJECT #1 - Done
        Scanner keyboard = new Scanner( System.in );
        try
        {
            do
            {
                // Ask for operands
                System.out.println( "\nPlease enter operand 1, or type \"stop\"" );
                int largest = keyboard.nextInt(); // Assume first input is largest
                System.out.println( "\nPlease enter operand 2" );
                int smallest = keyboard.nextInt(); // Assume second input is smallest

                // If necessary, set smallest operand
                if ( smallest > largest )
                {
                    int temp = smallest;
                    smallest = largest; // smallest = largest
                    largest = temp; // largest = smallest
                }

                // Mapping table algorithm
                System.out.printf( "The smaller operand is: %d; and the larger operand is: %d%n", smallest, largest );
                System.out.println( "Creating the mapping table:" );
                int mediation = 1; // Halving smallest (In Reverse, So Doubling)
                int duplation = largest; // Doubling largest
                while ( mediation <= smallest )
                {
                    op1.push( mediation ); // op1 stack stores mediation
                    op2.push( duplation ); // op2 stack stores duplication
                    System.out.printf( "%,d --> %,d %n", mediation, duplation );
                    mediation += mediation; // Halving smallest (In Reverse, So Doubling)
                    duplation += duplation; // Doubling largest
                }

                // Calculate and output result
                System.out.println( "\n---> Calculating the result" );
                System.out.printf( "%,d * %,d is: ", smallest, largest );
                int result = 0;
                mediation = 0; // Reset - (In Reverse, So Doubling)
                while ( mediation != smallest )
                {
                    if ( mediation + op1.peek() <= smallest )
                    {
                        mediation += op1.pop(); // Set new mediation value
                        int temp = op2.pop(); // Get duplation value
                        result += temp; // Add duplation value to result
                        System.out.printf( "%,d", temp ); // Output duplation of mediation
                        System.out.printf( mediation == smallest ? " = %,d" : " + ", result );
                    }
                    else
                    {
                        // Pop from mapping table
                        op1.pop();
                        op2.pop();
                    }
                }
            }
            while ( true );
        }
        catch ( InputMismatchException ime )
        {
            System.out.println( "Done with multiplying.\n" );
        }
    }

    public ArrayList< Integer > noAdjacentDuplicates( int... input )
    {
        // TODO PROJECT #1 - Need Help
        ArrayList< Integer > result = new ArrayList<>();
        Stack< Integer > stack = new Stack<>();

        System.out.println( "input = " + Arrays.toString( input ) );

        int index = 0;
        boolean duplicate = false;

        // Iterate input one by one, adding to stack and preventing duplicates
        while ( index < input.length )
        {
            int newEntry = input[index]; // Set newEntry

            // Empty Stack, then push newEntry
            if ( stack.isEmpty() )
            {
                stack.push( newEntry );
                index++;
            }
            // newEntry equals top entry of stack
            else if ( newEntry == stack.peek() )
            {
                duplicate = true;
                index++;
            }
            // newEntry not equal to top entry of stack
            else
            {
                // duplicate flag is true
                if ( duplicate )
                {
                    stack.pop();
                    duplicate = false;
                }
                // duplicate flag is false
                else
                {
                    stack.push( newEntry );
                    index++;
                }
            }
        }

        // Check duplicate flag of last input entry
        if ( duplicate )
            stack.pop();

        // Print stack in reverse (correct order)
        while ( !stack.isEmpty() )
        {
            result.add( 0, stack.pop() ); // Add to beginning of arrayList
        }
        return result;
    }

    public static void main( String[] args )
    {
        FunWithStack funWithStack = new FunWithStack();
        System.out.println( "*** DECIMAL TO BINARY CONVERTER ***" );
        funWithStack.decimalToBinary();
        System.out.println( "*** ANCIENT MULTIPLIER ***" );
        funWithStack.ancientMultiplier();

        System.out.println( "*** ELIMINATING ADJACENT DUPLICATES ***" );

        System.out.println( "--> testcase #1" );
        ArrayList< Integer > result = funWithStack.noAdjacentDuplicates( 1, 5, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5 );
        ArrayList< Integer > expected = new ArrayList<>();
        expected.add( 1 );
        if ( result.equals( expected ) )
            System.out.println( "result = " + result + " CORRECT" );
        else
        {
            System.out.println( "INCORRECT, expected: " + expected );
            System.out.println( "got: " + result );
        }

        System.out.println( "\n---> testcase #2" );
        result = funWithStack.noAdjacentDuplicates( 1, 9, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5 );
        expected.clear();
        expected.add( 1 );
        expected.add( 9 );
        expected.add( 5 );
        if ( result.equals( expected ) )
            System.out.println( "result = " + result + " CORRECT" );
        else
        {
            System.out.println( "INCORRECT, expected: " + expected );
            System.out.println( "got: " + result );
        }

        System.out.println( "\n---> testcase #3" );
        result = funWithStack.noAdjacentDuplicates( 1, 1, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5 );
        expected.clear();
        expected.add( 5 );
        if ( result.equals( expected ) )
            System.out.println( "result = " + result + " CORRECT" );
        else
        {
            System.out.println( "INCORRECT, expected: " + expected );
            System.out.println( "got: " + result );
        }

        System.out.println( "\n---> testcase #4" );
        result = funWithStack.noAdjacentDuplicates( 1, 1, 1, 5, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5 );
        expected.clear();
        if ( result.equals( expected ) )
            System.out.println( "result = " + result + " CORRECT" );
        else
        {
            System.out.println( "INCORRECT, expected: " + expected );
            System.out.println( "got: " + result );
        }

        System.out.println( "Done!" );
    }
}