package com.soc.taskaro.models;

import java.io.Serializable;

public class SubTask implements Serializable {
    public String subTask;

    public Integer subTaskState;

    public SubTask() {

    }

    public String getSubTask() {
        return subTask;
    }

    public void setSubTask(String subTask) {
        this.subTask = subTask;
    }

    public Integer getSubTaskState() {
        return subTaskState;
    }

    public void setSubTaskState(Integer subTaskState) {
        this.subTaskState = subTaskState;
    }
}