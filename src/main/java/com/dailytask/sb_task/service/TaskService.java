package com.dailytask.sb_task.service;

import com.dailytask.sb_task.model.Task;
import com.dailytask.sb_task.payload.TaskDTO;
import com.dailytask.sb_task.payload.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {
    TaskResponse getTask();
    TaskDTO createTask(TaskDTO taskDTO);
    String deleteTask(Long id);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
}
