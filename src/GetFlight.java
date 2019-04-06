/*
    Gets the flight corresponding to the command
 */
public class GetFlight implements Command {

    private Control c;
    private Library library;

    /*
        Gets the flight from the library
     */
    public GetFlight(Control _c, Library _library) {
        c = _c;
        library = _library;
    }

    /*
        Executes the getFlight command with a command and library
     */
    public void execute(String msg) {
        c.getFlight(msg, library);
    }
}
