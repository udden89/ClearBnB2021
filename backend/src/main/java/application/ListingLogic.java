package application;

import dtos.*;
import entityDO.Listing;
import entityDO.User;
import mapper.ListingService;
import repositories.BookingRepository;
import repositories.ListingRepository;
import repositories.ListingRevisionRepository;
import entityDO.ListingRevision;

import java.util.ArrayList;
import java.util.List;

public class ListingLogic {

    ListingRepository listingRepository;
    BookingRepository bookingRepository;
    Repositories repositories;
    FilteredListingDTO filteredListingDTO;
    AddListingDTO addListingDTOForBooking;
    ListingRevisionRepository listingRevisionRepository;
    private ListingService ls;

    public ListingLogic(Repositories repositories) {
        this.listingRepository = repositories.listingRepository;
        this.bookingRepository = repositories.bookingRepository;
         this.listingRevisionRepository = repositories.listingRevisionRepository;
        this.repositories = repositories;
        this.ls= new ListingService();
    }

    public ListingLogic() {
    }

    public Listing createNewListing(AddListingDTO dto, User user) {
        Listing newListing = ls.convertAddListingToListing(dto, user);
        return listingRepository.addListing(newListing);
    }

    public List<AddListingDTO> getAllListings(){
        List<Listing> allListings = listingRepository.findAllListings();
        List<AddListingDTO> allListingsDTOForBooking = new ArrayList<>();
        for ( Listing l: allListings
        ) {
            addListingDTOForBooking = new AddListingDTO(l);
            allListingsDTOForBooking.add(addListingDTOForBooking);
        }
        return allListingsDTOForBooking;
    }

    public List<FilteredListingDTO> getAllListingsDTO(){



            List<Listing> allListings = listingRepository.findAllListings();
            List<FilteredListingDTO> allListingsDTO = new ArrayList<>();
            //System.out.println("allListing list<Listing>: " + allListings);
            for ( Listing l: allListings
            ) {
              //  System.out.println("before convert: " + l);

                filteredListingDTO = ls.convertListingToFilteredDTO(l);
                //System.out.println("filteredListingDTO: " + filteredListingDTO);
                allListingsDTO.add(filteredListingDTO);
            }
            return allListingsDTO;
    }

    public List<FilteredListingDTO> getFilteredListings(ListingFilterDTO filter){
        List<Listing>matchedListing = listingRepository.filterListing(filter);
        List<FilteredListingDTO> matchedListingDTO = new ArrayList<>();

        for (Listing l: matchedListing
             ) {
            if(!bookingRepository.checkIfListingIsAlreadyBooked(filter.getAvailableStartDate(), filter.getAvailableEndDate(), l)){
                filteredListingDTO = ls.convertListingToFilteredDTO(l);
                matchedListingDTO.add(filteredListingDTO);
            }
        }

        return matchedListingDTO;
    }

    public List<GetAllListingsInSummaryFromUserDTO> getAllListingsInSummaryFromUser(int userID){

        User user = repositories.getUserRep().findUserById(userID);

        List <Listing> listingList = listingRepository.findAllListingsFromUser(user);

        ArrayList<GetAllListingsInSummaryFromUserDTO> allListingsDTO = new ArrayList<>();
        listingList.forEach((listing) ->{
            allListingsDTO.add(new GetAllListingsInSummaryFromUserDTO(listing.getId(), listing.getPrice(), listing.getDescription()));
        });


        return allListingsDTO;

    }

    public Listing updateListing(Listing listing){
        Listing oldList = listingRepository.findById(listing.getId()).get();


        if(listing.getPrice() == 0){
            listing.setPrice(oldList.getPrice());
        }
        if(listing.getDescription() ==(null)){
            listing.setDescription(oldList.getDescription());
        }
        if(listing.getAvailableStartDate()==(null)){
            listing.setAvailableStartDate(oldList.getAvailableStartDate());
        }
        if(listing.getAvailableEndDate()==(null)){
            listing.setAvailableEndDate(oldList.getAvailableEndDate());
        }


        createListingVersionBackup(oldList);

        return listingRepository.updateListing(listing);
    }

    private ListingRevision createListingVersionBackup(Listing oldList){
        ListingRevision copyOldList = new ListingRevision(oldList.getPrice(), oldList.getDescription(),
                oldList.getAvailableStartDate(), oldList.getAvailableEndDate(), oldList, oldList.getUser());
        listingRevisionRepository.addListingRevision(copyOldList);

        return copyOldList;
    }

    public SingeListingDTO getSingleListing(int id){
        Listing l =listingRepository.findById(id).get();
        return new SingeListingDTO(l.getId(), l.getPrice(),l.getDescription(), l.getAvailableStartDate(), l.getAvailableEndDate(),
                l.getAddress().getCity(),l.getAddress().getAddressListing(), l.getAmenities().getBathTub(),
                l.getAmenities().getParkingLot(), l.getAmenities().getStove(),l.getAmenities().getDoubleBed(),
                l.getAmenities().getBubblePool(), l.getAmenities().getBicycle(), l.getAmenities().getSauna());
    }
}
