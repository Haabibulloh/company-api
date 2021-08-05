package uz.pdp.companyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyapi.entity.Address;
import uz.pdp.companyapi.entity.Company;
import uz.pdp.companyapi.entity.Department;
import uz.pdp.companyapi.payload.CompanyDto;
import uz.pdp.companyapi.payload.DepartmentDto;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.repository.AddressRepository;
import uz.pdp.companyapi.repository.CompanyRepository;
import uz.pdp.companyapi.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> getMethod() {
        List<Department> departments = departmentRepository.findAll();
        return departments;
    }

    // AGAR ID TOPILMASA NULL QIYMAT QAYTARADI

    public Department getMethodById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        // costumer.orElse(null);
        if (!optionalDepartment.isPresent())
            return null;
        return optionalDepartment.get();
    }

    /*
    Department QO'SHAMIZ,
    RETURN ApiResponse QAYTARADI
    VALIDATSIYA QO'YDIK
     */
    public ApiResponse addMethod(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByName(departmentDto.getName());
        if (exists)
            return new ApiResponse("This Department already exist", false);
        Department department=new Department();
        department.setName(departmentDto.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("This Company is not exist", false);
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department successfully added", true);
    }
     /*
    Department YANGILAYMIZ,
    RETURN ApiResponse QAYTARADI
    Department JSONDA KELADI
     */

    public ApiResponse updateMethod(Integer id, DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByNameAndIdNot(departmentDto.getName(),id);
        if (exists)
            return new ApiResponse("This Department already exist", false);
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        Department department = optionalDepartment.get();

        department.setName(departmentDto.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("This Company is not exist", false);
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department successfully updated", true);
    }

    /*
   Department O'CHIRISH,
   RETURN ApiResponse QAYTARADI
   YO'LDA ID BERADI BIZGA
       */
    public ApiResponse deleteMethod(Integer id) {

        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Company successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
