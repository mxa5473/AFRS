/*
    Class blueprint for itineraries
 */

import java.util.*;

public class Itinerary {

    /*
        Creates a new arraylist for reservations, total cost, and itinerary ID
     */
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private double totalCost;
    private String IID = "";

    /*
        Creates a new itinerary with flights, IID, and passenger name
     */
    public Itinerary(ArrayList<Flight> conFlights, String newIID, String passengerName) {
        for (int x = 0; x < conFlights.size(); x++) {
            reservations.add(new Reservation(conFlights.get(x), passengerName, x + 1));
        }
        IID = newIID;
        for (int x = 0; x < reservations.size(); x++) {
            totalCost += reservations.get(x).getFlight().getCost();
        }
    }

    /*
        Gets the passenger name associated with the itinerary
     */
    public String getPassengerName() {
        return reservations.get(0).getPassengerName();
    }

    /*
        Returns the itinerary ID
     */
    public String getIID() {
        return IID;
    }

    /*
        Returns the total cost of the itinerary
     */
    public double getTotalCost() {
        return totalCost;
    }

    /*
        Returns the flight reservations associated with the itinerary
     */
    public ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        for (int x = 0; x < reservations.size(); x++) {
            flights.add(reservations.get(x).getFlight());
        }
        return flights;
    }


}

