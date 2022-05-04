import java.util.*;

/**
 * This class creates all permutations of the given string
 *
 * @author Ferid Ruano
 * @version 2/26/2019
 */
public class UniqueAnagrams
{
    private ArrayList< String > anagrams;

    public UniqueAnagrams()
    {
        this.anagrams = new ArrayList<>();
    }

    public void permutations( String word )
    {
        permutations( "", word );
        System.out.println( "Possible anagrams = " + this.anagrams );

        TreeSet< String > toTest = new TreeSet( this.anagrams );
        System.out.println( "Expected unique and sorted anagrams = " + toTest );
        System.out.println();

        sort(); // duplicates will be removed during the sorting process
    }

    private void permutations( String prefix, String suffix )
    {
        int numOfChars = suffix.length();
        if ( numOfChars == 1 )
        {
            //System.out.println(prefix + suffix);
            this.anagrams.add( prefix + suffix );
        }
        else
        {
            //TODO Project2 - Done

            // Iterate suffix string
            for ( int i = 0; i < numOfChars; i++ )
                // Concatenate a specific suffix char to prefix, then separate suffix and repeat recursively
                permutations( prefix + suffix.charAt( i ), suffix.substring( 0, i ) + suffix.substring( i + 1 ) );
        }
    }

    private void sort()
    {
        //TODO Project2 - Done

        // Iterate through arrayList and selectively sort anagrams
        for ( int index = 0; index < this.anagrams.size() - 1; index++ )
        {
            int indexOfNextSmallest = getIndexOfSmallestAndRemoveDuplicates( index, this.anagrams.size() );
            swap( index, indexOfNextSmallest );
        }
    }

    private int getIndexOfSmallestAndRemoveDuplicates( int first, int last )
    {
        //TODO Project2 - Done

        // Set first element in arrayList as smallest
        String smallest = this.anagrams.get( first );
        int indexOfSmallest = first;

        // Iterate through arrayList searching for smallest - [1...last]
        for ( int iterator = first + 1; iterator < last; iterator++ )
        {
            String temp = this.anagrams.get( iterator );

            // Compare arrayList iterator element with smallest
            if ( temp.compareTo( smallest ) < 0 )
            {
                smallest = temp;
                indexOfSmallest = iterator;
            }
        }

        // Starting at smallest element index
        // Prevents the removal of the first instance of smallest in arrayList
        int iterator = indexOfSmallest + 1;
        while ( iterator < last )
        {
            String temp = this.anagrams.get( iterator );

            // Compare arrayList iterator element with smallest
            if ( temp.equals( smallest ) )
            {
                this.anagrams.remove( iterator );
                last--; // Decrement arrayList size
            }
            else
                iterator++;
        }
        return indexOfSmallest;
    }

    private void swap( int i, int j )
    {
        //TODO Project2 - Done

        String temp = this.anagrams.get( i ); // Store this.anagram at index i as temp value
        this.anagrams.set( i, this.anagrams.get( j ) ); // Set this.anagrams at index i as index j
        this.anagrams.set( j, temp ); // Set this.anagrams at index j as temp value
    }

    private void display()
    {
        System.out.println( "Computed unique and sorted anagrams = " + this.anagrams );
    }

    public static void main( String[] args )
    {
        UniqueAnagrams uniqueAnagrams = new UniqueAnagrams();
        Scanner keyboard = new Scanner( System.in );

        System.out.println( "Please enter a word" );
        String word = keyboard.next();

        uniqueAnagrams.permutations( word );
        uniqueAnagrams.display();
    }
}
