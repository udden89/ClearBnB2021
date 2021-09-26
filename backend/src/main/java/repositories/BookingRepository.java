package repositories;

import entityDO.Listing;
import jakarta.persistence.EntityManager;
import entityDO.Booking;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class BookingRepository {
    private EntityManager entityManager;

    public BookingRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Optional<Booking> findById(Integer id){
        Booking booking = entityManager.find(Booking.class, id);
        return booking != null ? Optional.of(booking) : Optional.empty();
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

//    public boolean checkIfListingIsAlreadyBooked(Timestamp startDate, Timestamp endDate, Listing listing){
//
//        List bookings = entityManager.createQuery("SELECT b FROM Booking b WHERE b.listing = :listing " +
//                        "AND (b.startDate between :sDate AND :eDate " +
//                        "OR b.endDate between :sDate AND :eDate)", Booking.class)
//                .setParameter("listing", listing)
//                .setParameter("sDate", startDate)
//                .setParameter("eDate", endDate)
//                .getResultList();
//
//        return bookings.size() > 0;
//    }

    public List<Booking> findAllBookings(){
        return entityManager.createQuery("from Booking").getResultList();
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
