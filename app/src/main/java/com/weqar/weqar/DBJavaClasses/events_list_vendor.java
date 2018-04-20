package com.weqar.weqar.DBJavaClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriod on 5/4/18.
 */

public class events_list_vendor implements Parcelable{
    String Id;
    String UserId;
    String Title;
    String Name;
    String Image;
    String  Description;
    String Location;
    String  Latitude;
    String Longitude;
    String EventStart;
    String EventEnd;
    String Duration;
    String  Amount;
    String RegistrationRequired;
    String IsPaid;
    String Requirements;

    protected events_list_vendor(Parcel in) {
        Id = in.readString();
        UserId = in.readString();
        Title = in.readString();
        Name = in.readString();
        Image = in.readString();
        Description = in.readString();
        Location = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
        EventStart = in.readString();
        EventEnd = in.readString();
        Duration = in.readString();
        Amount = in.readString();
        RegistrationRequired = in.readString();
        IsPaid = in.readString();
        Requirements = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(UserId);
        dest.writeString(Title);
        dest.writeString(Name);
        dest.writeString(Image);
        dest.writeString(Description);
        dest.writeString(Location);
        dest.writeString(Latitude);
        dest.writeString(Longitude);
        dest.writeString(EventStart);
        dest.writeString(EventEnd);
        dest.writeString(Duration);
        dest.writeString(Amount);
        dest.writeString(RegistrationRequired);
        dest.writeString(IsPaid);
        dest.writeString(Requirements);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getEventStart() {
        return EventStart;
    }

    public void setEventStart(String eventStart) {
        EventStart = eventStart;
    }

    public String getEventEnd() {
        return EventEnd;
    }

    public void setEventEnd(String eventEnd) {
        EventEnd = eventEnd;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRegistrationRequired() {
        return RegistrationRequired;
    }

    public void setRegistrationRequired(String registrationRequired) {
        RegistrationRequired = registrationRequired;
    }

    public String getIsPaid() {
        return IsPaid;
    }

    public void setIsPaid(String isPaid) {
        IsPaid = isPaid;
    }

    public String getRequirements() {
        return Requirements;
    }

    public void setRequirements(String requirements) {
        Requirements = requirements;
    }
}
