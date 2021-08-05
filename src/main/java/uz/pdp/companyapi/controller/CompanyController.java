package uz.pdp.companyapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapi.entity.Company;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.payload.CompanyDto;
import uz.pdp.companyapi.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("/api/company")
    public ResponseEntity<List<Company>> getListMethod() {
        List<Company> companies = companyService.getMethod();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/api/company/{id}")
    public HttpEntity<Company> getMethodById(@PathVariable Integer id) {
        Company company = companyService.getMethodById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/api/company")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.addMethod(companyDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/company/{id}")
    public HttpEntity<ApiResponse> updateMethod(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.updateMethod(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/api/company/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteMethod(id);
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
