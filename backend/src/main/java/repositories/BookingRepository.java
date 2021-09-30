package repositories;

import entityDO.Listing;
import entityDO.User;
import jakarta.persistence.EntityManager;
import entityDO.Booking;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BookingRepository {
    private EntityManager entityManager;

    public BookingRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Optional<Booking> findById(int id){
        Booking booking = entityManager.find(Booking.class, id);
        return booking != null ? Optional.of(booking) : Optional.empty();
    }

    public List<Booking> findAGuestsOldBookings(User user){

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        try {
            List<Booking> bookings = entityManager.createQuery("SELECT b FROM Booking b WHERE b.endDate < :date AND " +
                    "b.user = :user OR b.listing.user = :user", Booking.class)
                    .setParameter("date", strDate)
                    .setParameter("user", user)
                    .getResultList();

            return bookings;

        }catch (jakarta.persistence.PersistenceException e){
            System.out.println("Error in findAGuestsOldBookings");
            System.out.println(e.getMessage());
        }finally {
            entityManager.clear();
        }

        return null;
    }


    public boolean checkIfListingIsAlreadyBooked(String startDate, String endDate, Listing listing){

        // so Marcus is searching in our DB for one booking's listing that is matching our new booking's listing
        // then new booking's start date is between old booking's start and end date
        // then new booking's end date is between old booking's start and end date

        List bookings = entityManager.createQuery("SELECT b FROM Booking b WHERE b.listing = :listing " +
                "AND (b.startDate between :sDate AND :eDate " +
                        "OR b.endDate between :sDate AND :eDate)", Booking.class)
                .setParameter("listing", listing)
                .setParameter("sDate", startDate)
                .setParameter("eDate", endDate)
                .getResultList();

        return bookings.size() > 0;
    }

    public boolean checkListingAvailableDates(String startDate, String endDate, Listing listing){
        List bookings = entityManager.createQuery("FROM Listing as l WHERE l.id = :listing " +
                "AND (:selectedStartDate IS NULL or l.availableStartDate <= :selectedStartDate) AND " +
                "(:selectedEndDate IS NULL or l.availableEndDate >= :selectedEndDate)", Listing.class)
                .setParameter("listing", listing.getId())
                .setParameter("selectedStartDate", startDate)
                .setParameter("selectedEndDate", endDate)
                .getResultList();
        return bookings.size() > 0;
    }

    public List<Booking> findAllBookings(){
        return entityManager.createQuery("FROM Booking").getResultList();
    }

    public Optional<Booking> addBooking(Booking booking){

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(booking);
            entityManager.getTransaction().commit();
            return Optional.of(booking);
        }
        catch (Exception ex){
           ex.printStackTrace();
        }
        return Optional.empty();
    }
}
