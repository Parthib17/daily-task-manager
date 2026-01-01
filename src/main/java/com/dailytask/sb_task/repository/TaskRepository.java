package com.dailytask.sb_task.repository;

import com.dailytask.sb_task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
