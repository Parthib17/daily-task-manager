package com.dailytask.sb_task.payload;

import com.dailytask.sb_task.model.Task;

import java.util.List;

public class TaskResponse {
    private List<TaskDTO> content;

    public TaskResponse() {
    }

    public TaskResponse(List<TaskDTO> content) {
        this.content = content;
    }

    public List<TaskDTO> getContent() {
        return content;
    }

    public void setContent(List<TaskDTO> content) {
        this.content = content;
    }
}
