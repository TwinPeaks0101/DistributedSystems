package booking;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.*;

public class Reducer {
    private static final int PORT = 5006;
    private String masterHost = "localhost";  // Master's host address
    private int masterPort = 5005;            // Port to send results back to Master

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Reducer started on port " + PORT);
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    StringBuilder result = new StringBuilder();
                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        result.append(inputLine);
                    }
                    processResults(result.toString());
                    sendResultsToMaster(result.toString());
                } catch (IOException e) {
                    System.err.println("Error handling a connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start the server on port " + PORT + ": " + e.getMessage());
        }
    }

    private void processResults(String jsonData) {
        try {
            JSONObject json = new JSONObject(jsonData);
            System.out.println("Received results at Reducer:");
            System.out.println(json.toString(4)); // Print formatted JSON
        } catch (JSONException e) {
            System.err.println("JSON parsing error in Reducer: " + e.getMessage());
        }
    }

    private void sendResultsToMaster(String jsonData) {
        try (Socket socket = new Socket(masterHost, masterPort);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(jsonData);
            System.out.println("Results sent back to Master.");
        } catch (IOException e) {
            System.err.println("Could not send results back to Master: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Reducer().start();
    }
}


