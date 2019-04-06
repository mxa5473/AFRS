/*
    Interface for finding flights with the departure and arrival airports
 */
import java.util.*;

public interface FlightFinder {
    ArrayList<Flight> bank = null;

    public ArrayList<ArrayList<Flight>> findFlights(Airport start, Airport end);
}