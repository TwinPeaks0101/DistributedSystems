package booking;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worker implements Runnable {
    private ServerSocket serverSocket;
    private final AccommodationList accommodations;

    public Worker(int port) {
        accommodations = new AccommodationList();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public AccommodationList getAccommodations() {
        return accommodations;
    }

    public Accommodation getAccommodations(int index) {
        return accommodations.get(index);
    }

    @Override
    public void run() {
        System.out.println("Worker running on port " + serverSocket.getLocalPort());
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from Master: " + inputLine);
                    try {
                        JSONObject jsonInput = new JSONObject(inputLine);

                        if (jsonInput.has("filterType")) {
                            if (jsonInput.getString("filterType").equals("accommodationFilter")) {
                                // Process filter
                                Map<String, List<Accommodation>> mapResults = processMapPhase(jsonInput);
                                sendResultsToReducer(mapResults);
                            }
                        } else {
                            Accommodation accommodation = new Accommodation(jsonInput);
                            accommodations.addAccommodation(accommodation);
                            System.out.println("Accommodation added: " + accommodation.getRoomName());
                        }
                    } catch (Exception e) {
                        System.err.println("Error handling input: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("IOException in handleClient: " + e.getMessage());
            }
        }).start();
    }

    private Map<String, List<Accommodation>> processMapPhase(JSONObject filters) {
        Map<String, List<Accommodation>> mapResults = new HashMap<>();
        for (Accommodation accommodation : accommodations.getAccommodationList()) {
            if (accommodation.matches(filters)) {
                String key = createKeyForAccommodation(accommodation, filters);
                List<Accommodation> tempList = mapResults.getOrDefault(key, new ArrayList<>());
                tempList.add(accommodation);
                mapResults.put(key, tempList);
            }
        }
        return mapResults;
    }

    private String createKeyForAccommodation(Accommodation accommodation, JSONObject filters) {
        // Create a unique key based on the accommodation and the filters.
        return accommodation.getArea().toString() + ":" + filters.optInt("price");
    }

    private void sendResultsToReducer(Map<String, List<Accommodation>> mapResults) {
        String reducerHost = "localhost";
        int reducerPort = 5006;

        try (Socket socket = new Socket(reducerHost, reducerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            JSONObject json = new JSONObject(mapResults); // Serialization might need custom handling
            out.println(json.toString());
            System.out.println("Mapped results sent to reducer.");
        } catch (IOException e) {
            System.err.println("Failed to send mapped results to reducer: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: Port Number ");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        new Thread(new Worker(port)).start();

    }
}