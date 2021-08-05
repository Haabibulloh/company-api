package uz.pdp.companyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.companyapi.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
   boolean existsByCorpName(String corpName);
   boolean existsByCorpNameAndIdNot(String corpName, Integer id);
}
