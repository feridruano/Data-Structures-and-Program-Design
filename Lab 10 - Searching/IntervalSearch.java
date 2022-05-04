import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

// TODO Project 4 - Done

/**
 * A class for finding an interval in a sorted array that holds everything in a list of the target data values.
 * <p>
 * Consider an array data of n numerical values in sorted order and a list of numerical target values. Your goal is to
 * compute the smallest range of array indices that contains all of the target values.
 * <p>
 * If a target value is smaller than data[0], the range should start with -1.
 * If a target value is larger than data[n - 1], the range should end with n.
 * <p>
 * For example, given the array: 5 8 10 13 15 20 22 26 and the target values (8, 2, 9, 17), the range is -1 to 5.
 * <p>
 * Devise and implement an efficient algorithm that solves this problem.
 * HINT:
 * 1) Find the max target and min target in the list of target values.
 * 2) Utilize the iterative binary search algorithm to find the position that min target and max target values
 * would have if inserted into the list of sorted data.
 *
 * @author Ferid Ruano
 * @version 4/2/2019
 */

public class IntervalSearch
{
    /* Finds the smallest interval in the sortedData holding all the target values.
     * @param sortedData    a sorted array of comparable values (n >= 1)
     * @param targetValues  a list of comparable values (m >= 1)
     * @return an ordered pair of integers, where the first value is the largest
     *         index i in the sorted data such that data[i] is smaller than all target values.
     *         If a target value is smaller than data[0], return -1.
     *         The second value in the pair is the smallest index j in the sorted data such that
     *         data[j] is greater than all target values. If a target value is larger than
     *         the last value in data, return the n.
     */
    public static < T extends Comparable< ? super T > >
    Interval findInterval( T[] sortedData, TreeSet< T > targetValues )
    {
        int leftBoundary = -1;
        int rightBoundary = sortedData.length;
        System.out.println( "Target list is " + targetValues );

        if ( sortedData.length > 0 && targetValues.size() > 0 )
        {
            T smallestTarget = targetValues.first();
            T largestTarget = targetValues.last();

            // largestTarget is smaller than first sortedData entry
            if ( sortedData[0].compareTo( largestTarget ) > 0 )
                rightBoundary = -1;
                // smallestTarget is larger than last sortedData entry
            else if ( sortedData[rightBoundary - 1].compareTo( smallestTarget ) < 0 )
                leftBoundary = rightBoundary;
                // At least one target are withing sortedData range
            else
            {
                System.out.println( "Smallest target is " + smallestTarget
                                    + "; largest target is " + largestTarget );

                int left = 0;
                int right = rightBoundary - 1;
                boolean notfound = true;
                System.out.println( "Search on the smallest value" );

                // Iteratively binary search through sortedData
                while ( notfound && left <= right )
                {
                    int mid = left + ( right - left ) / 2;
                    int comparison = sortedData[mid].compareTo( smallestTarget );
                    // sortedData is larger
                    if ( comparison > 0 )
                        right = mid - 1;
                    // sortedData is smaller
                    else if ( comparison < 0 )
                        left = mid + 1;
                    // sortedData is equal and checks for duplicates
                    else
                    {
                        // Sequentially check for duplicates
                        while ( mid > leftBoundary + 2 && sortedData[mid - 1].equals( smallestTarget ) )
                            mid--;
                        left = mid;
                        notfound = false;
                    }
                }

                // smallestTarget not found, but has a closest value
                leftBoundary = notfound ? left - 1 : left;
                System.out.println( "\tLeft Boundary set to " + leftBoundary + "\n" );

                System.out.println( "Search on the largest value" );
                // Check if largest is out of bounds
                if ( sortedData[rightBoundary - 1].compareTo( largestTarget ) > 0 )
                {
                    left = leftBoundary;
                    right = rightBoundary - 1;
                    notfound = true;

                    // Iteratively binary search through sortedData
                    while ( notfound && left <= right )
                    {
                        int mid = left + ( right - left ) / 2;
                        int comparison = sortedData[mid].compareTo( largestTarget );
                        System.out.println( "\tLeft: " + left + " Middle: " + mid + " Right: " + right );

                        // sortedData is smaller
                        if ( comparison < 0 )
                            left = mid + 1;
                        // sortedData is larger
                        else if ( comparison > 0 )
                            right = mid - 1;
                        // sortedData is equal and checks for duplicates
                        else
                        {
                            // Sequentially check for duplicates
                            while ( mid < rightBoundary - 1 && sortedData[mid + 1].equals( largestTarget ) )
                                mid++;
                            left = mid;
                            notfound = false;
                        }
                    }

                    // largestTarget is left position
                    rightBoundary = left;
                }
                System.out.println( "\tRight Boundary set to " + rightBoundary + "\n" );
            }
        }
        return new Interval( leftBoundary, rightBoundary );
    } // end findInterval

    /**
     * Initialize the list of ints by reading them from the keyboard
     *
     * @return a list of integers
     */
    public static TreeSet< Integer > getTargetValues()
    {
        TreeSet< Integer > targets = new TreeSet< Integer >();
        System.out.println( "Enter the list of integer values separated by spaces (all on one line), " +
                            "or just press enter if you are done." );
        Scanner keyboard = new Scanner( System.in );
        Scanner dataValues = new Scanner( keyboard.nextLine() );

        while ( dataValues.hasNextInt() )
        {
            targets.add( dataValues.nextInt() );
        }
        return targets;
    }

    public static void main( String args[] )
    {
        Random generator = new Random( 7 );
        Scanner keyboard = new Scanner( System.in );
        System.out.println( "How many elements in the array?" );
        final int DEFAULT = 10;
        int size;

        try
        {
            size = keyboard.nextInt();

        }
        catch ( NumberFormatException e )
        {
            System.out.println( "Could not convert input to an integer" );
            System.out.println( e.getMessage() );
            System.out.println( "Will use " + DEFAULT + " as the default value" );
            size = DEFAULT;
        }

        // generate a random array of integers
        Integer sortedData[] = new Integer[size];
        for ( int i = 0; i < size; i++ )
        {
            sortedData[i] = generator.nextInt( 10 );

            // guarantee is larger than the last value
            if ( i > 0 && sortedData[i] < sortedData[i - 1] )
                sortedData[i] += sortedData[i - 1];
        }

        System.out.println( "The sorted data is:" );
        for ( int i = 0; i < size; i++ )
        {
            System.out.print( "[" + i + "]=" + sortedData[i] + "  " );
        }
        System.out.println();

        //get the target values
        TreeSet< Integer > targets = getTargetValues();
        while ( targets.size() > 0 )
        {
            System.out.println( "The interval is: " + findInterval( sortedData, targets ) );
            System.out.println();
            targets = getTargetValues();
        }
        System.out.println( "\n*** Done ***" );
    }
} // end SearchArray