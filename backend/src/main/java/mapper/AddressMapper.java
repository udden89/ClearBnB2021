package mapper;
import dtos.AddAddressDTO;
import dtos.UpdateAddressDTO;
import entityDO.Address;
import entityDO.Listing;


public class AddressMapper {


    public Address convertAddAddressToAddress(AddAddressDTO dto, Listing listing) {

        return new Address(dto.getAddressListing(), dto.getCity(),listing);
    }

    public Address convertUpdateAddressToAddress(UpdateAddressDTO dto, Listing listing){

        return new Address(listing.getId(), dto.getCity(), dto.getAddressListing(), listing);

    }
}
