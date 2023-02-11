package com.bogdan.todoapp.Service;

import com.bogdan.todoapp.Dto.TaskDto;
import com.bogdan.todoapp.Entity.Task;
import com.bogdan.todoapp.Repository.TaskRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class TaskServiceImpl {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDto> findTaskByUserId(Long id) {
        List<Task> tasks = taskRepository.findByUserId(id);
        List<TaskDto> taskDtos = new ArrayList<>();

        tasks.forEach(t -> {
            TaskDto taskDto = mapToTaskDto(t);
            taskDtos.add(taskDto);
        });

        return taskDtos;
    }


    public ResponseEntity<List<TaskDto>> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return getStringResponseEntity(tasks);
    }

    public ResponseEntity<TaskDto> findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No task with id: " + id + " found."));

        TaskDto responseDto = mapToTaskDto(task);

        return new ResponseEntity<>(responseDto, OK);
    }

    public ResponseEntity<TaskDto> findByTaskName(String taskName) {
        Task tasks = taskRepository.findByTitle(taskName);


        TaskDto taskDto = mapToTaskDto(tasks);
        return new ResponseEntity<>(taskDto, OK);
    }

    public ResponseEntity<TaskDto> createTask(TaskDto taskDto) {
        Task task = mapToTask(taskDto);
        task.setUserId(taskDto.getUserId());
        TaskDto responseDto = mapToTaskDto(task);
        taskRepository.save(task);
        return new ResponseEntity<>(responseDto, OK);
    }

    public ResponseEntity<TaskDto> updateTask(TaskDto taskDto, long userId) {
        List<Task> currentTasks = taskRepository.findByUserId(userId);
        TaskDto responseDto = new TaskDto();

        List<Task> existingTitleTasks = currentTasks.stream()
                .filter(t -> t.getTitle().equals(taskDto.getTitle()))
                .toList();

        for (Task task : existingTitleTasks) {
            task.setTitle(taskDto.getTitle());
            task.setTaskDescription(taskDto.getTaskDescription());
            responseDto = mapToTaskDto(taskRepository.save(task));

        }


        return new ResponseEntity<>(responseDto, OK);

    }

    public void deleteTaskById(Long id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("task with id " + id +
                        " No found"));

        taskRepository.deleteById(existingTask.getId());
    }

    @NotNull
    private ResponseEntity<List<TaskDto>> getStringResponseEntity(List<Task> tasks) {
        List<TaskDto> taskResponseList = new ArrayList<>();

        tasks.forEach(t -> {
            TaskDto responseDto = mapToTaskDto(t);
            taskResponseList.add(responseDto);
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tasks", taskResponseList);
        return new ResponseEntity<>(taskResponseList, OK);
    }

    private Task mapToTask(TaskDto taskDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(taskDto, Task.class);
    }

    private void mapToTask(TaskDto taskDto, Task task) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            @Override
            protected void configure() {
                // TODO document why this method is empty
            }
        });
        mapper.map(taskDto, task);
    }

    private TaskDto mapToTaskDto(Task task) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(task, TaskDto.class);
    }

}
