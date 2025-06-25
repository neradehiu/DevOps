package com.atp.fwfe.dto.passwordreset;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {

    private String token;
    private String newPassword;
}
