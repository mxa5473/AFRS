import java.util.ArrayList;

public class CreateItinerary implements Command{

    Control c = null;
    Library library = null;
    public CreateItinerary(Control _c, Library _library){
        c = _c;
        library = _library;
    }

    public void execute(String msg){
        String[] result = msg.split(",");
        ArrayList<Flight> flight = new ArrayList<>();
        flight.add(library.getFlight());
        String resId = Integer.toString(library.incReservationCount());
        c.makeItinerary(Integer.parseInt(result[1]), resId, result[2], library);
    }
}
