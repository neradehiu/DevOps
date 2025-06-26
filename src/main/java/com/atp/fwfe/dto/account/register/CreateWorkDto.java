package com.atp.fwfe.dto.account.register;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateWorkDto {

    @NotBlank(message = "Vui lòng điền tên cơ sở kinh doanh")
    private String name;

    @NotBlank(message = "Giới thiệu cơ bản về cơ sở kinh doanh của bạn")
    private String description;

    @NotBlank(message = "Loại hình kinh doanh của bạn là gì? (vd: tạp hóa, caffee, xưởng may,...")
    private String type;

    @NotBlank(message = "Nhập địa chỉ cơ sở kinh doanh")
    private String address;

    private Boolean isPublic;

    @Min(1)
    private Integer maxReceiver;

    private Long createdBy;
}
