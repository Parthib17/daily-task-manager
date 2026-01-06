package com.dailytask.sb_task.controller;

import com.dailytask.sb_task.model.Task;
import com.dailytask.sb_task.payload.TaskDTO;
import com.dailytask.sb_task.payload.TaskResponse;
import com.dailytask.sb_task.security.jwt.JwtUtils;
import com.dailytask.sb_task.security.jwt.LoginRequest;
import com.dailytask.sb_task.security.jwt.LoginResponse;
import com.dailytask.sb_task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class TaskController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
            );
        } catch (AuthenticationException e){
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status" , false);

            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        LoginResponse response = new LoginResponse(jwtToken,userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

}
