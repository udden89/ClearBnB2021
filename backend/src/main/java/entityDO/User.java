package entityDO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="user")
@NamedQueries({
        @NamedQuery(name = "User.findByName",
        query = "SELECT u FROM User u WHERE u.firstName = :firstName AND u.surName = :surName"),

        @NamedQuery(name = "User.findAllUsers",
        query = "SELECT u FROM User u")
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;
    @Column (name = "first_name")
    private String firstName;
    @Column (name = "sur_name")
    private String surName;
    private String email;
    private String pw;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Listing> listings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<Rating> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Rating> rating = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<ChatMessage> sentMessage = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<ChatMessage> receivedMessage = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String pw, BankAccount bankAccount) {
        this.firstName = firstName;
        this.surName = lastName;
        this.email = email;
        this.pw = pw;
        this.bankAccount = bankAccount;
    }

    public User(String firstName, String surName, String email, String pw) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.pw = pw;
    }

    public User(String firstName, String surName, String email) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }

    public User(String email, String pw) {
        this.email = email;
        this.pw = pw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Rating> getReviews() {
        return reviews;
    }

    public void setReviews(List<Rating> reviews) {
        this.reviews = reviews;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void addListings(Listing listing) {
        listings.add(listing);
        listing.setUser(this);
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<ChatMessage> getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(List<ChatMessage> sentMessage) {
        this.sentMessage = sentMessage;
    }

    public List<ChatMessage> getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(List<ChatMessage> receivedMessage) {
        this.receivedMessage = receivedMessage;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "ID=" + id +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", bank='" + bankAccount + '\'' +
                '}';
    }
}

