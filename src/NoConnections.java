/*
    Finds a flight path without connections to get the client from one airport to another
 */

import java.util.*;

class NoConnections implements FlightFinder {
    ArrayList<Flight> bank;
    ArrayList<ArrayList<Flight>> results;

    /*
        Creates two lists with all the flights inside of them
     */
    public NoConnections(ArrayList<Flight> allSearchableFlights) {
        bank = new ArrayList<Flight>(allSearchableFlights);
        results = new ArrayList<ArrayList<Flight>>();
    }

    /*
        Adds flights that satisfies the client's request to the results array list
     */
    public ArrayList<ArrayList<Flight>> findFlights(Airport start, Airport end) {
        ArrayList<Flight> innerArray = new ArrayList<>();
        for (Flight cur : bank) {
            if (cur.getOrigin().equals(start) && cur.getDestination().equals(end)) {
                innerArray.add(cur);
            }
        }
        results.add(innerArray);
        return results;
    }
}