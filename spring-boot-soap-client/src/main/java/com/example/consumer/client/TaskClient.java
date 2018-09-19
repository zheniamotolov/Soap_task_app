package com.example.consumer.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.consumer.wsdl.AddTaskRequest;
import com.example.consumer.wsdl.AddTaskResponse;
import com.example.consumer.wsdl.TaskInfo;
import com.example.consumer.wsdl.DeleteTaskRequest;
import com.example.consumer.wsdl.DeleteTaskResponse;
import com.example.consumer.wsdl.GetAllTasksRequest;
import com.example.consumer.wsdl.GetAllTasksResponse;
import com.example.consumer.wsdl.GetTaskByIdRequest;
import com.example.consumer.wsdl.GetTaskByIdResponse;
import com.example.consumer.wsdl.UpdateTaskRequest;
import com.example.consumer.wsdl.UpdateTaskResponse;

public class TaskClient extends WebServiceGatewaySupport {
    public GetTaskByIdResponse getTaskById(long articleId) {
        GetTaskByIdRequest request = new GetTaskByIdRequest();
        request.setTaskId(articleId);
        GetTaskByIdResponse response = (GetTaskByIdResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("http://localhost:8080/soapws/getTaskByIdRequest"));
        return response;
    }

    public GetAllTasksResponse getAllTasks() {
        GetAllTasksRequest request = new GetAllTasksRequest();
        GetAllTasksResponse response = (GetAllTasksResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("http://localhost:8080/soapws/getAllTasksRequest"));
        return response;
    }

    public AddTaskResponse addTask(String title, String category) {
        AddTaskRequest request = new AddTaskRequest();
        request.setTitle(title);
        request.setCategory(category);
        AddTaskResponse response = (AddTaskResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("http://localhost:8080/soapws/addTaskRequest"));
        return response;
    }

    public UpdateTaskResponse updateTask(TaskInfo articleInfo) {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setTaskInfo(articleInfo);
        UpdateTaskResponse response = (UpdateTaskResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("http://localhost:8080/soapws/updateTaskRequest"));
        return response;
    }

    public DeleteTaskResponse deleteTask(long articleId) {
        DeleteTaskRequest request = new DeleteTaskRequest();
        request.setTaskId(articleId);
        DeleteTaskResponse response = (DeleteTaskResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("http://localhost:8080/soapws/deleteTaskRequest"));
        return response;
    }
}
