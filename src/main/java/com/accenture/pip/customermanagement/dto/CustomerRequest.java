package com.accenture.pip.customermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.datetime.standard.DateTimeFormatterFactoryBean;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Valid()
public class CustomerRequest {

    @Pattern(regexp = "[a-zA-Z][0-9a-zA-Z .,'-]*$")
    @NotNull(message = "firstName is mandatory")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z][0-9a-zA-Z .,'-]*$")
    @NotNull(message = "lastName is mandatory")
    private String lastName;

    @NotNull(message = "email is mandatory")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotNull(message = "contactNumber is mandatory")
    @Pattern(regexp="^[789]\\d{9}$")
    private String contactNumber;

    @NotNull
    @JsonProperty("address")
    @NotNull(message = "address is mandatory")
    private Set<AddressDTO> addressDTOs;

}
