package com.example.demo.Controller;

import com.example.demo.Entity.DTO.TaskRequestDto;
import com.example.demo.Entity.DTO.TaskResponseDto;
import com.example.demo.Service.TaskService;
import com.example.demo.exception.TaskNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @GetMapping("/")
    public ResponseEntity<?> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }
    @Validated
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequestDto taskDto) {
        try {
            taskService.createTask(taskDto);
            return ResponseEntity.ok("Task added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());}

    }
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskDto) {
        try {
            TaskResponseDto updatedTask = taskService.updateTask(id,taskDto);
            return ResponseEntity.ok(updatedTask);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) throws Exception {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task with ID " + id + " deleted successfully");
    }
}
