package com.dailytask.sb_task.service;

import com.dailytask.sb_task.model.Task;
import com.dailytask.sb_task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    //private List<Task> tasks = new ArrayList<>();

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getTask() {
        return taskRepository.findAll();
    }

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public String deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Task with id = " + id + " not found"
            );
        }

        taskRepository.deleteById(id);
        return "Task with id = " + id + " deleted successfully";
    }


    @Override
    public Task updateTask(Long id, Task task) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Task with id = " + id + " not found"
                ));

        // update fields (partial update – realistic)
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());

        return taskRepository.save(existingTask);
    }

}
