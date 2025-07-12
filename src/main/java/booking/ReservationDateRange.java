package booking;

public class ReservationDateRange {
    private ReservationDate from;
    private ReservationDate to;
    private boolean available;

    public ReservationDateRange(){
        ReservationDate dateFrom = new ReservationDate(1, 1, 2024);
        ReservationDate dateTo = new ReservationDate(31, 12, 2024);
        this.from = dateFrom;
        this.to = dateTo;
        this.available = true;
    }
    public ReservationDateRange(ReservationDate from, ReservationDate to){
        this.from = from;
        this.to = to;
        this.available = true;
    }

    public void setFrom(ReservationDate from) {
        this.from = from;
    }

    public void setTo(ReservationDate to) {
        this.to = to;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public ReservationDate getFrom() {
        return from;
    }

    public ReservationDate getTo() {
        return to;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "ReservationDateRange{" +
                "from=" + from +
                ", to=" + to +
                ", available=" + available +
                '}';
    }

    public static void main(String[] args) {
        ReservationDate from = new ReservationDate(2, 4, 2024);
        ReservationDate to = new ReservationDate(30, 4, 2024);
        ReservationDateRange range = new ReservationDateRange(from, to);
        System.out.println(range);
    }
}
