package com.accenture.pip.customermanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Valid()
public class AddressDTO {

    @JsonProperty(required = false)
    private String addressId;
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String pinCode;


}
