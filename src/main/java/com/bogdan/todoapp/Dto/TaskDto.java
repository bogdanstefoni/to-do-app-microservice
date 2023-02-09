package com.bogdan.todoapp.Dto;

public class TaskDto {

    private Long userId;

    private String title;

    private String taskDescription;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String TaskDescription) {
        this.taskDescription = TaskDescription;
    }

}
