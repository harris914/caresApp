package cares.innostark.com.cares.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bcm on 2/16/2016.
 */
public class SubHireGroups implements Parcelable {
    private String HireGroupDetailId;
    private String HireGroup;
    private String HireGroupId;
    private String VehicleMake;
    private String VehicleModel;
    private String VehicleCategory;
    private String ModelYear;
    private String StandardRt;
    private String TariffType;
    private String HireGroupCodeName;
    private String HireGroupName;
    private String DropoffCharge;
    private String Description;
    private String VehicleCategoryId;
    private String VehicleMakeId;
    private String VehicleModelId;
    private String ImageUrl;
    private String ImageDescription;
    private String NoOfDoors;
    private String TrunckCapacity;
    private String NoOfPassengers;
    private String NoOfAirBags;
    private String TotalStandardCharge;
    private String AllowedMileage;
    private String ExcessMileage;
    private String ExcessMileageRate;

    public SubHireGroups(Parcel in) {
        HireGroupDetailId = in.readString();
        HireGroup = in.readString();
        HireGroupId = in.readString();
        VehicleMake = in.readString();
        VehicleModel = in.readString();
        VehicleCategory = in.readString();
        ModelYear = in.readString();
        StandardRt = in.readString();
        TariffType = in.readString();
        HireGroupCodeName = in.readString();
        HireGroupName = in.readString();
        DropoffCharge = in.readString();
        Description = in.readString();
        VehicleCategoryId = in.readString();
        VehicleMakeId = in.readString();
        VehicleModelId = in.readString();
        ImageUrl = in.readString();
        ImageDescription = in.readString();
        NoOfDoors = in.readString();
        TrunckCapacity = in.readString();
        NoOfPassengers = in.readString();
        NoOfAirBags = in.readString();
        TotalStandardCharge=in.readString();
        ExcessMileage=in.readString();
        ExcessMileageRate=in.readString();
        AllowedMileage=in.readString();
    }

    public static final Creator<SubHireGroups> CREATOR = new Creator<SubHireGroups>() {
        @Override
        public SubHireGroups createFromParcel(Parcel in) {
            return new SubHireGroups(in);
        }

        @Override
        public SubHireGroups[] newArray(int size) {
            return new SubHireGroups[size];
        }
    };

    public SubHireGroups() {

    }

    public String getHireGroupDetailId() {
        return HireGroupDetailId;
    }

    public void setHireGroupDetailId(String hireGroupDetailId) {
        HireGroupDetailId = hireGroupDetailId;
    }

    public String getHireGroup() {
        return HireGroup;
    }

    public void setHireGroup(String hireGroup) {
        HireGroup = hireGroup;
    }

    public String getHireGroupId() {
        return HireGroupId;
    }

    public void setHireGroupId(String hireGroupId) {
        HireGroupId = hireGroupId;
    }

    public String getVehicleMake() {
        return VehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        VehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return VehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        VehicleModel = vehicleModel;
    }

    public String getVehicleCategory() {
        return VehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        VehicleCategory = vehicleCategory;
    }

    public String getModelYear() {
        return ModelYear;
    }

    public void setModelYear(String modelYear) {
        ModelYear = modelYear;
    }

    public String getStandardRt() {
        return StandardRt;
    }

    public void setStandardRt(String standardRt) {
        StandardRt = standardRt;
    }

    public String getTariffType() {
        return TariffType;
    }

    public void setTariffType(String tariffType) {
        TariffType = tariffType;
    }

    public String getHireGroupCodeName() {
        return HireGroupCodeName;
    }

    public void setHireGroupCodeName(String hireGroupCodeName) {
        HireGroupCodeName = hireGroupCodeName;
    }

    public String getHireGroupName() {
        return HireGroupName;
    }

    public void setHireGroupName(String hireGroupName) {
        HireGroupName = hireGroupName;
    }

    public String getDropoffCharge() {
        return DropoffCharge;
    }

    public void setDropoffCharge(String dropoffCharge) {
        DropoffCharge = dropoffCharge;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVehicleCategoryId() {
        return VehicleCategoryId;
    }

    public void setVehicleCategoryId(String vehicleCategoryId) {
        VehicleCategoryId = vehicleCategoryId;
    }

    public String getVehicleMakeId() {
        return VehicleMakeId;
    }

    public void setVehicleMakeId(String vehicleMakeId) {
        VehicleMakeId = vehicleMakeId;
    }

    public String getVehicleModelId() {
        return VehicleModelId;
    }

    public void setVehicleModelId(String vehicleModelId) {
        VehicleModelId = vehicleModelId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getImageDescription() {
        return ImageDescription;
    }

    public void setImageDescription(String imageDescription) {
        ImageDescription = imageDescription;
    }

    public String getNoOfDoors() {
        return NoOfDoors;
    }

    public void setNoOfDoors(String noOfDoors) {
        NoOfDoors = noOfDoors;
    }

    public String getTrunckCapacity() {
        return TrunckCapacity;
    }

    public void setTrunckCapacity(String trunckCapacity) {
        TrunckCapacity = trunckCapacity;
    }

    public String getNoOfPassengers() {
        return NoOfPassengers;
    }

    public void setNoOfPassengers(String noOfPassengers) {
        NoOfPassengers = noOfPassengers;
    }

    public String getNoOfAirBags() {
        return NoOfAirBags;
    }

    public void setNoOfAirBags(String noOfAirBags) {
        NoOfAirBags = noOfAirBags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HireGroupDetailId);
        dest.writeString(HireGroup);
        dest.writeString(HireGroupId);
        dest.writeString(VehicleMake);
        dest.writeString(VehicleModel);
        dest.writeString(VehicleCategory);
        dest.writeString(ModelYear);
        dest.writeString(StandardRt);
        dest.writeString(TariffType);
        dest.writeString(HireGroupCodeName);
        dest.writeString(HireGroupName);
        dest.writeString(DropoffCharge);
        dest.writeString(Description);
        dest.writeString(VehicleCategoryId);
        dest.writeString(VehicleMakeId);
        dest.writeString(VehicleModelId);
        dest.writeString(ImageUrl);
        dest.writeString(ImageDescription);
        dest.writeString(NoOfDoors);
        dest.writeString(TrunckCapacity);
        dest.writeString(NoOfPassengers);
        dest.writeString(NoOfAirBags);
        dest.writeString(TotalStandardCharge);
        dest.writeString(ExcessMileage);
        dest.writeString(ExcessMileageRate);
        dest.writeString(AllowedMileage);
    }


    public String getTotalStandardCharge() {
        return TotalStandardCharge;
    }

    public void setTotalStandardCharge(String totalStandardCharge) {
        TotalStandardCharge = totalStandardCharge;
    }

    public String getAllowedMileage() {
        return AllowedMileage;
    }

    public void setAllowedMileage(String allowedMileage) {
        AllowedMileage = allowedMileage;
    }

    public String getExcessMileage() {
        return ExcessMileage;
    }

    public void setExcessMileage(String excessMileage) {
        ExcessMileage = excessMileage;
    }

    public String getExcessMileageRate() {
        return ExcessMileageRate;
    }

    public void setExcessMileageRate(String excessMileageRate) {
        ExcessMileageRate = excessMileageRate;
    }
}
