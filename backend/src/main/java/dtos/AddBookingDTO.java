package dtos;

import entityDO.Listing;
import entityDO.User;

import java.util.Date;

public class AddBookingDTO {

    private String startDate;
    private String endDate;
    private int totalPrice;

    public AddBookingDTO() {
    }

    public AddBookingDTO(String startDate, String endDate, int totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
