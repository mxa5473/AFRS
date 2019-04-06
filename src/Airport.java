/*
    `Airport class holds information for airports
 */
import java.util.*;
public class Airport extends Observable {

    private String code;
    private String name;
    private ArrayList<String> weather;
    private ArrayList<String> temp;
    private int wCounter = 0;
    private int delayTime;
    private int intervalTime;
    private String currentWeather;

    /*
    Creates an airport object with the airport code and name
    */
    public Airport(String newCode, String newName){
        code = newCode;
        name = newName;
    }


    /*
        Returns airport code
    */
    public String getCode(){
        return code;
    }

    /*
        Changes the weather to the next set in the array
    */
    public void changeWeather(){
        if(wCounter == weather.size()){
            wCounter = 0;
        }
        currentWeather = String.format("airport,%s,%s,%s,%d", name, weather.get(wCounter), temp.get(wCounter++), delayTime);
        setChanged();
        notifyObservers();
    }

    /*
    Returns the weather at the airport
    */
    public String getWeather(){
        return currentWeather;
    }

    /*
    Adds weather for the airport
    */
    public void addWeather(ArrayList<String> newWeather, ArrayList<String> newTemp){
        weather = newWeather;
        temp = newTemp;
    }

    /*
    Adds the delay time for the airport
    */
    public void addDelay(int newDelay){
        delayTime = newDelay;
    }

    /*
    Adds the interval time for the airport
    */
    public void addInterval(int newInterval){
        intervalTime = newInterval;
    }

    /*
       Returns the Interval time for the airport
     */
    public int getIntervalTime(){return intervalTime;}

    /*
        Returns the delay time for the airport
     */
    public int getDelayTime(){return delayTime;}
}
