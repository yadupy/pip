package com.accenture.pip.customermanagement.exception;

import com.accenture.pip.customermanagement.constants.CustomerManagementConstant;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends AbstractCustomerManagementException{

    public EmailAlreadyInUseException(final Throwable cause){

        super(cause, HttpStatus.BAD_REQUEST,CustomerManagementConstant.EMAIL_ALREADY_IN_USE_EXCEPTION);
    }
    public EmailAlreadyInUseException( ){

        super(null, HttpStatus.BAD_REQUEST, CustomerManagementConstant.EMAIL_ALREADY_IN_USE_EXCEPTION);
    }

}
