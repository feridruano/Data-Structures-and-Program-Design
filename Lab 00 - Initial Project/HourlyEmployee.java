/**
 * HourlyEmployee class
 *
 * @name: Ferid Ruano
 * @class: COMP 151 - T/TH 3PM
 * @author: atb
 * @version: 12/17/2018
 */


public class HourlyEmployee extends StaffMember
{
    private int hoursWorked;
    private double payRate;
    public final static int MIN_HOURS = 20;
    public final static int MAX_HOURS = 100;

    public HourlyEmployee( String eName, String eAddress, String ePhone,
                           String socSecNumber, double rate )
    {
        super( eName, eAddress, ePhone, socSecNumber );
        // TODO - Done
        // Java compiler implicitly adds 'this' keyword, but prof requested 'this' use
        this.payRate = rate;
    }

    public void addHours( int moreHours )
    {
        // TODO - Done
        this.hoursWorked = moreHours;
    }

    public double calculatePayment()
    {
        // TODO - Question
        // Would we use 'this' keyword for payRate * hoursWorked?
        return payRate * hoursWorked;
    }

    public boolean equals( Object o )
    {
        boolean same = true;

        // TODO - Done
        // 'this' keyword refers to the current object and o is another instance
        if ( !( this == o ) )
        {
            same = false;
        }

        // Check if o is an instance of the Executive class
        if ( !( o instanceof HourlyEmployee ) )
        {
            same = false;
        }

        // Check if it's the same Executive. Name and SSN are most important identifiers
        HourlyEmployee other = ( HourlyEmployee ) o;
        if ( !( other.getName().equals( this.getName() ) && other.getSsn().equals( this.getSsn() ) ) )
        {
            same = false;
        }

        return same; // THIS IS A STUB
    }

    //-----------------------------------------------------------------
    //  Returns information about this hourly employee as a string.
    //-----------------------------------------------------------------
    public String toString()
    {
        // TODO - Done
        // Return details of Hourly Employee
        return "--- Hourly Employee --- Name: " + getName() +
               "\nAddress: " + getAddress() +
               "\nPhone: " + getPhone() +
               "\nSocial Security Number: " + getSsn() +
               "\nCurrent hours: " + hoursWorked + " at rate of: $" + calculatePayment();
    }
}
