package com.dailytask.sb_task.controller;

import com.dailytask.sb_task.model.Task;
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
    public ResponseEntity<List<Task>> getTask(){
        List<Task> task = taskService.getTask();
        return new ResponseEntity<>(task,HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody Task task){
        taskService.createTask(task);
        String msg = "Task added successfully";
        return new ResponseEntity<>(msg,HttpStatus.CREATED);
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
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        Task taskReturned = taskService.updateTask(id, task);
        return ResponseEntity.ok(taskReturned);
    }

}
