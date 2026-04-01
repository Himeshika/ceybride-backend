package com.ceybride.dto.guest;

import com.ceybride.enums.GuestSide;
import com.ceybride.enums.RsvpStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GuestResponseDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private GuestSide guestSide;
    private RsvpStatus rsvpStatus;
    private String groupType;
    private Integer tableNumber;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
