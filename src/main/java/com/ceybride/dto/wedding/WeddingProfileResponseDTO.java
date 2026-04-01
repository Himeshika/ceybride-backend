package com.ceybride.dto.wedding;

import com.ceybride.enums.WeddingStatus;
import com.ceybride.enums.WeddingType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class WeddingProfileResponseDTO {
    private Long id;
    private String weddingTitle;
    private String brideName;
    private String groomName;
    private String bridePhone;
    private String groomPhone;
    private String brideEmail;
    private String groomEmail;
    private WeddingType weddingType;
    private LocalDate weddingDate;
    private Integer guestCount;
    private BigDecimal totalBudget;
    private String venue;
    private WeddingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
