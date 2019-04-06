/*
    Generates new data from the files in AFRS/AFRS/data
 */

import java.io.*;
import java.util.*;
import java.time.*;

public class CreateAirports {

    private BufferedReader br1 = null;
    private ArrayList<Airport> airports = new ArrayList<>();
    private ArrayList<Flight> flights = new ArrayList<>();
    private ArrayList<Itinerary> itineraries = new ArrayList<>();

    public CreateAirports() {

    }

    /*
        Returns the itineraries from the reservations.txt file
     */
    public ArrayList<Itinerary> getReservationList() {
        try {
            File reservationList = new File("data/reservations.txt");
            br1 = new BufferedReader(new FileReader(reservationList));
            String resInfo;
            int z = 0;

            while ((resInfo = br1.readLine()) != null) {
                String[] info = resInfo.split(",");
                String id = info[0];
                String name = info[1];
                ArrayList<String> flightId = new ArrayList<>();
                for (int x = 2; x < info.length; x++) {
                    flightId.add(info[x]);
                }
                ArrayList<Flight> conFlights = new ArrayList<>();
                for (int x = 0; x < flightId.size(); x++) {
                    for (int i = 0; i < flights.size(); i++) {
                        if (flightId.get(x).equals(flights.get(i).getFlightNum())) {
                            conFlights.add(flights.get(i));
                        }
                    }
                }
                itineraries.add(new Itinerary(conFlights, id, name));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return itineraries;
    }

    /*
        Returns the flights entered in from the flights.txt file
     */
    public ArrayList<Flight> getFlightList() {
        try {
            File flightList = new File("data/flights.txt");
            br1 = new BufferedReader(new FileReader(flightList));
            String info;
            int i = 0;

            while ((info = br1.readLine()) != null) {
                Airport origin = null;
                Airport destination = null;
                String[] airportInfo = info.split(",");
                for (int x = 0; x < airports.size(); x++) {
                    if (airportInfo[0].equals(airports.get(x).getCode())) {
                        origin = airports.get(x);
                    } else if (airportInfo[1].equals(airports.get(x).getCode())) {
                        destination = airports.get(x);
                    }
                }
                String flightNum = airportInfo[4];
                Double cost = Double.parseDouble(airportInfo[5]);

                String[] departTime = airportInfo[2].split(":");
                if (departTime[1].contains("p") && Integer.parseInt(departTime[0]) < 12) {
                    int newArrive = Integer.parseInt(departTime[0]) + 12;
                    departTime[0] = Integer.toString(newArrive);
                }
                departTime[1] = departTime[1].substring(0, 2);
                LocalTime depart = LocalTime.of(Integer.parseInt(departTime[0]), Integer.parseInt(departTime[1]));

                String[] arriveTime = airportInfo[3].split(":");
                if (arriveTime[1].contains("p") && Integer.parseInt(arriveTime[0]) < 12) {
                    int newArrive = Integer.parseInt(arriveTime[0]) + 12;
                    arriveTime[0] = Integer.toString(newArrive);
                }
                arriveTime[1] = arriveTime[1].substring(0, 2);
                LocalTime arrive = LocalTime.of(Integer.parseInt(arriveTime[0]), Integer.parseInt(arriveTime[1]));

                flights.add(new Flight(origin, destination, flightNum, cost, depart, arrive));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flights;
    }

    /*
        Returns the airports and information gathered from the airports.txt, weather.txt, delayTime.txt, and
        intervalTime.txt files
     */
    public ArrayList<Airport> getAirportList() {
        try {
            File names = new File("data/airports.txt");
            File weather = new File("data/weather.txt");
            File delay = new File("data/delayTime.txt");
            File interval = new File("data/intervalTime.txt");
            br1 = new BufferedReader(new FileReader(names));

            String airport;
            while ((airport = br1.readLine()) != null) {
                String[] name = airport.split(",");
                airports.add(new Airport(name[0], name[1]));
            }
            br1.close();

            br1 = new BufferedReader(new FileReader(weather));
            String airportw;
            int x = 0;
            while ((airportw = br1.readLine()) != null) {
                String[] currWeather = airportw.split(",");
                ArrayList<String> weatherList = new ArrayList<>();
                ArrayList<String> temp = new ArrayList<>();

                for (int i = 1; i < currWeather.length; i++) {
                    if ((i % 2) == 0) {
                        temp.add(currWeather[i]);
                    } else {
                        weatherList.add(currWeather[i]);
                    }
                }
                airports.get(x++).addWeather(weatherList, temp);
            }
            br1.close();

            br1 = new BufferedReader(new FileReader(delay));
            String delayTime;
            x = 0;
            while ((delayTime = br1.readLine()) != null) {
                String[] delays = delayTime.split(",");
                airports.get(x++).addDelay(Integer.parseInt(delays[1]));
            }
            br1.close();

            br1 = new BufferedReader(new FileReader(interval));
            String intervalTime;
            x = 0;
            while ((intervalTime = br1.readLine()) != null) {
                String[] intervalTimes = intervalTime.split(",");
                airports.get(x++).addInterval(Integer.parseInt(intervalTimes[1]));
            }
            br1.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return airports;
    }
}