package com.soc.taskaro.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
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
    String user_id = "";
    String note_id = "";
    String heading = "";
    String description = "";


    public Note(String user_id, String note_id, String heading, String description) {
        this.user_id = user_id;
        this.note_id = note_id;
        this.heading = heading;
        this.description = description;
    }

    public Note() {
    }

    protected Note(Parcel in) {
        user_id = in.readString();
        note_id = in.readString();
        heading = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(note_id);
        dest.writeString(heading);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
