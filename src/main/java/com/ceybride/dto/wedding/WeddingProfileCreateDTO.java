package com.ceybride.dto.wedding;

import com.ceybride.enums.WeddingStatus;
import com.ceybride.enums.WeddingType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class WeddingProfileCreateDTO {

    @Size(max = 150)
    private String weddingTitle;

    @NotBlank
    @Size(max = 120)
    private String brideName;

    @NotBlank
    @Size(max = 120)
    private String groomName;

    @Size(max = 20)
    private String bridePhone;

    @Size(max = 20)
    private String groomPhone;

    @Email
    @Size(max = 120)
    private String brideEmail;

    @Email
    @Size(max = 120)
    private String groomEmail;

    @NotNull
    private WeddingType weddingType;

    @NotNull
    @Future
    private LocalDate weddingDate;

    @NotNull
    @Positive
    private Integer guestCount;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal totalBudget;

    @Size(max = 180)
    private String venue;

    private WeddingStatus status;
}
