import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for sorting an array of Comparable objects from smallest to
 * largest.
 * <p/>
 * This code is based on code from Chapter 8 and 9 of
 * Data Structures and Abstractions with Java 4/e by Carrano
 *
 * @author Ferid Ruano
 * @version 3/4/2019
 */


public class SortArray
{

    /**************************************************************
     * ITERATIVE SELECTION SORT
     **************************************************************/


    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static < T extends Comparable< ? super T > >
    void selectionSort( T[] a, int n )
    {
        for ( int index = 0; index < n - 1; index++ )
        {
            int indexOfNextSmallest = getIndexOfSmallest( a, index, n - 1 );
            swap( a, index, indexOfNextSmallest );
            // Assertion: a[0] <= a[1] <= . . . <= a[index] <= all other a[i]
        }
    } // end selectionSort


    /**
     * Task: Finds the index of the smallest value in an array.
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0 that is the index of the first
     *              array element to consider
     * @param last  an integer >= 0 that is the index of the last
     *              array element to consider
     * @return the index of the smallest value among
     * a[first], a[first+1], . . . , a[last]
     */
    private static < T extends Comparable< ? super T > >
    int getIndexOfSmallest( T[] a, int first, int last )
    {
        T min = a[first];
        int indexOfMin = first;
        for ( int index = first + 1; index <= last; index++ )
        {
            if ( a[index].compareTo( min ) < 0 )
            {
                min = a[index];
                indexOfMin = index;
                // Assertion: min is the smallest of a[first] through a[index].
            }
        }
        return indexOfMin;
    } // end getIndexOfSmallest


    /**
     * Task: Swaps the array elements a[i] and a[j].
     *
     * @param a an array of  objects
     * @param i an integer >= 0 and < a.length
     * @param j an integer >= 0 and < a.length
     */
    private static < T >
    void swap( T[] a, int i, int j )
    {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    } // end swap


    /**************************************************************
     * ITERATIVE INSERTION SORT
     **************************************************************/


    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static < T extends Comparable< ? super T > >
    void insertionSort( T[] a, int n )
    {
        insertionSort( a, 0, n - 1 );
    } // end insertionSort


    /**
     * Task: Iterative insertion sort of the  objects in a range of locations in an array into ascending order.
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0
     * @param last  an integer > first and < a.length
     */

    private static < T extends Comparable< ? super T > >
    void insertionSort( T[] a, int first, int last )
    {
        T temp;
        boolean foundLocation;
        int loc;

        for ( int i = first + 1; i <= last; i++ )
        {
            temp = a[i];

            // move values over to make room
            loc = i - 1;  // start with value to the left
            foundLocation = false;
            while ( loc >= first && !foundLocation )
            {
                if ( a[loc].compareTo( temp ) > 0 )
                {
                    a[loc + 1] = a[loc];
                    loc--;
                }
                else
                    foundLocation = true;  // found the spot
            }

            // put the value in the right place  
            a[loc + 1] = temp;
        }
    } // end insertionSort


    /**************************************************************
     * ITERATIVE SHELL SORT
     **************************************************************/

    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static < T extends Comparable< ? super T > >
    void shellSort( T[] a, int n )
    {
        shellSort( a, 0, n - 1 );
    } // end shellSort


    /**
     * Task: Use incremental insertion sort with different increments to
     * sort a range of values in the array
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0
     * @param last  an integer > first and < a.length
     */
    private static < T extends Comparable< ? super T > >
    void shellSort( T[] a, int first, int last )
    {
        int n = last - first + 1; // number of array elements
        for ( int space = n / 2; space > 0; space = space / 2 )
        {
            for ( int begin = first; begin < first + space; begin++ )
                incrementalInsertionSort( a, begin, last, space );
        }
    } // end shellSort


