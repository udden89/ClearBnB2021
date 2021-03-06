package entityDO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="start_date")
    private String startDate;
    @Column(name="end_date")
    private String endDate;

    @Column(name="total_price")
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name="owner_ID")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="listing_ID")
    private Listing listing;

    @JsonManagedReference
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Rating> rating = new ArrayList<>();

    public Booking(){}

    public Booking(User user, Listing listing, String startDate, String endDate, int totalPrice) {
        this.user = user;
        this.listing = listing;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
