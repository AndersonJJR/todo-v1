package com.anderson.todov1.model.tasks;

import com.anderson.todov1.model.tasks.dto.TaskCreateDTO;
import com.anderson.todov1.model.tasks.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponseDTO createTask(TaskCreateDTO todoRequest) {
        Task newTask = new Task(todoRequest);

        Task savedTask = taskRepository.save(newTask);
        return mapToResponseDto(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    @Transactional()
    public Page<TaskResponseDTO> findAllPaginated(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);

        List<TaskResponseDTO> dtoList = taskPage.getContent().stream().map(this::mapToResponseDto).collect(Collectors.toList());

        return new org.springframework.data.domain.PageImpl<>(dtoList, pageable, taskPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return null;
        }
        return mapToResponseDto(task);
    }

    @Transactional
    public TaskResponseDTO updateTask(UUID id, TaskCreateDTO todoRequest) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));

        if (todoRequest.title() != null) {
            task.setTitle(todoRequest.title());
        }
        if (todoRequest.description() != null) {
            task.setDescription(todoRequest.description());
        }
        if (todoRequest.expiredDate() != null) {
            task.setExpiredDate(todoRequest.expiredDate());
        }

        Task updatedTask = taskRepository.save(task);

        return mapToResponseDto(updatedTask);
    }

    @Transactional
    public boolean deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }

        Task task = taskRepository.findById(id).get();
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);

        return true;
    }

    @Transactional
    public boolean deleteTaskPermanently(UUID id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    private TaskResponseDTO mapToResponseDto(Task task) {
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getExpiredDate(), task.getCreatedAt(), task.getDeletedAt());
    }


}