package booking;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

//public class AccommodationFilter extends Accommodation implements Serializable {
//    private int minPrice;
//    private int maxPrice;
//    private int minRanking;
//    private int maxRanking;
//    private LocalDate dateFrom;
//    private LocalDate dateTo;
//
//    public AccommodationFilter(String accType, String roomName, Integer numOfPersons, Area area, Integer stars, Integer numOfReviews, Image roomImage, int pricePerNight, int minPrice, int maxPrice, int minRanking, int maxRanking, LocalDate dateFrom, LocalDate dateTo) {
//        super(accType, roomName, numOfPersons, area, stars, numOfReviews, roomImage, pricePerNight);
//        this.minPrice = minPrice;
//        this.maxPrice = maxPrice;
//        this.minRanking = minRanking;
//        this.maxRanking = maxRanking;
//        this.dateFrom = dateFrom;
//        this.dateTo = dateTo;
//        // this.availableFrom = LocalDate.parse(obj.getString("availableFrom"));
//        // this.availableTo = LocalDate.parse(obj.getString("availableTo"));
//    }
//
//    public int getMinPrice() {
//        return minPrice;
//    }
//
//    public int getMaxPrice() {
//        return maxPrice;
//    }
//
//    public int getMinRanking() {
//        return minRanking;
//    }
//
//    public int getMaxRanking() {
//        return maxRanking;
//    }
//
//    public LocalDate getDateFrom() {
//        return dateFrom;
//    }
//
//    public LocalDate getDateTo() {
//        return dateTo;
//    }
//
//    public void setMinPrice(int minPrice) {
//        this.minPrice = minPrice;
//    }
//
//    public void setMaxPrice(int maxPrice) {
//        this.maxPrice = maxPrice;
//    }
//
//    public void setMinRanking(int minRanking) {
//        this.minRanking = minRanking;
//    }
//
//    public void setMaxRanking(int maxRanking) {
//        this.maxRanking = maxRanking;
//    }
//
//    public void setDateFrom(LocalDate dateFrom) {
//        this.dateFrom = dateFrom;
//    }
//
//    public void setDateTo(LocalDate dateTo) {
//        this.dateTo = dateTo;
//    }
//
//    @Override
//    public String toString() {
//        return super.toString() +
//                "minPrice=" + minPrice +
//                ", maxPrice=" + maxPrice +
//                ", minRanking=" + minRanking +
//                ", maxRanking=" + maxRanking +
//                ", dateFrom=" + dateFrom +
//                ", dateTo=" + dateTo +
//                '}';
//    }
//
//    public static void main(String[] args) {
//
//    }
//}
