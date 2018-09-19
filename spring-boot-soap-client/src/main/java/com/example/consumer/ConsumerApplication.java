package com.example.consumer;

import com.example.consumer.client.TaskClient;
import com.example.consumer.wsdl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(TaskClient taskClient) {
        return args -> {
            System.out.println("get task by id");
            GetTaskByIdResponse taskByIdResponse = taskClient.getTaskById(1);
            TaskInfo taskInfo = taskByIdResponse.getTaskInfo();
            System.out.println(taskInfo.getTaskId() + ", " + taskInfo.getTitle()
                    + ", " + taskInfo.getCategory());

            System.out.println("get all tasks");
            GetAllTasksResponse allTasksResponse = taskClient.getAllTasks();
            allTasksResponse.getTaskInfo().stream()
                    .forEach(e -> System.out.println(e.getTaskId() + ", " + e.getTitle() + ", " + e.getCategory()));

            System.out.println("add task");
            String title = "create program";
            String category = "it";
            AddTaskResponse addTaskResponse = taskClient.addTask(title, category);
            taskInfo = addTaskResponse.getTaskInfo();
            if (taskInfo != null) {
                System.out.println(taskInfo.getTaskId() + ", " + taskInfo.getTitle()
                        + ", " + taskInfo.getCategory());
            }
            ServiceStatus serviceStatus = addTaskResponse.getServiceStatus();
            System.out.println("StatusCode: " + serviceStatus.getStatusCode() +
                    ", Message: " + serviceStatus.getMessage());

            System.out.println("update task");
            taskInfo = new TaskInfo();
            taskInfo.setTaskId(1);
            taskInfo.setTitle("Update:create a bot");
            taskInfo.setCategory("it");
            UpdateTaskResponse updateTaskResponse = taskClient.updateTask(taskInfo);
            serviceStatus = updateTaskResponse.getServiceStatus();
            System.out.println("StatusCode: " + serviceStatus.getStatusCode() +
                    ", Message: " + serviceStatus.getMessage());
            System.out.println("delete task");
            long taskId = 3;
            DeleteTaskResponse deleteTaskResponse = taskClient.deleteTask(taskId);
            serviceStatus = deleteTaskResponse.getServiceStatus();
            System.out.println("StatusCode: " + serviceStatus.getStatusCode() +
                    ", Message: " + serviceStatus.getMessage());
        };
    }
}
