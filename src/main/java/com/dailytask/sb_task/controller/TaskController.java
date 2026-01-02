package com.dailytask.sb_task.controller;

import com.dailytask.sb_task.model.Task;
import com.dailytask.sb_task.payload.TaskDTO;
import com.dailytask.sb_task.payload.TaskResponse;
import com.dailytask.sb_task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class TaskController {

    private TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<TaskResponse> getTask(){
        TaskResponse taskResponse = taskService.getTask();
        return new ResponseEntity<>(taskResponse,HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        TaskDTO taskDTO1 = taskService.createTask(taskDTO);
        return new ResponseEntity<>(taskDTO1,HttpStatus.CREATED);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        try{
            String status = taskService.deleteTask(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }

    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        TaskDTO taskReturned = taskService.updateTask(id, taskDTO);
        return new ResponseEntity<>(taskReturned,HttpStatus.OK);
    }

}
