/*
    Finds the flight path to get a client from one airport to another with at most two connections
 */

import java.util.*;
import java.time.*;

public class TwoConnections implements FlightFinder {
    ArrayList<Flight> bank;
    ArrayList<ArrayList<Flight>> results;

    /*
        Creates two new arraylists of flights, bank and results
     */
    public TwoConnections(ArrayList<Flight> allSearchableFlights) {
        bank = new ArrayList<Flight>(allSearchableFlights);
        results = new ArrayList<ArrayList<Flight>>();
    }

    /*
        Attempts to find flights based on the departure and arrival airports.
     */
    public ArrayList<ArrayList<Flight>> findFlights(Airport start, Airport end) {
        ArrayList<Flight> starters = new ArrayList<Flight>();
        ArrayList<Flight> middlers = new ArrayList<Flight>();
        ArrayList<Flight> enders = new ArrayList<Flight>();
        ArrayList<Flight> workingStarters = new ArrayList<Flight>();
        ArrayList<Flight> workingMiddlers = new ArrayList<Flight>();
        ArrayList<Flight> workingEnders = new ArrayList<Flight>();
        NoConnections noConns = new NoConnections(bank);

        for (Flight cur : bank) {
            if (cur.getOrigin().getCode().equals(start.getCode()) && !cur.getDestination().getCode().equals(end.getCode()) && !starters.contains(cur)) {//for some reason, && !cur.getDestination().equals(end) does not prevent you from getting sets of flights that go ATL-JFK, JFK-ROC, ROC-JFK
                starters.add(cur);//sometimes flights directly to the destination are entered. the next leg goes to another airport, and then straight back to the original airport
            } else if (cur.getDestination().getCode().equals(end.getCode()) && !cur.getOrigin().getCode().equals(start.getCode()) && !enders.contains(cur)) {
                enders.add(cur);
            } else {
            }
        }

        for (int x=0; x < starters.size(); x++) {
            //System.out.println("Iterating in starter array");
            for (int i=0; i < enders.size(); i++) {
                //System.out.println("Iterating in ender array");
                ArrayList<ArrayList<Flight>> totMiddler = noConns.findFlights(starters.get(x).getDestination(), enders.get(i).getOrigin());
                for (int z = 0; z < totMiddler.get(0).size(); z++) {
                    Flight curMiddler = totMiddler.get(0).get(z);
                    Duration reqTimeLeg1 = Duration.ofMinutes((long) starters.get(x).getDestination().getIntervalTime() + (long) starters.get(x).getDestination().getDelayTime());//duration of time needed between flight 1 and flight 2
                    Duration reqTimeLeg2 = Duration.ofMinutes((long) curMiddler.getDestination().getIntervalTime() + (long) curMiddler.getDestination().getDelayTime());//duration of time needed between flight 2 and flight 3
                    Duration actTimeLeg1 = Duration.between(starters.get(x).getArriveTime(), curMiddler.getDepartTime()); //the actual available time between the first and second legs (actual time during leg(layover) #1)
                    Duration actTimeLeg2 = Duration.between(curMiddler.getArriveTime(), enders.get(i).getArriveTime()); //actual time for layover two

                    //System.out.println("Checking time constraints");


                    if ((actTimeLeg1.compareTo(reqTimeLeg1) >= 0) && (actTimeLeg2.compareTo(reqTimeLeg2) >= 0) &&
                            !workingStarters.contains(starters.get(x))) {//if theres adequate time on the first and second layover
                        workingStarters.add(starters.get(x));
                        workingMiddlers.add(curMiddler);
                        workingEnders.add(enders.get(i));
                    }
                }
                totMiddler.clear();
            }
        }
        results.add(workingStarters);
        results.add(workingMiddlers);
        results.add(workingEnders);

//        Set<ArrayList<Flight>> noDuplicatesAllowed = new HashSet<ArrayList<Flight>>();
//        noDuplicatesAllowed.addAll(results);
//        results.clear();
//        results.addAll(noDuplicatesAllowed);
        return results;
    }
}