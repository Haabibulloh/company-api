package uz.pdp.companyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyapi.entity.Address;
import uz.pdp.companyapi.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
