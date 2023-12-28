package util;

import com.accenture.pip.customermanagement.dto.AddressDTO;
import com.accenture.pip.customermanagement.entity.Address;
import com.accenture.pip.customermanagement.entity.Customer;

import java.util.Arrays;

public class CustomerUtil {

    public static Customer createCustomer(){
        Address address = addAddress();

        Customer customer = Customer.builder()
                //.id(1)
                .customerId("d37291a7-9929-400d-9170-99aace2fb957")
                .firstName("naruto").lastName("uzumaki")
                .email("naruto.uzumaki@gmail.com")
                .contactNumber("9785481460")
                .addresses(Arrays.asList(address))
                .build();
        address.setCustomer(customer);

        return customer;
    }
    public static Address addAddress(){
   return   Address
                .builder()
                .addressId( "3898db4e-fb79-43db-9b14-c902cc0040ab")
                //.id(1)
                .houseNo("b-402")
                .street( "pratap raod")
                .city("gurugram")
                .state("haryana")
                .pinCode(122001)
                .build();
    }

    public static AddressDTO addAddressDTO(){
        return   AddressDTO
                .builder()
                .addressId( "3898db4e-fb79-43db-9b14-c902cc0040ab")
                //.id(1)
                .houseNo("b-402")
                .street( "pratap raod")
                .city("gurugram")
                .state("haryana")
                .pinCode("122001")
                .build();
    }
}
