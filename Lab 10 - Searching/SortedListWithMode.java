/**
 * A class that implements the ADT sorted list by using a chain of linked nodes.
 * Duplicate entries are allowed.
 *
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author Ferid Ruano
 * @version 4/2/2019
 */
public class SortedListWithMode< T >
{
    private SortedLinkedList< Integer > myList;

    public SortedListWithMode()
    {
        this.myList = new SortedLinkedList<>();
    }

    public void add( Integer element )
    {
        this.myList.add( element );
    }

    public String toString()
    {
        String content = "has " + this.myList.getLength() + " element(s): ";
        for ( int i = 1; i <= this.myList.getLength(); i++ )
        {
            content += this.myList.getEntry( i ) + " ";

        }
        return content;
    }

    // Part c: Using only the public methods of SortedLinkedList,
    // find the mode. The mode is the most frequent value.
    // NOTE - this list is 1 based
    public Integer getMode()
    {
        // TODO Project 2c - Done
        Integer mode = null;
        int modeCount = 0;

        // Find mode in a non-empty chain
        if ( !myList.isEmpty() )
        {
            int modeIndex = 1;
            int listLength = this.myList.getLength();
            // Iterate through ArrayList and set a mode to count
            while ( modeIndex <= listLength )
            {
                Integer currentMode = this.myList.getEntry( modeIndex ); // Get mode entry to check

                boolean same = true;
                int compareIndex = modeIndex + 1;
                // Compare entries with modeIndex object
                while ( same && compareIndex <= listLength )
                {
                    if ( this.myList.getEntry( compareIndex ).equals( currentMode ) )
                        compareIndex++;
                    else
                        same = false;
                }

                // Check for a more frequent mode
                if ( compareIndex - modeIndex > modeCount )
                {
                    modeCount = compareIndex - modeIndex;
                    mode = currentMode;
                }

                // Set new modeIndex
                modeIndex += compareIndex - modeIndex; // Jump to next mode to count frequency
            }
        }
        System.out.println( "---> mode is " + mode + "; mode count is " + modeCount );
        return mode;
    } // end getMode

    public static void main( String args[] )
    {
        SortedListWithMode< Integer > data = new SortedListWithMode<>();
        System.out.println( "The mode of the empty list should be null, got: " + data.getMode() );

        // test list of 1 element
        data.add( 9 );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 9, got: " + data.getMode() );

        // test list of 2 elements
        data.add( 13 );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 9, got: " + data.getMode() );

        // test list of 3 elements
        data.add( 13 );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 13, got: " + data.getMode() );

        // test list of 3 elements
        data = new SortedListWithMode<>();
        data.add( 9 );
        data.add( 9 );
        data.add( 13 );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 9, got: " + data.getMode() );

        data = new SortedListWithMode<>();
        for ( int i = 0; i < 10; i++ )
            data.add( i );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 0, got: " + data.getMode() );

        for ( int i = 0; i < 10; i++ )
            for ( int j = 0; j < i; j++ )
                data.add( i );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 9, got: " + data.getMode() );

        for ( int i = 0; i < 21; i++ )
            for ( int j = 8; j < i; j++ )
                data.add( i );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 20, got: " + data.getMode() );

        for ( int i = 0; i < 14; i++ )
            data.add( 6 );
        System.out.println( "\nThe data " + data );
        System.out.println( "The mode should be 6, got: " + data.getMode() );


        System.out.println( "\n*** Done ***" );
    } // end main
}
