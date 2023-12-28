package com.accenture.pip.customermanagement.exception;

import com.accenture.pip.customermanagement.constants.CustomerManagementConstant;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends AbstractCustomerManagementException{
    public CustomerNotFoundException(final Throwable cause){

        super(cause, HttpStatus.NOT_FOUND, CustomerManagementConstant.CUSTOMER_NOT_FOUND);
    }
    public CustomerNotFoundException( ){

        super(null, HttpStatus.NOT_FOUND, CustomerManagementConstant.CUSTOMER_NOT_FOUND);
    }
}
