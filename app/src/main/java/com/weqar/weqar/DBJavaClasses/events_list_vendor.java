package com.weqar.weqar.DBJavaClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriod on 5/4/18.
 */

public class events_list_vendor implements Parcelable{
    String Id;
    String Title;
    String Name;
    String Image;
    String  Description;

    protected events_list_vendor(Parcel in) {
        Id = in.readString();
        Title = in.readString();
        Name = in.readString();
        Image = in.readString();
        Description = in.readString();
    }

    public static final Creator<events_list_vendor> CREATOR = new Creator<events_list_vendor>() {
        @Override
        public events_list_vendor createFromParcel(Parcel in) {
            return new events_list_vendor(in);
        }

        @Override
        public events_list_vendor[] newArray(int size) {
            return new events_list_vendor[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Title);
        dest.writeString(Name);
        dest.writeString(Image);
        dest.writeString(Description);
    }
}
