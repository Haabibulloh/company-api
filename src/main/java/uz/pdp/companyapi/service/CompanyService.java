package uz.pdp.companyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyapi.entity.Address;
import uz.pdp.companyapi.entity.Company;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.payload.CompanyDto;
import uz.pdp.companyapi.repository.AddressRepository;
import uz.pdp.companyapi.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    public List<Company> getMethod() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    // AGAR ID TOPILMASA NULL QIYMAT QAYTARADI

    public Company getMethodById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        // costumer.orElse(null);
        if (!optionalCompany.isPresent())
            return null;
        return optionalCompany.get();
    }

    /*
    Company QO'SHAMIZ,
    RETURN ApiResponse QAYTARADI
    VALIDATSIYA QO'YDIK
     */
    public ApiResponse addMethod(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists)
            return new ApiResponse("This Company already exist", false);
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("This Address is not exist", false);
        Address address = optionalAddress.get();
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Company successfully added", true);
    }
     /*
    Company YANGILAYMIZ,
    RETURN ApiResponse QAYTARADI
    Company JSONDA KELADI
     */

    public ApiResponse updateMethod(Integer id, CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (exists)
            return new ApiResponse("This Company already exist", false);
        Optional<Company> optionalCompany = companyRepository.findById(id);
        Company company = optionalCompany.get();

        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("This Address is not exist", false);
        Address address = optionalAddress.get();
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Company successfully updated", true);
    }

    /*
   Company O'CHIRISH,
   RETURN ApiResponse QAYTARADI
   YO'LDA ID BERADI BIZGA
       */
    public ApiResponse deleteMethod(Integer id) {

        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
