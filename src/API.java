/*
    The API class that passes commands from the client and calls executables in the system
 */

import java.util.ArrayList;

public class API {

    private Control c;
    private Library library;
    private AirportFinder airFinder;
    private CreateItinerary createItinerary;
    private GetFlight flightGetter;
    private GetItinerary itineraryGetter;
    private DeleteItinerary deleter;

    /*
        Creates a new API with a new library, airport list, airport finder, create itinerary, flight getter, intinerary
        getter, and deleter
     */
    public API() {
        c = new Control();
        library = new Library(c);
        c.getAirportList(library.getAirports());
        airFinder = new AirportFinder(c);
        createItinerary = new CreateItinerary(c, library);
        flightGetter = new GetFlight(c, library);
        itineraryGetter = new GetItinerary(c, library);
        deleter = new DeleteItinerary(c, library);
    }

    /*
     * Command pattern implementation
     * The API class will act as the invoker to the command interface
     */

    /*
     * Contains instance variables which represents actions
     * and their types will be the Command interface
     */

    /*
     * Receives the string from server and checks the first word
     * the client sent to the server and based on the word execute
     * the command.
     */

    public String getQuery(String result) {
        if (result.contains("airport")) {
            String[] msg = result.split(","); // Call get airport info
            if (msg.length == 1) {
                return "error, unknown airport";
            } else {
                airFinder.execute(msg[1]);
            }
        } else if (result.contains("reserve")) {
            createItinerary.execute(result);
        } else if (result.contains("info")) {
            flightGetter.execute(result);
        } else if (result.contains("retrieve")) {
            itineraryGetter.execute(result);
        } else if (result.contains("delete")) {
            deleter.execute(result);
        } else {
            return "error, unknown command";
        }

        return "none";
    }

    /*
        Returns the response from the query
     */
    public ArrayList<Object> getResponse() {
        ArrayList<Object> response = c.queryResponse();
        return response;
    }

}
