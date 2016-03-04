package cares.innostark.com.cares.Models;

import android.content.ClipData;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by bcm on 2/15/2016.
 */
public class OperationWorkPlaces implements Parcelable{
    private String LocationName;
    private String OperationWorkplaceId;
    private String CityId;
    private String OperationId;
    private String Longitude;
    private String Latitude;
    private String Phone;
    private String Address;
    private ArrayList<ClipData.Item> items;

    public OperationWorkPlaces()
    {

    }

    protected OperationWorkPlaces(Parcel in) {
        LocationName = in.readString();
        OperationWorkplaceId = in.readString();
        CityId = in.readString();
        OperationId = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
        Phone = in.readString();
        Address = in.readString();
    }

    public static final Creator<OperationWorkPlaces> CREATOR = new Creator<OperationWorkPlaces>() {
        @Override
        public OperationWorkPlaces createFromParcel(Parcel in) {
            return new OperationWorkPlaces(in);
        }

        @Override
        public OperationWorkPlaces[] newArray(int size) {
            return new OperationWorkPlaces[size];
        }
    };

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getOperationWorkplaceId() {
        return OperationWorkplaceId;
    }

    public void setOperationWorkplaceId(String operationWorkplaceId) {
        OperationWorkplaceId = operationWorkplaceId;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getOperationId() {
        return OperationId;
    }

    public void setOperationId(String operationId) {
        OperationId = operationId;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(LocationName);
        dest.writeString(OperationWorkplaceId);
        dest.writeString(CityId);
        dest.writeString(OperationId);
        dest.writeString(Longitude);
        dest.writeString(Latitude);
        dest.writeString(Phone);
        dest.writeString(Address);
    }

}
