package com.accenture.pip.customermanagement.service;

import com.accenture.pip.customermanagement.dto.AddressDTO;
import com.accenture.pip.customermanagement.dto.AddressUpdateRequest;
import com.accenture.pip.customermanagement.dto.CustomerResponse;
import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;
import com.accenture.pip.customermanagement.exception.AddressNotFoundException;
import com.accenture.pip.customermanagement.exception.CustomerNotFoundException;
import com.accenture.pip.customermanagement.mapper.CustomerMapper;
import com.accenture.pip.customermanagement.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerMapper mapper;
    @Transactional
    public AddressDTO getAddress(String addressId) {

        log.info("fetching records for the customer with addressId {}", addressId);
        Optional<Address> address = addressRepository.findByAddressId(addressId);
        if(!address.isPresent()){
            throw new AddressNotFoundException(new Throwable("Invalid addressId"));
        }
        log.info("found address with addressId {}", addressId);
        return mapper.addressToAddressDTO(address.get());

    }

    @Transactional
    public AddressDTO updateAddress(AddressUpdateRequest updateRequest, String addressId) {
        log.info("fetching records for the customer with addressId {}", addressId);
        Optional<Address> address = addressRepository.findByAddressId(addressId);
        if(!address.isPresent()){
            throw new AddressNotFoundException(new Throwable("Invalid addressId"));
        }
        log.info("found address with addressId {}", addressId);

        Address updatedAddress = addressRepository.saveAndFlush(validateAddressUpdateRequest(updateRequest,address.get()));
        log.info("updated address {}",addressId);
        return mapper.addressToAddressDTO(updatedAddress);
    }

    private Address validateAddressUpdateRequest(AddressUpdateRequest updateRequest,Address address) {

        if(StringUtils.isNotEmpty(updateRequest.getStreet())){
            address.setStreet(updateRequest.getStreet());
        }
        if(StringUtils.isNotEmpty(updateRequest.getHouseNo())){
            address.setStreet(updateRequest.getHouseNo());
        }
        if(StringUtils.isNotEmpty(updateRequest.getCity())){
            address.setCity(updateRequest.getCity());
        }
        if(StringUtils.isNotEmpty(updateRequest.getState())){
            address.setStreet(updateRequest.getState());
        }
        if(StringUtils.isNotEmpty(updateRequest.getPinCode())){
            address.setStreet(updateRequest.getPinCode());
        }
        return address;
    }
}
