package uz.pdp.companyapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapi.entity.Company;
import uz.pdp.companyapi.entity.Worker;
import uz.pdp.companyapi.payload.CompanyDto;
import uz.pdp.companyapi.payload.WorkerDto;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.service.CompanyService;
import uz.pdp.companyapi.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping("/api/worker")
    public ResponseEntity<List<Worker>> getListMethod() {
        List<Worker> workers = workerService.getMethod();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/api/worker/{id}")
    public HttpEntity<Worker> getMethodById(@PathVariable Integer id) {
        Worker worker = workerService.getMethodById(id);
        return ResponseEntity.ok(worker);
    }

    @PostMapping("/api/worker")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.addMethod(workerDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/worker/{id}")
    public HttpEntity<ApiResponse> updateMethod(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.updateMethod(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/api/worker/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteMethod(id);
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
