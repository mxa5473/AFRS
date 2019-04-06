/*
    Control class takes the string that was sent from the client to the server and
    takes gives back the information requested
 */

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Control implements Observer {

    /*
        Creates the previousQuery, currentQuery, currentObject, and airportList arraylists setting them to null
     */
    private ArrayList<Object> previousQuery = null;
    private ArrayList<Object> currentQuery = null;
    private ArrayList<Object> currentObject = null;
    private ArrayList<Airport> airportList = null;

    /*
        Creates a new previousQuery, currentQuery, and currentObject arraylist
     */
    public Control() {
        previousQuery = new ArrayList<>();
        currentQuery = new ArrayList<>();
        currentObject = new ArrayList<>();
    }


    /*
        Deletes an itinerary from the library
     */
    public void deleteItinerary(String msg, Library library) {
        String[] info = msg.split(",");
        ArrayList<Itinerary> itineraries = library.getItineraries();
        int size = itineraries.size();
        for (int x = 0; x < itineraries.size(); x++) {
            if (itineraries.get(x).getFlights().get(0).getOrigin().getCode().equals(info[2]) &&
                    itineraries.get(x).getFlights().get(0).getDestination().getCode().equals(info[3]) &&
                    itineraries.get(x).getPassengerName().equals(info[1])) {
                itineraries.remove(x);
            }
        }

        currentObject.clear();
        if (size > itineraries.size()) {
            currentObject.add("delete,successful");
            library.updateItineraryList(itineraries);

            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(new File("data/reservations.txt")));
                itineraries = library.getItineraries();
                for (int x = 0; x < itineraries.size(); x++) {
                    int count = 1;
                    String output = count + "," + itineraries.get(x).getPassengerName() + "," + itineraries.get(x).getFlights().get(0).getFlightNum();
                    pw.println(output);
                    pw.flush();
                    pw.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            currentObject.add("error, Itinerary not found");
        }
    }

    /*
        Gets the flight information requested in the command from the library
     */
    public void getFlight(String msg, Library library) {
        String[] info = msg.split(",");
        String origin = info[1];
        String destination = info[2];
        int connections = 0;

        if (info.length > 3 && info[3] != null) {
            connections = Integer.parseInt(info[3]);
        }

        Airport originAirport = null;
        Airport destinationAirport = null;
        for (int x = 0; x < library.getAirports().size(); x++) {
            if (origin.equals(library.getAirports().get(x).getCode())) {
                originAirport = library.getAirports().get(x);
            } else if (destination.equals(library.getAirports().get(x).getCode())) {
                destinationAirport = library.getAirports().get(x);
            }
        }

        ArrayList<ArrayList<Flight>> results = null;
        if (connections == 0) {
            NoConnections finder = new NoConnections(library.getFlights());
            results = finder.findFlights(originAirport, destinationAirport);
        }
        else if(connections == 1){
            OneConnection finder = new OneConnection((library.getFlights()));
            results = finder.findFlights(originAirport, destinationAirport);
        }
        else if(connections == 2){
            TwoConnections finder = new TwoConnections((library.getFlights()));
            results = finder.findFlights(originAirport, destinationAirport);
        }

        ArrayList<Flight> first = null;
        ArrayList<Flight> second = null;
        ArrayList<Flight> third = null;
        if (connections >= 0) {
            first = results.get(0);
        }
        if (connections >= 1) {
            second = results.get(1);
        }
        if (connections == 2){
            third = results.get(2);
        }

        currentObject.clear();
        String firstLine = "info," + first.size();
        currentObject.add(firstLine);
        for (int x = 0; x < first.size(); x++) {

            int totalCost = 0;
            for (int i = 0; i <= connections; i++) {
                totalCost += results.get(i).get(x).getCost();
            }

            String output = "";
            for (int i = 0; i <= connections; i++) {
                if (i == 0) {
                    output = output + String.format("%d,%d,%d,%.0f,%s,%s,%s,%s", x + 1, totalCost, connections + 1,
                            first.get(x).getCost(), first.get(x).getOrigin().getCode(), first.get(x).getStringDepartTime(),
                            first.get(x).getDestination().getCode(), first.get(x).getStringArriveTime());
                }
                else if(i == 1){
                    output = output + String.format(",%.0f,%s,%s,%s,%s", second.get(x).getCost(), second.get(x).getOrigin().getCode(),
                            second.get(x).getStringDepartTime(), second.get(x).getDestination().getCode(), second.get(x).getStringArriveTime());
                }
                else if(i == 2){
                    output = output + String.format(",%.0f,%s,%s,%s,%s", third.get(x).getCost(), third.get(x).getOrigin().getCode(),
                            third.get(x).getStringDepartTime(), third.get(x).getDestination().getCode(), third.get(x).getStringArriveTime());
                }
            }
            currentObject.add(output);
        }
    }

    /*
        Creates a new itinerary with an id, resID, passenger name, and library
     */
    public void makeItinerary(int id, String resID, String passengerName, Library library) {
        ArrayList<Flight> flights = new ArrayList<>();
        int listId = id;
        String selected = previousQuery.get(listId).toString();
        String[] info = selected.split(",");
        int connections = Integer.parseInt(info[2]);

        Airport originAirport = null;
        Airport destinationAirport = null;
        int end = 6;
        if(info.length == 13){
            end = 11;
        }
        else if(info.length == 18){
            end = 16;
        }
        for (int x = 0; x < library.getAirports().size(); x++) {
            if (info[4].equals(library.getAirports().get(x).getCode())) {
                originAirport = library.getAirports().get(x);
            } else if (info[end].equals(library.getAirports().get(x).getCode())) {
                destinationAirport = library.getAirports().get(x);
            }
        }

        ArrayList<ArrayList<Flight>> results = null;
        if (connections == 1) {
            NoConnections finder = new NoConnections(library.getFlights());
            results = finder.findFlights(originAirport, destinationAirport);
        } else if (connections == 2) {
            OneConnection finder = new OneConnection(library.getFlights());
            results = finder.findFlights(originAirport, destinationAirport);
        } else if (connections == 3) {
            TwoConnections finder = new TwoConnections(library.getFlights());
            results = finder.findFlights(originAirport, destinationAirport);
        }

        if (connections >= 1) {
            flights.add(results.get(0).get(listId - 1));
        }
        if (connections >= 2) {
            flights.add(results.get(1).get(listId - 1));
        }
        if (connections == 3) {
            flights.add(results.get(2).get(listId - 1));
        }
        currentObject.clear();
        Itinerary itinerary = new Itinerary(flights, resID, passengerName);
        library.addItinerary(itinerary);
        File out = new File("data/reservations.txt");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(out, true));
            String flightNums = "";
            ArrayList<Flight> iFlights = itinerary.getFlights();
            for (int x = 0; x < iFlights.size(); x++) {
                flightNums = flightNums + "," + iFlights.get(x).getFlightNum();
            }
            String output = itinerary.getIID() + "," + itinerary.getPassengerName() + flightNums;
            pw.println(output);
            pw.flush();
            pw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        String response = "reserve,successful";
        currentObject.add(response);
    }

    /*
        Returns the information for an itinerary from the command given and the list of itineraries provided
     */
    public void getItinerary(String results, ArrayList<Itinerary> itineraries) {
        String[] info = results.split(",");
        String passengerName = info[1];
        String origin = "";
        String destination = "";
        if (info.length > 2) {
            origin = info[2];
        }
        if (info.length == 4) {
            destination = info[3];
        }
        currentObject.clear();
        ArrayList<Itinerary> retrieved = new ArrayList<>();
        for (int x = 0; x < itineraries.size(); x++) {
            if (passengerName.equals(itineraries.get(x).getPassengerName())) {
                retrieved.add(itineraries.get(x));

                if (!origin.equals("")) {
                    ArrayList<Flight> retrievedFlights;
                    retrievedFlights = retrieved.get(0).getFlights();
                    if (!retrievedFlights.get(0).getOrigin().getCode().equals(origin)) {
                        retrieved.remove(x);
                        continue;
                    }
                }

                if (!destination.equals("")) {
                    ArrayList<Flight> retrievedFlights;
                    retrievedFlights = retrieved.get(0).getFlights();
                    if (!retrievedFlights.get(0).getDestination().getCode().equals(destination)) {
                        retrieved.remove(x);
                    }
                }
            }
        }

        currentObject.add("retrieve," + retrieved.size());
        ;
        for (int x = 0; x < retrieved.size(); x++) {
            Itinerary curFlight = retrieved.get(x);
            String output = String.format("%.0f,%d,%.0f,%s,%s,%s,%s,%s", curFlight.getTotalCost(), curFlight.getFlights().size(),
                    curFlight.getFlights().get(0).getCost(), curFlight.getFlights().get(0).getFlightNum(), curFlight.getFlights().get(0).getOrigin().getCode(),
                    curFlight.getFlights().get(0).getStringDepartTime(), curFlight.getFlights().get(0).getDestination().getCode(),
                    curFlight.getFlights().get(0).getStringArriveTime());
            if(curFlight.getFlights().size() > 1){
                output = output + String.format(",%.0f,%s,%s,%s,%s", curFlight.getFlights().get(1).getCost(),
                        curFlight.getFlights().get(1).getOrigin().getCode(), curFlight.getFlights().get(1).getStringDepartTime(),
                        curFlight.getFlights().get(1).getDestination().getCode(), curFlight.getFlights().get(1).getStringArriveTime());
            }
            if(curFlight.getFlights().size() > 2){
                output = output + String.format(",%.0f,%s,%s,%s,%s", curFlight.getFlights().get(2).getCost(),
                        curFlight.getFlights().get(2).getOrigin().getCode(), curFlight.getFlights().get(2).getStringDepartTime(),
                        curFlight.getFlights().get(2).getDestination().getCode(), curFlight.getFlights().get(2).getStringArriveTime());
            }
            currentObject.add(output);
        }


        if (currentObject.size() == 0) {
            currentObject.add("Itinerary not found");
        }
    }

    /*
        Returns the information for an airport matching the airport code
    */
    public void getAirportInfo(String code) {
        currentObject.clear();
        for (int x = 0; x < airportList.size(); x++) {
            if (airportList.get(x).getCode().equals(code)) {
                currentObject.add(airportList.get(x));
                airportList.get(x).changeWeather();
            }
        }
        if (currentObject.size() == 0) {
            String error = "error: unknown airport";
            currentObject.add(error);
        }
    }

    /*
        Returns the object that the request contained and makes the currentObject the previousQuery
     */
    public ArrayList<Object> queryResponse() {
        if (currentObject.get(0) instanceof String) {
            previousQuery = currentObject;
        }
        return currentObject;
    }

    /*
        Updates the airport weather information
     */
    public void update(Observable obs, Object obj) {
        previousQuery = currentObject;
        if (currentObject.get(0) instanceof Airport) {
            String weather = (((Airport) currentObject.get(0)).getWeather());
            currentObject.clear();
            currentObject.add(weather);
        }
    }

    /*
        Returns a list of airports
     */
    public void getAirportList(ArrayList<Airport> newAirports) {
        airportList = newAirports;
    }
}
