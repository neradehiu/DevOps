package com.atp.fwfe.dto.adrequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateUserRequest {

    @NotBlank(message = "Vui lòng nhập Username")
    private String username;

    @NotBlank(message = "Vui lòng điền Password")
    private String password;

    @NotBlank(message = "Vui lòng điền Email")
    @Email(message = "Sai định dạng email, ví dụ: ten + @ + tên miền + .com")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Chỉ chấp nhận email Gmail")
    private String email;

    @NotBlank(message = "Vui lòng định dạng đúng vai trò (phân quyền hạn) cho tài khoản này!")
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN|ROLE_MANAGER", message = "Vai trò không hợp lệ")
    private String role;
}
