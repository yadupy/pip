package com.accenture.pip.customermanagement.service;

import com.accenture.pip.customermanagement.dto.AddressDTO;
import com.accenture.pip.customermanagement.dto.AddressUpdateRequest;
import com.accenture.pip.customermanagement.dto.CustomerResponse;
import com.accenture.pip.customermanagement.dto.CustomerUpdateRequest;
import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;
import com.accenture.pip.customermanagement.exception.AddressNotFoundException;
import com.accenture.pip.customermanagement.exception.CustomerNotFoundException;
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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import util.CustomerUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AddressServiceTest {

    @InjectMocks
    AddressService addressService;
    @Mock
    AddressRepository addressRepository;
    @Mock
    CustomerMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAddressSuccess() throws IOException {

        Address existingAddress = CustomerUtil.addAddress();
        AddressDTO response = CustomerUtil.addAddressDTO();

        when(addressRepository.findByAddressId(any())).thenReturn(Optional.of(existingAddress));
        when(mapper.addressToAddressDTO(any())).thenReturn(response);

        AddressDTO addressDTO  = addressService.getAddress(existingAddress.getAddressId());
        Assertions.assertFalse(addressDTO.getPinCode().isEmpty());
    }

    @Test
    public void testGetAddressFailure() throws IOException {

        Address existingAddress = CustomerUtil.addAddress();
        AddressDTO response = CustomerUtil.addAddressDTO();

        when(addressRepository.findByAddressId(any())).thenReturn(Optional.empty());
        when(mapper.addressToAddressDTO(any())).thenReturn(response);

        Assertions.assertThrows(AddressNotFoundException.class,()-> {
            addressService.getAddress(UUID.randomUUID().toString());
        });
    }

    @Test
    public void testUpdateCustomerSuccess() throws IOException {

        Address existingAddress = CustomerUtil.addAddress();
        Address updatedAddress = CustomerUtil.addAddress();
        updatedAddress.setCity("newCity");
        updatedAddress.setHouseNo("newHouse");
        updatedAddress.setStreet("newStreet");
        updatedAddress.setPinCode(122222);
        AddressDTO response = AddressDTO
                .builder()
                .addressId( "3898db4e-fb79-43db-9b14-c902cc0040ab")
                //.id(1)
                .houseNo("newHouse")
                .street( "newStreet")
                .city("newCity")
                .state("haryana")
                .pinCode("122222")
                .build();

        String body = Resources.toString(Resources.getResource("payloads/updateAddress.json"), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        AddressUpdateRequest updateRequest = gson.fromJson(body, AddressUpdateRequest.class);

        when(addressRepository.findByAddressId(any())).thenReturn(Optional.of(existingAddress));
        when(addressRepository.saveAndFlush(any())).thenReturn(updatedAddress);
        when(mapper.addressToAddressDTO(any())).thenReturn(response);

        AddressDTO addressDTO  = addressService.updateAddress(updateRequest,existingAddress.getAddressId());
        Assertions.assertEquals(addressDTO.getPinCode(),updateRequest.getPinCode());
    }
    @Test
    public void testUpdateCustomerFailure() throws IOException {

        Address existingAddress = CustomerUtil.addAddress();
        AddressDTO response = CustomerUtil.addAddressDTO();

        String body = Resources.toString(Resources.getResource("payloads/updateAddress.json"), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        AddressUpdateRequest updateRequest = gson.fromJson(body, AddressUpdateRequest.class);


        when(addressRepository.findByAddressId(any())).thenReturn(Optional.empty());
        when(mapper.addressToAddressDTO(any())).thenReturn(response);

        Assertions.assertThrows(AddressNotFoundException.class,()-> {
            addressService.updateAddress(updateRequest,UUID.randomUUID().toString());
        });
    }
}
