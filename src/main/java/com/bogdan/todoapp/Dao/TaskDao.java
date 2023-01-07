package com.bogdan.todoapp.Dao;

import com.bogdan.todoapp.Entity.Task;
import com.bogdan.todoapp.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskDao {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAllTasks() {

        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> findByTaskTitle(String taskTitle) {

        return taskRepository.findByTitle(taskTitle).stream().findFirst();
    }

    public Task createTask(Task task) {

        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {

        return taskRepository.save(task);
    }

    public void removeTaskById(Long id) {

        taskRepository.deleteById(id);
    }

}
