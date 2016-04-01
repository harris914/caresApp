package cares.innostark.com.cares.Mappings;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.OperationWorkPlaces;

/**
 * Created by bcm on 2/15/2016.
 */
public class OperationWorkPlaceMapper {
    private static final String TAG_LOCATION_NAME = "LocationName";
    private static final String TAG_OPERATION_WORK_PLACE_ID = "OperationWorkplaceId";
    private static final String TAG_CITY_ID = "CityId";
    private static final String TAG_OPERATION_ID = "OperationId";
    private static final String TAG_LONGITUDE = "Longitude";
    private static final String TAG_LATITUDE = "Latitude";
    private static final String TAG_PHONE = "Phone";
    private static final String TAG_ADDRESS= "Address";


    public static ArrayList<OperationWorkPlaces> MapOperationWorkPlaces(JSONArray activities)  {
        ArrayList<OperationWorkPlaces> models = new ArrayList<>();
        for (int i = 0; i < activities.length(); i++) {
            try {
                JSONObject c = activities.getJSONObject(i);
                models.add(MapOperationWorkPlace(c));
            }
            catch (JSONException ex) {
                Log.e("Op Work Place Mapper",ex.getMessage());
            }
        }
        return models;
    }

    private static OperationWorkPlaces MapOperationWorkPlace(JSONObject c) throws JSONException {

        OperationWorkPlaces model = new OperationWorkPlaces();
        String loc_name = c.getString(TAG_LOCATION_NAME);
        String op_work_pl_id= c.getString(TAG_OPERATION_WORK_PLACE_ID);
        String city_id = c.getString(TAG_CITY_ID);
        String op_id = c.getString(TAG_OPERATION_ID);
        String longitude ;
        if(c.isNull(TAG_LONGITUDE)) {            // checking if null is being received or not from the response
            longitude = null;
        } else {
            longitude = c.getString(TAG_LONGITUDE);
        }
        String latitude ;
        if(c.isNull(TAG_LATITUDE)) {            // checking if null is being received or not from the response
            latitude = null;
        } else {
            latitude = c.getString(TAG_LATITUDE);
        }
        String phone;
        if(c.isNull(TAG_PHONE)) {            // checking if null is being received or not from the response
            phone = null;
        } else {
            phone = c.getString(TAG_PHONE);
        }
        String address = c.getString(TAG_ADDRESS);

        model.setPhone(phone);
        model.setAddress(address);
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        model.setLocationName(loc_name);
        model.setCityId(city_id);
        model.setOperationId(op_id);
        model.setOperationWorkplaceId(op_work_pl_id);
        return model;
    }
}
