/*
    Flight class holds the flight information
 */
import java.time.*;
import java.util.*;
public class Flight {

    private Airport origin;
    private Airport destination;
    private String flightNum;
    private LocalTime arriveTime;
    private LocalTime departTime;
    private double cost;

    public Flight(Airport _origin, Airport _destination, String _flightNum, double _cost, LocalTime depart, LocalTime arrive){
        origin = _origin;
        destination = _destination;
        flightNum = _flightNum;
        cost = _cost;
        departTime = depart;
        arriveTime = arrive;
    }

    public String getFlightNum(){
        return flightNum;
    }

    public double getCost(){
        return cost;
    }

    /*
        Returns the arrival time for the flight
     */

    public LocalTime getArriveTime() {
        return arriveTime;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }

    public String getStringArriveTime() {
        String time = "";
        String m = "a";

        if(arriveTime.getHour() == 24){
            time = "0";
        }
        else if(arriveTime.getHour() > 12){
            int hour = arriveTime.getHour() - 12;
            time = Integer.toString(hour);
            m = "p";
        }
        else{
            time = Integer.toString(arriveTime.getHour());
        }
        String minute = "";
        if(arriveTime.getMinute() < 10){
            minute = "0" + Integer.toString(arriveTime.getMinute());
        }
        else{
            minute = Integer.toString(arriveTime.getMinute());
        }
        time = time + ":" + minute + m;
        return time;
    }

    /*
        Returns the departure time for the flight
     */
    public String getStringDepartTime() {
        String time = "";
        String m = "a";

        if(departTime.getHour() == 24){
            time = "0";
        }
        else if(departTime.getHour() > 12){
            int hour = departTime.getHour() - 12;
            time = Integer.toString(hour);
            m = "p";
        }
        else{
            time = Integer.toString(departTime.getHour());
        }
        String minute = "";
        if(departTime.getMinute() < 10){
            minute = "0" + Integer.toString(departTime.getMinute());
        }
        else{
            minute = Integer.toString(departTime.getMinute());
        }
        time = time + ":" + minute + m;
        return time;
    }

    public Airport getOrigin(){return origin;}

    public Airport getDestination() {
        return destination;
    }
}
