package booking;

import java.util.Date;

public class ReservationDate implements Comparable<ReservationDate> {
    private int day;
    private int month;
    private int year;

    public ReservationDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public int getDay() {
        return day;
    }


    public int getMonth() {
        return month;
    }


    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public void setMonth(int month) {
        this.month = month;
    }


    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    public static void main(String[] args) {

    }

    @Override
    public int compareTo(ReservationDate otherDate) {
        // First, compare years
        int yearComparison = Integer.compare(this.year, otherDate.year);
        if (yearComparison != 0) {
            return yearComparison;
        }

        // If years are equal, compare months
        int monthComparison = Integer.compare(this.month, otherDate.month);
        if (monthComparison != 0) {
            return monthComparison;
        }

        // If months are equal, compare days
        return Integer.compare(this.day, otherDate.day);
    }
}
