package cares.innostark.com.cares.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bcm on 7/18/2016.
 */
public class BookingModel implements Parcelable{
    private String pickUpLocation;
    private String dropLocation;
    private String pickUpDateTime;
    private String dropDateTime;
    private String totalCharge;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;
    private String bookingId;
    private String imageUrl;

    public BookingModel(Parcel in) {
        pickUpLocation = in.readString();
        dropLocation = in.readString();
        pickUpDateTime = in.readString();
        dropDateTime = in.readString();
        totalCharge = in.readString();
        vehicleYear = in.readString();
        vehicleModel = in.readString();
        vehicleMake = in.readString();
        bookingId = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<BookingModel> CREATOR = new Creator<BookingModel>() {
        @Override
        public BookingModel createFromParcel(Parcel in) {
            return new BookingModel(in);
        }

        @Override
        public BookingModel[] newArray(int size) {
            return new BookingModel[size];
        }
    };

    public BookingModel() {

    }

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

    public String getPickUpDateTime() {
        return pickUpDateTime;
    }

    public void setPickUpDateTime(String pickUpTime) {
        this.pickUpDateTime = pickUpTime;
    }

    public String getDropDateTime() {
        return dropDateTime;
    }

    public void setDropDateTime(String dropTime) {
        this.dropDateTime = dropTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pickUpLocation);
        dest.writeString(dropLocation);
        dest.writeString(pickUpDateTime);
        dest.writeString(dropDateTime);
        dest.writeString(totalCharge);
        dest.writeString(vehicleMake);
        dest.writeString(vehicleModel);
        dest.writeString(vehicleYear);
        dest.writeString(bookingId);
        dest.writeString(imageUrl);
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
