package com.ceybride.dto.wedding;

import com.ceybride.dto.expense.ExpenseBySideDTO;
import com.ceybride.dto.expense.ExpenseSummaryDTO;
import com.ceybride.dto.guest.GuestStatsDTO;
import com.ceybride.dto.ritual.RitualResponseDTO;
import com.ceybride.dto.task.TaskResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardSummaryDTO {
    private GuestStatsDTO guestStats;
    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private ExpenseSummaryDTO expenseSummary;
    private List<ExpenseBySideDTO> sideTotals;
    private List<TaskResponseDTO> upcomingTasks;
    private List<RitualResponseDTO> upcomingRituals;
}
