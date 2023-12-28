package com.accenture.pip.customermanagement.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import com.accenture.pip.customermanagement.dto.CustomerRequest;
import com.accenture.pip.customermanagement.dto.CustomerResponse;
import com.accenture.pip.customermanagement.dto.CustomerUpdateRequest;
import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;
import com.accenture.pip.customermanagement.exception.ContactNumberAlreadyInUseException;
import com.accenture.pip.customermanagement.exception.CustomerNotFoundException;
import com.accenture.pip.customermanagement.exception.EmailAlreadyInUseException;
import com.accenture.pip.customermanagement.mapper.CustomerMapper;
import com.accenture.pip.customermanagement.repository.AddressRepository;
import com.accenture.pip.customermanagement.repository.CustomerRepository;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import util.CustomerUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CustomerManagementServiceTest {

    @InjectMocks
    CustomerManagementService managementService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerManagementService customerManagementService;
    @Mock
    AddressRepository addressRepository;

    @Mock
    CustomerMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomerSuccess() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();
        String body = Resources.toString(Resources.getResource("payloads/addCustomer.json"), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();
        CustomerRequest customerRequest = gson.fromJson(body,CustomerRequest.class);
        when(customerRepository.findByEmailOrContactNumber(any(),any())).thenReturn(Optional.empty());
        when(customerRepository.saveAndFlush(any())).thenReturn(existingCustomer);
        when(mapper.customerRequestToCustomer(any())).thenReturn(existingCustomer);
        when(mapper.customerToCustomerResponse(any())).thenReturn(response);
        CustomerResponse savedCustomer  = managementService.createCustomer(customerRequest);
        Assertions.assertEquals(savedCustomer.getEmail(),existingCustomer.getEmail());
        Assertions.assertEquals(savedCustomer.getContactNumber(),existingCustomer.getContactNumber());
    }

    @Test
    public void testCreateCustomerFailure() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();
        String body = Resources.toString(Resources.getResource("payloads/addCustomer.json"), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();
        CustomerRequest customerRequest = gson.fromJson(body,CustomerRequest.class);
        when(customerRepository.findByEmailOrContactNumber(any(),any())).thenReturn(Optional.of(existingCustomer));

        Assertions.assertThrows(ContactNumberAlreadyInUseException.class,()-> {
            managementService.createCustomer(customerRequest);
        });
    }
    @Test
    public void testGetAllCustomerSuccess() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();

        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();

        when(customerRepository.findAll()).thenReturn(List.of(existingCustomer));
        when(mapper.customerToCustomerResponse(any())).thenReturn(response);
        List<CustomerResponse> savedCustomer  = managementService.getAllCustomers();
        Assertions.assertFalse(savedCustomer.isEmpty());

    }

    @Test
    public void testGetCustomerSuccess() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();

        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();

        when(customerRepository.findByCustomerId(any())).thenReturn(Optional.of(existingCustomer));
        when(mapper.customerToCustomerResponse(any())).thenReturn(response);

        CustomerResponse savedCustomer  = managementService.getCustomer(existingCustomer.getCustomerId());

        Assertions.assertFalse(savedCustomer.getContactNumber().isEmpty());

    }
    @Test
    public void testGetCustomerFailure() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();

        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();

        when(customerRepository.findByCustomerId(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class,()-> {
            managementService.getCustomer(UUID.randomUUID().toString());
        });

    }

    @Test
    public void testUpdateCustomerSuccess() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();

        String body = Resources.toString(Resources.getResource("payloads/updateCustomer.json"), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        CustomerUpdateRequest customerUpdateRequest = gson.fromJson(body, CustomerUpdateRequest.class);
        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .addressDTOs(Set.of(CustomerUtil.addAddressDTO()))
                .build();


        when(customerRepository.findByCustomerId(any())).thenReturn(Optional.of(existingCustomer));
        when(customerManagementService.updateExistingCustomer(any(),any())).thenReturn(CustomerUtil.createCustomer());
        when(customerRepository.saveAndFlush(any())).thenReturn(existingCustomer);

        //when(customerManagementService.)
        when(mapper.customerToCustomerResponse(any())).thenReturn(response);

        CustomerResponse savedCustomer  = managementService.updateCustomer(customerUpdateRequest,existingCustomer.getCustomerId());
        Assertions.assertNotNull(savedCustomer);
        Assertions.assertFalse(savedCustomer.getAddressDTOs().isEmpty());


    }

    @Test
    public void testDeletedCustomerSuccess() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();

        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();

        when(customerRepository.findByCustomerId(any())).thenReturn(Optional.of(existingCustomer));

        managementService.deleteCustomer(existingCustomer.getCustomerId());

        Mockito.verify(customerRepository, times(1)).delete(existingCustomer);

    }

    @Test
    public void testDeletedCustomerFailure() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();

        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();

        when(customerRepository.findByCustomerId(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class,()-> {
            managementService.deleteCustomer(UUID.randomUUID().toString());
        });

    }

    @Test
    public void testGetCustomerWithSpecificAddress() throws IOException {
        Customer existingCustomer = CustomerUtil.createCustomer();

        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID().toString())
                .email(existingCustomer.getEmail())
                .contactNumber(existingCustomer.getContactNumber())
                .build();

        Address address = CustomerUtil.addAddress();
        address.setCustomer(existingCustomer);

        when(addressRepository.findByAddressId(any())).thenReturn(Optional.of(address));
        when(mapper.customerToCustomerResponse(any())).thenReturn(response);

        CustomerResponse savedCustomer  = managementService.getCustomerWithSpecificAddress(address.getAddressId());

        Assertions.assertFalse(savedCustomer.getContactNumber().isEmpty());

    }
}
