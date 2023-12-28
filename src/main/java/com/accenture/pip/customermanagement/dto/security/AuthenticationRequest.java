package com.accenture.pip.customermanagement.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AuthenticationRequest {

    private String userName;
    private String password;

}
