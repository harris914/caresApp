package cares.innostark.com.cares.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bcm on 2/16/2016.
 */
public class ModelStep1 implements Parcelable {
    private String pickUpLocation;
    private String dropLocation;
    private String pickUpTime;
    private String dropTime;
    private String pickUpDate;
    private String dropDate;

    public ModelStep1()
    {

    }

    protected ModelStep1(Parcel in) {
        pickUpLocation = in.readString();
        dropLocation = in.readString();
        pickUpTime = in.readString();
        dropTime = in.readString();
        pickUpDate = in.readString();
        dropDate = in.readString();
    }

    public static final Creator<ModelStep1> CREATOR = new Creator<ModelStep1>() {
        @Override
        public ModelStep1 createFromParcel(Parcel in) {
            return new ModelStep1(in);
        }

        @Override
        public ModelStep1[] newArray(int size) {
            return new ModelStep1[size];
        }
    };

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getDropDate() {
        return dropDate;
    }

    public void setDropDate(String dropDate) {
        this.dropDate = dropDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pickUpLocation);
        dest.writeString(dropLocation);
        dest.writeString(pickUpTime);
        dest.writeString(dropTime);
        dest.writeString(pickUpDate);
        dest.writeString(dropDate);
    }
}
