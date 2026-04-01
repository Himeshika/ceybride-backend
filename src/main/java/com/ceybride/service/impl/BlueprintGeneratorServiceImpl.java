package com.ceybride.service.impl;

import com.ceybride.dto.wedding.BlueprintGenerateRequestDTO;
import com.ceybride.dto.wedding.BlueprintGenerateResponseDTO;
import com.ceybride.dto.wedding.BlueprintPreviewDTO;
import com.ceybride.entity.Expense;
import com.ceybride.entity.ExpenseTemplate;
import com.ceybride.entity.RitualSchedule;
import com.ceybride.entity.RitualTemplate;
import com.ceybride.entity.Task;
import com.ceybride.entity.TaskTemplate;
import com.ceybride.entity.User;
import com.ceybride.entity.WeddingProfile;
import com.ceybride.enums.ExpenseCategory;
import com.ceybride.enums.PaymentStatus;
import com.ceybride.enums.ResponsibleSide;
import com.ceybride.enums.TaskCategory;
import com.ceybride.enums.TaskPriority;
import com.ceybride.enums.TaskStatus;
import com.ceybride.enums.WeddingStatus;
import com.ceybride.exception.BadRequestException;
import com.ceybride.mapper.WeddingProfileMapper;
import com.ceybride.repository.ExpenseRepository;
import com.ceybride.repository.ExpenseTemplateRepository;
import com.ceybride.repository.RitualScheduleRepository;
import com.ceybride.repository.RitualTemplateRepository;
import com.ceybride.repository.TaskRepository;
import com.ceybride.repository.TaskTemplateRepository;
import com.ceybride.repository.WeddingProfileRepository;
import com.ceybride.service.BlueprintGeneratorService;
import com.ceybride.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlueprintGeneratorServiceImpl implements BlueprintGeneratorService {

    private final WeddingProfileRepository weddingProfileRepository;
    private final TaskTemplateRepository taskTemplateRepository;
    private final ExpenseTemplateRepository expenseTemplateRepository;
    private final RitualTemplateRepository ritualTemplateRepository;
    private final TaskRepository taskRepository;
    private final ExpenseRepository expenseRepository;
    private final RitualScheduleRepository ritualScheduleRepository;
    private final UserService userService;

    @Override
    @Transactional
    public BlueprintGenerateResponseDTO generate(BlueprintGenerateRequestDTO request) {
        WeddingProfile profile = upsertProfile(request);

        boolean hasGeneratedContent =
                taskRepository.existsByWeddingProfileIdAndIsGeneratedTrue(profile.getId()) ||
                        expenseRepository.existsByWeddingProfileIdAndIsGeneratedTrue(profile.getId()) ||
                        ritualScheduleRepository.existsByWeddingProfileIdAndIsGeneratedTrue(profile.getId());

        if (hasGeneratedContent) {
            throw new BadRequestException("Blueprint data already exists. Use /api/blueprint/regenerate to rebuild generated content.");
        }

        return generateInternal(profile);
    }

    @Override
    @Transactional
    public BlueprintGenerateResponseDTO regenerate(BlueprintGenerateRequestDTO request) {
        WeddingProfile profile = upsertProfile(request);

        taskRepository.deleteByWeddingProfileIdAndIsGeneratedTrue(profile.getId());
        expenseRepository.deleteByWeddingProfileIdAndIsGeneratedTrue(profile.getId());
        ritualScheduleRepository.deleteByWeddingProfileIdAndIsGeneratedTrue(profile.getId());

        return generateInternal(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public BlueprintPreviewDTO preview() {
        WeddingProfile profile = weddingProfileRepository.findByUserId(userService.getCurrentUserEntity().getId())
                .orElseThrow(() -> new BadRequestException("Create a wedding profile first before previewing templates"));

        List<TaskTemplate> taskTemplates =
                taskTemplateRepository.findByWeddingTypeAndIsActiveTrueOrderByDefaultDayOffsetAsc(profile.getWeddingType());
        List<ExpenseTemplate> expenseTemplates =
                expenseTemplateRepository.findByWeddingTypeAndIsActiveTrue(profile.getWeddingType());
        List<RitualTemplate> ritualTemplates =
                ritualTemplateRepository.findByWeddingTypeAndIsActiveTrueOrderBySequenceOrderAsc(profile.getWeddingType());

        return BlueprintPreviewDTO.builder()
                .weddingType(profile.getWeddingType().name())
                .taskTemplateCount(taskTemplates.size())
                .expenseTemplateCount(expenseTemplates.size())
                .ritualTemplateCount(ritualTemplates.size())
                .sampleTasks(taskTemplates.stream().limit(5).map(TaskTemplate::getTitle).toList())
                .sampleExpenses(expenseTemplates.stream().limit(5).map(ExpenseTemplate::getTitle).toList())
                .sampleRituals(ritualTemplates.stream().limit(5).map(RitualTemplate::getRitualName).toList())
                .build();
    }

    private WeddingProfile upsertProfile(BlueprintGenerateRequestDTO request) {
        validateRequest(request);

        User user = userService.getCurrentUserEntity();
        WeddingProfile profile = weddingProfileRepository.findByUserId(user.getId())
                .orElseGet(() -> WeddingProfile.builder().user(user).build());

        profile.setWeddingTitle(normalizeNullable(request.getWeddingTitle()));
        profile.setBrideName(request.getBrideName().trim());
        profile.setGroomName(request.getGroomName().trim());
        profile.setBridePhone(normalizeNullable(request.getBridePhone()));
        profile.setGroomPhone(normalizeNullable(request.getGroomPhone()));
        profile.setBrideEmail(normalizeNullable(request.getBrideEmail()));
        profile.setGroomEmail(normalizeNullable(request.getGroomEmail()));
        profile.setWeddingType(request.getWeddingType());
        profile.setWeddingDate(request.getWeddingDate());
        profile.setGuestCount(request.getGuestCount());
        profile.setTotalBudget(request.getTotalBudget());
        profile.setVenue(normalizeNullable(request.getVenue()));
        profile.setStatus(WeddingStatus.PLANNING);

        return weddingProfileRepository.save(profile);
    }

    private void validateRequest(BlueprintGenerateRequestDTO request) {
        if (request.getWeddingDate() == null || !request.getWeddingDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Wedding date must be a future date");
        }
        if (request.getGuestCount() == null || request.getGuestCount() <= 0) {
            throw new BadRequestException("Guest count must be greater than zero");
        }
        if (request.getTotalBudget() == null || request.getTotalBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Total budget must be greater than zero");
        }
    }

    private BlueprintGenerateResponseDTO generateInternal(WeddingProfile profile) {
        List<TaskTemplate> taskTemplates =
                taskTemplateRepository.findByWeddingTypeAndIsActiveTrueOrderByDefaultDayOffsetAsc(profile.getWeddingType());
        List<ExpenseTemplate> expenseTemplates =
                expenseTemplateRepository.findByWeddingTypeAndIsActiveTrue(profile.getWeddingType());
        List<RitualTemplate> ritualTemplates =
                ritualTemplateRepository.findByWeddingTypeAndIsActiveTrueOrderBySequenceOrderAsc(profile.getWeddingType());

        if (taskTemplates.isEmpty() || expenseTemplates.isEmpty() || ritualTemplates.isEmpty()) {
            throw new BadRequestException("Templates are missing for wedding type: " + profile.getWeddingType());
        }

        int generatedTaskCount = 0;
        int generatedExpenseCount = 0;
        int generatedRitualCount = 0;

        for (TaskTemplate template : taskTemplates) {
            Task task = Task.builder()
                    .weddingProfile(profile)
                    .title(template.getTitle())
                    .description(template.getDescription())
                    .category(template.getCategory())
                    .priority(template.getPriority())
                    .status(TaskStatus.PENDING)
                    .dueDate(profile.getWeddingDate().plusDays(template.getDefaultDayOffset()))
                    .assignedSide(template.getDefaultAssignedSide())
                    .isGenerated(true)
                    .build();
            taskRepository.save(task);
            generatedTaskCount++;
        }

        for (ExpenseTemplate template : expenseTemplates) {
            BigDecimal estimate = profile.getTotalBudget()
                    .multiply(template.getDefaultBudgetPercentage())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

            Expense expense = Expense.builder()
                    .weddingProfile(profile)
                    .title(template.getTitle())
                    .category(template.getCategory())
                    .estimatedAmount(estimate)
                    .actualAmount(null)
                    .paidAmount(BigDecimal.ZERO)
                    .remainingAmount(estimate)
                    .responsibleSide(template.getResponsibleSide())
                    .paymentStatus(PaymentStatus.PENDING)
                    .dueDate(profile.getWeddingDate().minusDays(14))
                    .notes("Auto-generated from wedding blueprint")
                    .isGenerated(true)
                    .build();
            expenseRepository.save(expense);
            generatedExpenseCount++;
        }

        for (RitualTemplate template : ritualTemplates) {
            LocalDateTime eventDateTime = profile.getWeddingDate()
                    .plusDays(template.getDefaultDayOffset())
                    .atTime(9, 0)
                    .plusHours(template.getSequenceOrder());

            RitualSchedule ritual = RitualSchedule.builder()
                    .weddingProfile(profile)
                    .ritualName(template.getRitualName())
                    .eventDateTime(eventDateTime)
                    .luckyColor(template.getDefaultLuckyColor())
                    .facingDirection(template.getDefaultFacingDirection())
                    .notes(template.getNotes())
                    .sequenceOrder(template.getSequenceOrder())
                    .isCompleted(false)
                    .isGenerated(true)
                    .build();
            ritualScheduleRepository.save(ritual);
            generatedRitualCount++;
        }

        SmartRuleCounts extraCounts = applySmartRules(profile);

        return BlueprintGenerateResponseDTO.builder()
                .weddingProfile(WeddingProfileMapper.toResponse(profile))
                .generatedTaskCount(generatedTaskCount + extraCounts.additionalTasks())
                .generatedExpenseCount(generatedExpenseCount + extraCounts.additionalExpenses())
                .generatedRitualCount(generatedRitualCount)
                .build();
    }

    private SmartRuleCounts applySmartRules(WeddingProfile profile) {
        int additionalTasks = 0;
        int additionalExpenses = 0;

        if (profile.getGuestCount() > 300) {
            additionalTasks += saveGeneratedTask(
                    profile,
                    "Finalize large hall seating plan",
                    "Prepare seating blocks for high guest volume",
                    TaskCategory.GUEST,
                    ResponsibleSide.SHARED,
                    21
            );
            additionalTasks += saveGeneratedTask(
                    profile,
                    "Confirm high-volume catering logistics",
                    "Coordinate service timing and food station flow",
                    TaskCategory.CATERING,
                    ResponsibleSide.SHARED,
                    14
            );
            additionalTasks += saveGeneratedTask(
                    profile,
                    "Manage overflow guest logistics",
                    "Plan parking, overflow seating, and reception flow",
                    TaskCategory.GUEST,
                    ResponsibleSide.SHARED,
                    10
            );
        }

        if (profile.getTotalBudget().compareTo(new BigDecimal("1000000")) < 0) {
            additionalTasks += saveGeneratedTask(
                    profile,
                    "Review budget-sensitive categories",
                    "Recheck decoration, entertainment, and optional costs",
                    TaskCategory.PAYMENT,
                    ResponsibleSide.SHARED,
                    30
            );
        }

        if (profile.getTotalBudget().compareTo(new BigDecimal("3500000")) > 0) {
            additionalTasks += saveGeneratedTask(
                    profile,
                    "Review premium vendor package options",
                    "Consider premium coverage for décor and photography",
                    TaskCategory.VENDOR,
                    ResponsibleSide.SHARED,
                    45
            );

            BigDecimal extraEstimate = new BigDecimal("50000.00");
            Expense extraExpense = Expense.builder()
                    .weddingProfile(profile)
                    .title("Premium contingency allocation")
                    .category(ExpenseCategory.OTHER)
                    .estimatedAmount(extraEstimate)
                    .actualAmount(null)
                    .paidAmount(BigDecimal.ZERO)
                    .remainingAmount(extraEstimate)
                    .responsibleSide(ResponsibleSide.SHARED)
                    .paymentStatus(PaymentStatus.PENDING)
                    .dueDate(profile.getWeddingDate().minusDays(20))
                    .notes("Auto-generated premium contingency")
                    .isGenerated(true)
                    .build();
            expenseRepository.save(extraExpense);
            additionalExpenses++;
        }

        return new SmartRuleCounts(additionalTasks, additionalExpenses);
    }

    private int saveGeneratedTask(
            WeddingProfile profile,
            String title,
            String description,
            TaskCategory category,
            ResponsibleSide assignedSide,
            int daysBeforeWedding
    ) {
        Task task = Task.builder()
                .weddingProfile(profile)
                .title(title)
                .description(description)
                .category(category)
                .priority(TaskPriority.MEDIUM)
                .status(TaskStatus.PENDING)
                .dueDate(profile.getWeddingDate().minusDays(daysBeforeWedding))
                .assignedSide(assignedSide)
                .isGenerated(true)
                .build();
        taskRepository.save(task);
        return 1;
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private record SmartRuleCounts(int additionalTasks, int additionalExpenses) {
    }
}