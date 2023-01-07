package com.bogdan.todoapp.Dto;

import java.util.List;

public class TaskDto {

    private Long id;

    private String title;

    private List<String> taskDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(List<String> TaskDescription) {
        this.taskDescription = TaskDescription;
    }

}
