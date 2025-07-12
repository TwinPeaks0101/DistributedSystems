package booking;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class AvailabilityOfAccommodations implements Runnable{
    private Map<String, ReservationDateRange> roomAvailability;

    @Override
    public void run() {
        // Εδώ μπορείτε να ορίσετε μια συνεχή λειτουργία που θα εκτελείται από το νήμα
        while (true) {
            // Προσθέστε τυχόν επιπλέον λειτουργίες που θέλετε να εκτελεστούν επανειλημμένα

            try {
                Thread.sleep(1000); // Περιμένει 1 δευτερόλεπτο πριν συνεχίσει
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public AvailabilityOfAccommodations() {

        this.roomAvailability = new HashMap<>();
    }

    public Map<String, ReservationDateRange> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Initial input to map from JSONfile
     *
     * @param path
     */
    public void addRoomAsAvailableFromJSON(Path path) {
        AccommodationList accommodationList = new AccommodationList(path);
        for (int i = 0; i < accommodationList.getLengthOfAccommodationList(); i++) {
            roomAvailability.put(accommodationList.get(i).getRoomName(), new ReservationDateRange());
        }
    }

    /**
     * From Manager input to map
     *
     * @param roomName
     * @param from
     * @param to
     */
    public void addRoomAsAvailableFromManager(String roomName, ReservationDate from, ReservationDate to) {
        System.out.println("..................function: addRoomAsAvailableFromManager...............................");
        boolean exist = false;
        for (String key : roomAvailability.keySet()) {
            if (key.equals(roomName)) {
                roomAvailability.put(roomName, new ReservationDateRange(from, to));
                exist = true;
            }
        }
        if (exist) {
            System.out.println("The room " + roomName + " successfully inserted as available");
        } else {
            System.out.println("The specific room " + roomName + " does not exist.");
        }
    }

    /**
     * booking of a room - client function
     *
     * @param roomName
     */
    public synchronized void bookingOfRoom(String roomName) {
        System.out.println("..................function: bookingOfRoom...............................");
        boolean booking = false;
        for (String key : roomAvailability.keySet()) {
            ReservationDateRange range = roomAvailability.get(key);
            if (key.equals(roomName))
                if (range.isAvailable()) {
                    range.setAvailable(false);
                    booking = true;
                }
        }
        if (booking) {
            System.out.println("The " + roomName + " is successfully booked.");
        } else {
            System.out.println("The " + roomName + " is not available.");
        }
    }

    @Override
    public String toString() {
        return "Manager{" +
                "roomAvailability=" + roomAvailability +
                '}';
    }

    public static void main(String[] args) {
        // Default gemisma tou list apo JSON file
        AccommodationList list = new AccommodationList(Path.of("src/main/java/booking/accommodations.json")); // object
        // Default gemisma tou map apo JSON file
        AvailabilityOfAccommodations availabilityOfAccommodations = new AvailabilityOfAccommodations(); // object

        ReservationDate from = new ReservationDate(20, 4, 2024);
        ReservationDate to = new ReservationDate(30, 4, 2024);

        availabilityOfAccommodations.addRoomAsAvailableFromJSON(Path.of("src/main/java/booking/accommodations.json")); // map

        availabilityOfAccommodations.addRoomAsAvailableFromManager("lala", from, to);
        // ta typwnei opws akrivws ta exei parei apo to JSON
        for (String key : availabilityOfAccommodations.getRoomAvailability().keySet()) {
            System.out.println(key + ": " +availabilityOfAccommodations.getRoomAvailability().get(key));
        }
        // O manager allazei mia hmerominia gia th diathesimotita tou dwmatiou
        availabilityOfAccommodations.addRoomAsAvailableFromManager("130", from, to);

        for (String key : availabilityOfAccommodations.getRoomAvailability().keySet()) {
            System.out.println(key + ": " +availabilityOfAccommodations.getRoomAvailability().get(key));
        }

        // booking of a room
        availabilityOfAccommodations.bookingOfRoom("235");
        availabilityOfAccommodations.bookingOfRoom("500");

    }
}