    /**
     * Task: Sorts equally spaced elements of an array into
     * ascending order.
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0 that is the index of the first
     *              array element to consider
     * @param last  an integer >= first and < a.length that is the
     *              index of the last array element to consider
     * @param space the difference between the indices of the
     *              elements to sort
     */
    private static < T extends Comparable< ? super T > >
    void incrementalInsertionSort( T[] a, int first, int last, int space )
    {
        int unsorted, index;
        for ( unsorted = first + space; unsorted <= last;
              unsorted = unsorted + space )
        {
            T firstUnsorted = a[unsorted];
            for ( index = unsorted - space; ( index >= first ) &&
                                            ( firstUnsorted.compareTo( a[index] ) < 0 );
                  index = index - space )
            {
                a[index + space] = a[index];
            }
            a[index + space] = firstUnsorted;
        }
    } // end incrementalInsertionSort


    /**************************************************************
     * MERGE SORT
     **************************************************************/


    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static < T extends Comparable< ? super T > >
    void mergeSort( T[] a, int n )
    {
        mergeSort( a, 0, n - 1 );
    } // end mergeSort


    /**
     * Task: Merge sort on a portion of an array.  Creates a temporary array
     * then calls the recursive function
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0 that is the index of the first
     *              array element to consider
     * @param last  an integer >= 0 that is the index of the last
     *              array element to consider
     */
    private static < T extends Comparable< ? super T > >
    void mergeSort( T[] a, int first, int last )
    {
        T tempArray[] = ( T[] ) new Comparable< ? >[a.length];
        mergeSort( a, tempArray, first, last );
    } // end mergeSort


    /**
     * Task: recursively merge sort a portion of an array.
     *
     * @param a         an array of Comparable objects
     * @param tempArray an array used by the merge step
     * @param first     an integer >= 0 that is the index of the first
     *                  array element to consider
     * @param last      an integer >= 0 that is the index of the last
     *                  array element to consider
     */
    private static < T extends Comparable< ? super T > >
    void mergeSort( T[] a, T[] tempArray, int first, int last )
    {
        if ( last > first )        // we have some work to do
        {
            int mid = ( last + first ) / 2;
            mergeSort( a, tempArray, first, mid );
            mergeSort( a, tempArray, mid + 1, last );
            merge( a, tempArray, first, mid + 1, last );
        }

    } // end mergeSort

    /**
     * Task: Merge the elements in two contiguous sorted sublists
     *
     * @param a         an array of Comparable objects
     * @param tempArray a temporay array used in the merge
     * @param first     an integer >= 0 and < mid
     * @param mid       an integer  <= last
     * @param last      an integer  < a.length
     */
    private static < T extends Comparable< ? super T > >
    void merge( T[] a, T[] tempArray, int first, int mid, int last )
    {
        int beginHalf1 = first;
        int endHalf1 = mid - 1;
        int beginHalf2 = mid;
        int endHalf2 = last;
        // While both subarrays are not empty, compare an element in one subarray with
        //an element in the other; then copy the smaller item into the temporary array

        int index = first; //next available location in tempArray
        while ( ( beginHalf1 <= endHalf1 ) && ( beginHalf2 <= endHalf2 ) )
        {
            if ( a[beginHalf1].compareTo( a[beginHalf2] ) < 0 )

            {
                tempArray[index] = a[beginHalf1];
                beginHalf1++;
            }
            else
            {
                tempArray[index] = a[beginHalf2];
                beginHalf2++;
            }
            index++;
        }

        //Assertion: One subarray has been completely copied to tempArray.


        // copy any remaining values from the first subarray over to temp
        while ( beginHalf1 <= endHalf1 )
            tempArray[index++] = a[beginHalf1++];

        // copy any remaining values from the second subarray over to temp
        while ( beginHalf2 <= endHalf2 )
            tempArray[index++] = a[beginHalf2++];

        // copy values back
        for ( int i = first; i <= last; i++ )
        {
            a[i] = tempArray[i];
        }
    } // end merge


    /**
     * ***********************************************************
     * QUICK SORT -
     * RECURSIVE
     * MEDIAN OF THREE
     * DOES INSERTION SORT ON SMALL ARRAYS
     * ************************************************************
     */

