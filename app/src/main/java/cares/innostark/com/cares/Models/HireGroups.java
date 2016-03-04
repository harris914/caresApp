package cares.innostark.com.cares.Models;

import org.json.JSONArray;

import java.util.ArrayList;

import cares.innostark.com.cares.Mappings.SubHireGroupsMapper;

/**
 * Created by bcm on 2/16/2016.
 */
public class HireGroups {
    private String HireGroupId;
    private String HireGroupCodeName;
    private String HireGroupName;
    private String DropOffCharge;
    private String Description;
    private String Title;
    private String SubTitle;
    private String PhotoUrl;
    private ArrayList<SubHireGroups> SubHireGroup;

    public String getHireGroupId() {
        return HireGroupId;
    }

    public void setHireGroupId(String hireGroupId) {
        HireGroupId = hireGroupId;
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

    public void setHireGroupName(String hireGriupName) {
        HireGroupName = hireGriupName;
    }

    public String getDropOffCharge() {
        return DropOffCharge;
    }

    public void setDropOffCharge(String dropOffCharge) {
        DropOffCharge = dropOffCharge;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public ArrayList<SubHireGroups> getSubHireGroup() {
        return SubHireGroup;
    }

    public void setSubHireGroup(JSONArray subHireGroup) {
        //SubHireGroup = subHireGroup;
        SubHireGroup= SubHireGroupsMapper.MapSubHireGroups(subHireGroup);
    }
}
