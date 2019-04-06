import java.util.*;
import java.time.*;

public class OneConnection implements FlightFinder{
	
	ArrayList<Flight> bank;
	ArrayList<ArrayList<Flight>> results;

	
	public OneConnection(ArrayList<Flight> allSearchableFlights){
		bank = new ArrayList<Flight>(allSearchableFlights);
		results = new ArrayList<ArrayList<Flight>>();
	}
	
	public ArrayList<ArrayList<Flight>> findFlights(Airport start, Airport end){
		/*
		for each flight in bank
			if it starts at start, put it in a bucket (called starters)
			if it ends at end, put it in another bucket (called enders)
		
		for each starter
			find enders that start at start's end. (that have a common layover)
			put each of those into results.
		*/
		ArrayList<Flight> starters = new ArrayList<Flight>();
		ArrayList<Flight> enders = new ArrayList<Flight>();
		
		//go through the bank, seperate into correctly originating flights and correctly terminating flights
		for (Flight cur : bank){
			if(cur.getOrigin().equals(start)){
				starters.add(cur);
			}else if(cur.getDestination().equals(end)){
				enders.add(cur);
			}
		}
		
		/*
		iterate through starters
			iterate through enders
				if starter.origin == ender.origin
					put the both of them into their own arraylist
					put that arraylist into results
		*/
        ArrayList<Flight> workingStart = new ArrayList<>();
        ArrayList<Flight> workingEnd = new ArrayList<>();
        for (Flight curStarter : starters) {
            for (Flight curEnder : enders) {
                Duration betweenFlightsActual = Duration.between(curStarter.getArriveTime(), curEnder.getDepartTime());
                Duration betweenFlightsNeeded = Duration.ofMinutes((long) curStarter.getDestination().getIntervalTime() + (long) curStarter.getDestination().getDelayTime());
                if(curStarter.getDepartTime().getHour() > curStarter.getArriveTime().getHour()){
                	continue;
				}
                else if (curStarter.getDestination().equals(curEnder.getOrigin()) && betweenFlightsActual.compareTo(betweenFlightsNeeded) >= 0) {
                    workingStart.add(curStarter);
                    workingEnd.add(curEnder);
                }
            }
            //only return results where
            //"The system shall only consider flight combinations with multiple legs when the layover time, i.e. time between a scheduled arrival time and the departure time of the next flight, is greater than or equal to the minimum connection time plus the current delay time for the intermediate airport where the connection is taking place"
            //firstFlight.arrivalTime - secondFlight.departureTime >= intermediateAirport.intervalTime + intermediateAirport.delayTime
        }
        results.add(workingStart);
        results.add(workingEnd);
        return results;
    }
}