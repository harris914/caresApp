package cares.innostark.com.cares.Mappings;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.HireGroups;

/**
 * Created by bcm on 2/16/2016.
 */
public class HireGroupsMapper {
    public static ArrayList<HireGroups> MapHireGroups(JSONArray activities)  {
        ArrayList<HireGroups> models = new ArrayList<>();
        for (int i = 0; i < activities.length(); i++) {
            try {
                JSONObject c = activities.getJSONObject(i);
                models.add(MapHireGroup(c));
            }
            catch (JSONException ex) {
                Log.e("Hire Group Mapper",ex.getMessage());
            }
        }
        return models;
    }
    private static HireGroups MapHireGroup(JSONObject c) throws JSONException {

        HireGroups model = new HireGroups();
        JSONArray subhiregroups;
        String hiregroupid = c.getString("HireGroupId");
        String hiregroupcodename= c.getString("HireGroupCodeName");
        String hiregroupname = c.getString("HireGroupName");
        String dropoffcharge = c.getString("DropoffCharge");
        String description = c.getString("Description");
        String title = c.getString("Title");
        String subtitle = c.getString("SubTitle");
        String photourl = c.getString("PhotoUrl");
        subhiregroups=c.getJSONArray("SubHireGroups");

        model.setSubHireGroup(subhiregroups);
        model.setHireGroupId(hiregroupid);
        model.setHireGroupCodeName(hiregroupcodename);
        model.setHireGroupName(hiregroupname);
        model.setDropOffCharge(dropoffcharge);
        model.setDescription(description);
        model.setTitle(title);
        model.setSubTitle(subtitle);
        model.setPhotoUrl(photourl);
        return model;
    }
}
