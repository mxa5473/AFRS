/*
    Client class connects with server, sends commands, and receives the requested information
 */

import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) {
        new Client();
    }

    /*
        Creates a new client with a client parser, print writer, and buffer reader.  The client then tries to open a
        connection to localhost port 8686 and accepts requests from the client.  Each request is ended with a
        semicolon ';'
     */
    public Client() {
        ClientParser parser = new ClientParser();
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
            Socket cs = new Socket("localhost", 8686);
            br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()));
            while (true) {
                String request = "";
                while (true) {
                    request = request + parser.request();
                    if (request.contains(";")) {
                        break;
                    }

                }
                pw.println(request);
                pw.flush();
                String response = "";
                while (!(response = br.readLine()).equals("done")) {
                    parser.parse(response);
                }
            }
        } catch (IOException ioe) {
            System.out.println("Server disconnected");
        }
        try {
            pw.close();
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
