package com.weqar.weqar.DBJavaClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriod on 4/4/18.
 */

public class dashboard_list implements Parcelable{
    String Type;
    String Id;
    String Title;
    String  Logo;
    String  Image;

    protected dashboard_list(Parcel in) {
        Type = in.readString();
        Id = in.readString();
        Title = in.readString();
        Logo = in.readString();
        Image = in.readString();
    }

    public static final Creator<dashboard_list> CREATOR = new Creator<dashboard_list>() {
        @Override
        public dashboard_list createFromParcel(Parcel in) {
            return new dashboard_list(in);
        }

        @Override
        public dashboard_list[] newArray(int size) {
            return new dashboard_list[size];
        }
    };

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

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

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Type);
        dest.writeString(Id);
        dest.writeString(Title);
        dest.writeString(Logo);
        dest.writeString(Image);
    }
}
