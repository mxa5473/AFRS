/*
    Api class takes commands from user to be sent to server and prints the
    information that was received
 */

import java.util.*;

public class ClientParser {

    Scanner in = null;

    public ClientParser() {
        in = new Scanner(System.in);
    }

    /*
        Returns the next part of the line
     */
    public String request() {
        String msg = in.nextLine();
        return msg;
    }

    /*
        Prints the response
     */
    public void parse(String response) {
        System.out.println(response);
    }
}
