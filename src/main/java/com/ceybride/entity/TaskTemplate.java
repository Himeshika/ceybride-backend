package com.ceybride.entity;

import com.ceybride.enums.ResponsibleSide;
import com.ceybride.enums.TaskCategory;
import com.ceybride.enums.TaskPriority;
import com.ceybride.enums.WeddingType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WeddingType weddingType;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TaskCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskPriority priority;

    @Column(nullable = false)
    private Integer defaultDayOffset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ResponsibleSide defaultAssignedSide;

    @Column(nullable = false)
    private boolean isActive;
}
