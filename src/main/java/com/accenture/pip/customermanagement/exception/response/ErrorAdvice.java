package com.accenture.pip.customermanagement.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorAdvice {

    private String errorCode = "0";
    private String errorMsg ="";
    private String errorReason = "";
}
