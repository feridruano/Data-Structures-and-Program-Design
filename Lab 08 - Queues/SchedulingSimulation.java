/**
 * A class that simulates scheduling job executions by a certain priority.
 *
 * @author Ferid Ruano
 * @version 3/19/2019
 */

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class SchedulingSimulation
{
    // Instance Variables
    private PriorityQueue< Job > waitingJobs;
    private ArrayDeque< Job > allJobs;
    private Job currentJob;
    public final int TIME_SLICE_PER_JOB = 3;
    public final int SIMULATION_DURATION = 100;
    public final int JOB_PROBABILITY = 30;
    public final int JOB_PRIORITY = 4;
    public final int JOB_MIN_TIME = 1;
    public final int JOB_MAX_TIME = 5;
    public final static int SORT_BY_PRIORITY = 0; // Decided not used, please view main() comment.
    public final static int SORT_BY_LENGTH = 1;

    /**
     * Secondary constructor
     *
     * @param
     */
    SchedulingSimulation( int sortBy )
    {
        // Instantiate instance variables
        currentJob = null;
        this.allJobs = new ArrayDeque<>();

        // Create a new sorted priority queue
        if ( sortBy == SORT_BY_LENGTH )
            // Sort by length - Comparator used for readability, otherwise code provided in doc
            this.waitingJobs = new PriorityQueue<>( Comparator.comparingInt( Job :: getTimeLeft ) );
        // Else
        this.waitingJobs = new PriorityQueue<>(); // Priority queue with natural ordering


        // Code with multiple sorting as well as anonymous inner class
//        if ( sortBy == SORT_BY_PRIORITY )
//            this.waitingJobs = new PriorityQueue<>();
//        else if ( sortBy == SORT_BY_LENGTH )
//            this.waitingJobs = new PriorityQueue<>( new Comparator< Job >()
//            {
//                @Override
//                public int compare( Job job1, Job job2 )
//                {
//                    return job1.getTimeLeft() - job2.getTimeLeft();
//                }
//            } );
//        else
//            throw new IllegalArgumentException( "Invalid Order Selection" );
    }

    /**
     * Begin the simulation.
     */
    public void runSimulation()
    {
        // Compiles but the algorithm is wrong.
        // I completed the other programs before break and forgot about this last method.
        // Algorithm doesn't use fields required nor executes the simulation in correct order.
        // Nor does it add unfinished jobs back into waitingJobs and updates timeleft

        Random random = new Random();
        int timeSlice = 0;
        for ( int clock = 0; clock < SIMULATION_DURATION; clock++ )
        {
            reportAtTimeMarker( clock );

            if ( currentJob == null )
                System.out.println( "\tExecuting: NONE" );
            else
            {
                if ( currentJob.isFinished() )
                {
                    System.out.println( "\tCompleted: " + currentJob.toString() );
                }
            }
            if ( timeSlice > 0 )
            {
                timeSlice--;
                System.out.println( "\tExecuting: " + currentJob.toString() );
            }
            else if ( !waitingJobs.isEmpty() )
            {
                currentJob = waitingJobs.poll();
                if ( currentJob != null )
                {
                    System.out.println( "\tCreated: " + currentJob.toString() );
                    timeSlice = currentJob.getTimeLeft() - 1;
                }
            }

            if ( random.nextInt() < JOB_PROBABILITY )
                generateJob( clock );
        }
    }

    /**
     * Create a new job.
     *
     * @param
     */
    public void generateJob( int clock )
    {
        Random dataGen = new Random();
        int priority = dataGen.nextInt( JOB_PRIORITY + 1 ) + 1;
        int timeLeft = dataGen.nextInt( JOB_MAX_TIME - JOB_MIN_TIME + 1 ) + 1;
        Job job = new Job( priority, clock, timeLeft );
        if ( currentJob == null )
            currentJob = job; // Begin job execution if processor is free
        else
            waitingJobs.offer( job ); // Otherwise offer to priority queue
        System.out.println( "\tCreated: " + job.toString() );
    }

    /**
     * @param
     */
    public void reportAtTimeMarker( int simulationTimer )
    {
        System.out.println( "Time Marker " + simulationTimer + "\twaiting: " + waitingJobs.size() );
    }

    /**
     * Generate simulation report.
     */
    public void finalSimulationReport()
    {
        DecimalFormat df = new DecimalFormat( "#0.00" );
        int jobsCreated = Job.getJobCreated();
        int jobsActive = waitingJobs.size();
        int waitJobSum = 0;

        System.out.println( "*****************  Final Report  ***************" );
        System.out.println( "Active Jobs:" );
        while ( !waitingJobs.isEmpty() )
        {
            this.currentJob = this.waitingJobs.poll();
            if ( this.currentJob != null )
            {
                waitJobSum += this.currentJob.getTimeLeft();
                System.out.println( currentJob.toString() );
            }
        }
        System.out.println( "The number of jobs currently executing is " + jobsActive );
        System.out.println( "The number of completed jobs is " + ( jobsCreated - jobsActive ) );
        System.out.println( "The total number of jobs is " + jobsCreated );
        System.out.print( "The average time left for unfinished jobs is " );
        System.out.println( df.format( ( double ) waitJobSum / jobsActive ) );
    }

    /**
     * Main Method
     */
    public static void main( String[] args )
    {
        // We could let the user select a number representing an ordering.
        // However, there are only two sorts and I decided to randomly select one.
        Random setOrder = new Random();
        boolean ordering = setOrder.nextBoolean();
        System.out.printf( "***STARTING THE SIMULATION WITH PRIORITY MODE SET TO %s***%n", ( ordering
                                                                                             ? "SORT_BY_PRIORITY"
                                                                                             : "SORT_BY_LENGTH" ) );
        SchedulingSimulation simulator = new SchedulingSimulation( ordering ? 1 : 0 );
        simulator.runSimulation();
        simulator.finalSimulationReport();
    }
}
