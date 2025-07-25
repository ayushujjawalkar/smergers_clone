package com.abhi.smergersclone.AUTHENTICATION;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {
    private String email;
    private String otp;
}
