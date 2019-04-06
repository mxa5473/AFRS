/*
    Server class connects with the client and receives commands
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {

    /*
        Creates a new ServerSocket as Null
     */
    private ServerSocket ss = null;

    public static void main(String[] args) {
        new Server();
    }

    /*
        Attends to open a new ServerSocket at port 8686
     */
    public Server() {
        try {
            ss = new ServerSocket(8686);
            while (true) {
                Socket cs = ss.accept();
                new AFRSServer(cs).start();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*
        Creates a new AFRSServer with a new printer writer, buffer reader, socket, and api.
        This tries to run the server and parses commands by splitting them at a semicolon ';'
     */
    public class AFRSServer extends Thread {

        PrintWriter pw = null;
        BufferedReader br = null;
        Socket cs = null;
        API api = null;

        public AFRSServer(Socket _cs) {
            cs = _cs;
            api = new API();
            try {
                pw = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        public void run() {

            try {

                while (true) {
                    String request = br.readLine();
                    String[] send = request.split(";");
                    ArrayList<Object> response = new ArrayList<>();
                    String errorMsg = api.getQuery(send[0]);
                    if (!errorMsg.contains("error")) {
                        response = api.getResponse();
                    } else {
                        response.add(errorMsg);
                    }
                    for (int x = 0; x < response.size(); x++) {
                        pw.println(response.get(x));
                        pw.flush();
                    }
                    pw.println("done");
                    pw.flush();
                }
            } catch (IOException ioe) {
            }

            try {
                pw.close();
                br.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
