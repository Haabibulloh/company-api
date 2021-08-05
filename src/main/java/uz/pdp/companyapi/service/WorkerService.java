package uz.pdp.companyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.companyapi.entity.Address;
import uz.pdp.companyapi.entity.Company;
import uz.pdp.companyapi.entity.Department;
import uz.pdp.companyapi.entity.Worker;
import uz.pdp.companyapi.payload.CompanyDto;
import uz.pdp.companyapi.payload.WorkerDto;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.repository.AddressRepository;
import uz.pdp.companyapi.repository.CompanyRepository;
import uz.pdp.companyapi.repository.DepartmentRepository;
import uz.pdp.companyapi.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    WorkerRepository workerRepository;

    public List<Worker> getMethod() {
        List<Worker> workers = workerRepository.findAll();
        return workers;
    }

    // AGAR ID TOPILMASA NULL QIYMAT QAYTARADI

    public Worker getMethodById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        // costumer.orElse(null);
        if (!optionalWorker.isPresent())
            return null;
        return optionalWorker.get();
    }

    /*
    Worker QO'SHAMIZ,
    RETURN ApiResponse QAYTARADI
    VALIDATSIYA QO'YDIK
     */
    public ApiResponse addMethod(WorkerDto workerDto) {
        boolean exists = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (exists)
            return new ApiResponse("This Worker already exist", false);
        Worker worker=new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("This Address is not exist", false);
        Address address = optionalAddress.get();
        worker.setAddress(address);
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("This Department is not exist", false);
        Department department = optionalDepartment.get();
        worker.setDepartment(department);
        workerRepository.save(worker);
        return new ApiResponse("Worker successfully added", true);
    }
     /*
    Worker YANGILAYMIZ,
    RETURN ApiResponse QAYTARADI
    Worker JSONDA KELADI
     */

    public ApiResponse updateMethod(Integer id, WorkerDto workerDto) {
        boolean exists = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(),id);
        if (exists)
            return new ApiResponse("This Worker already exist", false);
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        Worker worker = optionalWorker.get();

        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("This Address is not exist", false);
        Address address = optionalAddress.get();
        worker.setAddress(address);
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("This Department is not exist", false);
        Department department = optionalDepartment.get();
        worker.setDepartment(department);
        workerRepository.save(worker);
        return new ApiResponse("Worker successfully updated", true);
    }

    /*
   Worker O'CHIRISH,
   RETURN ApiResponse QAYTARADI
   YO'LDA ID BERADI BIZGA
       */
    public ApiResponse deleteMethod(Integer id) {

        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
