/*package booking;

import java.nio.file.Path;
import java.util.*;
import java.util.Scanner;

public class Menu {
    private static final int MANAGER_MODE = 1;
    private static final int CLIENT_MODE = 2;
    private static final Scanner scanner = new Scanner(System.in);

    private static int selectMode() {
        int selection;
        do {
            System.out.println("Welcome! Please choose between Manager and Client mode:");
            System.out.println("1: Manager Mode");
            System.out.println("2: Client Mode");
            selection = scanner.nextInt();
        } while (selection != MANAGER_MODE && selection != CLIENT_MODE);
        return selection;
    }
    private void managerMode() {
        System.out.println("You are now in Manager Mode!");
        int selection;
        do {
            displayManagerMenu();
            selection = scanner.nextInt();
            handleManagerSelection(selection);
        } while (selection != 0);
    }
    private static void displayManagerMenu() {
        System.out.println("Manager Menu:");
        System.out.println("1: Change accommodation availability");
        System.out.println("2: Add accommodation");
        System.out.println("3: Add range of available dates");
        System.out.println("4: View booked accommodations");
        System.out.println("0: Exit Manager Mode");
    }
    private void handleManagerSelection(int selection) {
        switch (selection) {
            case 1:
                changeAccommodationAvailability();
                break;
            case 2:
                addAccommodation();
                break;
            case 3:
                addAvailableDates();
                break;
            case 4:
                viewBookedAccommodations();
                break;
            default:
                System.out.println("Invalid selection");
                break;
        }
    }
    private static void changeAccommodationAvailability() {
        Accommodation accommodation = new Accommodation();
        AccommodationList accommodationList = new AccommodationList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the index of the accommodation : ");
        accommodationList.getAccommodationList();
        int index = scanner.nextInt();
        Accommodation selectedAccommodation = accommodationList.get(index);
        System.out.println("Enter new availability (true/false):");
        boolean availability = scanner.nextBoolean();
       // selectedAccommodation.setAvailable(availability);
        System.out.println("Accommodation availability updated successfully!");

    }
    private void addAccommodation() {
        Accommodation accommodation = new Accommodation();
        Accommodation  newAccommodation = accommodation.createAccommodation();
        AccommodationList accommodationList = new AccommodationList();
        accommodationList.getAccommodationList().add(newAccommodation);
        System.out.println("Accommodation added successfully!");
    }
    private static void addAvailableDates() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the availability date range:");
        System.out.println("From (DD MM YYYY):");
        int fromDay = scanner.nextInt();
        int fromMonth = scanner.nextInt();
        int fromYear = scanner.nextInt();
        ReservationDate from = new ReservationDate(fromDay, fromMonth, fromYear);

        System.out.println("To (DD MM YYYY):");
        int toDay = scanner.nextInt();
        int toMonth = scanner.nextInt();
        int toYear = scanner.nextInt();
        ReservationDate to = new ReservationDate(toDay, toMonth, toYear);

        // Create a ReservationDateRange object with the provided dates
        ReservationDateRange dateRange = new ReservationDateRange(from, to);
        System.out.println(STR."Available dates set successfully: \{dateRange}");
    }
    private static void viewBookedAccommodations() {
        AccommodationList accommodationList = new AccommodationList();
        System.out.println("Booked Accommodations:");
        if (accommodationList == null) {
            System.out.println("No accommodations booked.");
        } else {
            boolean AccommodationList = false;
            System.out.println(AccommodationList);
        }
    }
    private static void clientMode() {
        System.out.println("You are now in Client Mode!");
        int selection;
        do {
            displayClientMenu();
            selection = scanner.nextInt();
            handleClientSelection(selection);
        } while (selection != 0);
    }
    private static void displayClientMenu() {
        System.out.println("Client Menu:");
        System.out.println("1: Filter by area");
        System.out.println("2: Filter by date range");
        System.out.println("3: Filter by number of people");
        System.out.println("4: Filter by price");
        System.out.println("5: Filter by ranking");
        System.out.println("0: Logout");
    }
    private static void handleClientSelection(int selection) {
        switch (selection) {
            case 1:
                filterByArea();
                break;
            case 2:
                filterByDateRange();
                break;
            case 3:
                filterByNumberOfPeople();
                break;
            case 4:
                filterByPrice();
                break;
            case 5:
                filterByRanking();
                break;
            default:
                System.out.println("Invalid selection");
                break;
        }
    }
    private static void filterByArea() {
        System.out.println("Filtering by area...");
        System.out.println("Enter the area details to filter by:");
        AccommodationList accommodations = new AccommodationList();
        //accommodations.getAccommodationList();
        System.out.print("City: ");
        String city = scanner.next();
        System.out.print("Road: ");
        String road = scanner.next();
        System.out.print("Number: ");
        String number = scanner.next();
        System.out.print("Zip Code: ");
        String zipCode = scanner.next();
        boolean found = false;
        for (int i = 0; i < accommodations.getAccommodationList().size(); i++) {
            Area area = new Area(city,road, number, zipCode); //accommodations.getArea();
            if (area.getCity().equals(accommodations.getAccommodationList().get(i).getArea().getCity()) && area.getRoad().equals(accommodations.getAccommodationList().get(i).getArea().getRoad()) &&
                    area.getNumber().equals(accommodations.getAccommodationList().get(i).getArea().getNumber()) && area.getZipCode().equals(accommodations.getAccommodationList().get(i).getArea().getNumber())) {
                System.out.println(accommodations); // Print the matched accommodation
                found = true;
            }
        }

        if (!found) {
            System.out.println("No accommodations found for the provided area.");
        }
    }
    private static void filterByDateRange() {
        System.out.println("Filtering by date range...");
        System.out.println("Enter the date range:");

        // Get user input for the date range
        System.out.print("From (DD MM YYYY): ");
        int fromDay = scanner.nextInt();
        int fromMonth = scanner.nextInt();
        int fromYear = scanner.nextInt();
        ReservationDate fromDate = new ReservationDate(fromDay, fromMonth, fromYear);

        System.out.print("To (DD MM YYYY): ");
        int toDay = scanner.nextInt();
        int toMonth = scanner.nextInt();
        int toYear = scanner.nextInt();
        ReservationDate toDate = new ReservationDate(toDay, toMonth, toYear);

        // Assuming you have a list of accommodations, you can filter them by date range
        AccommodationList accommodations = new AccommodationList();
        accommodations.getAccommodationList();

        // Iterate over accommodations and check if they fall within the specified date range
        boolean found = false;
        for (int i = 0; i < accommodations.getAccommodationList().size(); i++) {
            ReservationDateRange dateRange = new ReservationDateRange(new ReservationDate(fromDay, fromMonth, fromYear), new ReservationDate(toDay, toMonth, toYear));
            if (dateRange.getFrom().compareTo(fromDate) >= 0 && dateRange.getTo().compareTo(toDate) <= 0) {
                System.out.println(accommodations); // Print the matched accommodation
                found = true;
            }
        }

        if (!found) {
            System.out.println("No accommodations found within the specified date range.");
        }
    }
    private static void filterByNumberOfPeople() {
        System.out.println("Filtering by number of people...");
        System.out.print("Enter the number of people: ");
        int numberOfPeople = scanner.nextInt();

        // Assuming you have a list of accommodations, you can filter them by number of people
        AccommodationList accommodations = new AccommodationList();
        accommodations.getAccommodationList();

        // Iterate over accommodations and check if they can accommodate the specified number of people
        boolean found = false;
        for (int i = 0; i < accommodations.getAccommodationList().size(); i++) {
            if (accommodations.getAccommodationList().get(i).getNumOfPersons() >= numberOfPeople) {
                System.out.println(accommodations); // Print the matched accommodation
                found = true;
            }
        }

        if (!found) {
            System.out.println(STR."No accommodations found that can accommodate \{numberOfPeople} people.");
        }
    }

    private static void filterByPrice() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the maximum price per night: ");
        double maxPrice = scanner.nextDouble();

        AccommodationList accommodations = new AccommodationList();
        accommodations.getAccommodationList();

        boolean found = false;
        for (int i = 0; i < accommodations.getAccommodationList().size(); i++) {
            double pricePerNight = accommodations.getAccommodationList().get(i).getPricePerNight();
            if (pricePerNight <= maxPrice) {
                System.out.println(accommodations); // Print the matched accommodation
                found = true;
            }
        }

        if (!found) {
            System.out.println("No accommodations found within the specified price range.");
        }
    }


    private static void filterByRanking () {
        System.out.println("Filtering by ranking...");
        System.out.print("Enter the minimum ranking (1-5): ");
        int minRanking = scanner.nextInt();
        System.out.print("Enter the maximum ranking (1-5): ");
        int maxRanking = scanner.nextInt();
        AccommodationList accommodations = new AccommodationList();
        accommodations.getAccommodationList();
        boolean found = false;
        for (int i = 0; i < accommodations.getAccommodationList().size(); i++) {
            int ranking = accommodations.getAccommodationList().get(i).getRanking();
            if (ranking >= minRanking && ranking <= maxRanking) {
                System.out.println(accommodations); // Print the matched accommodation
                found = true;
            }
        }

        if (!found) {
            System.out.println("No accommodations found within the specified ranking range.");
        }
    }
    public static void main(String[] args) {
        AccommodationList ac;
        ac = new AccommodationList(Path.of("src/main/java/booking/accommodations.json"));
        int modeSelection = selectMode();
        switch (modeSelection) {
            case MANAGER_MODE:
                //managerMode();
                break;
            case CLIENT_MODE:
                clientMode();
                break;
            default:
                System.out.println("Invalid selection. Exiting...");
                break;
        }
        scanner.close();
    }
}

*/