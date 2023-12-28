package com.accenture.pip.customermanagement.exception;

import com.accenture.pip.customermanagement.constants.CustomerManagementConstant;
import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends AbstractCustomerManagementException{
    public AddressNotFoundException(final Throwable cause){

        super(cause, HttpStatus.NOT_FOUND, CustomerManagementConstant.ADDRESS_NOT_FOUND);
    }
    public AddressNotFoundException( ){

        super(null, HttpStatus.NOT_FOUND, CustomerManagementConstant.ADDRESS_NOT_FOUND);
    }
}
