package uz.pdp.companyapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.companyapi.entity.Address;
import uz.pdp.companyapi.payload.AddressDto;
import uz.pdp.companyapi.payload.response.ApiResponse;
import uz.pdp.companyapi.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/api/address")
    public ResponseEntity<List<Address>> getListMethod() {
        List<Address> addresses = addressService.getMethod();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/api/address/{id}")
    public HttpEntity<Address> getMethodById(@PathVariable Integer id) {
        Address addressById = addressService.getMethodById(id);
        return ResponseEntity.ok(addressById);
    }

    @PostMapping("/api/address")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addMethod(addressDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/address/{id}")
    public HttpEntity<ApiResponse> updateMethod(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.updateMethod(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/api/address/{id}")
    public ResponseEntity<?> deleteMethod(@PathVariable Integer id) {
        ApiResponse apiResponse = addressService.deleteMethod(id);
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
