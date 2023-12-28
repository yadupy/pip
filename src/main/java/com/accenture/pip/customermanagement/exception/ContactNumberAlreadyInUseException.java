package com.accenture.pip.customermanagement.exception;

import com.accenture.pip.customermanagement.constants.CustomerManagementConstant;
import org.springframework.http.HttpStatus;

public class ContactNumberAlreadyInUseException  extends AbstractCustomerManagementException{

    public ContactNumberAlreadyInUseException(final Throwable cause){

        super(cause, HttpStatus.BAD_REQUEST, CustomerManagementConstant.EMAIL_ALREADY_IN_USE_EXCEPTION);
    }
    public ContactNumberAlreadyInUseException( ){

        super(null, HttpStatus.BAD_REQUEST, CustomerManagementConstant.EMAIL_ALREADY_IN_USE_EXCEPTION);
    }
}
