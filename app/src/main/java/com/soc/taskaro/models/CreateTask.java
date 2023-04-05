package com.soc.taskaro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CreateTask implements Parcelable {
    public String user_id = "";
    public String title = "";

    public String description = "";
    public String product_id = "";

    public CreateTask(String user_id, String title, String description, String product_id) {
        this.user_id = user_id;
        this.title = title;
        this.product_id = product_id;
        this.description = description;
    }

    public CreateTask() {
    }


    protected CreateTask(Parcel in) {
        user_id = in.readString();
        title = in.readString();
        description = in.readString();
        product_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(product_id);
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
