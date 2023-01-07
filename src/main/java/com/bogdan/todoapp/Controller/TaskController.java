package com.bogdan.todoapp.Controller;

import com.bogdan.todoapp.Dto.TaskDto;
import com.bogdan.todoapp.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<String> getAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("/task/{taskName}")
    public ResponseEntity<String> findByTaskName(@PathVariable String taskName) {
        return taskService.findByTaskName(taskName);
    }

    @PostMapping("/")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<String> updateTask(@RequestBody TaskDto TaskDto, @PathVariable Long taskId) {
        TaskDto.setId(taskId);

        return taskService.updateTask(TaskDto);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable Long id) {

        taskService.deleteTaskById(id);
    }
}
