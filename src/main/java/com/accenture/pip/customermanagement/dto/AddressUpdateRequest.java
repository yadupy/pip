package com.accenture.pip.customermanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Valid()
/*@ToString
@EqualsAndHashCode*/
public class AddressUpdateRequest {

    private String houseNo;
    private String street;
    private String city;
    private String state;

    @Pattern(regexp = "([1-9]{1}[0-9]{5}|[1-9]{1}[0-9]{3}\\\\s[0-9]{3})",message = "Invalid pin code")
    private String pinCode;
}
