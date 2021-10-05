package mapper;

import dtos.AddListingDTO;
import dtos.SingeListingDTO;
import dtos.UpdateListingDTO;
import dtos.FilteredListingDTO;
import entityDO.Listing;
import entityDO.User;

public class ListingMapper {

    // this converts DTO into a fake
    public Listing convertAddListingToListing(AddListingDTO dto, User owner) {
        return new Listing(dto.getPrice(), dto.getDescription(), dto.getAvailableStartDate(), dto.getAvailableEndDate(), owner);
    }

    public Listing convertupdateListingToListing(UpdateListingDTO dto, User owner) {
        return new Listing(dto.getId(), dto.getPrice(), dto.getDescription(), dto.getAvailableStartDate(), dto.getAvailableEndDate(), owner);
    }

    public FilteredListingDTO convertListingToFilteredDTO(Listing listing){

        return new FilteredListingDTO(listing.getId(), listing.getDescription(), (int) Math.ceil(listing.getPrice()*1.15), listing.getAddress().getCity(), listing.getAddress().getAddressListing());
    }


}