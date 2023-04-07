package com.soc.taskaro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    String user_id = "";
    String note_id = "";
    String title = "";
    String description = "";

    public Note(String user_id, String note_id, String title, String description) {
        this.user_id = user_id;
        this.note_id = note_id;
        this.title = title;
        this.description = description;
    }

    public Note() {
    }

    protected Note(Parcel in) {
        user_id = in.readString();
        note_id = in.readString();
        title = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(note_id);
        dest.writeString(title);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
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
}
