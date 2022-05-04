import java.text.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * A class that simulates a train line with passengers.
 *
 * @author Ferid Ruano
 * @version 3/12/2019
 */
public class TrainSimulation
{
    // an array that will hold all stations
    private Station[] allStations;
    // a queue that will hold all passengers, so we can print statistics
    // when the simulation is over
    private Queue< Passenger > allPassengers;
    // a queue that will hold all trains
    private Queue< Train > allTrains;
    // keeps track of the number of trains en route
    private int trainCount;
    // total number of passengers created
    private int passengersCreated;
    // total number of passengers on the trains
    private int passengersOnTrains;
    // total number of passengers off the trains
    private int passengersDelivered;
    // number of stations for the simulation
    private final int STATIONS = 10;
    private final int FINAL_STATION = STATIONS - 1;
    // frequency of trains departing from the station 0
    private final int TRAIN_INTERVAL = 5;
    // max number of passengers per train
    private final int TRAIN_CAPACITY = 20;
    // simulation time
    private final int DURATION = 50;
    // time range between two stations
    private final int MIN_TIME_TO_NEXT_STATION = 5;
    private final int MAX_TIME_TO_NEXT_STATION = 9;
    // max number of passengers to be randomly generated in one simulation tick
    private final int MAX_NUM_OF_PASSENGERS = 10;

    public Random generator;

    /**
     * Default constructor
     */
    public TrainSimulation()
    {
        // create an array that will hold all stations
        this.allStations = new Station[STATIONS];
        // create a queue that will hold all trains
        this.allTrains = new ArrayDeque<>();
        // create a queue that will hold all passengers
        this.allPassengers = new ArrayDeque<>();
        // initialize counters
        this.trainCount = 0;
        this.passengersCreated = 0;
        this.passengersOnTrains = 0;
        this.passengersDelivered = 0;
        // create Random object to be used for generating random values
        this.generator = new Random( 101 );
        // generate all stations
        generateStations();
    }

    /**
     * Create all train station objects.
     */
    public void generateStations()
    {
        // TODO Project 3 - Step #1 - Done

        // Fill the allStation array with Station objects where the value
        // of "time to next station" is randomly generated.
        for ( int stationNumber = 0; stationNumber < STATIONS; stationNumber++ )
        {
            int timeToNextStation = generator.nextInt( ( MAX_TIME_TO_NEXT_STATION - MIN_TIME_TO_NEXT_STATION ) + 1 ) +
                                    MIN_TIME_TO_NEXT_STATION;
            Station station = new Station( stationNumber, timeToNextStation ); // Create new station
            this.allStations[stationNumber] = station; // Add new station to allStations
            System.out.println( station.toString() );
        }
        System.out.println(); // Newline
    }

    /**
     * Create a new train object.
     *
     * @param clock Simulated timer.
     */
    public void startNewTrain( int clock )
    {
        if ( ( clock % TRAIN_INTERVAL ) == 0 )
        {
            // TODO Project 3 - Step #3 - Done
            Train train = new Train( TRAIN_CAPACITY ); // Create new train object
            allTrains.offer( train ); // Add train to the allTrains queue
            trainCount++;// Increment the train count
            System.out.println( "New Train: " + train.toString() );
        }
    }

    /**
     * Create all passengers objects and add them to their respective train station.
     *
     * @param clock Simulated timer.
     */
    public void generatePassengers( int clock )
    {
        // randomly generate number of new passengers
        int newPassengers = this.generator.nextInt( MAX_NUM_OF_PASSENGERS + 1 );

        // TODO Project 3 - Step #2 - Done

        // Create the calculated number of passenger objects
        while ( newPassengers > 0 )
        {
            int destStation = generator.nextInt( FINAL_STATION ) + 1; // [1...9] Destination stations
            int srcStation = generator.nextInt( destStation ); // [0...(destStation - 1)] Source stations
            Passenger passenger = new Passenger( srcStation, destStation, clock ); // Create new passenger
            System.out.println( passenger.toString() );
            allPassengers.offer( passenger ); // Add passenger to allPassengers queue
            this.passengersCreated++; // Increment the passengers created by 1
            allStations[srcStation].addPassenger( passenger ); // Add passenger to source station
            newPassengers--; // Loop control variable
        }
    }

