package com.abhi.smergersclone.AUTHENTICATION;

import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private Role role; // e.g., INVESTOR or OWNER
}
