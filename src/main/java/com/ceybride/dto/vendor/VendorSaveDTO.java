package com.ceybride.dto.vendor;

import com.ceybride.enums.BookingStatus;
import com.ceybride.enums.VendorCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorSaveDTO {

    @NotBlank
    @Size(max = 150)
    private String name;

    private VendorCategory category;

    @Size(max = 20)
    private String phone;

    @Email
    @Size(max = 120)
    private String email;

    @Size(max = 255)
    private String address;

    @DecimalMin(value = "0.00")
    private BigDecimal estimatedCost;

    @DecimalMin(value = "0.00")
    private BigDecimal actualCost;

    private BookingStatus bookingStatus;

    @Size(max = 500)
    private String notes;
}
