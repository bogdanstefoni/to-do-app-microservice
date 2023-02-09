package com.bogdan.todoapp.Controller;

import com.bogdan.todoapp.Dto.TaskDto;
import com.bogdan.todoapp.Service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @GetMapping("/list/{userId}")
    public List<TaskDto> findTasksByUserId(@PathVariable Long userId) {
        return taskServiceImpl.findTaskByUserId(userId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return taskServiceImpl.findAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {
        return taskServiceImpl.findById(id);
    }

    @GetMapping("/task/{taskName}")
    public ResponseEntity<List<TaskDto>> findByTaskName(@PathVariable String taskName) {
        return taskServiceImpl.findByTaskName(taskName);
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto, @PathVariable Long userId) {
        taskDto.setUserId(userId);

        return taskServiceImpl.createTask(taskDto);
    }

    @PutMapping("/update/{taskName}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto TaskDto, @PathVariable String taskName) {

        return taskServiceImpl.updateTask(TaskDto);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable Long id) {

        taskServiceImpl.deleteTaskById(id);
    }
}
