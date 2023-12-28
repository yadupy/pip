package com.accenture.pip.customermanagement.exception;

import com.accenture.pip.customermanagement.constants.CustomerManagementConstant;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends AbstractCustomerManagementException{
    public AuthenticationException(final Throwable cause){

        super(cause, HttpStatus.FORBIDDEN, CustomerManagementConstant.AUTHENTICATION_FAILED);
    }
    public AuthenticationException( ){

        super(null, HttpStatus.FORBIDDEN, CustomerManagementConstant.AUTHENTICATION_FAILED);
    }
}
