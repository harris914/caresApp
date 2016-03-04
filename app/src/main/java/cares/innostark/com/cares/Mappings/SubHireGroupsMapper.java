package cares.innostark.com.cares.Mappings;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.SubHireGroups;

/**
 * Created by bcm on 2/17/2016.
 */
public class SubHireGroupsMapper {


    public static ArrayList<SubHireGroups> MapSubHireGroups(JSONArray activities)  {
        ArrayList<SubHireGroups> models = new ArrayList<>();
        for (int i = 0; i < activities.length(); i++) {
            try {
                JSONObject c = activities.getJSONObject(i);
                models.add(MapSubHireGroup(c));
            }
            catch (JSONException ex) {
                Log.e("Sub Hire  Group Mapper",ex.getMessage());
            }
        }
        return models;
    }

    private static SubHireGroups MapSubHireGroup(JSONObject c) throws JSONException {

        SubHireGroups model = new SubHireGroups();
        String hiregroupdetailid = c.getString("HireGroupDetailId");
        String hiregroup= c.getString("HireGroup");
        String hiregroupid = c.getString("HireGroupId");
        String vehiclemake = c.getString("VehicleMake");
        String vehiclemodel = c.getString("VehicleModel");
        String vehiclecategory = c.getString("VehicleCategory");
        String modelyear = c.getString("ModelYear");
        String standardrt = c.getString("StandardRt");
        String tarifftype = c.getString("TariffType");
        String hiregroupcodename = c.getString("HireGroupCodeName");
        String hiregroupname = c.getString("HireGroupName");
        String dropoffcharge = c.getString("DropoffCharge");
        String description = c.getString("Description");
        String vehiclecategoryid = c.getString("VehicleCategoryId");
        String vehiclemakeid = c.getString("VehicleMakeId");
        String vehiclemodelid = c.getString("VehicleModelId");
        String imageurl = c.getString("ImageUrl");
        String imagedescription = c.getString("ImageDescription");
        String noOfdoors = c.getString("NoOfDoors");
        String trunkcapacity = c.getString("TrunckCapacity");
        String noOfpassengers = c.getString("NoOfPassengers");
        String noOfairbags=c.getString("NoOfAirBags");

        model.setHireGroupDetailId(hiregroupdetailid);
        model.setHireGroup(hiregroup);
        model.setHireGroupId(hiregroupid);
        model.setVehicleMake(vehiclemake);
        model.setVehicleModel(vehiclemodel);
        model.setVehicleCategory(vehiclecategory);
        model.setModelYear(modelyear);
        model.setStandardRt(standardrt);
        model.setTariffType(tarifftype);
        model.setHireGroupCodeName(hiregroupcodename);
        model.setHireGroupName(hiregroupname);
        model.setDropoffCharge(dropoffcharge);
        model.setDescription(description);
        model.setVehicleCategoryId(vehiclecategoryid);
        model.setVehicleMakeId(vehiclemakeid);
        model.setVehicleModelId(vehiclemodelid);
        model.setImageUrl(imageurl);
        model.setImageDescription(imagedescription);
        model.setNoOfDoors(noOfdoors);
        model.setTrunckCapacity(trunkcapacity);
        model.setNoOfPassengers(noOfpassengers);
        model.setNoOfAirBags(noOfairbags);
        return model;
    }
}
