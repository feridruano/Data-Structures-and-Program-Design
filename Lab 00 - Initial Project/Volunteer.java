/**
 * Volunteer class
 *
 * @name: Ferid Ruano
 * @class: COMP 151 - T/TH 3PM
 * @author: atb
 * @version: 12/17/2018
 */

public class Volunteer extends StaffMember
{
    public Volunteer( String eName, String eAddress, String ePhone, String socSecNumber )
    {
        super( eName, eAddress, ePhone, socSecNumber );
    }

    //-----------------------------------------------------------------
    //  Returns a zero pay value for this volunteer.
    //-----------------------------------------------------------------
    public double calculatePayment()
    {
        // TODO - Done
        return 0.00; // Zero Dollars
    }

    public String toString()
    {
        // TODO - Done
        // Return details of Volunteer
        return "--- Volunteer --- Name: " + getName() +
               "\nAddress: " + getAddress() +
               "\nPhone: " + getPhone() +
               "\nSocial Security Number: " + getSsn();
    }
}
