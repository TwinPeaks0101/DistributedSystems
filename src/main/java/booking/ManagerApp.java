package booking;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ManagerApp {
    private static final String MASTER_HOST = "localhost";
    private static final int MASTER_PORT = 5000;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void start() {
        try {
            socket = new Socket(MASTER_HOST, MASTER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);
            int choice = -1;

            while (choice != 0) {
                System.out.println("1. Add Accommodation");
                System.out.println("2. View Bookings");
                System.out.println("3. Send All Accommodations");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        addAccommodation(scanner);
                        break;
                    case 2:
                        viewBookings(scanner);
                        break;
                    case 3:
                        sendAllAccommodations();
                        break;
                    case 0:
                        System.out.println("Exiting.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAllAccommodations(){
        final int totalAccommodations = 83;
        for (int i = 0; i <totalAccommodations; i++){
            out.println(i);
            try {
                String response = in.readLine();
                System.out.println("Master response for accommodation " + (i + 1) + ": ");
            }catch(IOException e){
                System.out.println("Error receiving response from Master for accommodations");
                e.printStackTrace();
            }
        }
    }

    private void addAccommodation(Scanner scanner) {
        System.out.println("--- Add New Accommodation ---");
        //To exw pros to paron na douleuei me index-->dld an baleis 0 tha parei to prwto dwmatio apo to json, 1 to deutero ktlp
        System.out.print("Enter the index of the accommodation to add: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        out.println(index);

        // response apo Master
        try {
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewBookings(Scanner scanner) {
        System.out.println("--- View Bookings ---");
        System.out.println("TODO.");
    }

    public static void main(String[] args) {
        new ManagerApp().start();
    }
}