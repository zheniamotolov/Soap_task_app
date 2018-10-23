package com.example.restapi.controller;

import com.example.restapi.exeptions.ResourceNotFoundException;
import com.example.restapi.model.Task;
import com.example.restapi.repository.CategoryRepository;
import com.example.restapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
public class TaskController {
    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories/{id}/tasks")
    public List<Task> getAll(@PathVariable Long id) {
        return taskRepository.findByCategoryId(id);
    }

    @PostMapping("/categories/{id}/tasks")
    public Task add(@PathVariable Long id, @Valid @RequestBody Task task) {
        return categoryRepository.findById(id).map(category -> {
            task.setCategory(category);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));
    }

    @PutMapping("/categories/{categoryId}/tasks/{taskId}")
    public Task updateAnswer(@PathVariable Long categoryId,
                             @PathVariable Long taskId,
                             @Valid @RequestBody Task taskRequest) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Question not found with id " + categoryId);
        }

        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setContent(taskRequest.getContent());
                    task.setTitle(taskRequest.getTitle());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + taskId));
    }

    @DeleteMapping("/categories/{categoryId}/tasks/{taskId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long categoryId,
                                          @PathVariable Long taskId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Question not found with id " + categoryId);
        }

        return taskRepository.findById(taskId)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + taskId));

    }
}
