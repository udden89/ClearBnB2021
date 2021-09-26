package dtos;

public class GetAllListingsInSummaryFromUserDTO {
    private int id;
    private int price;
    private String description;

    public GetAllListingsInSummaryFromUserDTO(int id, int price, String description) {
        this.id = id;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "GetAllListingsOfAUserDTO{" +
                "id=" + id +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}