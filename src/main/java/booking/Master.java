package booking;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Master {
    private static final int MASTER_PORT_MANAGER = 5000;
    private static final int MASTER_PORT_CLIENT = 5004;
    private static final int MASTER_PORT_REDUCER = 5005;
    private String clientHost = "localhost";
    private int clientPort = 5007;
    private List<String> workerNodes;
    private List<Accommodation> accommodations;

    public Master(List<String> workerNodes) {
        this.workerNodes = new ArrayList<>(workerNodes);
        accommodations = new ArrayList<>();
    }

    public void startServer() {
        new Thread(() -> listenOnPort(MASTER_PORT_MANAGER, "Manager")).start();
        new Thread(() -> listenOnPort(MASTER_PORT_CLIENT, "Client")).start();
        new Thread(() -> listenOnPort(MASTER_PORT_REDUCER, "Reducer")).start();
    }

    private void listenOnPort(int port, String source) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started for " + source + " on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                if ("Manager".equals(source)) {
                    handleManager(clientSocket);
                } else if ("Client".equals(source)) {
                    handleClient(clientSocket);
                } else if ("Reducer".equals(source)) {
                    handleReducer(clientSocket);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port + ": " + e.getMessage());
        }
    }

    private void handleManager(Socket clientSocket) {
        // Start a new thread for each manager connection using MasterWorkerThread
        new MasterWorkerThread(clientSocket, this).start();
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String input;
            while ((input = reader.readLine()) != null) {
                try {
                    JSONObject jsonInput = new JSONObject(input);
                    if (jsonInput.has("filterType")) { // Ensuring it's a filter JSON
                        System.out.println("Received filter from client: " + jsonInput.toString());
                        distributeFilters(jsonInput);  // Distribute filters to workers
                    } else {
                        System.out.println("Received unexpected data type from client.");
                    }
                } catch (JSONException e) {
                    System.err.println("JSON parsing error from client data: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        } finally {
            try {
                clientSocket.close(); // Ensure the socket is always closed after handling
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void handleReducer(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String jsonData = reader.readLine();
            if (jsonData != null) {
                System.out.println("Received results from Reducer: " + jsonData);
                forwardResultsToClient(jsonData);
            }
        } catch (IOException e) {
            System.err.println("Error receiving results from Reducer: " + e.getMessage());
        }
    }

    private void forwardResultsToClient(String results) {
        System.out.println("Forwarding results to ClientApp...");
        try (Socket socket = new Socket(clientHost, clientPort);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(results);
            System.out.println("Results forwarded to ClientApp.");
        } catch (IOException e) {
            System.err.println("Failed to send results to ClientApp: " + e.getMessage());
        }
    }

    public synchronized void addAccommodation(Path jsonFilePath, int index) {
        Accommodation accommodation = ReadJson.readFile(jsonFilePath, index);
        accommodations.add(accommodation);

        String workerInfo = getWorkerNode(accommodation.getRoomName());
        sendAccommodationToWorker(workerInfo, accommodation);
    }

    private String getWorkerNode(String roomName) {
        int nodeIndex = Math.abs(roomName.hashCode()) % workerNodes.size();
        return workerNodes.get(nodeIndex);
    }

    private void sendAccommodationToWorker(String workerInfo, Accommodation accommodation) {
        String[] parts = workerInfo.split(":");
        String workerHost = parts[0];
        int workerPort = Integer.parseInt(parts[1]);

        try (Socket workerSocket = new Socket(workerHost, workerPort);
             PrintWriter out = new PrintWriter(workerSocket.getOutputStream(), true)) {

            JSONObject jsonAccommodation = new JSONObject();
            jsonAccommodation.put("accType", accommodation.getAccType());
            jsonAccommodation.put("roomName", accommodation.getRoomName());
            jsonAccommodation.put("numOfPersons", accommodation.getNumOfPersons().toString());

            JSONObject jsonArea = new JSONObject();
            jsonArea.put("city", accommodation.getArea().getCity());
            jsonArea.put("road", accommodation.getArea().getRoad());
            jsonArea.put("number", accommodation.getArea().getNumber());
            jsonArea.put("zipCode", accommodation.getArea().getZipCode());
            jsonAccommodation.put("area", jsonArea);

            jsonAccommodation.put("stars", accommodation.getStars().toString());
            jsonAccommodation.put("numOfReviews", accommodation.getNumOfReviews().toString());
            jsonAccommodation.put("roomImage", accommodation.getRoomImage().getAddress());
            jsonAccommodation.put("pricePerNight", accommodation.getPricePerNight().toString());

            JSONObject jsonAvailability = new JSONObject();
            jsonAvailability.put("availableFrom", accommodation.getAvailableFrom().toString());
            jsonAvailability.put("availableTo", accommodation.getAvailableTo().toString());
            jsonAccommodation.put("availability", jsonAvailability);

            out.println(jsonAccommodation.toString());
            System.out.println("Sent " + accommodation.getRoomName() + " to worker at " + workerInfo);
        } catch (IOException e) {
            System.err.println("Could not send accommodation to worker " + workerInfo);
            e.printStackTrace();
        }
    }
    private void distributeFilters(JSONObject filters) {
        for (String workerNode : workerNodes) {
            sendFiltersToWorker(workerNode, filters);
        }
    }

    private void sendFiltersToWorker(String workerInfo, JSONObject filters) {
        String[] parts = workerInfo.split(":");
        String workerHost = parts[0];
        int workerPort = Integer.parseInt(parts[1]);

        try (Socket socket = new Socket(workerHost, workerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(filters.toString());
            System.out.println("Filters sent to worker at " + workerInfo);
        } catch (IOException e) {
            System.err.println("Could not send filters to worker at " + workerInfo + ": " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No worker nodes specified");
            System.exit(1);
        }

        Master master = new Master(Arrays.asList(args));
        master.startServer();
    }
}