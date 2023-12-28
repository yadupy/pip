package com.accenture.pip.customermanagement.controller;

import com.accenture.pip.customermanagement.dto.AddressDTO;
import com.accenture.pip.customermanagement.dto.AddressUpdateRequest;
import com.accenture.pip.customermanagement.dto.CustomerResponse;
import com.accenture.pip.customermanagement.dto.CustomerUpdateRequest;
import com.accenture.pip.customermanagement.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer address Management Api", description = "This api is used to manage address for customer records")
@RestController
@Validated
@Slf4j
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/get/{addressId}")
    @Operation(
            summary = "fetches an address",
            description = "fetches an address based on addressId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<AddressDTO> getAddress(@PathVariable @NotNull String addressId){
        log.info("GET Method called to fetch customer with addressId {}",addressId);
        AddressDTO addressDTO  = addressService.getAddress(addressId);
        return ResponseEntity.ok(addressDTO);
    }

    @PatchMapping("/update/{addressId}")
    @Operation(
            summary = "update existing address",
            description = "update existing address based on addressId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable @NotNull String addressId,
            @RequestBody @NotNull @Valid AddressUpdateRequest updateRequest){
        log.info("PATCH Method called to update an existing address with addressId :{}",addressId);
        AddressDTO response = addressService.updateAddress(updateRequest,addressId);
        return ResponseEntity.ok(response);
    }


}
