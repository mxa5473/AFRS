import java.lang.reflect.Array;
import java.util.*;
public class Library {

    private ArrayList<Airport> airports = null;
    private ArrayList<Flight> flights = null;
    private ArrayList<Itinerary> itineraries = null;
    private int reservationCount;
    private Control c = null;

    public Library(Control _c){
        c = _c;
        makeObjects();
    }

    public void makeObjects(){
        CreateAirports ca = new CreateAirports();
        airports = ca.getAirportList();
        flights = ca.getFlightList();
        itineraries = ca.getReservationList();
        reservationCount = itineraries.size();

        for(int x=0; x < airports.size(); x++){
            airports.get(x).addObserver(c);
        }
    }

    public void updateItineraryList(ArrayList<Itinerary> newList){
        itineraries = newList;
    }

    public void addItinerary(Itinerary itinerary){
        itineraries.add(itinerary);
    }

    public int incReservationCount(){
        return itineraries.size() + 1;
    }

    public ArrayList<Airport> getAirports(){
        return airports;
    }

    public ArrayList<Flight> getFlights(){
        return flights;
    }

    public Flight getFlight(){ return flights.get(0); }

    public ArrayList<Itinerary> getItineraries() {
        return itineraries;
    }
}
