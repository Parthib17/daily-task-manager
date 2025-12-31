package com.dailytask.sb_task.service;

import com.dailytask.sb_task.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    private List<Task> tasks = new ArrayList<>();
    private Long id=0L;
    @Override
    public List<Task> getTask() {
        return tasks;
    }

    @Override
    public void createTask(Task task) {
        id = id+1;
        task.setId(id);
        tasks.add(task);
    }

    @Override
    public String deleteTask(Long id) {
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getId().equals(id)){
                tasks.remove(i);
                return "Task with id = "+id+" deleted successfully";
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Task with id = " + id + " not found"
        );
    }

    @Override
    public Task updateTask(Long id, Task task) {
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getId().equals(id)){
                task.setId(id);
                tasks.set(i,task);
                return tasks.get(i);
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Task with id = " + id + " not found"
        );
    }
}
