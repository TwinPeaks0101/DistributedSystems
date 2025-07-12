package booking;

import org.json.JSONObject;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class Accommodation implements Serializable {
    //private int price;
    private String accType = "";
    private String roomName;
    private Integer numOfPersons;
    private Area area;
    private Integer stars;
    private Integer numOfReviews;
    private Image roomImage;
    private Integer pricePerNight;
    private LocalDate availableFrom;
    private LocalDate availableTo;

    public Accommodation() {
    }

   public Accommodation(JSONObject obj) {
       this.accType = obj.getString("accType");
       this.roomName = obj.getString("roomName");
       this.numOfPersons = obj.getInt("numOfPersons");
       JSONObject jsonArea = obj.getJSONObject("area");
       this.area = new Area(jsonArea.getString("city"), jsonArea.getString("road"),
               jsonArea.getString("number"), jsonArea.getString("zipCode"));
       this.stars = obj.getInt("stars");
       this.numOfReviews = obj.getInt("numOfReviews");
       this.roomImage = new Image(obj.getString("roomImage"));
       this.pricePerNight = obj.getInt("pricePerNight");
       JSONObject jsonAvailability = obj.getJSONObject("availability");
       this.availableFrom = LocalDate.parse(jsonAvailability.getString("availableFrom"));
       this.availableTo = LocalDate.parse(jsonAvailability.getString("availableTo"));
   }


    public Accommodation(String accType, String roomName, Integer numOfPersons, Area area, Integer stars, Integer numOfReviews, Image roomImage, int pricePerNight) {
        this.accType = accType;
        this.roomName = roomName;
        this.numOfPersons = numOfPersons;
        this.area = area;
        this.stars = stars;
        this.numOfReviews = numOfReviews;
        this.roomImage = roomImage;
        this.pricePerNight = pricePerNight;
    }

    public boolean matches(JSONObject filters) {
        // Area
        if (filters.has("area") && !this.area.getCity().equalsIgnoreCase(filters.getString("area"))) {
            return false;
        }

        // Number of Persons
        if (filters.has("numOfPersons") && (this.numOfPersons == null || this.numOfPersons < filters.getInt("numOfPersons"))) {
            return false;
        }

        // Price Range
        if (filters.has("price")) {
            JSONObject priceFilter = filters.getJSONObject("price");
            int minPrice = priceFilter.getInt("minPrice");
            int maxPrice = priceFilter.getInt("maxPrice");
            if (this.pricePerNight == null || this.pricePerNight < minPrice || this.pricePerNight > maxPrice) {
                return false;
            }
        }

        // Star Ranking
        if (filters.has("stars")) {
            JSONObject starsFilter = filters.getJSONObject("stars");
            int minRanking = starsFilter.getInt("minRanking");
            int maxRanking = starsFilter.getInt("maxRanking");
            if (this.stars == null || this.stars < minRanking || this.stars > maxRanking) {
                return false;
            }
        }

        // Dates
        if (filters.has("dates")) {
            JSONObject datesFilter = filters.getJSONObject("dates");
            LocalDate dateFrom = LocalDate.parse(datesFilter.getString("dateFrom"));
            LocalDate dateTo = LocalDate.parse(datesFilter.getString("dateTo"));
            if (availableFrom == null || availableTo == null ||
                    !availableFrom.isBefore(dateTo) || !availableTo.isAfter(dateFrom)) {
                return false;
            }
        }

        return true;
    }


    public String getAccType() {
        return accType;
    }

    public Integer getPricePerNight(){return pricePerNight;}

    public String getRoomName() {
        return roomName;
    }

    public Integer getNumOfPersons() {
        return numOfPersons;
    }

    public Area getArea() {
        return area;
    }

    public Integer getStars() {
        return stars;
    }

    public Integer getNumOfReviews() {
        return numOfReviews;
    }

    public Image getRoomImage() {
        return roomImage;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }
    public void setPricePerNight(Integer pricePerNight){
        this.pricePerNight = pricePerNight;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setNumOfPersons(Integer numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setNumOfReviews(Integer numOfReviews) {
        this.numOfReviews = numOfReviews;
    }

    public void setRoomImage(Image roomImage) {
        this.roomImage = roomImage;
    }
    public ReservationDateRange getDateRange() {
        return new ReservationDateRange();
    }
    public int getMaxCapacity() {
        return getNumOfPersons();
    }
    public int getRanking() {
        return getStars();
    }
    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDate getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "accType='" + accType + '\'' +
                ", roomName='" + roomName + '\'' +
                ", numOfPersons=" + numOfPersons +
                ", area=" + area +
                ", stars=" + stars +
                ", numOfReviews=" + numOfReviews +
                ", roomImage=" + roomImage +
                ", pricePerNight=" + pricePerNight +
                '}';
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("accType", this.accType);
        json.put("roomName", this.roomName);
        json.put("numOfPersons",this.numOfPersons);
        json.put("area", new JSONObject()
                .put("city", this.area.getCity())
                .put("road", this.area.getRoad())
                .put("number", this.area.getNumber())
                .put("zipCode", this.area.getZipCode()));
        json.put("stars", this.stars);
        json.put("numOfReviews", this.numOfReviews);
        json.put("roomImage", this.roomImage.getAddress());
        json.put("pricePerNight", this.pricePerNight);
        return json;
    }

    public static void main(String[] args) throws IOException {
        ReadJson.readFile(Path.of("src/main/java/booking/accommodations.json"), 0);
    }
}
