package com.soc.taskaro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.soc.taskaro.createtask.SubTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateTask implements Parcelable {
    public String user_id = "";
    public String title = "";

    public String description = "";
    public String product_id = "";

    public boolean isImportant, isUrgent, isNotificationSelected;

    public ArrayList<SubTask> subTasks;

    public String date="", time="";

   public ArrayList<Boolean> daysArrayList;

    public CreateTask(String user_id, String title, String description, String product_id, boolean isImportant, boolean isUrgent, boolean isNotificationSelected, ArrayList<SubTask> subTasks, String date, String time, ArrayList<Boolean> daysArrayList) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.product_id = product_id;
        this.isImportant = isImportant;
        this.isUrgent = isUrgent;
        this.isNotificationSelected = isNotificationSelected;
        this.subTasks = subTasks;
        this.date = date;
        this.time = time;
        this.daysArrayList = daysArrayList;
    }

    public CreateTask(String user_id, String title, String description, String product_id, boolean isImportant, boolean isUrgent, boolean isNotificationSelected, ArrayList<SubTask> subTasks) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.product_id = product_id;
        this.isImportant = isImportant;
        this.isUrgent = isUrgent;
        this.isNotificationSelected = isNotificationSelected;
        this.subTasks = subTasks;
    }

    public CreateTask() {
    }

    protected CreateTask(Parcel in) {
        user_id = in.readString();
        title = in.readString();
        description = in.readString();
        product_id = in.readString();
        isImportant = in.readByte() != 0;
        isUrgent = in.readByte() != 0;
        isNotificationSelected = in.readByte() != 0;
        date = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(product_id);
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

    public static final Creator<CreateTask> CREATOR = new Creator<CreateTask>() {
        @Override
        public CreateTask createFromParcel(Parcel in) {
            return new CreateTask(in);
        }

        @Override
        public CreateTask[] newArray(int size) {
            return new CreateTask[size];
        }
    };
}
