package com.anderson.todov1.controller;

import com.anderson.todov1.model.tasks.TaskService;
import com.anderson.todov1.model.tasks.dto.TaskCreateDTO;
import com.anderson.todov1.model.tasks.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskCreateDTO todoRequest) {
        TaskResponseDTO response = taskService.createTask(todoRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> findAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);

        Page<TaskResponseDTO> tasksPage = taskService.findAllPaginated(pageable);

        return ResponseEntity.ok(tasksPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findTaskById(@PathVariable UUID id) {
        TaskResponseDTO taskResponse = taskService.findTaskById(id);

        if (taskResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable UUID id,
            @RequestBody TaskCreateDTO todoRequest) {

        try {
            TaskResponseDTO updatedTask = taskService.updateTask(id, todoRequest);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        boolean deleted = taskService.deleteTask(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTaskPermanently(@PathVariable UUID id) {
        boolean wasDeleted = taskService.deleteTaskPermanently(id);

        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
