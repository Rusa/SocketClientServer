/**
 * @version 1.10 1997-06-27
 * @author Cay Horstmann
 */

import java.io.*;
import java.net.*;
import java.util.*;

/**
 This program implements a multithreaded server that listens to
 port 1717 and echoes back all client input.
 */
public class Server {
    public static void main(String[] args) {
        try (
            ServerSocket s = new ServerSocket(1717)
        ) {
            int i = 1;
            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 This class handles the client input for one server socket
 connection.
 */
class ThreadedEchoHandler implements Runnable {

    public ThreadedEchoHandler(Socket i) {
        incoming = i;
    }

    private Socket incoming;

    public void run() {
        try (
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();

            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);
        ){
            out.println("Hello! Enter BYE to exit.");

            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);

                if (line.trim().equals("BYE")) {
                    done = true;
                    out.println("GoodBye");
                } else {
                    out.println("Echo: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Server");
            e.printStackTrace();
        }
    }
}

