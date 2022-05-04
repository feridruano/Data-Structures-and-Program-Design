/**
 * A class that represents a job.
 *
 * @author Ferid Ruano
 * @version 3/19/2019
 */

public class Job implements Comparable< Job >
{
    // Instance Variables
    private int jobNo;
    private int priority;
    private int createdAtTime;
    private int timeLeft;
    private static int jobsCreated = 1; // Static - Data shared with all instances of job class

    /**
     * Secondary Constructor
     *
     * @param priority      Job object priority or precedence.
     * @param createdAtTime Time the job was created.
     * @param timeLeft      Time remaining to execute job object.
     */
    Job( int priority, int createdAtTime, int timeLeft )
    {
        this.jobNo = jobsCreated;
        this.priority = priority;
        this.createdAtTime = createdAtTime;
        this.timeLeft = timeLeft;
        jobsCreated++;
    }

    /**
     * Compares job object priorities.
     *
     * @param job Job object to compare to.
     * @return Int value representing if this object is greater than, less than, or equal to other object.
     */
    public int compareTo( Job job )
    {
        // Compare priority only
        return this.priority - job.priority;
    }

    /**
     * Gets the time remaining of a job object.
     *
     * @return Time remaining of job object.
     */
    public int getTimeLeft()
    {
        return this.timeLeft;
    }

    /**
     * Gets the number of jobs created.
     *
     * @return Number of jobs created.
     */
    public static int getJobCreated()
    {
        return jobsCreated;
    }

    /**
     * Updates the time left of the job object.
     *
     * @param timeLeft New time remaining for job object.
     */
    public void update( int timeLeft )
    {
        this.timeLeft = timeLeft;
    }

    /**
     * Checks if the job object is done executing.
     *
     * @return True if job object's time left is == 0.
     */
    public boolean isFinished()
    {
        return this.timeLeft == 0;
    }

    /**
     * Output job object data.
     *
     * @return A String containing job object data.
     */
    public String toString()
    {
        return "Job #" + this.jobNo + " priority(" + this.priority + ") created at " +
               this.createdAtTime + ", time left " + this.timeLeft;
    }
}
