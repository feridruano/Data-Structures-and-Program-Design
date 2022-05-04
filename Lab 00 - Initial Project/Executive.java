/**
 * Executive class
 *
 * @name: Ferid Ruano
 * @class: COMP 151 - T/TH 3PM
 * @author: atb
 * @version: 12/17/2018
 */


import java.text.DecimalFormat;

public class Executive extends StaffMember
{
    private double bonus;
    private double yearly;
    public static final int MIN_BONUS = 500;
    public static final int MAX_BONUS = 5000;
    private final int NUMBER_OF_MONTHS = 12;

    public Executive( String eName, String eAddress, String ePhone,
                      String socSecNumber, double yearly )
    {
        super( eName, eAddress, ePhone, socSecNumber );
        // TODO - Done
        // Requires 'this' keyword to prevent ambiguity
        this.yearly = yearly;
    }

    public void awardBonus( double execBonus )
    {
        // TODO - Done
        // Java compiler implicitly adds 'this' keyword, but prof requested 'this' use
        this.bonus = execBonus;
    }

    //-----------------------------------------------------------------
    //  Computes and returns the pay for an executive, which is the
    //  regular employee payment plus a one-time bonus.
    //-----------------------------------------------------------------
    public double calculatePayment()
    {
        // TODO - Done
        return yearly / NUMBER_OF_MONTHS + bonus;
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
        if ( !( o instanceof Executive ) )
        {
            same = false;
        }

        // Check if it's the same Executive. Name and SSN are most important identifiers
        Executive other = ( Executive ) o;
        if ( !( other.getName().equals( this.getName() ) && other.getSsn().equals( this.getSsn() ) ) )
        {
            same = false;
        }

        return same;
    }

    public String toString()
    {
        // TODO - Done
        DecimalFormat currencyFormat = new DecimalFormat( "'$'0.00" ); // Format Output
        // Return details of Executive
        return "--- Executive --- Name: " + getName() +
               "\nAddress: " + getAddress() +
               "\nPhone: " + getPhone() +
               "\nSocial Security Number: " + getSsn() +
               "\nYearly salary of: " + currencyFormat.format( yearly );
    }
}
