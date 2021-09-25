package application;

import dtos.AddListingDTO;
import entityDO.Listing;
import filter.ListingFilter;
import org.hibernate.Filter;
import org.hibernate.Session;
import repositories.ListingRepository;

import java.sql.Timestamp;
import java.util.List;

public class ListingLogic {

    ListingRepository listingRepository;
    Repositories rp;
    List<AddListingDTO> matchedListingDTO;
    AddListingDTO addListingDTO;

    public ListingLogic(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public ListingLogic() {
    }

    public Listing createNewListing(Listing listing) {
        return listingRepository.addListing(listing);
    }

    public List<Listing> getAllListings(){
        return listingRepository.findAllListings();
    }


    public List<Listing> getFilteredListings(ListingFilter filter){
        // time should send as argument
        rp = new Repositories();
        Session session = rp.entityManager.unwrap(Session.class);

        // Filter does not run condition?
        // If wrong name on filter, system crashes, which means we have connected to our filter
        // But why it oe
//        Filter availableDateFilter = session.enableFilter("dateFilter");
//        System.out.println(session.getEnabledFilter("dateFilter"));

        Timestamp ts1 = filter.getAvailableStartDate();
        Timestamp ts2 = filter.getAvailableEndDate();
        Boolean isBathTub = filter.isBathTub();
        Boolean isParkingLot = filter.isParkingLot();
        Boolean isStove = filter.isStove();
        Boolean isDoubleBed = filter.isDoubleBed();
        Boolean isBubblePool = filter.isBubblePool();
        Boolean isCycle = filter.isCycle();
        Boolean isSauna = filter.isSauna();
        int maxPrice = filter.getPrice();

//        System.out.println("ts1: " + ts1);
//        System.out.println("ts2: " + ts2);
        System.out.println("isBathTub: " + isBathTub);
        System.out.println("isParkingLot: " + isParkingLot);
        System.out.println("isStove: " + isStove);
        System.out.println("isDoubleBed: " + isDoubleBed);
        System.out.println("isBubblePool: " + isBubblePool);
        System.out.println("isCycle: " + isCycle);
        System.out.println("isSauna: " + isSauna);
        System.out.println("MAX price: " + maxPrice);


        // Though this works, we don't want to return the whole list of Listing back to
        // front page, because this would send User info with it, DTO it!

        List matchedListing = session.createQuery("FROM Listing as l WHERE " +
                "(:selectedStartDate IS NULL or l.availableStartDate <= :selectedStartDate) AND " +
                "(:selectedEndDate IS NULL or l.availableEndDate >= :selectedEndDate) AND " +
                        "(:isBathTub IS FALSE or l.amenities.isBathTub IS :isBathTub) AND " +
                        "(:isParkingLot IS FALSE or l.amenities.isParkingLot IS :isParkingLot) AND " +
                        "(:isStove IS FALSE or l.amenities.isStove IS :isStove) AND " +
                        "(:isDoubleBed IS FALSE or l.amenities.isDoubleBed IS :isDoubleBed) AND " +
                        "(:isBubblePool IS FALSE or l.amenities.isBubblePool IS :isBubblePool) AND " +
                        "(:isCycle IS FALSE or l.amenities.isCycle IS :isCycle) AND " +
                        "(:isSauna IS FALSE or l.amenities.isSauna IS :isSauna) AND " +
                        "(:maxPrice = 0 or l.price <= :maxPrice)")
                .setParameter("selectedStartDate", ts1)
                .setParameter("selectedEndDate", ts2)
                .setParameter("isBathTub", isBathTub)
                .setParameter("isParkingLot", isParkingLot)
                .setParameter("isStove", isStove)
                .setParameter("isDoubleBed", isDoubleBed)
                .setParameter("isBubblePool", isBubblePool)
                .setParameter("isCycle", isCycle)
                .setParameter("isSauna", isSauna)
                .setParameter("maxPrice", maxPrice)
                .list();

        for (Object l : matchedListing
             ) {
            System.out.println( l);
        }

//        for (Object l: matchedListing
//             ) {
//            addListingDTO = new AddListingDTO(l);
//             matchedListingDTO(){
//            })
//        }


        return matchedListing;
    }

}
