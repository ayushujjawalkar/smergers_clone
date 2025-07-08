package com.abhi.smergersclone.dto;

import com.abhi.smergersclone.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private Role role; // e.g., INVESTOR or OWNER
}
