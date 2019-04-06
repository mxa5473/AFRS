/*
    Finds an airport matching the one in the command given to it
 */
public class AirportFinder implements Command {

    Control c;

    /*
        Sets c to the control command given in the command
     */
    public AirportFinder(Control _c) {
        c = _c;
    }

    /*
        Returns the airport information for the airport in the command given
     */
    public void execute(String msg) {
        c.getAirportInfo(msg);
    }

}
