package uz.pdp.companyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyapi.entity.Address;
import uz.pdp.companyapi.payload.AddressDto;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getMethod() {
        List<Address> addresses = addressRepository.findAll();
        return addresses;
    }

    // AGAR ID TOPILMASA NULL QIYMAT QAYTARADI

    public Address getMethodById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        // costumer.orElse(null);
        if (!optionalAddress.isPresent())
            return null;
        return optionalAddress.get();
    }

    /*
    Address QO'SHAMIZ,
    RETURN ApiResponse QAYTARADI
    VALIDATSIYA QO'YDIK
     */
    public ApiResponse addMethod(AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreetAndHomeNumber(addressDto.getStreet(), addressDto.getHomeNumber());
        if (exists)
            return new ApiResponse("This Address already exist", false);
        Address newAddress=new Address();
        newAddress.setStreet(addressDto.getStreet());
        newAddress.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(newAddress);
        return new ApiResponse("Address successfully added", true);
    }
     /*
    Address YANGILAYMIZ,
    RETURN ApiResponse QAYTARADI
    Address JSONDA KELADI
     */

    public ApiResponse updateMethod(Integer id, AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreetAndHomeNumberAndIdNot(addressDto.getStreet(), addressDto.getHomeNumber(),id);
        if (exists)
            return new ApiResponse("This Address number already exist", false);
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("This Address is not exist",false);
        Address address = optionalAddress.get();
       address.setStreet(addressDto.getStreet());
       address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address successfully updated", true);
    }
    /*
   MIJOZ O'CHIRISH,
   RETURN ApiResponse QAYTARADI
   YO'LDA ID BERADI BIZGA
       */
    public ApiResponse deleteMethod(Integer id){

        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address successfully deleted", true);
        } catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }
}
