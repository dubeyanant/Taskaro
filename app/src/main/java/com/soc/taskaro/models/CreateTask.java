package com.soc.taskaro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CreateTask implements Parcelable {
    public String user_id = "";
    public String title = "";

    public String description = "";
    public String product_id = "";

    public boolean isImportant, isUrgent;

    public CreateTask(String user_id, String title, String description, String product_id, Boolean isImportant, Boolean isUrgent) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.product_id = product_id;
        this.isImportant = isImportant;
        this.isUrgent = isUrgent;
    }

    public CreateTask() {
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean isImportant) {
        isImportant = isImportant;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean isUrgent) {
        isUrgent = isUrgent;
    }

    protected CreateTask(Parcel in) {
        user_id = in.readString();
        title = in.readString();
        description = in.readString();
        product_id = in.readString();
        isImportant = in.readByte() != 0;
        isUrgent = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(product_id);
        dest.writeByte((byte) (isImportant ? 1 : 0));
        dest.writeByte((byte) (isUrgent ? 1 : 0));
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
