package com.dailytask.sb_task.service;

import com.dailytask.sb_task.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {
    List<Task> getTask();
    void createTask(Task task);
    String deleteTask(Long id);
    Task updateTask(Long id, Task task);
}
