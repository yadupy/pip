package com.accenture.pip.customermanagement.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwtToken;
}
