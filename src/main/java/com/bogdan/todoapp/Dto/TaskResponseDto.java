package com.bogdan.todoapp.Dto;

import java.util.List;

public class TaskResponseDto {

    private String title;

    private List<String> TaskDescription;

    private String environment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(List<String> TaskDescription) {
        this.TaskDescription = TaskDescription;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
