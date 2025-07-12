package booking;

public class Reservation {
    private Accommodation accommodation;
    private Tenant tenant;
    private Integer numOfPeople;
    private ReservationDateRange date;

    public Reservation(String motel, String number, int numOfPersons, Area crete, int stars, int numOfReviews, Image motelImage, boolean available) {
    }
    public Reservation(Accommodation accommodation, Tenant tenant, Integer numOfPeople, ReservationDateRange date) {
        this.accommodation = accommodation;
        this.tenant = tenant;
        this.numOfPeople = numOfPeople;
        this.date = date;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Integer getNumOfPeople() {
        return numOfPeople;
    }

    public ReservationDateRange getDate() {
        return date;
    }
    public Integer getprice(){
        Integer price = null;
        return  price;}

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setNumOfPeople(Integer numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public void setDate(ReservationDateRange date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return STR."Reservation{accommodation=\{accommodation}, tenant=\{tenant}, numOfPeople=\{numOfPeople}, date=\{date},price=\{getprice()}\{'}'}";
    }
}
