package com.anderson.todov1.model.tasks;

import com.anderson.todov1.model.tasks.dto.TaskCreateDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Task")
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Task() {
    }

    public Task(TaskCreateDTO dto) {

        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;

        this.title = dto.title();
        this.description = dto.description();
        this.expiredDate = dto.expiredDate();

        this.status = true;
    }
}
