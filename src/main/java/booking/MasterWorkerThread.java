package booking;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MasterWorkerThread extends Thread {
    private Socket socket;
    private Master master; // Ensure Master class can process JSON accommodations

    public MasterWorkerThread(Socket socket, Master master) {
        this.socket = socket;
        this.master = master;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                try {
                    int index = Integer.parseInt(inputLine);
                    Path jsonFilePath = Paths.get("src/main/java/booking/accommodations.json");
                    master.addAccommodation(jsonFilePath, index);
                    out.println("Accommodation added by index.");
                } catch (NumberFormatException e) {
                    out.println("Error: Invalid index format");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}