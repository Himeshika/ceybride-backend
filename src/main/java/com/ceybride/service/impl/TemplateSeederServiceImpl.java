package com.ceybride.service.impl;

import com.ceybride.entity.ExpenseTemplate;
import com.ceybride.entity.RitualTemplate;
import com.ceybride.entity.TaskTemplate;
import com.ceybride.enums.ExpenseCategory;
import com.ceybride.enums.ResponsibleSide;
import com.ceybride.enums.TaskCategory;
import com.ceybride.enums.TaskPriority;
import com.ceybride.enums.WeddingType;
import com.ceybride.repository.ExpenseTemplateRepository;
import com.ceybride.repository.RitualTemplateRepository;
import com.ceybride.repository.TaskTemplateRepository;
import com.ceybride.service.TemplateSeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TemplateSeederServiceImpl implements TemplateSeederService, CommandLineRunner {

    private final TaskTemplateRepository taskTemplateRepository;
    private final ExpenseTemplateRepository expenseTemplateRepository;
    private final RitualTemplateRepository ritualTemplateRepository;

    @Override
    @Transactional
    public void run(String... args) {
        seedAll();
    }

    @Override
    @Transactional
    public void seedAll() {
        for (WeddingType type : WeddingType.values()) {
            if (taskTemplateRepository.countByWeddingTypeAndIsActiveTrue(type) == 0) {
                seedTaskTemplates(type);
            }
            if (expenseTemplateRepository.countByWeddingTypeAndIsActiveTrue(type) == 0) {
                seedExpenseTemplates(type);
            }
            if (ritualTemplateRepository.countByWeddingTypeAndIsActiveTrue(type) == 0) {
                seedRitualTemplates(type);
            }
        }
    }

    private void seedTaskTemplates(WeddingType type) {
        switch (type) {
            case SINHALA_PORUWA -> {
                addTask(type, "Confirm poruwa ceremony arrangements", "Finalize poruwa structure and setup", TaskCategory.RITUAL, TaskPriority.HIGH, -60, ResponsibleSide.SHARED);
                addTask(type, "Book photographer", "Reserve wedding photography and videography", TaskCategory.VENDOR, TaskPriority.HIGH, -90, ResponsibleSide.SHARED);
                addTask(type, "Finalize bridal attire", "Complete Kandyan saree and fitting", TaskCategory.ATTIRE, TaskPriority.HIGH, -45, ResponsibleSide.BRIDE_SIDE);
                addTask(type, "Prepare wedding invitation list", "Draft and verify guest list", TaskCategory.GUEST, TaskPriority.MEDIUM, -75, ResponsibleSide.SHARED);
                addTask(type, "Confirm catering menu", "Lock menu and headcount assumptions", TaskCategory.CATERING, TaskPriority.HIGH, -30, ResponsibleSide.SHARED);
                addTask(type, "Arrange wedding cake", "Confirm cake design and delivery", TaskCategory.VENDOR, TaskPriority.MEDIUM, -20, ResponsibleSide.SHARED);
                addTask(type, "Finalize music or band", "Review reception music flow", TaskCategory.VENDOR, TaskPriority.MEDIUM, -35, ResponsibleSide.GROOM_SIDE);
                addTask(type, "Complete seating plan", "Assign guest tables and family zones", TaskCategory.GUEST, TaskPriority.HIGH, -10, ResponsibleSide.SHARED);
            }
            case TAMIL_HINDU -> {
                addTask(type, "Coordinate temple rituals", "Confirm priest, pooja items, and sequence", TaskCategory.RITUAL, TaskPriority.HIGH, -45, ResponsibleSide.SHARED);
                addTask(type, "Finalize traditional attire", "Confirm saree and ceremonial wear", TaskCategory.ATTIRE, TaskPriority.HIGH, -40, ResponsibleSide.SHARED);
                addTask(type, "Book wedding hall", "Confirm venue booking and payments", TaskCategory.VENUE, TaskPriority.HIGH, -90, ResponsibleSide.SHARED);
                addTask(type, "Prepare guest invitation list", "Review family invitations", TaskCategory.GUEST, TaskPriority.MEDIUM, -60, ResponsibleSide.SHARED);
            }
            case MUSLIM_NIKAH -> {
                addTask(type, "Prepare nikah documents", "Ensure required documentation is ready", TaskCategory.LEGAL, TaskPriority.HIGH, -30, ResponsibleSide.SHARED);
                addTask(type, "Confirm witnesses and wali", "Finalize required participants for nikah", TaskCategory.RITUAL, TaskPriority.HIGH, -20, ResponsibleSide.SHARED);
                addTask(type, "Book reception venue", "Confirm venue and catering arrangement", TaskCategory.VENUE, TaskPriority.HIGH, -60, ResponsibleSide.SHARED);
                addTask(type, "Finalize outfit arrangements", "Confirm bride and groom attire", TaskCategory.ATTIRE, TaskPriority.MEDIUM, -35, ResponsibleSide.SHARED);
            }
            case CHRISTIAN -> {
                addTask(type, "Book church ceremony slot", "Confirm church schedule and documents", TaskCategory.RITUAL, TaskPriority.HIGH, -75, ResponsibleSide.SHARED);
                addTask(type, "Coordinate choir and music", "Finalize wedding music program", TaskCategory.VENDOR, TaskPriority.MEDIUM, -30, ResponsibleSide.SHARED);
                addTask(type, "Finalize bridal party details", "Confirm bridesmaids and groomsmen arrangements", TaskCategory.GUEST, TaskPriority.MEDIUM, -25, ResponsibleSide.SHARED);
                addTask(type, "Prepare reception flow", "Create order of events and reception notes", TaskCategory.GENERAL, TaskPriority.MEDIUM, -15, ResponsibleSide.SHARED);
            }
            case OTHER -> {
                addTask(type, "Book venue", "Reserve event location", TaskCategory.VENUE, TaskPriority.HIGH, -60, ResponsibleSide.SHARED);
                addTask(type, "Prepare guest list", "Draft guest list and RSVPs", TaskCategory.GUEST, TaskPriority.MEDIUM, -45, ResponsibleSide.SHARED);
                addTask(type, "Confirm vendors", "Shortlist and confirm vendors", TaskCategory.VENDOR, TaskPriority.MEDIUM, -30, ResponsibleSide.SHARED);
            }
        }
    }

    private void seedExpenseTemplates(WeddingType type) {
        addExpense(type, "Venue", ExpenseCategory.VENUE, ResponsibleSide.SHARED, new BigDecimal("30.00"));
        addExpense(type, "Catering", ExpenseCategory.CATERING, ResponsibleSide.SHARED, new BigDecimal("25.00"));
        addExpense(type, "Photography", ExpenseCategory.PHOTOGRAPHY, ResponsibleSide.SHARED, new BigDecimal("10.00"));
        addExpense(type, "Attire", ExpenseCategory.ATTIRE, ResponsibleSide.BRIDE_SIDE, new BigDecimal("10.00"));
        addExpense(type, "Jewelry", ExpenseCategory.JEWELRY, ResponsibleSide.BRIDE_SIDE, new BigDecimal("8.00"));
        addExpense(type, "Decoration", ExpenseCategory.DECORATION, ResponsibleSide.SHARED, new BigDecimal("7.00"));
        addExpense(type, "Entertainment", ExpenseCategory.ENTERTAINMENT, ResponsibleSide.GROOM_SIDE, new BigDecimal("5.00"));
        addExpense(type, "Transport", ExpenseCategory.TRANSPORT, ResponsibleSide.SHARED, new BigDecimal("5.00"));
    }

    private void seedRitualTemplates(WeddingType type) {
        switch (type) {
            case SINHALA_PORUWA -> {
                addRitual(type, "Leaving home", 0, 1, "Gold", "East", "Bride leaves home at auspicious time");
                addRitual(type, "Poruwa ceremony", 0, 2, "Ivory", "North", "Main poruwa ritual");
                addRitual(type, "Pahan pattu kirima", 0, 3, "White", "East", "Traditional blessing ritual");
                addRitual(type, "Reception entry", 0, 4, "Maroon", "South", "Entrance to reception");
                addRitual(type, "Homecoming", 1, 5, "Cream", "West", "Post-wedding family welcome");
            }
            case TAMIL_HINDU -> {
                addRitual(type, "Temple blessings", 0, 1, "Red", "East", "Temple opening blessings");
                addRitual(type, "Main wedding ritual", 0, 2, "Gold", "North", "Core ceremonial sequence");
                addRitual(type, "Reception", 0, 3, "Green", "South", "Reception and family greetings");
            }
            case MUSLIM_NIKAH -> {
                addRitual(type, "Nikah ceremony", 0, 1, "White", "East", "Nikah contract and blessing");
                addRitual(type, "Family gathering", 0, 2, "Green", "North", "Close family welcome");
                addRitual(type, "Reception", 0, 3, "Gold", "South", "Wedding reception");
            }
            case CHRISTIAN -> {
                addRitual(type, "Church ceremony", 0, 1, "White", "East", "Church vows and blessing");
                addRitual(type, "Signing of register", 0, 2, "Silver", "North", "Formal register signing");
                addRitual(type, "Reception entry", 0, 3, "Blue", "South", "Reception start");
            }
            case OTHER -> {
                addRitual(type, "Main ceremony", 0, 1, "White", "East", "Main ceremony");
                addRitual(type, "Reception", 0, 2, "Gold", "South", "Reception");
            }
        }
    }

    private void addTask(
            WeddingType weddingType,
            String title,
            String description,
            TaskCategory category,
            TaskPriority priority,
            int offset,
            ResponsibleSide side
    ) {
        taskTemplateRepository.save(TaskTemplate.builder()
                .weddingType(weddingType)
                .title(title)
                .description(description)
                .category(category)
                .priority(priority)
                .defaultDayOffset(offset)
                .defaultAssignedSide(side)
                .isActive(true)
                .build());
    }

    private void addExpense(
            WeddingType weddingType,
            String title,
            ExpenseCategory category,
            ResponsibleSide side,
            BigDecimal percentage
    ) {
        expenseTemplateRepository.save(ExpenseTemplate.builder()
                .weddingType(weddingType)
                .title(title)
                .category(category)
                .responsibleSide(side)
                .defaultBudgetPercentage(percentage)
                .isActive(true)
                .build());
    }

    private void addRitual(
            WeddingType weddingType,
            String ritualName,
            int offset,
            int order,
            String color,
            String direction,
            String notes
    ) {
        ritualTemplateRepository.save(RitualTemplate.builder()
                .weddingType(weddingType)
                .ritualName(ritualName)
                .defaultDayOffset(offset)
                .sequenceOrder(order)
                .defaultLuckyColor(color)
                .defaultFacingDirection(direction)
                .notes(notes)
                .isActive(true)
                .build());
    }
}