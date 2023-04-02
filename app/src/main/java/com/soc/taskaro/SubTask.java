package com.soc.taskaro;

import java.io.Serializable;

public class SubTask implements Serializable {
    public String subTask;

    public SubTask() {

    }

    public SubTask(String subTask) {
        this.subTask = subTask;
    }

    public String getSubTask() {
        return subTask;
    }

    public void setSubTask(String subTask) {
        this.subTask = subTask;
    }
}