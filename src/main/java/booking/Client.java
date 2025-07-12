package booking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client extends Thread {
    Accommodation accommodation;

    Client(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void run() {
        Socket requestSocket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;


        try {

            /* Create socket for contacting the server on port 4321*/
            requestSocket = new Socket("localhost", 1234);

            /* Create the streams to send and receive data from server */
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());


            /* Write the object */
            out.writeObject(accommodation);
            Accommodation accommodation1 = (Accommodation) in.readObject();

            System.out.println("Server: " + accommodation1.toString());

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // list from reading json file
        AccommodationList list = new AccommodationList(Path.of("src/main/java/booking/accommodations.json"));
        //System.out.println(list);
        /**
         * list after Manager input of new Accommodation
         * calls AccommodationList.addAccommodation()
         * then calls Accommodation.createAccommodation()
         * and with Scanner(System.in) takes input of Manager
         */
        //list.addAccommodation();
        System.out.println(list);

    }
}
