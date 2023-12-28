package com.accenture.pip.customermanagement.mapper;

import com.accenture.pip.customermanagement.dto.AddressDTO;
import com.accenture.pip.customermanagement.dto.CustomerRequest;
import com.accenture.pip.customermanagement.dto.CustomerResponse;
import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper( CustomerMapper.class );
    @Mapping(target = "addressDTOs", source = "addresses")
    @Mapping(target = "id", source = "customerId")
    CustomerResponse customerToCustomerResponse(Customer entity);

    @Mapping(target = "addresses", source = "addressDTOs")
    @Mapping(target = "customerId", source = "id")
    Customer customerResponseToCustomer(CustomerResponse customerResponse);

    @Mapping(target = "addresses", source = "addressDTOs")
    Customer customerRequestToCustomer(CustomerRequest customerRequest);

    AddressDTO addressToAddressDTO(Address address);

    Address addressDTOAddress(AddressDTO addressDTO);
}
