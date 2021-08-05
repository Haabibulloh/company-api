package uz.pdp.companyapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotNull(message = "Street doesn't have to be empty ")
    private String street;
    @NotNull(message = "Home number doesn't have to be empty ")
    private String homeNumber;
}
