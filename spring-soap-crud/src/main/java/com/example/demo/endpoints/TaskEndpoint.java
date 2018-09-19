package com.example.demo.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.entites.Task;
import com.example.demo.soap_tasks_ws.AddTaskRequest;
import com.example.demo.soap_tasks_ws.AddTaskResponse;
import com.example.demo.soap_tasks_ws.TaskInfo;
import com.example.demo.soap_tasks_ws.DeleteTaskRequest;
import com.example.demo.soap_tasks_ws.DeleteTaskResponse;
import com.example.demo.soap_tasks_ws.GetAllTasksResponse;
import com.example.demo.soap_tasks_ws.GetTaskByIdRequest;
import com.example.demo.soap_tasks_ws.GetTaskByIdResponse;
import com.example.demo.soap_tasks_ws.ServiceStatus;
import com.example.demo.soap_tasks_ws.UpdateTaskRequest;
import com.example.demo.soap_tasks_ws.UpdateTaskResponse;
import com.example.demo.services.ITaskService;

@Endpoint
public class TaskEndpoint {
    private static final String NAMESPACE_URI = "http://www.tasks.com/task-ws";
    @Autowired
    private ITaskService taskService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTaskByIdRequest")
    @ResponsePayload
    public GetTaskByIdResponse getTask(@RequestPayload GetTaskByIdRequest request) {
        GetTaskByIdResponse response = new GetTaskByIdResponse();
        TaskInfo taskInfo = new TaskInfo();
        BeanUtils.copyProperties(taskService.getTaskById(request.getTaskId()), taskInfo);
        response.setTaskInfo(taskInfo);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllTasksRequest")
    @ResponsePayload
    public GetAllTasksResponse getAllTasks() {
        GetAllTasksResponse response = new GetAllTasksResponse();
        List<TaskInfo> taskInfoList = new ArrayList<>();
        List<Task> taskList = taskService.getAllTasks();
        for (int i = 0; i < taskList.size(); i++) {
            TaskInfo ob = new TaskInfo();
            BeanUtils.copyProperties(taskList.get(i), ob);
            taskInfoList.add(ob);
        }
        response.getTaskInfo().addAll(taskInfoList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addTaskRequest")
    @ResponsePayload
    public AddTaskResponse addTask(@RequestPayload AddTaskRequest request) {
        AddTaskResponse response = new AddTaskResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setCategory(request.getCategory());
        boolean flag = taskService.addTask(task);
        if (flag == false) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Content Already Available");
            response.setServiceStatus(serviceStatus);
        } else {
            TaskInfo taskInfo = new TaskInfo();
            BeanUtils.copyProperties(task, taskInfo);
            response.setTaskInfo(taskInfo);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateTaskRequest")
    @ResponsePayload
    public UpdateTaskResponse updateTask(@RequestPayload UpdateTaskRequest request) {
        Task task = new Task();
        BeanUtils.copyProperties(request.getTaskInfo(), task);
        taskService.updateTask(task);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Content Updated Successfully");
        UpdateTaskResponse response = new UpdateTaskResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteTaskRequest")
    @ResponsePayload
    public DeleteTaskResponse deleteTask(@RequestPayload DeleteTaskRequest request) {
        Task task = taskService.getTaskById(request.getTaskId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (task == null) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Content Not Available");
        } else {
            taskService.deleteTask(task);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        DeleteTaskResponse response = new DeleteTaskResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
