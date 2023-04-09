package com.soc.taskaro.fragments;

public class TasksPojo {
    String taskHeading, taskDescription;
    int taskLevel;

    public TasksPojo(String taskHeading, String taskDescription, int taskLevel) {
        this.taskHeading = taskHeading;
        this.taskDescription = taskDescription;
        this.taskLevel = taskLevel;
    }

    public int getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }
}