package booking;

import org.json.JSONObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AccommodationList {
    private List<Accommodation> accommodationList = new ArrayList<>();
    int length;

    public AccommodationList(){
    }

    /**
     * reads JSON file and fills the array list of accommodations and the map of available rooms
     * @param path
     */
    public AccommodationList(Path path){
        int size = ReadJson.getJsonArray(path).length();
        System.out.println(size);
        for (int i = 0; i < size; i++){
            Accommodation accommodation = ReadJson.readFile(Path.of("src/main/java/booking/accommodations.json"), i);
            accommodationList.add(accommodation);
        }
        this.length = size;
    }

    public void setAccommodationList(List<Accommodation> accommodationList) {
        this.accommodationList.addAll(accommodationList);
    }


    public int getLength(){
        return this.length;
    }

    public  void addAccommodation(Accommodation accommodation){
        accommodationList.add(accommodation);
    }

    public List<Accommodation> getAccommodationList(){
        return this.accommodationList;
    }

    public int getLengthOfAccommodationList(){
        return accommodationList.size();
    }
    public Accommodation get(int index){
        return accommodationList.get(index);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Accommodation accommodation : this.accommodationList) {
            s.append(accommodation.toString()).append("\n");
        }
        return s.toString();
    }

    public Stream<Accommodation> stream() {
        return accommodationList.stream();
    }

    public void main(String[] args) {
        AccommodationList list = new AccommodationList(Path.of("src/main/java/booking/accommodations.json"));
        System.out.println(list);
    }
}
