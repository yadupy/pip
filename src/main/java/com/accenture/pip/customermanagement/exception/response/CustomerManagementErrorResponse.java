package com.accenture.pip.customermanagement.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerManagementErrorResponse {

    private ErrorAdvice errorAdvice;
}
