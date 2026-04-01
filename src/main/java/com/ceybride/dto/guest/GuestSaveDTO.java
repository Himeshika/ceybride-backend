package com.ceybride.dto.guest;

import com.ceybride.enums.GuestSide;
import com.ceybride.enums.RsvpStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GuestSaveDTO {

    @NotBlank
    @Size(max = 120)
    private String fullName;

    @Size(max = 20)
    private String phone;

    @Email
    @Size(max = 120)
    private String email;

    private GuestSide guestSide;
    private RsvpStatus rsvpStatus;

    @Size(max = 80)
    private String groupType;

    private Integer tableNumber;

    @Size(max = 500)
    private String notes;
}
