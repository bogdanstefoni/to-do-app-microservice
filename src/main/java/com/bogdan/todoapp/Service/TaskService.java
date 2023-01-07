package com.bogdan.todoapp.Service;

import com.bogdan.todoapp.Dao.TaskDao;
import com.bogdan.todoapp.Dto.TaskDto;
import com.bogdan.todoapp.Dto.TaskResponseDto;
import com.bogdan.todoapp.Entity.Task;
import com.bogdan.todoapp.Exception.RestResponse;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private static Environment environment;
    @Autowired
    private TaskDao taskDao;

    public ResponseEntity<String> findAllTasks() {
        List<Task> tasks = taskDao.findAllTasks();
        List<TaskResponseDto> taskResponseList = new ArrayList<>();

        tasks.forEach(t -> {
            TaskResponseDto responseDto = mapToTaskResponseDto(t);
            taskResponseList.add(responseDto);
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tasks", taskResponseList);
        return RestResponse.createSuccessResponse(jsonObject);
    }

    public ResponseEntity<String> findById(Long id) {
        Task task = taskDao.findById(id)
                .orElseThrow(() -> new RuntimeException("No task with id: " + id + " found."));

        TaskResponseDto responseDto = mapToTaskResponseDto(task);

        return RestResponse.createSuccessResponse(new JSONObject(responseDto));
    }

    public ResponseEntity<String> findByTaskName(String taskName) {
        Task task = taskDao.findByTaskTitle(taskName)
                .orElseThrow(() -> new RuntimeException("task " + taskName + " not found"));

        TaskResponseDto responseDto = mapToTaskResponseDto(task);

        return RestResponse.createSuccessResponse(new JSONObject(responseDto));
    }

    public ResponseEntity<String> createTask(TaskDto taskDto) {
        Task task = mapToTask(taskDto);

        return RestResponse.createSuccessResponse(new JSONObject(mapToTaskResponseDto(
                taskDao.createTask(task)
        )));
    }

    public ResponseEntity<String> updateTask(TaskDto TaskDto) {
        Task task = taskDao.findById(TaskDto.getId())
                .orElseThrow(() -> new RuntimeException("task " + TaskDto.getId() +
                        " No found"));

        mapToTask(TaskDto, task);

        TaskResponseDto TaskResponseDto = mapToTaskResponseDto(taskDao.updateTask(task));

        return RestResponse.createSuccessResponse(new JSONObject(TaskResponseDto));
    }

    public void deleteTaskById(Long id) {
        Task existingTask = taskDao.findById(id)
                .orElseThrow(() -> new RuntimeException("task with id " + id +
                        " No found"));

        taskDao.removeTaskById(existingTask.getId());
    }


    private Task mapToTask(TaskDto TaskDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(TaskDto, Task.class);
    }

    private void mapToTask(TaskDto TaskDto, Task task) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            @Override
            protected void configure() {
//                skip(destination.getPassword());
            }
        });
        mapper.map(TaskDto, task);
    }

    private TaskResponseDto mapToTaskResponseDto(Task task) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(task, TaskResponseDto.class);
    }

}
