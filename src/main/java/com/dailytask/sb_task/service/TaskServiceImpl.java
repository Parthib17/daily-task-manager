package com.dailytask.sb_task.service;

import com.dailytask.sb_task.model.Task;
import com.dailytask.sb_task.payload.TaskDTO;
import com.dailytask.sb_task.payload.TaskResponse;
import com.dailytask.sb_task.repository.TaskRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskResponse getTask() {
        List<Task> tasks= taskRepository.findAll();
        List<TaskDTO> taskDTO = tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .toList();

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setContent(taskDTO);
        return taskResponse;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskDTO.class);
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
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {

        Task task = modelMapper.map(taskDTO,Task.class);

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Task with id = " + id + " not found"
                ));

        // update fields (partial update – realistic)
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());

        Task task1 = taskRepository.save(existingTask);
        return modelMapper.map(task1, TaskDTO.class);
    }

}
