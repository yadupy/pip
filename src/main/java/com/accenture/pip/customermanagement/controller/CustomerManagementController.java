package com.accenture.pip.customermanagement.controller;

import com.accenture.pip.customermanagement.dto.CustomerRequest;
import com.accenture.pip.customermanagement.dto.CustomerResponse;
import com.accenture.pip.customermanagement.dto.CustomerUpdateRequest;
import com.accenture.pip.customermanagement.service.CustomerManagementService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/*
addCustomer
- getAllCustomers
- getCustomer
- updateCustomer
- deleteCustomer
- deleteCustomerBeforeSpecificDate
- getCustomerWithSpecificAddress*/


@Tag(name = "Customer Management Api", description = "This api is used to manage customer records")
@RestController
@Validated
@Slf4j
@RequestMapping("/customer")
public class CustomerManagementController {

    @Autowired
    CustomerManagementService customerManagementService;

    @PostMapping("/create")
    @Operation(
            summary = "create a new customer",
            description = "creates a new customer from CustomerRequest object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody @NotNull @Valid CustomerRequest customerRequest){
        log.info("POST Method called to create customer with email address:{}",customerRequest.getEmail());
        CustomerResponse customerResponse = customerManagementService.createCustomer(customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "fetches all the customers",
            description = "fetches all the customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        log.info("GET Method called to fetch all the customers");
        List<CustomerResponse> customerResponseList = customerManagementService.getAllCustomers();
        return ResponseEntity.ok(customerResponseList);
    }

    @GetMapping("/get/{customerId}")
    @Operation(
            summary = "fetches a customer",
            description = "fetches a customer based on customerId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable @Valid @NotNull String customerId){
        log.info("GET Method called to fetch customer with customerId {}",customerId);
        CustomerResponse customerResponse  = customerManagementService.getCustomer(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    @PatchMapping("/update/{customerId}")
    @Operation(
            summary = "update existing customer",
            description = "update existing customer based on customerId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable @NotNull String customerId,
            @RequestBody @NotNull @Valid CustomerUpdateRequest customerRequest){
        log.info("PATCH Method called to update an existing customer with customerId :{}",customerId);
        CustomerResponse customerResponse = customerManagementService.updateCustomer(customerRequest,customerId);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/delete/customerId/{customerId}")
    @Operation(
            summary = "fetches all the customers",
            description = "fetches all the customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<String> deleteCustomer(@PathVariable @Valid @NotNull String customerId){
        log.info("DELETE Method called to delete customer with customerId,{}",customerId);
         customerManagementService.deleteCustomer(customerId);
        return ResponseEntity.ok("customer deleted successfully");
    }

    @DeleteMapping("/delete/date/{date}")
    @Operation(
            summary = "deletes all the customers",
            description = "deletes all the customers created before date parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<String> deleteCustomerBeforeSpecificDate(
            @PathVariable @NotNull  @Valid @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") String date){
        log.info("DELETE Method called to delete customers created before ,{}",date);
        customerManagementService.deleteCustomerBeforeSpecificDate(date);
        return ResponseEntity.ok("customers deleted successfully");
    }

    @GetMapping("/get/addressId/{addressId}")
    @Operation(
            summary = "fetches a customer",
            description = "fetches a customer based on addressId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400",description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Resource Not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),
    })
    @ResponseBody
    public ResponseEntity<CustomerResponse> getCustomerWithSpecificAddress(@PathVariable @Valid @NotNull String addressId){
        log.info("GET Method called to fetch customer with customerId {}",addressId);
        CustomerResponse customerResponse  = customerManagementService.getCustomerWithSpecificAddress(addressId);
        return ResponseEntity.ok(customerResponse);
    }

}
