package com.soc.taskaro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Parcelable, Serializable {
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
    public String user_id = "";
    public String title = "";
    public String description = "";
    public String task_id = "";
    public boolean isImportant, isUrgent, isNotificationSelected;
    public ArrayList<SubTask> subTasks;
    public ArrayList<Integer> subTaskStateList;
    public String date = "", time = "";
    public ArrayList<Boolean> daysArrayList;

    public Task(String user_id, String title, String description, String task_id, boolean isImportant, boolean isUrgent, boolean isNotificationSelected, ArrayList<SubTask> subTasks, ArrayList<Integer> subTaskStateList, String date, String time, ArrayList<Boolean> daysArrayList) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.task_id = task_id;
        this.isImportant = isImportant;
        this.isUrgent = isUrgent;
        this.isNotificationSelected = isNotificationSelected;
        this.subTasks = subTasks;
        this.subTaskStateList = subTaskStateList;
        this.date = date;
        this.time = time;
        this.daysArrayList = daysArrayList;
    }

    public Task(String user_id, String title, String description, String task_id, boolean isImportant, boolean isUrgent, boolean isNotificationSelected, ArrayList<SubTask> subTasks, ArrayList<Integer> subTaskStateList) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.task_id = task_id;
        this.isImportant = isImportant;
        this.isUrgent = isUrgent;
        this.isNotificationSelected = isNotificationSelected;
        this.subTasks = subTasks;
        this.subTaskStateList = subTaskStateList;
    }

    public Task() {
    }

    protected Task(Parcel in) {
        user_id = in.readString();
        title = in.readString();
        description = in.readString();
        task_id = in.readString();
        isImportant = in.readByte() != 0;
        isUrgent = in.readByte() != 0;
        isNotificationSelected = in.readByte() != 0;
        date = in.readString();
        time = in.readString();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public boolean isNotificationSelected() {
        return isNotificationSelected;
    }

    public void setNotificationSelected(boolean notificationSelected) {
        isNotificationSelected = notificationSelected;
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public ArrayList<Integer> getSubTaskStateList() {
        return subTaskStateList;
    }

    public void setSubTaskStateList(ArrayList<Integer> subTaskStateList) {
        this.subTaskStateList = subTaskStateList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Boolean> getDaysArrayList() {
        return daysArrayList;
    }

    public void setDaysArrayList(ArrayList<Boolean> daysArrayList) {
        this.daysArrayList = daysArrayList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(task_id);
        dest.writeByte((byte) (isImportant ? 1 : 0));
        dest.writeByte((byte) (isUrgent ? 1 : 0));
        dest.writeByte((byte) (isNotificationSelected ? 1 : 0));
        dest.writeString(date);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
