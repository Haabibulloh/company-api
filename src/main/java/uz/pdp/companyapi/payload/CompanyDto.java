package uz.pdp.companyapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "Corp name doesn't have to be empty ")
    private String corpName;
    @NotNull(message = "Director name doesn't have to be empty ")
    private String directorName;

    private Integer addressId;
}
