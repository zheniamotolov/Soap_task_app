package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entites.Task;
import com.example.demo.repositores.TaskRepository;
import com.example.demo.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskService implements ITaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Override
	public Task getTaskById(long taskId) {
		Task obj = taskRepository.findByTaskId(taskId);
		return obj;
	}
	@Override
	public List<Task> getAllTasks(){
		List<Task> list = new ArrayList<>();
		taskRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addTask(Task task){
	   List<Task> list = taskRepository.findByTitleAndCategory(task.getTitle(), task.getCategory());
       if (list.size() > 0) {
    	   return false;
       } else {
    	   task = taskRepository.save(task);
    	   return true;
       }
	}
	@Override
	public void updateTask(Task task) {
		taskRepository.save(task);
	}
	@Override
	public void deleteTask(Task task) {
		taskRepository.delete(task);
	}
}
