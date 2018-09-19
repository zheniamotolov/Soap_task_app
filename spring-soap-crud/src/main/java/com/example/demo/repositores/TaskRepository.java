package com.example.demo.repositores;

import java.util.List;

import com.example.demo.entites.Task;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findByTaskId(long taskId);

    List<Task> findByTitleAndCategory(String title, String category);
}
