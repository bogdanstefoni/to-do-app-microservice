package com.bogdan.todoapp.Controller;

import com.bogdan.todoapp.Dto.TaskDto;
import com.bogdan.todoapp.Service.TaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @GetMapping("/list/{userId}")
    public List<TaskDto> findTasksByUserId(@PathVariable Long userId) {
        logger.info("Task by user id called: {}", userId);
        return taskServiceImpl.findTaskByUserId(userId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        logger.info("All tasks called");
        return taskServiceImpl.findAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {
        return taskServiceImpl.findById(id);
    }

    @GetMapping("/task/{taskName}")
    public ResponseEntity<TaskDto> findByTaskName(@PathVariable String taskName) {
        return taskServiceImpl.findByTaskName(taskName);
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto, @PathVariable Long userId) {
        taskDto.setUserId(userId);
        logger.info("Task was created with user id: {}, {}", taskDto, userId);

        return taskServiceImpl.createTask(taskDto);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long userId) {
        logger.info("Task was updated with user id: {}, {}", taskDto, userId);

        return taskServiceImpl.updateTask(taskDto, userId);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable Long id) {

        taskServiceImpl.deleteTaskById(id);
    }
}
