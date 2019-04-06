/*
    Reservation class holds information for reservations
 */
public class Reservation {

    private String RID;
    private Flight flight;
    private double cost;
    private String passengerName;

    /*
        Creates a new reservations with a flight, passenger name, string, and reservation ID
     */
    public Reservation(Flight _flight, String _passengerName, int newRID) {
        passengerName = _passengerName;
        flight = _flight;
        cost = flight.getCost();
        RID = Integer.toString(newRID);
    }

    /*
        Returns the flight associated with the reservation
     */
    public Flight getFlight() {
        return flight;
    }

    /*
        Returns the name of the passenger associated with the reservation
     */
    public String getPassengerName() {
        return passengerName;
    }

}
