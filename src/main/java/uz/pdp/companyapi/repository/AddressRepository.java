package uz.pdp.companyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyapi.entity.Address;
import uz.pdp.companyapi.entity.Company;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    boolean existsByStreetAndHomeNumber(String street, String homeNumber);
    boolean existsByStreetAndHomeNumberAndIdNot(String street, String homeNumber, Integer id);
}
