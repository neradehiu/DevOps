package com.atp.fwfe.dto.account.adrequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateUserRequest {

    // username và password sẽ do người dùng tự đặt
    private String email;
    private String role;
    private boolean locked;
    private String updatedBy;
}