    /**
     * Move all train objects toward their next station.
     *
     * @param clock Simulated timer.
     */
    public void moveTrains( int clock )
    {
        System.out.println( "\n>> Moving each train <<" );
        int trainsToCheck = this.trainCount;

        for ( int i = 0; i < trainsToCheck; i++ )
        {
            // TODO Project 3 - Step #4 - Done
            Train train = allTrains.poll();
            if ( train != null )
            {
                train.move();
                if ( train.getTimeToNext() == 0 )
                {
                    // Unload the passengers from the train
                    int passengersLeaving = train.unloadPassengers( train.getNextStation() );
                    // Update the passenger counters
                    this.passengersOnTrains -= passengersLeaving;
                    this.passengersDelivered += passengersLeaving;

                    // Load the waiting passengers on the train
                    int passengersEntering = train.loadPassengers( this.allStations[train.getNextStation()], clock );
                    // Update the passenger counter
                    this.passengersOnTrains += passengersEntering;

                    // If the train is at the final station
                    if ( train.getNextStation() == FINAL_STATION )
                    {
                        System.out.println( "The final destination has arrived." );
                        trainCount--; // Decrement number of trains
                    }
                    else
                    {
                        // Update train's next station data
                        train.updateStation( allStations[train.getNextStation()].getTimeToNextStation() );
                        allTrains.offer( train ); // Add train back to allTrain queue
                        System.out.println( train.toString() );
                    }
                }
                else
                {
                    allTrains.offer( train ); // Add train back to allTrain queue
                    System.out.println( train.toString() );
                }
            }
        }
    }

    /**
     * Generate report at respective time markers.
     *
     * @param simulationTimer Current time on simulated clock.
     */
    public void reportAtTimeMarker( int simulationTimer )
    {
        int passengersWaiting = this.passengersCreated - this.passengersOnTrains - this.passengersDelivered;

        System.out.println( "-----At time marker " + simulationTimer + " -> passengers waiting: " + passengersWaiting +
                            "\t on trains: " + this.passengersOnTrains + "\t active trains: " + this.trainCount +
                            "-----" );
        System.out.println();
    }

    /**
     * Generate statistics based on train data.
     *
     * @param clock Simulated timer.
     */
    public void finalSimulationReport( int clock )
    {
        DecimalFormat df = new DecimalFormat( "#0.00" );
        System.out.println( "*****************  Final Report  ***************" );
        System.out.println( "The total number of passengers is " + this.passengersCreated );
        System.out.println( "The number of passengers currently on a train " + this.passengersOnTrains );
        System.out.println( "The number of passengers delivered is " + this.passengersDelivered );
        int passengersWaiting = this.passengersCreated - this.passengersOnTrains - this.passengersDelivered;
        System.out.println( "The number of passengers waiting is " + passengersWaiting );

        int waitBoardedSum = 0;
        Passenger passenger;
        for ( int i = 0; i < this.passengersCreated; i++ )
        {
            passenger = this.allPassengers.poll();
            if ( passenger.boarded() )
            {
                waitBoardedSum += passenger.waitTime( clock );
            }
        }
        System.out.print( "The average wait time for passengers that have boarded is " );
        System.out.println( df.format(
                ( double ) waitBoardedSum / ( this.passengersOnTrains + this.passengersDelivered ) ) );
    }

    /**
     * Main method
     *
     * @param args An array of string arguments.
     */
    public static void main( String args[] )
    {
        System.out.println( "**************  TRAIN SIMULATION  **************\n" );
        TrainSimulation simulator = new TrainSimulation();

        System.out.println( "--> Starting the clock; duration set to " + simulator.DURATION + "\n" );
        for ( int clock = 0; clock < simulator.DURATION; clock++ )
        {
            simulator.generatePassengers( clock );
            simulator.startNewTrain( clock );
            simulator.moveTrains( clock );
            simulator.reportAtTimeMarker( clock );
        }
        simulator.finalSimulationReport( simulator.DURATION );
    }
}