    private static int MIN_SIZE = 7;

    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static < T extends Comparable< ? super T > >
    void quickSort( T[] a, int n )
    {
        quickSort( a, 0, n - 1 );
    }


    /**
     * Task: Recursively sorts an array into ascending order. Uses quick sort with
     * median-of-three pivot selection for arrays of at least
     * MIN_SIZE elements, and uses insertion sort for other arrays.
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0 that is the index of the first
     *              array element to consider
     * @param last  an integer >= 0 that is the index of the last
     *              array element to consider
     */
    private static < T extends Comparable< ? super T > >
    void quickSort( T[] a, int first, int last )
    {
        if ( last - first + 1 < MIN_SIZE )
        {
            insertionSort( a, first, last );
        }
        else
        {
            // Create the partition: Smaller | Pivot | Larger
            int pivotIndex = partition( a, first, last );

            // Sort subarrays Smaller and Larger
            quickSort( a, first, pivotIndex - 1 );
            quickSort( a, pivotIndex + 1, last );
        }
    }


    /**
     * Task: Sorts the first, middle, and last elements of an
     * array into ascending order.
     *
     * @param a     an array of Comparable objects
     * @param first the integer index of the first array element; first >= 0
     * @param mid   the integer index of the middle array element
     * @param last  the integer index of the last array element;
     *              last - first >= 2, last < a.length
     */
    private static < T extends Comparable< ? super T > >
    void sortFirstMiddleLast( T[] a, int first, int mid, int last )
    {
        orderTwoItems( a, first, mid ); // make a[first] <= a[mid]
        orderTwoItems( a, mid, last ); // make a[mid] <= a[last]
        orderTwoItems( a, first, mid ); // make a[first] <= a[mid]
    } // end sortFirstMiddleLast


    /**
     * Task: Orders two given array elements into ascending order
     * so that a[i] <= a[j].
     *
     * @param a an array of Comparable objects
     * @param i an integer >= 0 and < array.length
     * @param j an integer >= 0 and < array.length
     */
    private static < T extends Comparable< ? super T > >
    void orderTwoItems( T[] a, int i, int j )
    {
        if ( a[i].compareTo( a[j] ) > 0 )
            swap( a, i, j );
    } // end orderTwoItems


    /**
     * Finds recursively indexes of nine pivot candidates
     *
     * @param indexes indexes of pivot candidates
     * @param a       an array of Comparable objects
     * @param first   the integer index of the first sub-array element
     * @param mid     the integer index of the middle sub-array element
     * @param last    the integer index of the last sub-array element
     * @param size    number of elements in the sub-array
     */
    private static < T extends Comparable< ? super T > >
    void findNineCandidatePivotIndexes( ArrayList< Integer > indexes, T[] a, int first, int mid, int last, int size )
    {
        //TODO Project 4 - Done
        final int NUMBER_OF_SUB_ARRAYS = 8;
        int numOfElements = last - first + 1;

        // Base case
        if ( numOfElements > NUMBER_OF_SUB_ARRAYS )
            indexes.add( mid );

        // Number of elements is greater than the total possible number of elements of each sub array
        if ( numOfElements > size / NUMBER_OF_SUB_ARRAYS )
        {
            findNineCandidatePivotIndexes( indexes, a, first, first + ( mid - first ) / 2, mid, size );
            findNineCandidatePivotIndexes( indexes, a,
                                           mid + 1, ( mid + 1 ) + ( last - ( mid + 1 ) ) / 2, last, size );
        }
    }

