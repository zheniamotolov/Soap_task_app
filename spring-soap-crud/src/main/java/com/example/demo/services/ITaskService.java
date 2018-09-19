package com.example.demo.services;

import com.example.demo.entites.Task;

import java.util.List;


public interface ITaskService {
    List<Task> getAllTasks();

    Task getTaskById(long taskId);

    boolean addTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);
}
