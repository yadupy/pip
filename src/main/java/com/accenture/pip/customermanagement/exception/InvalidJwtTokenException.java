package com.accenture.pip.customermanagement.exception;

import com.accenture.pip.customermanagement.constants.CustomerManagementConstant;
import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException  extends AbstractCustomerManagementException{
    public InvalidJwtTokenException(final Throwable cause){

        super(cause, HttpStatus.UNAUTHORIZED, CustomerManagementConstant.INVALID_JWT_TOKEN);
    }
    public InvalidJwtTokenException( ){

        super(null, HttpStatus.UNAUTHORIZED, CustomerManagementConstant.INVALID_JWT_TOKEN);
    }
}
