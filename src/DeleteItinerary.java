/*
    Deletes an itinerary from the library
 */
public class DeleteItinerary implements Command {

    private Control c;
    private Library library;

    /*
        Removes the itinerary from the library
     */
    public DeleteItinerary(Control _c, Library _library) {
        c = _c;
        library = _library;
    }

    /*
        Executes the deleteItinerary command
     */
    public void execute(String msg) {
        c.deleteItinerary(msg, library);
    }
}
