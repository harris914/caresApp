package cares.innostark.com.cares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.SubHireGroups;

/**
 * Created by bcm on 2/17/2016.
 */
public class VehicleAdapter extends ArrayAdapter {

    ArrayList<SubHireGroups> result;
    Context c;
    LayoutInflater inflater;
    String hireGroupName;
    SubHireGroups s;
    TextView vehicleChargePerDay,totalVehicleCharge;

    public VehicleAdapter(Context context, ArrayList<SubHireGroups> list,String sh_name) {
        super(context,0, list);
        c=context;
        result=list;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hireGroupName=sh_name;
    }
    @Override
    public int getCount() {
        return result.size();
    }

//    @Override
//    public Object getItem(int position) {
//        return null;
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v=convertView;

        s = result.get(position);
        if(s != null) {
            v = inflater.inflate(R.layout.hire_vehicles_detail, null);
            final TextView typeTrans = (TextView) v.findViewById(R.id.v_type_transmission);
            final TextView vehicle_info = (TextView) v.findViewById(R.id.vehicle_info);
            final TextView passenger_count= (TextView) v.findViewById(R.id.passenger_count);
            final ImageView vehicleImage = (ImageView) v.findViewById(R.id.vehicleImage);
            final TextView suitcase_count= (TextView) v.findViewById(R.id.suitcase_count);
            final TextView airbag_count= (TextView) v.findViewById(R.id.airbag_count);
            final TextView door_count= (TextView) v.findViewById(R.id.door_count);
            final ImageButton getCharge=(ImageButton) v.findViewById(R.id.getCharge);
            final Button checkout=(Button) v.findViewById(R.id.checkout);
            checkout.setVisibility(View.INVISIBLE);
            //vehicleChargePerDay= (TextView) v.findViewById(R.id.vehicle_charge_per_day);
            totalVehicleCharge= (TextView) v.findViewById(R.id.total_vehicle_charge);

            if(typeTrans != null)
            {
                typeTrans.setText(hireGroupName+ " | " + s.getVehicleCategory());
            }
            if(vehicleImage != null) {
                Picasso.with(c).load(s.getImageUrl()).into(vehicleImage);
            }
            if(vehicle_info != null){
                vehicle_info.setText(s.getVehicleMake()+ " " + s.getVehicleModel()+ " " +s.getModelYear());
            }
            if(passenger_count != null){
                passenger_count.setText(s.getNoOfPassengers());
            }
            if(suitcase_count != null){
                suitcase_count.setText(s.getTrunckCapacity());
            }
            if(airbag_count != null){
                airbag_count.setText(s.getNoOfAirBags());
            }
            if(door_count != null){
                door_count.setText(s.getNoOfDoors());
            }
            //setting on click listener on getcharge button to get vehicle charge
            getCharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //vehicleCharge.setVisibility(View.VISIBLE);
                    //checkout.setVisibility(View.VISIBLE);
                    //getCharge.setVisibility(View.GONE);
                    ((ListView) parent).performItemClick(v, position, 0);
                    //this sends a callback to the previous Activity's OnItemClick on which the listView is being shown and
                    // I make the API call to get charges.
                }
            });

            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });

        }
        return v;
    }
}
