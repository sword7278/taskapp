package com.pasupathi.TasksApp.repo;

import com.pasupathi.TasksApp.entity.Task;
import com.pasupathi.TasksApp.entity.User;
import com.pasupathi.TasksApp.enums.Priority;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user, Sort sort);
    List<Task> findByCompletedAndUser(boolean completed, User user);
    List<Task> findByPriorityAndUser(Priority priority, User user, Sort sort);
}