    /**
     * Performs insertion sort on nine pivot candidates
     *
     * @param indexes indexes of pivot candidates
     * @param a       an array of Comparable objects
     */
    private static < T extends Comparable< ? super T > >
    void insertionSortNinePivotCandidates( ArrayList< Integer > indexes, T[] a )
    {
        //TODO Project 4 - Done

        // Reusing given insertionSort() method
        // First element is sorted
        int first = indexes.get( 0 );
        // Iterate through indexes arrayList
        int index = 0;
        int length = indexes.size();
        for ( index = indexes.get( index + 1 ); index < length; index++ )
        {
            // Set temp as a[] element at specific index stored in indexes arrayList
            T temp = a[indexes.get( index )];

            // Move values over to make room
            int loc = index - 1; // Start with value to the left
            boolean foundLocation = false;

            // a[] index from sorted
            while ( loc >= first && !foundLocation )
            {
                // Get index entries within indexes arrayList
                int indexAtLoc = indexes.get( loc );
                int indexAfterLoc = indexes.get( loc + 1 );

                // Compare unsorted to sorted entries
                if ( a[indexAtLoc].compareTo( temp ) > 0 )
                {
                    a[indexAfterLoc] = a[indexes.get( loc )];
                    loc--;
                }
                else
                    foundLocation = true; // Found the spot
            }
            // Put the value in the right place
            a[indexes.get( loc + 1 )] = temp;
        }
    }

    /**
     * Task: Partitions an array as part of quick sort into two subarrays
     * called Smaller and Larger that are separated by a single
     * element called the pivot.
     * Elements in Smaller are left of the pivot and <= pivot.
     * Elements in Larger are right of the pivot and >= pivot.
     *
     * @param a     an array of Comparable objects
     * @param first the integer index of the first array element;
     *              first >= 0
     * @param last  the integer index of the last array element;
     *              last >= first; last < a.length
     * @return the index of the pivot
     */
    private static < T extends Comparable< ? super T > >
    int partition( T[] a, int first, int last )
    {
        //TODO Project 4 - Done

        int mid = first + ( last - first ) / 2; // Calculate mid
        int numOfElements = last - first + 1; // Calculate number of elements in array

        // Number of elements is exactly 7 or between 8 and 40 inclusively, then pivotIndex is mid
        int pivotIndex = mid; // Set pivotIndex as mid

        // No need for if statements when equal to 7 or between 8 and 40. Flow continue as median of three
        // Number of elements is greater than 40, then do median of nine
        if ( numOfElements > 40 )
        {
            // Create an arrayList to pass as an argument
            ArrayList< Integer > indexes = new ArrayList<>( 9 );
            // Add already known indexes of median of nine
            indexes.add( first );
            indexes.add( last );

            // Find median of nine indexes
            findNineCandidatePivotIndexes( indexes, a, first, mid, last, numOfElements );
            // Sort median of nine indexes
            Collections.sort( indexes );
            // Sort the nine elements in the array
            insertionSortNinePivotCandidates( indexes, a );
            // Set new pivotIndex once indexes are sorted in ascending order
            pivotIndex = indexes.get( indexes.size() / 2 );
        }

        // Flow continues as median of three
        sortFirstMiddleLast( a, first, mid, last );

        if ( numOfElements > 3 )
        {
            // Move pivot to next-to-last position in array
            swap( a, mid, last - 1 );
            pivotIndex = last - 1;
        }

        // done choosing pivot
        // at this point pivot is moved to the appropriate spot in the array
        if ( numOfElements > 3 )
        {
            T pivotValue = a[pivotIndex];

            // Determine subarrays Smaller = a[first..endSmaller]
            // and                 Larger  = a[endSmaller+1..last-1]
            // such that entries in Smaller are <= pivotValue and
            // entries in Larger are >= pivotValue; initially, these subarrays are empty

            int indexFromLeft = first + 1;
            int indexFromRight = last - 2;

            boolean done = false;
            while ( !done )
            {
                // Starting at beginning of array, leave entries that are < pivotValue;
                // locate first entry that is >= pivotValue; you will find one,
                // since last entry is >= pivot
                while ( a[indexFromLeft].compareTo( pivotValue ) < 0 )
                    indexFromLeft++;

                // Starting at end of array, leave entries that are > pivot;
                // locate first entry that is <= pivot; you will find one,
                // since first entry is <= pivot

                while ( a[indexFromRight].compareTo( pivotValue ) > 0 )
                    indexFromRight--;

                assert a[indexFromLeft].compareTo( pivotValue ) >= 0 &&
                       a[indexFromRight].compareTo( pivotValue ) <= 0;

                if ( indexFromLeft < indexFromRight )
                {
                    swap( a, indexFromLeft, indexFromRight );
                    indexFromLeft++;
                    indexFromRight--;
                }
                else
                    done = true;
            }

            // Place pivotValue between the subarrays Smaller and Larger
            swap( a, pivotIndex, indexFromLeft );
            pivotIndex = indexFromLeft;

            // Assertion:
            //   Smaller = a[first..pivotIndex-1]
            //   Pivot = a[pivotIndex]
            //   Larger = a[pivotIndex+1..last]
        }
        return pivotIndex;
    } // end partition


