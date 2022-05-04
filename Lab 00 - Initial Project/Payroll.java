/**
 * Payroll class
 *
 * @name: Ferid Ruano
 * @class: COMP 151 - T/TH 3PM
 * @author: atb
 * @version: 12/17/2018
 */

import java.security.InvalidParameterException;
import java.util.*;

public class Payroll
{
    private ArrayList< StaffMember > staffList;
    private final String HOURLY = "Hourly";
    private final String EXECUTIVE = "Executive";
    private final String VOLUNTEER = "Volunteer";

    public Payroll( Scanner file )
    {
        System.out.println( "---> Reading staff data from the file" );
        // TODO - Done
        // New ArrayList
        staffList = new ArrayList<>();

        // Read and split data into tokens and create objects depending on staff type
        while ( file.hasNextLine() )
        {
            String line = file.nextLine();
            // Split line into tokens. * Data is ASSUMED to be in the following order (else errors):
            // [0] = Title, [1] = name, [2] = address, [3] = ssn, [4] = phone, [5] = pay
            String[] tokens = line.split( "," );
            System.out.println( "Processing record: " + line );

            // Check for Hourly Employee or Executive
            if ( tokens.length == 6 && ( tokens[0].equals( HOURLY ) || tokens[0].equals( EXECUTIVE ) ) )
            {
                // Check for a valid SSN (ddd-dd-dddd) and valid payment(d+.00)
                if ( tokens[4].matches( "\\d{3}-\\d{2}-\\d{4}" ) && tokens[5].matches( "\\d+.\\d{2}" ) )
                {
                    if ( tokens[0].equals( HOURLY ) )
                    {
                        // Instantiate an HourlyEmployee with data from tokens array, then add it to staffList
                        staffList.add( new HourlyEmployee( tokens[1], tokens[2], tokens[3], tokens[4], Double.parseDouble( tokens[5] ) ) );
                    }
                    else
                    {
                        // Instantiate an Executive with data from tokens array, then add it to staffList
                        staffList.add( new Executive( tokens[1], tokens[2], tokens[3], tokens[4], Double.parseDouble( tokens[5] ) ) );
                    }
                }
                // Not a valid SSN (ddd-dd-dddd) or valid payment(d+.00)
                else
                {
                    System.out.println( ">>>>> InputMismatchException in staff record - incompatible data - record: \""
                                        + line + "\" ignored <<<<<<" );
                }
            }
            // Check for Volunteer
            else if ( tokens.length == 5 && tokens[0].equals( VOLUNTEER ) )
            {
                // Check for a valid SSN (ddd-dd-dddd)
                if ( tokens[4].matches( "\\d{3}-\\d{2}-\\d{4}" ) )
                {
                    // Instantiate a Volunteer with data from tokens array, then add it to staffList
                    staffList.add( new Volunteer( tokens[1], tokens[2], tokens[3], tokens[4] ) );
                }
                // Not a valid SSN (ddd-dd-dddd) or valid payment(d+.00)
                else
                {
                    System.out.println( ">>>>> InputMismatchException in staff record - incompatible data - record: \""
                                        + line + "\" ignored <<<<<<" );
                }
            }
            // Not a valid staff member produce an exception
            else
            {
                try
                {
                    // Not a valid staff member check
                    if ( !tokens[0].equals( HOURLY ) && !tokens[0].equals( EXECUTIVE ) &&
                         !tokens[0].equals( VOLUNTEER ) )
                    {
                        throw new InvalidParameterException();
                    }
                    // Check for missing elements in the array
                    else
                    {
                        System.out.println(
                                ">>>>> NoSuchElementException in staff record - missing data - record: \"" + line +
                                "\" ignored <<<<<" );
                    }
                }
                catch ( InvalidParameterException e )
                {
                    System.out.println(
                            ">>>>> InvalidParameterException in staff record - The employee type \'" + tokens[0] +
                            "\' is not supported - record: \"" + line + "\" ignored <<<<<" );
                }

            }
        } // End while
        System.out.println( "---> Finished reading from the file\n" );
    }

    public void prepareForPayDay()
    {
        Random random = new Random( 101 );

        // TODO - Done
        // Iterate through staffList
        for ( StaffMember employee : staffList )
        {
            // Check if staffList object is an Executive
            if ( employee instanceof Executive )
            {
                int execBonus = random.nextInt( Executive.MAX_BONUS - Executive.MIN_BONUS ) + Executive.MIN_BONUS;
                ( ( Executive ) employee ).awardBonus( execBonus );
            }
            // Check if staffList object is an HourlyEmployee
            if ( employee instanceof HourlyEmployee )
            {
                int hoursWorked = random.nextInt( HourlyEmployee.MAX_HOURS - HourlyEmployee.MIN_HOURS ) +
                                  HourlyEmployee.MIN_HOURS;
                ( ( HourlyEmployee ) employee ).addHours( hoursWorked );
            }
        }
    }

    //-----------------------------------------------------------------
    //  Pays all staff members.
    //-----------------------------------------------------------------
    public double[] processPayroll()
    {
        // TODO - Done
        // Create double array to store return values
        double[] staffPayment = new double[staffList.size()];
        // Iterate through the staffList to calculate payment of each staff member
        for ( int i = 0; i < staffList.size(); i++ )
        {
            staffPayment[i] = staffList.get( i ).calculatePayment();
        }
        return staffPayment;
    }

    public ArrayList< StaffMember > getStaffList()
    {
        // TODO - Done
        return this.staffList;
    }

    public void displayStaffData()
    {
        // TODO -Done
        System.out.println( "The company has " + staffList.size() + " employees" );

        // Iterate through the staffList and print each staff member's toString method
        for ( StaffMember employee : staffList )
        {
            System.out.println( employee.toString() );
            System.out.println();
        }

    }
}
