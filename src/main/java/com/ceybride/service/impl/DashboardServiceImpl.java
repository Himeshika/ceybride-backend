package com.ceybride.service.impl;

import com.ceybride.dto.expense.ExpenseBySideDTO;
import com.ceybride.dto.expense.ExpenseSummaryDTO;
import com.ceybride.dto.guest.GuestStatsDTO;
import com.ceybride.dto.ritual.RitualResponseDTO;
import com.ceybride.dto.task.TaskResponseDTO;
import com.ceybride.dto.wedding.DashboardSummaryDTO;
import com.ceybride.enums.TaskStatus;
import com.ceybride.repository.TaskRepository;
import com.ceybride.service.DashboardService;
import com.ceybride.service.ExpenseService;
import com.ceybride.service.GuestService;
import com.ceybride.service.RitualScheduleService;
import com.ceybride.service.TaskService;
import com.ceybride.service.WeddingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final GuestService guestService;
    private final TaskService taskService;
    private final ExpenseService expenseService;
    private final RitualScheduleService ritualScheduleService;
    private final TaskRepository taskRepository;
    private final WeddingProfileService weddingProfileService;

    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryDTO getSummary() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();

        GuestStatsDTO guestStats = guestService.getStats();
        long totalTasks = taskRepository.countByWeddingProfileId(weddingId);
        long completedTasks = taskRepository.countByWeddingProfileIdAndStatus(weddingId, TaskStatus.COMPLETED);
        long pendingTasks = totalTasks - completedTasks;

        ExpenseSummaryDTO expenseSummary = expenseService.getSummary();
        List<ExpenseBySideDTO> sideTotals = expenseService.getBySide();
        List<TaskResponseDTO> upcomingTasks = taskService.getUpcoming();
        List<RitualResponseDTO> upcomingRituals = ritualScheduleService.getUpcoming();

        return DashboardSummaryDTO.builder()
                .guestStats(guestStats)
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .pendingTasks(pendingTasks)
                .expenseSummary(expenseSummary)
                .sideTotals(sideTotals)
                .upcomingTasks(upcomingTasks)
                .upcomingRituals(upcomingRituals)
                .build();
    }
}