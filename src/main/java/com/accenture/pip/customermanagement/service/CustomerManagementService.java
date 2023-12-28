package com.accenture.pip.customermanagement.service;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerManagementService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerMapper mapper;
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerRequest){
        log.info("checking if the email address is already in registered");
        Optional<Customer> customer  = customerRepository.findByEmailOrContactNumber(customerRequest.getEmail(),customerRequest.getContactNumber());
        if(customer.isPresent()){
            log.error("customer already exists, rejecting new customer creation");
            if(customer.get().getEmail().equalsIgnoreCase(customerRequest.getEmail())) {
                log.info("new customer can't be created, since email : {} already in use", customerRequest.getEmail());
                throw new EmailAlreadyInUseException();
            }
            else {
                log.info("new customer can't be created, since contact number : {} already in use", customerRequest.getContactNumber());
                throw new ContactNumberAlreadyInUseException();
            }
        }
        else{
            Customer newCustomer  = mapper.customerRequestToCustomer(customerRequest);
            List<Address> addresses = newCustomer.getAddresses();
             addresses.stream()
                    .forEach(address -> address.setCustomer(newCustomer));

           /* for(Address address : addresses){
                address.setCustomer(newCustomer);
            }*/
            //newCustomer.setCustomerId(UUID.randomUUID().toString());
            Customer createdCustomer = customerRepository.saveAndFlush(newCustomer);
            log.info("crated a new customer with email : {} and id: {}",createdCustomer.getEmail(), createdCustomer.getCustomerId());
            return mapper.customerToCustomerResponse(createdCustomer);
        }
    }

    @Transactional
    public List<CustomerResponse> getAllCustomers() {
        log.info("fetching all the customers from database");
        List<CustomerResponse> customerList =  new ArrayList<>();
        customerList.addAll(customerRepository.findAll()
                .stream()
                .map(customer-> mapper.customerToCustomerResponse(customer))
                .toList());
        log.info("found total {} customers from the records",customerList.size());

        return customerList;

    }

    @Transactional
    public CustomerResponse getCustomer(String customerId) {
        log.info("fetching records for the customer with customerId {}", customerId);
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if(!existingCustomer.isPresent()){
            throw new CustomerNotFoundException(new Throwable("Invalid customerId"));
        }
        log.info("found customer with email {} and customerId {}", existingCustomer.get().getEmail(), customerId);
        CustomerResponse response =  mapper.customerToCustomerResponse(existingCustomer.get());
        return response;
    }



    public CustomerResponse updateCustomer(CustomerUpdateRequest customerRequest, String customerId) {
        log.info("fetching records for the customer with customerId {}", customerId);
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if(!existingCustomer.isPresent()){
            throw new CustomerNotFoundException(new Throwable("Invalid customerId"));
        }
        log.info("found customer with email {} and customerId {}", existingCustomer.get().getEmail(), customerId);

        Customer updatedCustomer =  customerRepository.saveAndFlush(updateExistingCustomer(existingCustomer,customerRequest));
        log.info("updated existing customer with email address: {} and customerId {}"
                ,updatedCustomer.getEmail(), updatedCustomer.getCustomerId());

        return  mapper.customerToCustomerResponse(updatedCustomer);

    }


    public Customer updateExistingCustomer(Optional<Customer> existingCustomer, CustomerUpdateRequest customerRequest){
        Customer newCustomer = existingCustomer.get();

        if(customerRequest.getAddressDTOs()!=null) {
            List<Address> addressList = new ArrayList<>(customerRequest.getAddressDTOs()
                    .stream()
                    .map(addressDTO -> mapper.addressDTOAddress(addressDTO))
                    .toList());
            addressList.forEach(address -> address.setCustomer(newCustomer));
            newCustomer.getAddresses().addAll(addressList);
            addressList.addAll(existingCustomer.get().getAddresses());
        }


        if(StringUtils.isNotEmpty(customerRequest.getEmail())) {
            newCustomer.setEmail(customerRequest.getEmail());
        }

        return newCustomer;
    }

    @Transactional()
    public void deleteCustomer(String customerId) {
        log.info("fetching records for the customer with customerId {}", customerId);
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if(!existingCustomer.isPresent()){
            throw new CustomerNotFoundException(new Throwable("Invalid customerId"));
        }
        log.info("found customer with email {} and customerId {}", existingCustomer.get().getEmail(), customerId);
        log.info("deleting customer with email {}", existingCustomer.get().getEmail());
        customerRepository.delete(existingCustomer.get());
        log.info("deleted customer successfully");

    }

    @Transactional
    public void deleteCustomerBeforeSpecificDate(String dateStr) {
        log.info("fetching records for the customer created before  {}", dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        List<Customer> customersToDelete = customerRepository.findAllWithCreationDateTimeBefore(date.atStartOfDay());
        if(customersToDelete.isEmpty()){
            throw new CustomerNotFoundException(new Throwable("No customer found created before specified date"));
        }
        log.info("found total {} customers from the records",customersToDelete.size());
        log.info("deleting total {} the customers",customersToDelete.size());
        customerRepository.deleteAll(customersToDelete);
        log.info("deleted customers successfully");
    }

    @Transactional
    public CustomerResponse getCustomerWithSpecificAddress(String addressId) {
        log.info("fetching records for the customer with addressId {}", addressId);
        Optional<Address> address = addressRepository.findByAddressId(addressId);
        if(!address.isPresent() || address.get().getCustomer()==null){
            throw new CustomerNotFoundException(new Throwable("Invalid addressId"));
        }
        Customer existingCustomer = address.get().getCustomer();
        log.info("found customer with email {} and addressId {}", existingCustomer.getEmail(), addressId);
        return mapper.customerToCustomerResponse(existingCustomer);
    }

}
