
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This program makes a socket connection to the Rusa Server
 */
public class ClientRusa {

    public static void main(String[] args) {

        try (
            Socket s = new Socket("localhost", 1717);

            InputStream inStream = s.getInputStream();
            OutputStream outStream = s.getOutputStream();

            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream, true);
        ) {
            String line = in.nextLine();
            System.out.println(line);

            System.out.println(">>>>>>Client>>>>>");

            Scanner input = new Scanner(System.in);

            while (true) {
                line = input.nextLine();
                out.println(line);
                String inputType = in.nextLine();
                System.out.println(inputType);
                if (line.equals("BYE")) break;
            }
        } catch (IOException e) {
            System.out.println("Client");
            e.printStackTrace();
        }
    }
}
