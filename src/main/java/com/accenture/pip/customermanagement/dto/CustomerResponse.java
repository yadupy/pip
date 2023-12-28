package com.accenture.pip.customermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Valid()
public class CustomerResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    @JsonProperty("address")
    private Set<AddressDTO> addressDTOs;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
