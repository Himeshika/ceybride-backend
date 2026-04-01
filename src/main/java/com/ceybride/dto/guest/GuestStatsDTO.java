package com.ceybride.dto.guest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestStatsDTO {
    private long totalGuests;
    private long brideSideCount;
    private long groomSideCount;
    private long confirmedCount;
    private long pendingCount;
    private long declinedCount;
}
