package uz.pdp.companyapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    @NotNull(message = "Name doesn't have to be empty ")
    private String name;
    @NotNull(message = "Phone number doesn't have to be empty ")
    private String phoneNumber;

    private Integer addressId;

    private Integer departmentId;
}
