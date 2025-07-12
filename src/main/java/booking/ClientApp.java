package booking;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.net.*;

public class ClientApp {
    private static final String MASTER_HOST = "localhost";
    private static final int MASTER_PORT = 5004;
    private static final int CLIENT_PORT = 5007;

    public static void start() {
        new Thread(ClientApp::startServer).start();

        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter filters to send to Master or type 'exit' to quit:");
            System.out.println("Choose area (or type 'exit' to quit):");
            String area = in.nextLine();
            if (area.equalsIgnoreCase("exit")) break;

            System.out.println("Choose date from (YYYY-MM-DD):");
            String dateFrom = in.nextLine();
            System.out.println("Choose date to (YYYY-MM-DD):");
            String dateTo = in.nextLine();
            System.out.println("How many people:");
            int numOfPersons = in.nextInt();
            System.out.println("Choose minimum price:");
            double minPrice = in.nextDouble();
            System.out.println("Choose maximum price:");
            double maxPrice = in.nextDouble();
            in.nextLine(); // consume newline
            System.out.println("Enter minimum ranking:");
            int minRanking = in.nextInt();
            System.out.println("Enter maximum ranking:");
            int maxRanking = in.nextInt();
            in.nextLine(); // consume newline

            JSONObject filter = new JSONObject();
            filter.put("filterType", "accommodationFilter");
            filter.put("area", area);
            filter.put("dates", new JSONObject().put("dateFrom", dateFrom).put("dateTo", dateTo));
            filter.put("numOfPersons", numOfPersons);
            filter.put("price", new JSONObject().put("minPrice", minPrice).put("maxPrice", maxPrice));
            filter.put("stars", new JSONObject().put("minRanking", minRanking).put("maxRanking", maxRanking));

            sendToMaster(filter.toString());
        }
    }

    private static void sendToMaster(String jsonFilters) {
        try (Socket socket = new Socket(MASTER_HOST, MASTER_PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            writer.write(jsonFilters);
            writer.newLine();
            writer.flush();
            System.out.println("Filters sent to Master.");
        } catch (IOException e) {
            System.err.println("Could not send filters to Master at " + MASTER_HOST + ":" + MASTER_PORT);
            e.printStackTrace();
        }
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(CLIENT_PORT)) {
            System.out.println("ClientApp server started, waiting for results from Master...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String results = reader.readLine();
                    System.out.println("Received results from Master:");
                    System.out.println(results);
                } catch (IOException e) {
                    System.err.println("Error during results reception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start ClientApp server on port " + CLIENT_PORT + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        start();
    }
}

