public class GetItinerary implements Command {

    private Library library;
    private Control c;
    public GetItinerary(Control _c, Library _library){
        c = _c;
        library = _library;
    }

    public void execute(String msg){
        c.getItinerary(msg, library.getItineraries());
    }
}