    /**************************************************************
     *
     * COUNTING SORT
     *
     **************************************************************/
    /**
     * Making only one pass through the array, counts the number of times
     * each integer occurs in the array. These counts are then used
     * to sort the array
     *
     * @param a        array to be sorted
     * @param maxValue the maximum possible value in the array
     */
    public static void countingSort( int[] a, int maxValue )
    {
        //TODO Project 2 - Done

        // Create Counter array
        int[] counter = new int[maxValue + 1];

        // Iterate through a[]
        for ( int decimal : a )
            counter[decimal]++;

        int openIndex = 0; // Index for tracking a[]'s next available space
        // Iterator through counter[]
        for ( int iterator = 0; iterator < counter.length; iterator++ )
            // Add decimal to a[] in sorted order
            while ( counter[iterator] > 0 )
            {
                a[openIndex] = iterator; // Add entries to a[]
                counter[iterator]--; // Decrement counter[iterator] entry
                openIndex++; // Increment openIndex
            }
    } // end countingSort


    /**************************************************************
     *
     * BINARY RADIX SORT
     *
     **************************************************************/


    /**
     * Utilizing radix sort algorithm sorts the values based on their internal binary
     * representation
     *
     * @param a         array containing integers to be sorted
     * @param maxNumber the largest possible value if the array
     */
    public static void binaryRadixSort( Integer[] a, int maxNumber )
    {
        //TODO Project 3 - Done

        int indexBucket0;
        int indexBucket1;
        final int NUMBER_OF_BUCKETS = 2;
        Integer[][] buckets = new Integer[NUMBER_OF_BUCKETS][a.length];

        // Point temp array to a[]
        Integer[] temp = a; // Memory Address of a[]

        // Number of binary places to check
        int bitsToCheck = Integer.SIZE - Integer.numberOfLeadingZeros( maxNumber );
        int bitMask = 1; // Create a bitMask for bitPosition

        for ( int bitPosition = 0; bitPosition < bitsToCheck; bitPosition++ )
        {
            // Reset bucket indexes to 0
            indexBucket0 = 0;
            indexBucket1 = 0;

            // Iterate through a[]
            for ( int entry : a )
            {
                // Isolated bit with bitMask
                int isolatedBit = ( entry & bitMask ) >> bitPosition;
                // Add isolatedBit to appropriate bucket
                if ( isolatedBit == 0 )
                {
                    buckets[0][indexBucket0] = entry;
                    indexBucket0++;
                }
                else
                {
                    buckets[1][indexBucket1] = entry;
                    indexBucket1++;
                }
            }

            // Add bucket1 to bucket0
            for ( int index = 0; index < indexBucket1; index++ )
            {
                buckets[0][indexBucket0] = buckets[1][index];
                buckets[1][index] = null;
                indexBucket0++;
            }

            // Swap pointers
            a = buckets[0]; // a[] points to sorted bucket[0] memory address
            buckets[0] = temp; // Bucket[0] points to original a[] memory address

            // Left Shift bitMask
            bitMask = Integer.rotateLeft( bitMask, 1 );
        } // end "bitPosition" for

        // Manually copy array if only one bit to check
        if ( bitsToCheck == 1 )
            System.arraycopy( a, 0, temp, 0, a.length );

    } // end BinaryRadixSort
}// end SortArray