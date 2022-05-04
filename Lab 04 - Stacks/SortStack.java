/** * A class that provides methods for sorting a stack of * Comparable objects, placing the smallest at the top of the stack. * * @author Ferid Ruano * @version 02/17/2019 */import java.util.*;public class SortStack< T extends Comparable< ? super T > >{    private Stack< T > stack;    public SortStack()    {        this.stack = new Stack<>();    }    /**     * Sorts a stack.     *     * @return a sorted stack     */    public Stack< T > sort()    {        // TODO PROJECT #5 Part1        // this.stack represents the original stack        Stack< T > destination = new Stack<>();        Stack< T > temp = new Stack<>();        while ( !this.stack.isEmpty() )        {            T entry = stack.pop(); // Pop from original stack            // Check if destination stack is empty            if ( destination.isEmpty() )            {                System.out.println( "push " + entry + " from original to destination" );                destination.push( entry );            }            // Destination stack is not empty            else            {                System.out.println( "Moving entries from destination to temp" );                // While entry is greater than top entry of destination stack                while ( !destination.isEmpty() && entry.compareTo( destination.peek() ) > 0 )                {                    T tempEntry = destination.pop(); // Store popped entry                    System.out.println( "--> push " + tempEntry + " from destination to temp" );                    temp.push( tempEntry ); // Push popped entry                }                System.out.println( "push " + entry + " to destination" );                destination.push( entry ); // Push entry from stack original                System.out.println( "Moving entries from temp to destination" );                // While temp stack is not empty move to destination                while ( !temp.isEmpty() )                {                    T tempEntry = temp.pop(); // Store popped entry                    System.out.println( "--> push " + tempEntry + " from destination to temp" );                    destination.push( tempEntry ); // Push popped entry                }            }        }        return destination;    }    /**     * Sorts a stack. (revised version)     *     * @return a sorted stack     */    public Stack< T > sortRevised()    {        // TODO PROJECT #5 Part2        // this.stack represents the original stack        Stack< T > destination = new Stack<>();        Stack< T > temp = new Stack<>();        while ( !this.stack.isEmpty() )        {            T entry = stack.pop(); // Pop from original stack            // Check if destination stack is empty            if ( destination.isEmpty() )            {                System.out.println( "push " + entry + " from original to destination" );                destination.push( entry );            }            // Destination stack is not empty            else            {                System.out.println( "Moving entries from destination to temp" );                // While entry is greater than top entry of destination stack                while ( !destination.isEmpty() && entry.compareTo( destination.peek() ) > 0 )                {                    T tempEntry = destination.pop(); // Store popped entry                    System.out.println( "--> push " + tempEntry + " from destination to temp" );                    temp.push( tempEntry ); // Push popped entry                }                System.out.println( "Moving entries from temp to destination" );                // While temp stack is not empty move to destination                while ( !temp.isEmpty() && entry.compareTo( temp.peek() ) < 0 )                {                    T tempEntry = temp.pop(); // Store popped entry                    System.out.println( "--> push " + tempEntry + " from destination to temp" );                    destination.push( tempEntry ); // Push popped entry                }                System.out.println( "push " + entry + " from original to destination" );                destination.push( entry ); // Push entry from original stack            }        }        System.out.println( "Moving any remaining entries from temp to destination" );        // While temp stack is not empty move entries to destination        while ( !temp.isEmpty() )        {            T tempEntry = temp.pop(); // Store popped entry            System.out.println( "--> push " + tempEntry + " from destination to temp" );            destination.push( tempEntry ); // Push popped entry        }        return destination;    }    public void setStack( T... elements )    {        this.stack.clear();        System.out.println( "Setting the original stack to:" );        for ( int i = 0; i < elements.length; i++ )        {            this.stack.push( elements[i] );            System.out.print( elements[i] + " " );        }        System.out.println();    }    public static void main( String[] args )    {        SortStack sc = new SortStack();        sc.setStack( "03", "09", "01", "04", "06", "05", "07", "08", "00", "02" );        System.out.println( "\n***Calling sort method***" );        Stack< String > sortedStack = sc.sort();        System.out.println( "\nStack should be sorted (with sort()) ...." );        while ( !sortedStack.isEmpty() )            System.out.print( sortedStack.pop() + " " );        System.out.println();        System.out.println( "\n===================================" );        System.out.println( "\nTesting the revised method" );        sc.setStack( "03", "09", "01", "04", "06", "05", "07", "08", "00", "02" );        System.out.println( "\n***Calling sortRevised method***" );        sortedStack = sc.sortRevised();        System.out.println( "\nStack should be sorted (with sortRevised()) ...." );        while ( !sortedStack.isEmpty() )            System.out.print( sortedStack.pop() + " " );        System.out.println();    } // end main} // end SortStack