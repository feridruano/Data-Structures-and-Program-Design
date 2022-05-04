import java.security.InvalidParameterException;

/**
 * StaffMember class
 *
 * @name: Ferid Ruano
 * @class: COMP 151 - T/TH 3PM
 * @author: atb
 * @version: 12/17/2018
 */


public abstract class StaffMember
{
    private String name;
    private String address;
    private String phone;
    private String ssn;

    public StaffMember( String eName, String eAddress, String ePhone, String socSecNumber )
    {
        // TODO - Done
        // Java compiler implicitly adds 'this' keyword, but prof requested 'this' use
        this.name = eName;
        this.address = eAddress;
        this.phone = ePhone;
        this.ssn = socSecNumber;
    }

    public String getName()
    {
        // TODO - Question
        // Should we add 'this' professor on getter methods as well?
        return this.name;
    }

    public String getAddress()
    {
        // TODO - Done
        return this.address;
    }

    public String getPhone()
    {
        // TODO - Done
        return this.phone;
    }

    public void setSsn( String ssn ) throws InvalidParameterException
    {
        // TODO - Done
        this.ssn = ssn;
    }

    public String getSsn()
    {
        // TODO - Done
        return this.ssn;
    }

    public boolean equals( Object o )
    {
        boolean same = true;

        // TODO
        return same;
    }

    public String toString()
    {
        // TODO - Question
        // Should we use getName() or this.name?
        // My answer would be to use getName() for greater encapsulation, correct?
        return "--- Executive --- Name: " + getName() +
               "\nAddress: " + getAddress() +
               "\nPhone: " + getPhone() +
               "\nSocial Security Number: " + getSsn();
    }

    public abstract double calculatePayment();
}
