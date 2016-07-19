package cares.innostark.com.cares;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    TextView totalVehicleCharge;

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
            if (position % 2 == 1) {
                v.setBackgroundColor(Color.parseColor("#EEEEEE"));
            } else {
                v.setBackgroundColor(Color.parseColor("#DCDCDC"));
            }
            final TextView typeTrans = (TextView) v.findViewById(R.id.v_type_transmission);
            final TextView vehicle_info = (TextView) v.findViewById(R.id.vehicle_info);
            final TextView passenger_count= (TextView) v.findViewById(R.id.passenger_count);
            final ImageView vehicleImage = (ImageView) v.findViewById(R.id.vehicleImage);
            final TextView suitcase_count= (TextView) v.findViewById(R.id.suitcase_count);
            final TextView airbag_count= (TextView) v.findViewById(R.id.airbag_count);
            final TextView door_count= (TextView) v.findViewById(R.id.door_count);
            final Button getCharge=(Button) v.findViewById(R.id.getCharge);
            final Button checkout=(Button) v.findViewById(R.id.checkout);
            checkout.setVisibility(View.INVISIBLE);
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


            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(200);              //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            getCharge.startAnimation(anim);

            getCharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {        //setting on click listener on getcharge button to get vehicle charge
                    ((ListView) parent).performItemClick(v, position , 0);
                    //this sends a callback to the previous Activity's OnItemClick on which the listView is being shown and
                    // I make the API call to get charges.
                }
            });

            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position , 0);
                }
            });

        }
        return v;
    }
}
