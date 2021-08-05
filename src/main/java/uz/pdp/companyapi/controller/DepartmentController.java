package uz.pdp.companyapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapi.entity.Company;
import uz.pdp.companyapi.entity.Department;
import uz.pdp.companyapi.payload.CompanyDto;
import uz.pdp.companyapi.payload.DepartmentDto;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.service.CompanyService;
import uz.pdp.companyapi.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/api/department")
    public ResponseEntity<List<Department>> getListMethod() {
        List<Department> departments = departmentService.getMethod();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/api/department/{id}")
    public HttpEntity<Department> getMethodById(@PathVariable Integer id) {
        Department department = departmentService.getMethodById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping("/api/department")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.addMethod(departmentDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/department/{id}")
    public HttpEntity<ApiResponse> updateMethod(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.updateMethod(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/api/department/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable Integer id) {
        ApiResponse apiResponse = departmentService.deleteMethod(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
